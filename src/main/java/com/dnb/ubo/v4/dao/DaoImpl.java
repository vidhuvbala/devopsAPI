package com.dnb.ubo.v4.dao;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;
import org.neo4j.driver.v1.exceptions.Neo4jException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.dnb.ubo.v4.constants.C;
import com.dnb.ubo.v4.constants.Query;
import com.dnb.ubo.v4.exception.ApplicationException;
import com.dnb.ubo.v4.response.JsonBuilder;
import com.dnb.ubo.v4.response.ResponseWrapper;

@Component(value = "apiDaoImplV4")
public class DaoImpl implements Dao {

    @Autowired
    private  Driver driver;
    @Autowired
    private Environment environment;

    /**
     * Method to fetch the owner ship structure/List of Beneficiaries from
     * database
     *
     * @param dunsNumber
     * @param countryCode
     * @return
     * @throws ApplicationException
     */
    @Override
    public ResponseWrapper getBeneficiaryOps(final Map<String,Object> reqDetails, final String operationFlag) throws ApplicationException {

        // Initialize
         ResponseWrapper apiResponseWrapper = null;

         List<Map<String, Object>> linkageValuesList = new ArrayList<>();

        // get the duns number
        final String dunsNbr = (String)reqDetails.get(C.DUNSPADDED);

        try {
            // start the timer
            Instant startDBTime = Instant.now();

            // fetch Nodes and relationship (A->B) from first query
            Map<String, Object> nodesAndRelationshipList = fetchNodeAndRelationship(dunsNbr,(Integer)reqDetails.get(C.DEPTH));

            // Check If invoke further details of Node
            boolean ifInvokeFurtherDetails = checkIfToInvokeFurtherDetails(nodesAndRelationshipList);

            // if to get further details link node details,linkage
            linkageValuesList = fetchLinkage(linkageValuesList, dunsNbr, ifInvokeFurtherDetails);

            // get the end time of database query
            Instant endDBBtime = Instant.now();

            apiResponseWrapper = chkStrucOrList(reqDetails, operationFlag, linkageValuesList, nodesAndRelationshipList);

            // log the time
            apiResponseWrapper.getResponseTimes().put(C.TIME_FOR_GRAPHDB, Duration.between(startDBTime, endDBBtime).toMillis());

            // return
            return apiResponseWrapper;
        } catch (Neo4jException e) {
            if (e.getMessage().contains(C.TRANSTIMEOUT)) {
                throw new ApplicationException(C.UB017, e, "");
            } else {
            throw new ApplicationException(C.UB016, e, "");
            }
        } catch (ApplicationException e) {
            throw new ApplicationException(e.getErrorCode(), e, "");
        } catch (Exception ex) {
            throw new ApplicationException(C.UB019, ex, "");
        }
    }

    /**
     * @param linkageValuesList
     * @param dunsNbr
     * @param ifInvokeFurtherDetails
     * @return List<Map<String, Object>>
     * @throws ApplicationException
     */
    public List<Map<String, Object>> fetchLinkage(List<Map<String, Object>> linkageValuesList, final String dunsNbr, boolean ifInvokeFurtherDetails) throws ApplicationException {
        List<Map<String, Object>> linkageValuesListCopy = linkageValuesList;
        if (ifInvokeFurtherDetails) {

            linkageValuesListCopy = fetchCorporateLinkage(dunsNbr);
        }
        return linkageValuesListCopy;
    }

    /**
     * @param reqDetails
     * @param operationFlag
     * @param linkageValuesList
     * @param nodesAndRelationshipList
     * @return ResponseWrapper
     * @throws ApplicationException
     */
    public ResponseWrapper chkStrucOrList(final Map<String, Object> reqDetails, final String operationFlag, List<Map<String, Object>> linkageValuesList, Map<String, Object> nodesAndRelationshipList)
                    throws ApplicationException {
        ResponseWrapper apiResponseWrapper;
        if (C.BENEF_STRUCTURE.equals(operationFlag)) {
            apiResponseWrapper = JsonBuilder.getBeneficialOwnershipStructureResponse(reqDetails, nodesAndRelationshipList,  linkageValuesList);
        } else {
            apiResponseWrapper = JsonBuilder.getBeneficialOwnershipListResponse(reqDetails, nodesAndRelationshipList,  linkageValuesList);
        }
        return apiResponseWrapper;
    }

    /**
     * @param nodeAndRelationshipList
     * @param session
     * @param inputDunsNumber
     * @param linkageValues
     * @return
     * @throws ApplicationException
     */
    public  static final boolean checkIfToInvokeFurtherDetails(final Map<String, Object> nodeAndRelationshipList) {

        if (!nodeAndRelationshipList.isEmpty()) {
            List nodes = (List) nodeAndRelationshipList.get(C.NODES);
            if (nodes.size() > 1) {
                return true;

            }
        }
        return false;
    }

    /**
     * Method for Fetching Corporate Linkage
     *
     * @param session
     * @param inputDunsNumber
     * @param linkageValues
     * @return
     * @throws ApplicationException
     */
    public  final List<Map<String, Object>> fetchCorporateLinkage(final String inputDunsNumber) throws ApplicationException {

        // inititialize
        final List<Map<String, Object>> linkageValues = new ArrayList<>();

        // put params
        final Map<String, Object> params = new HashMap<>();
        params.put(C.REQUESTED_DUNS_FOR_QUERY, inputDunsNumber);
         try (Session session = driver.session()) {
        // invoke response
             final StatementResult rsForCorporateLinkage = session.readTransaction(new TransactionWork<StatementResult>() {

                 @Override
                 public StatementResult execute(Transaction tx) {
                     StringBuilder query = new StringBuilder(Query.CORPORATE_LINKAGE_QUERY_1);
                     // invoke query
                     return tx.run(query.toString(), params);
                 }
             });

        // populate response
        Record record;
        Map<String, Object> recordValue;
        while (rsForCorporateLinkage.hasNext()) {
            record = rsForCorporateLinkage.next();
            recordValue = record.asMap();
            linkageValues.add(recordValue);
        }

        } catch (Neo4jException e) {
            if (e.getMessage().contains(C.TRANSTIMEOUT)) {
                throw new ApplicationException(C.UB017, e, "");
            } else {
            throw new ApplicationException(C.UB016, e, "");
            }
        }
        return linkageValues;
    }

    /**
    * Method for Fetching Beneficiary
     *
     * @param session
     * @param inputDunsNumber
     * @param results
     * @param responseCalculation
     * @throws ApplicationException
     */
    public  final Map<String, Object> fetchNodeAndRelationship(final String inputDunsNumber,final Integer depth) throws ApplicationException {
        // initialize
        final Map<String, Object> data = new HashMap<>();

        // put parameter
        final Map<String, Object> params = setDUNSForQuery(inputDunsNumber,depth);
        try (Session session = driver.session()) {
            final StatementResult rsForList = session.readTransaction(new TransactionWork<StatementResult>() {

                @Override
                public StatementResult execute(Transaction tx) {
        StringBuilder query = new StringBuilder(Query.BENEF_LIST_QUERY_1);
        // invoke query
                    return tx.run(query.toString(), params);
                }
            });

        // populate response
        if (rsForList.hasNext()) {
            Record record = rsForList.next();
                data.put(C.TARGET_DUNS, record.get(C.TARGET_DUNS).asLong());
                data.put(C.NODES, record.get(C.NODES).asList());
                data.put(C.RELATIONS, record.get(C.RELATIONS).asList());
                data.put(C.DETAILS, record.get(C.FIRMOGRAPHICS).asList());
        }

        } catch (Neo4jException e) {
            if (e.getMessage().contains(C.TRANSTIMEOUT)) {
                throw new ApplicationException(C.UB017, e, "");
            } else {
            throw new ApplicationException(C.UB016, e, "");
            }
        }
        return data;
    }

      /**
     * @param inputDunsNumber
     * @return
     */
    private static final Map<String, Object> setDUNSForQuery(final String inputDunsNumber,final Integer depth) {
        final Map<String, Object> params = new HashMap<>();
        params.put(C.REQUESTED_DUNS_FOR_QUERY, inputDunsNumber);
        if(null != depth ){
            params.put(C.DEPTH, depth);
        }else{
            params.put(C.DEPTH, -1);
        }

        return params;
    }

}