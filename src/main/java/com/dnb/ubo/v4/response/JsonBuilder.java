/**
 * @Name: UBOApiResponseBuilder.java
 * @Class_Description: This is the response framing class for all the UBO API
 *                     operations
 * @author: Cognizant Technology Solutions
 * @Created_On: Sep 5, 2016
 *
 *              Confidential and Proprietary. Copyright (C) 2016 D&B. All Rights
 *              Reserved.
 */
package com.dnb.ubo.v4.response;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.neo4j.driver.internal.InternalNode;
import org.neo4j.driver.v1.types.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dnb.ubo.v4.calculation.ResponseCalculation;
import com.dnb.ubo.v4.constants.C;
import com.dnb.ubo.v4.exception.ApplicationException;
import com.dnb.ubo.v4.jsonbeans.Address;
import com.dnb.ubo.v4.jsonbeans.AddressCountry;
import com.dnb.ubo.v4.jsonbeans.AddressCountry_;
import com.dnb.ubo.v4.jsonbeans.AddressCountry__;
import com.dnb.ubo.v4.jsonbeans.ApiResponse;
import com.dnb.ubo.v4.jsonbeans.Beneficiary;
import com.dnb.ubo.v4.jsonbeans.BeneficiaryAddress;
import com.dnb.ubo.v4.jsonbeans.BeneficiaryType;
import com.dnb.ubo.v4.jsonbeans.BusinessEntityType_;
import com.dnb.ubo.v4.jsonbeans.BusinessEntityType__;
import com.dnb.ubo.v4.jsonbeans.CorporateLinkage;
import com.dnb.ubo.v4.jsonbeans.InquiryDetail;
import com.dnb.ubo.v4.jsonbeans.Member;
import com.dnb.ubo.v4.jsonbeans.MemberAddress;
import com.dnb.ubo.v4.jsonbeans.MemberType;
import com.dnb.ubo.v4.jsonbeans.NodeSummaryByGeography;
import com.dnb.ubo.v4.jsonbeans.Organization;
import com.dnb.ubo.v4.jsonbeans.OwnershipStructure;
import com.dnb.ubo.v4.jsonbeans.OwnershipSummary;
import com.dnb.ubo.v4.jsonbeans.OwnershipUnavailableReason_;
import com.dnb.ubo.v4.jsonbeans.ProductResponse;
import com.dnb.ubo.v4.jsonbeans.Relationship;
import com.dnb.ubo.v4.jsonbeans.TransactionInfo_;
import com.dnb.ubo.v4.jsonbeans.TransactionStatus;
import com.dnb.ubo.v4.request.RequestValidator;

public class JsonBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonBuilder.class);

    private JsonBuilder() {

    }

    /**
     * Method to frame the response and perform the calculations for ownership
     * structure
     *
     * @param reqDetails
     * @param targetValue
     * @param nodeValueList
     * @param relationshipValueList
     * @param beneficiarydbList
     * @param linkageValues
     * @param targetFlag
     * @return
     * @throws ApplicationException
     */

    public static final ResponseWrapper getBeneficialOwnershipStructureResponse(final Map<String, Object> reqDetails, final Map<String, Object> nodesAndRelationshipList, final List<Map<String, Object>> linkageValuesList)
                    throws ApplicationException {

        // Duns & the flag to identify if we get response from DB
        final String methodToInvoke = findMethodToInvoke(nodesAndRelationshipList);
        final String reqDunsNbr = (String) reqDetails.get(C.DUNSNBR);

        // Response Objects Creation
        ApiResponse apiResponse = new ApiResponse();
        InquiryDetail inquiryDetail = new InquiryDetail();
        TransactionInfo_ transactionInfo = new TransactionInfo_();
        ProductResponse productResponse = new ProductResponse();
        TransactionStatus transactionStatus = new TransactionStatus();

        // Setting timer
        long totalCalculationTime = 0L;

        if ((C.OWNERSHIPERR).equals(methodToInvoke)) {
            // No ownership Structure found
            populateTargetPresentNoDataResponse(apiResponse, productResponse, transactionStatus, reqDetails, nodesAndRelationshipList);
        } else if ((C.TARGETABSENT).equals(methodToInvoke)) {
            // No Target Duns Found.
            populateTargetNotPresentResponse(apiResponse, productResponse, transactionStatus, reqDetails);
        } else {
            // Start timer
            Instant startTimeForCalculation = Instant.now();

            // Invoke calculation
            ResponseCalculation responseCalculation = new ResponseCalculation();
            // To check whether max_depth is available in request parameter
            boolean maxDepthAva = false;
            if (null != reqDetails.get(C.DEPTH)) {
                maxDepthAva = true;
            }
            final Map<String, Map<Long, Object>> resultOfCalculation = responseCalculation.calculatebeneficialOwnershipList(nodesAndRelationshipList, maxDepthAva);
            responseCalculation = null;

            // Record time
            Instant endTimeForCalculation = Instant.now();
            totalCalculationTime = Duration.between(startTimeForCalculation, endTimeForCalculation).toMillis();

            // Populate Product Response
            transactionStatus = populateProductResponse(productResponse, resultOfCalculation, linkageValuesList, reqDetails, nodesAndRelationshipList);
        }

        // Populate Inquiry Details
        JsonBuilderHelper.populateInquiryDetail(inquiryDetail, reqDetails, reqDunsNbr);

        // populate Transaction Info
        populateTransationInfo(reqDetails, transactionInfo);

        // Populate Response Wrapper with All details
        return populateResponseWrapper(apiResponse, inquiryDetail, transactionInfo, productResponse, transactionStatus, totalCalculationTime);
    }

    /**
     * @param data
     * @return
     */
    private static final String findMethodToInvoke(final Map<String, Object> data) {

        if (!data.isEmpty()) {
            List nodes = (List) data.get(C.NODES);
            if (nodes.size() == 1) {
                return C.OWNERSHIPERR;
            }
        } else {
            return C.TARGETABSENT;
        }
        return "";
    }

    /**
     * @param dbData
     * @return
     */
    private static final List<String> populateRelations(final Map<String, Object> dbData) {

        // Initialize
        List relationshipValueList = new ArrayList<>();
        final List<String> relations = new ArrayList<>();
        List rel;
        String from, to, relation, relationType;
        int index;
        Long fromId, toId, relationId;
        String share;

        // get relationships
        relationshipValueList = (List) dbData.get(C.RELATIONS);

        // For all relationships, get required elements
        for (int i = C.ZERO; i < relationshipValueList.size(); i++) {
            rel = (List) relationshipValueList.get(i);
            from = (String) rel.get(C.ZERO);
            index = from.lastIndexOf(C.PIPE);
            fromId = new Long(from.substring(C.ZERO, index));
            to = (String) rel.get(C.ONE);
            index = to.lastIndexOf(C.PIPE);
            toId = new Long(to.substring(C.ZERO, index));
            share = String.format("%f", rel.get(C.TWO));
            relationId = (Long) (rel.get(C.THREE));
            relationType = (String) (rel.get(C.FOUR));
            relation = relationId + "-" + fromId + "-" + toId + "-" + share + "-" + relationType;
            relations.add(relation);

        }
        return relations;

    }

    /**
     * @param apiResponse
     * @param productResponse
     * @param transactionStatus
     * @param reqDetails
     */
    private static final void populateTargetNotPresentResponse(final ApiResponse apiResponse, final ProductResponse productResponse, final TransactionStatus transactionStatus, final Map<String, Object> reqDetails) {

        // Set transaction Status
        transactionStatus.setStatusCode(C.UB020);
        transactionStatus.setStatusMessage(C.NOTARGETDUNS);
        transactionStatus.setIsFailure(false);
        apiResponse.setTransactionStatus(transactionStatus);

        // Set Product Response
        final Organization organization = new Organization();
        if (organization.getBeneficiaries().isEmpty()) {
            organization.setBeneficiaries(null);
        }
        productResponse.setOrganization(organization);
        apiResponse.setProductResponse(productResponse);

        // Log error
        LOGGER.error(C.ERROR + C.VERSIONV4 + "  " + C.NOTARGETDUNS + " " + C.DUNS + " " + reqDetails.get(C.DUNSNBR) + " " + C.TRANSACTION_ID + " " + reqDetails.get(C.TRANSACTION_ID));
    }

    /**
     * @param apiResponse
     * @param productResponse
     * @param transactionStatus
     * @param reqDetails
     * @param nodesAndRelationshipList
     */
    private static final void populateTargetPresentNoDataResponse(final ApiResponse apiResponse, final ProductResponse productResponse, final TransactionStatus transactionStatus, final Map<String, Object> reqDetails,
                    Map<String, Object> nodesAndRelationshipList) {

        // populate country code to response
        final Organization organization = new Organization();
        final Long targetDunsValue = (Long) nodesAndRelationshipList.get(C.TARGET_DUNS);
        for (Node targetNode : (List<Node>) nodesAndRelationshipList.get(C.DETAILS)) {
            final Address add = new Address();
            if (targetNode.id() == targetDunsValue) {
                final InternalNode nod = (InternalNode) targetNode;
                final Map<String, Object> nodeMap = nod.asMap();
                if (nodeMap.containsKey(C.ISO_2_COUNTRY_CODE)) {
                    final AddressCountry addCntry = new AddressCountry();
                    addCntry.setIsoAlpha2Code(nodeMap.get(C.ISO_2_COUNTRY_CODE).toString());
                    add.setAddressCountry(addCntry);
                }
                organization.setAddress(add);
             }

        }
        if (organization.getBeneficiaries().isEmpty()) {
            organization.setBeneficiaries(null);
        }
        productResponse.setOrganization(organization);
        apiResponse.setProductResponse(productResponse);

        // Set transaction Status
        transactionStatus.setStatusCode(C.UB002);
        transactionStatus.setStatusMessage(C.NOOWNERSHIPDATA);
        transactionStatus.setIsFailure(false);
        apiResponse.setTransactionStatus(transactionStatus);

        // Log Error
        LOGGER.error(C.ERROR + C.VERSIONV4 + "  " + C.NOOWNERSHIPDATA + " " + C.DUNS + " " + reqDetails.get(C.DUNSNBR) + " " + C.TRANSACTION_ID + " " + reqDetails.get(C.TRANSACTION_ID));

    }

    /**
     * @param apiResponse
     * @param inquiryDetail
     * @param transactionInfo
     * @param productResponse
     * @param transactionStatus
     * @param totalCalculationTime
     * @return
     * @throws ApplicationException
     */
    private static final ResponseWrapper populateResponseWrapper(final ApiResponse apiResponse, final InquiryDetail inquiryDetail, final TransactionInfo_ transactionInfo, final ProductResponse productResponse,
                    final TransactionStatus transactionStatus, final long totalCalculationTime) throws ApplicationException {

        // Set Details to apiresponse
        apiResponse.setInquiryDetail(inquiryDetail);
        apiResponse.setTransactionInfo(transactionInfo);
        apiResponse.setTransactionStatus(transactionStatus);
        apiResponse.setProductResponse(productResponse);

        // Remove unwanted fields
        JsonCleaner.removeEmptyField(apiResponse);

        // Populate Wrapper with APi response & time
        ResponseWrapper apiResponseWrapper = new ResponseWrapper();
        apiResponseWrapper.setApiResponse(apiResponse);
        apiResponseWrapper.getResponseTimes().put(C.TIME_FOR_CALCULATIONS, totalCalculationTime);

        // Return wrapper
        return apiResponseWrapper;
    }

    /**
     * @param reqDetails
     * @param reqDunsNbr
     * @param inquiryDetail
     * @param transactionInfo
     */
    private static final void populateTransationInfo(final Map<String, Object> reqDetails, final TransactionInfo_ transactionInfo) {

        // Set transaction Info
        transactionInfo.setTransactionId(new SimpleDateFormat(C.TRANSACTION_ID_FORMAT).format(new Date()));
        transactionInfo.setTransactionTimestamp(new SimpleDateFormat(C.TIMESTAMP_GET_FORMAT).format(new Date()));
        transactionInfo.setRequestId((String) reqDetails.get(C.TRANSACTION_ID));
        transactionInfo.setRequestTimestamp((String) reqDetails.get(C.TRANSACTION_TIME_STAMP));
    }

    /**
     * Method to populate the Nodes
     *
     * @param internalNodes
     * @param dbResponseListValue
     * @param dunsNbr
     * @return List<Member>
     * @throws ApplicationException
     */
    public static final List<Member> getMemberList(final List<Node> internalNodes, final Map<String, Map<Long, Object>> dbResponseListValue, final String dunsNbr) {
        final List<Member> memberList = new ArrayList<>();
        Member member;
        MemberType membrType;
        MemberAddress memberAddress;
        InternalNode nod;
        ArrayList<String> nodeTypes;
        Map<String, Object> nodeMap;

        // For all Nodes
        for (Node node : internalNodes) {
            member = new Member();
            membrType = new MemberType();
            memberAddress = new MemberAddress();
            nod = (InternalNode) node;
            nodeTypes = (ArrayList) nod.labels();
            nodeMap = nod.asMap();

            if (null != nodeMap) {

                // Set member Id
                member.setMemberId(Integer.parseInt(String.valueOf(node.id())));

                // Set Label as organization or individual
                if ((C.ORGANISATION).equalsIgnoreCase(nodeTypes.get(0))) {
                    membrType.setCode(C.MEBR_TYPE_CODE_BUSINESS);
                } else if ((C.INDIVIDUAL).equalsIgnoreCase(nodeTypes.get(0))) {
                    membrType.setCode(C.MEBR_TYPE_CODE_INDIVIDUAL);
                }
                member.setMemberType(membrType);

                // setting the member details
                final AddressCountry_ addCountry = new AddressCountry_();
                final BusinessEntityType_ businessType = new BusinessEntityType_();

                JsonBuilderHelper.populateMemberPersonalDetails(member, nodeMap);
                // Set the address details of Member
                populateMemberAddressDetails(memberAddress, addCountry, nodeMap);
                member.setMemberAddress(memberAddress);
                populateMemberBusinessDetails(member, businessType, nodeMap);
                JsonBuilderHelper.setMemberCBOCode(nodeMap, member);
                if(nodeMap.containsKey(C.CONTROL_TYPE) ){
                    JsonBuilderHelper.setMemberControlType(nodeMap, member);

                }

                if (null != member.getDuns() && member.getDuns().equals(dunsNbr)) {
                    member.setIsBeneficiary(false);
                    member.setDepth(0);
                }
                // Populate calculation details
                setAdditionalMemberDetails(member, dbResponseListValue, node);
            }
            // Add this member to the member list
            memberList.add(member);
        }
        return memberList;
    }

    /**
     * Method to populate the Relations
     *
     * @param nodesAndRelationshipList
     * @return
     * @throws ApplicationException
     */
    public static final List<Relationship> getRelationshipList(final Map<String, Object> nodesAndRelationshipList) {

        // Initialize
        final List<Relationship> relationshipList = new ArrayList<>();
        String[] relationdtls;
        Relationship jsonrelation;

        final List<String> relationList = populateRelations(nodesAndRelationshipList);

        for (String relations : relationList) {

            // get relationship id
            relationdtls = relations.split("-");

            // Set to relationship
            jsonrelation = new Relationship();
            jsonrelation.setRelationshipId(Integer.valueOf(relationdtls[C.ZERO].toString()));
            if ("S".equals(relationdtls[C.FOUR])) {
                jsonrelation.setRelationshipType(C.SHARES_HELD_BY);
            } else {
                jsonrelation.setRelationshipType(C.MAJORITY);
            }

            jsonrelation.setFromMember(Integer.valueOf(relationdtls[C.ONE].trim()));
            jsonrelation.setToMember(Integer.valueOf(relationdtls[C.TWO].trim()));

            // Set share percentage after conversion
            if (null != relationdtls[C.THREE] && !C.NULL_STRING.equals(relationdtls[C.THREE])) {
                jsonrelation.setSharePercentage(Double.parseDouble(relationdtls[C.THREE].trim()));
            }

            // Add this to the list
            relationshipList.add(jsonrelation);
        }

        return relationshipList;
    }

    /**
     * Method to frame the response and perform the calculations for beneficiary
     * list
     *
     * @param reqDetails
     * @param nodesAndRelationshipList
     * @param targetValue
     * @param nodeDetailsSet
     * @return
     * @throws ApplicationException
     */
    public static final ResponseWrapper getBeneficialOwnershipListResponse(final Map<String, Object> reqDetails, final Map<String, Object> nodesAndRelationshipList, final List<Map<String, Object>> linkageValuesList)
                    throws ApplicationException {

        // Duns & the flag to identify if we get response from DB
        final String methodToInvoke = findMethodToInvoke(nodesAndRelationshipList);
        final String reqDunsNbr = (String) reqDetails.get(C.DUNSNBR);

        // Response population Details
        final ApiResponse wrapper = new ApiResponse();
        ResponseCalculation responseCalculation = new ResponseCalculation();
        final InquiryDetail inquiryDetail = new InquiryDetail();
        final TransactionInfo_ transactionInfo = new TransactionInfo_();
        TransactionStatus transactionStatus = new TransactionStatus();
        final ProductResponse productResponse = new ProductResponse();

        // Calculation Details
        long totalCalculationTime = 0L;

        if ((C.OWNERSHIPERR).equals(methodToInvoke)) {
            // No ownership structure found
            populateTargetPresentNoDataResponse(wrapper, productResponse, transactionStatus, reqDetails, nodesAndRelationshipList);

        } else if ((C.TARGETABSENT).equals(methodToInvoke)) {
            // Target Not found
            populateTargetNotPresentResponse(wrapper, productResponse, transactionStatus, reqDetails);

        } else {

            // start timer
            Instant startTimeForCalculation = Instant.now();
            // To check whether max_depth is available in request parameter
            boolean maxDepthAva = false;
            if (null != reqDetails.get(C.DEPTH)) {
                maxDepthAva = true;
            }
            // Invoke Calculation
            final Map<String, Map<Long, Object>> calculationResponse = responseCalculation.calculatebeneficialOwnershipList(nodesAndRelationshipList, maxDepthAva);
            responseCalculation = null;

            // end timer
            Instant endTimeForCalculation = Instant.now();
            totalCalculationTime = Duration.between(startTimeForCalculation, endTimeForCalculation).toMillis();

            // Populate transaction status
            transactionStatus = populateListResponse(calculationResponse, linkageValuesList, productResponse, reqDetails, nodesAndRelationshipList);
        }

        // populate Inquiry details
        JsonBuilderHelper.populateInquiryDetail(inquiryDetail, reqDetails, reqDunsNbr);

        // populate Transaction Info
        populateTransationInfo(reqDetails, transactionInfo);

        // Populate Response
        return populateResponseWrapper(wrapper, inquiryDetail, transactionInfo, productResponse, transactionStatus, totalCalculationTime);
    }

    /**
     * Method to populate the Beneficiary
     *
     * @param dbResponseListValue
     * @param internalNodes
     * @param dunsNbr
     * @return
     * @throws ApplicationException
     */
    public static final List<Beneficiary> populateBeneficiary(final Map<String, Map<Long, Object>> dbResponseListValue, final List<Node> internalNodes, final String dunsNbr) throws ApplicationException {

        // Initialize
        final List<Beneficiary> beneficiaryList = new ArrayList<>();
        Beneficiary beneficiary;
        BeneficiaryType beneficiaryType;
        BeneficiaryAddress beneficiaryAddress;
        InternalNode nod;
        ArrayList<String> nodeTypes;
        Map<String, Object> nodeMap;

        for (Node node : internalNodes) {

            beneficiary = new Beneficiary();
            beneficiaryType = new BeneficiaryType();
            beneficiaryAddress = new BeneficiaryAddress();
            nod = (InternalNode) node;
            nodeTypes = (ArrayList) nod.labels();
            nodeMap = nod.asMap();

            if (null != nodeMap) {
                beneficiary.setMemberId(Integer.parseInt(String.valueOf(node.id())));
                // Beneficiary type
                populateBeneficiaryType(nodeTypes, beneficiaryType);
                beneficiary.setBeneficiaryType(beneficiaryType);

                // Other beneficiary Details
                setBeneficiaryDetails(nodeMap, beneficiary, dbResponseListValue, node, beneficiaryAddress);

            }

            // Beneficiary Address
            beneficiary.setBeneficiaryAddress(beneficiaryAddress);
            beneficiaryList.add(beneficiary);
        }
        // Removing Target Duns from Beneficiary List
        JsonBuilderSupport.removeTargetDuns(beneficiaryList, dunsNbr);
        return beneficiaryList;
    }

    /**
     * Method to populate the Response
     *
     * @param e
     * @return
     */
    public static final ApiResponse getExceptionBeneficiariesOperation(final ApplicationException e) {

        final ApiResponse apiResponse = new ApiResponse();
        final TransactionStatus transactionStatus = new TransactionStatus();
        final String errorCode = e.getErrorCode();
        final String responseType = e.getExResponseType();
        String errorValue = "";

        if (null != responseType) {
            errorValue = JsonBuilderHelper.getErrorvalue(responseType, errorValue);
        }
        // Unprocessable Entity
        exceptionForUnprocessableEntity(transactionStatus, errorCode, responseType, errorValue);

        if (C.UB014.equals(errorCode)) {
            transactionStatus.setStatusMessage(C.REQERROR);
            transactionStatus.setStatusCode(C.UB014);
            transactionStatus.setIsFailure(true);
        } else if (C.UB017.equals(errorCode)) {
            transactionStatus.setStatusMessage(C.GRAPHTIMEOUT);
            transactionStatus.setStatusCode(C.UB017);
            transactionStatus.setIsFailure(true);
        } else if (null != responseType && responseType.contains(C.XMLAPP)) {
            transactionStatus.setStatusMessage(C.MEDIATYPEERR);
            transactionStatus.setStatusCode(C.UB011);
            transactionStatus.setIsFailure(true);
        } else if (null != responseType && responseType.contains(C.INVALID_REQUEST_METHOD)) {
            transactionStatus.setStatusMessage(C.INVALIDHTTPMETHOD);
            transactionStatus.setStatusCode(C.UB008);
            transactionStatus.setIsFailure(true);
        } else if (C.UB016.equals(errorCode)) {
            transactionStatus.setStatusMessage(C.GRAPHDBERR);
            transactionStatus.setStatusCode(C.UB016);
            transactionStatus.setIsFailure(true);
        } else if (StringUtils.isEmpty(transactionStatus.getStatusCode())) {
            transactionStatus.setStatusMessage(C.INTERNALERROR);
            transactionStatus.setStatusCode(C.UB019);
            transactionStatus.setIsFailure(true);
        }

        // set the status
        apiResponse.setTransactionStatus(transactionStatus);
        return apiResponse;
    }

    /**
     * @param transactionStatus
     * @param code
     * @param text
     * @param value
     */
    private static final void exceptionForUnprocessableEntity(final TransactionStatus transactionStatus, final String code, final String text, final String value) {
        if (null != text && (text.contains(C.PRODUCT_CODE) || (text.contains(C.DUNS)) || (text.contains(C.TRANSACTION_ID)))) {

            transactionStatus.setStatusMessage(C.INVALIDMISSINGPARAM + " " + value);
            transactionStatus.setStatusCode(C.UB012);
            transactionStatus.setIsFailure(true);
        } else if ((null != text && text.contains(C.INVALID_DATAVALUE)) || (null != text && text.contains(C.INVALID_DATE))) {

            transactionStatus.setStatusMessage(C.INVALIDMISSINGPARAM + " " + value);
            transactionStatus.setStatusCode(C.UB012);
            transactionStatus.setIsFailure(true);
        } else if (C.UB012.equals(code)) {
            transactionStatus.setStatusMessage(C.INVALIDMISSINGPARAM + " " + value);
            transactionStatus.setStatusCode(C.UB012);
            transactionStatus.setIsFailure(true);
        }
        exceptionForFilters(transactionStatus, text, code);
    }

    /**
     * @param transactionStatus
     * @param text
     * @param code
     */
    private static final void exceptionForFilters(final TransactionStatus transactionStatus, final String text, final String code) {
        if (null != text && text.contains(C.INVALID_BOOLEAN_VALUE)) {
            transactionStatus.setStatusMessage(C.INVALIDMISSINGPARAM + " includeUnknownHoldings/excludeUndisclosedHoldings");
            transactionStatus.setStatusCode(C.UB012);
            transactionStatus.setIsFailure(true);
        } else if (null != text && text.contains(C.DOUBLE)) {
            transactionStatus.setStatusMessage(C.INVALIDMISSINGPARAM + " ownershipPercentage");
            transactionStatus.setStatusCode(C.UB012);
            transactionStatus.setIsFailure(true);
        } else if (null != text && text.contains(C.INTEGER)) {
            transactionStatus.setStatusMessage(C.INVALIDMISSINGPARAM + " depth");
            transactionStatus.setStatusCode(C.UB012);
            transactionStatus.setIsFailure(true);
        } else if (C.UB022.equals(code)) {
            transactionStatus.setStatusMessage(C.INVALIDFILTER);
            transactionStatus.setStatusCode(C.UB022);
            transactionStatus.setIsFailure(true);
        }
    }

    /**
     * Method to populate calculation % of the Member details
     *
     * @param member
     * @param dbResponseListValue
     * @param node
     */
    public static final void setAdditionalMemberDetails(final Member member, final Map<String, Map<Long, Object>> dbResponseListValue, final Node node) {
        final Map<Long, Object> indirect = dbResponseListValue.get(C.INDIRECT);
        final Map<Long, Object> direct = dbResponseListValue.get(C.DIRECT);
        final Map<Long, Object> beneficialSum = dbResponseListValue.get(C.BENEFECIALSUM);
        final Map<Long, Object> isBeneficiary = dbResponseListValue.get(C.ISBENFECIARY);
        final Map<Long, Object> depth = dbResponseListValue.get(C.DEPTH);
        final Map<Long, Object> undisclosed = dbResponseListValue.get(C.UNDISCLOSED);
        final Map<Long, Object> orgBen = dbResponseListValue.get(C.ORGBEN);

        if (direct.containsKey(node.id())) {
            member.setDirectOwnershipPercentage((Double) direct.get(node.id()));
        }
        if (indirect.containsKey(node.id())) {
            member.setIndirectOwnershipPercentage((Double) indirect.get(node.id()));
        }
        member.setIsBeneficiary(false);
        if (isBeneficiary.containsKey(node.id())) {
            member.setIsBeneficiary(true);
        }
        if (beneficialSum.containsKey(node.id()) && member.getIsBeneficiary()) {
            member.setBeneficialOwnershipPercentage((Double) beneficialSum.get(node.id()));
        }
        if (depth.containsKey(node.id())) {
            member.setDepth((Integer) depth.get(node.id()));
        }
        benPerForCBOMember(member, node, beneficialSum, orgBen);

        // Setting beneficial % as null for fully undisclosed cases
        if (undisclosed.containsKey(node.id()) && null != member.getBeneficialOwnershipPercentage() && C.ZEROPER.equals(member.getBeneficialOwnershipPercentage())) {
            member.setBeneficialOwnershipPercentage(null);
        }
    }

    /**
     * @param member
     * @param node
     * @param beneficialSum
     */
    public static final void benPerForCBOMember(final Member member, final Node node, final Map<Long, Object> beneficialSum, final Map<Long, Object> orgBen) {

        // Key set
        final Set<Long> keySet = beneficialSum.keySet();

        // Loop through the key set
        for (Long key : keySet) {
            if (key < C.ZERO) {
                Long keyAbs = Math.abs(key);
                if (keyAbs.equals(node.id()) && null != member.getOwnershipUnavailableReason() && !member.getOwnershipUnavailableReason().isEmpty()) {
                    member.setBeneficialOwnershipPercentage((Double) beneficialSum.get(key));
                    member.setIsBeneficiary(true);
                    // Added for depth being passed as request parameter
                } else if (keyAbs.equals(node.id()) && null != orgBen && !orgBen.isEmpty() && orgBen.containsKey(node.id())) {
                    member.setBeneficialOwnershipPercentage((Double) beneficialSum.get(key));
                }
            }
        }
    }

    /**
     * To populate Member address details in structure
     *
     * @param memberAddress
     * @param addCountry
     * @param nodeMap
     */
    private static final void populateMemberAddressDetails(final MemberAddress memberAddress, final AddressCountry_ addCountry, final Map<String, Object> nodeMap) {
        if (nodeMap.containsKey(C.ADDRESS_LINE_1)) {
            memberAddress.setAddressLine1(nodeMap.get(C.ADDRESS_LINE_1).toString());
        }
        if (nodeMap.containsKey(C.ADDRESS_LINE_2)) {
            memberAddress.setAddressLine2(nodeMap.get(C.ADDRESS_LINE_2).toString());
        }
        if (nodeMap.containsKey(C.ADDRESS_LINE_3)) {
            memberAddress.setAddressLine3(nodeMap.get(C.ADDRESS_LINE_3).toString());
        }
        if (nodeMap.containsKey(C.PRIMARY_TOWN)) {
            memberAddress.setPrimaryTown(nodeMap.get(C.PRIMARY_TOWN).toString());
        }
        if (nodeMap.containsKey(C.POSTAL_CODE)) {
            memberAddress.setZipOrPostalCode(nodeMap.get(C.POSTAL_CODE).toString());
        }
        if (nodeMap.containsKey(C.PROVINCE_STATE)) {
            memberAddress.setProvinceOrState(nodeMap.get(C.PROVINCE_STATE).toString());
        }
        if (nodeMap.containsKey(C.ISO_2_COUNTRY_CODE)) {
            addCountry.setIsoAlpha2Code(nodeMap.get(C.ISO_2_COUNTRY_CODE).toString());
            memberAddress.setAddressCountry(addCountry);
        }
        if (nodeMap.containsKey(C.COUNTY)) {
            memberAddress.setCounty(nodeMap.get(C.COUNTY).toString());
        }
    }

    /**
     * To populate Member OutOfBusiness details in structure
     *
     * @param member
     * @param businessType
     * @param nodeMap
     */
    private static final void populateMemberBusinessDetails(final Member member, final BusinessEntityType_ businessType, final Map<String, Object> nodeMap) {
        if (null != nodeMap.get(C.OUT_OF_BUSINESS) && nodeMap.containsKey(C.OUT_OF_BUSINESS)) {
            if ((C.OUT_OF_BUSINESS_TRUE_CODE1).equals(nodeMap.get(C.OUT_OF_BUSINESS).toString()) || (C.OUT_OF_BUSINESS_TRUE_CODE2).equals(nodeMap.get(C.OUT_OF_BUSINESS).toString())
                            || (C.OUT_OF_BUSINESS_TRUE_CODE3).equals(nodeMap.get(C.OUT_OF_BUSINESS).toString())) {
                member.setIsOutofBusiness(true);
            } else {
                member.setIsOutofBusiness(false);
            }
        }
        if (nodeMap.containsKey(C.BUSINESS_ENTITY_TYPE)) {
            businessType.setCode(Integer.parseInt(nodeMap.get(C.BUSINESS_ENTITY_TYPE).toString()));
            member.setBusinessEntityType(businessType);
        }
    }

    /**
     * Method to populate Beneficiary Details
     *
     * @param beneficiary
     * @param dbResponseListValue
     * @param node
     */
    private static final void setAdditionalBeneficiaryDetails(final Beneficiary beneficiary, final Map<String, Map<Long, Object>> dbResponseListValue, final Node node) {
        final Map<Long, Object> indirect = dbResponseListValue.get(C.INDIRECT);
        final Map<Long, Object> direct = dbResponseListValue.get(C.DIRECT);
        final Map<Long, Object> beneficialSum = dbResponseListValue.get(C.BENEFECIALSUM);
        final Map<Long, Object> isBeneficiary = dbResponseListValue.get(C.ISBENFECIARY);
        final Map<Long, Object> depth = dbResponseListValue.get(C.DEPTH);
        final Map<Long, Object> isUndisclosed = dbResponseListValue.get(C.UNDISCLOSED);
        final Map<Long, Object> orgBen = dbResponseListValue.get(C.ORGBEN);

        if (direct.containsKey(node.id())) {
            beneficiary.setDirectOwnershipPercentage((Double) direct.get(node.id()));
        }
        if (indirect.containsKey(node.id())) {
            beneficiary.setIndirectOwnershipPercentage((Double) indirect.get(node.id()));
        }
        beneficiary.setIsBeneficiary(false);
        if (isBeneficiary.containsKey(node.id())) {
            beneficiary.setIsBeneficiary(true);
        }
        if (depth.containsKey(node.id())) {
            beneficiary.setDepth((Integer) depth.get(node.id()));
        }
        if (isUndisclosed.containsKey(node.id())) {
            beneficiary.setIsUndisclosed(true);
        }
        setBenPerForBenefeciary(beneficiary, node, beneficialSum, orgBen);

        // Setting beneficial % as null for fully undisclosed cases
        if (null != beneficiary.getIsUndisclosed() && null != beneficiary.getBeneficialOwnershipPercentage() && C.ZEROPER.equals(beneficiary.getBeneficialOwnershipPercentage())) {
            beneficiary.setBeneficialOwnershipPercentage(null);
        }
    }

    /**
     * @param beneficiary
     * @param node
     * @param beneficialSum
     */
    public static final void setBenPerForBenefeciary(final Beneficiary beneficiary, final Node node, final Map<Long, Object> beneficialSum, final Map<Long, Object> orgBen) {
        if (beneficialSum.containsKey(node.id()) && beneficiary.getIsBeneficiary()) {
            beneficiary.setBeneficialOwnershipPercentage((Double) beneficialSum.get(node.id()));
        }
        final Set<Long> keySet = beneficialSum.keySet();
        for (Long key : keySet) {
            if (key < C.ZERO) {
                setBenForCBO(beneficiary, node, beneficialSum, orgBen, key);
            }
        }
    }

    /**
     * Splitting the function setBenPerForBenefeciary due to sonar violation
     *
     * @param Beneficiary
     *            beneficiary
     * @param Node
     *            node
     * @param Map
     *            <Long, Object> beneficialSum
     * @param Map
     *            <Long, Object> orgBen
     * @param Long
     *            key
     */
    public static void setBenForCBO(final Beneficiary beneficiary, final Node node, final Map<Long, Object> beneficialSum, final Map<Long, Object> orgBen, Long key) {
        Long keyAbs = Math.abs(key);
        if (keyAbs.equals(node.id()) && null != beneficiary.getOwnershipUnavailableReason() && !beneficiary.getOwnershipUnavailableReason().isEmpty()) {
            beneficiary.setBeneficialOwnershipPercentage((Double) beneficialSum.get(key));
            beneficiary.setIsBeneficiary(true);
            // Added for depth being passed as request parameter
        } else if (keyAbs.equals(node.id()) && null != orgBen && !orgBen.isEmpty() && orgBen.containsKey(node.id())) {
            beneficiary.setBeneficialOwnershipPercentage((Double) beneficialSum.get(key));
        }
    }

    /**
     * @param valueToCheck
     * @return
     */
    public static final boolean nullcheck(final String valueToCheck) {
        return null != valueToCheck && !(C.NULL_STRING.equals(valueToCheck));
    }

    /**
     * Method to populate Beneficiary Details
     *
     * @param nodeMap
     * @param beneficiary
     * @param dbResponseListValue
     * @param node
     * @param beneficiaryAddress
     * @throws ApplicationException
     */
    private static final void setBeneficiaryDetails(final Map<String, Object> nodeMap, final Beneficiary beneficiary, final Map<String, Map<Long, Object>> dbResponseListValue, final Node node,
                    final BeneficiaryAddress beneficiaryAddress) throws ApplicationException {

        final AddressCountry__ beneficiaryAddressCountry = new AddressCountry__();
        final BusinessEntityType__ businessEntityType = new BusinessEntityType__();

        try {
            populateBeneficiaryPersonalDetails(beneficiary, nodeMap);
            populateBeneficiaryBusinessDetails(beneficiary, businessEntityType, nodeMap);
            populateBenificaryAddressDetails(beneficiaryAddress, beneficiaryAddressCountry, nodeMap);
            setBenefecialCBOCode(nodeMap, beneficiary);
            JsonBuilderHelper.setBenefecialControlType(nodeMap, beneficiary);

            setAdditionalBeneficiaryDetails(beneficiary, dbResponseListValue, node);

        } catch (Exception ex) {
            throw new ApplicationException(C.UB019, ex, "");
        }
    }

    /**
     * To set ownership unavailable reason code to Beneficiaries
     *
     * @param Map
     *            <Integer, String>cboHashCodeMap
     * @param Map
     *            .Entry<String, Object>entry
     * @param Beneficiary
     *            beneficiary
     */
    private static final void setBenefecialCBOCode(final Map<String, Object> nodeMap, final Beneficiary beneficiary) {
        if (nodeMap.containsKey(C.OWNERSHIP_UNAVAILABLE_REASON_CODE)) {
            List<OwnershipUnavailableReason_> unavilableLst = new ArrayList<OwnershipUnavailableReason_>();
            final String unavailableReasonCode = nodeMap.get(C.OWNERSHIP_UNAVAILABLE_REASON_CODE).toString();
            final String unavailableReasonCodeStr = unavailableReasonCode.replace(C.OPEN_SQUARE_BRACKET, "").replace(C.CLOSE_SQUARE_BRACKET, "");
            final String[] unavailableReasonCodeAray = unavailableReasonCodeStr.split(C.COMMA_SEPERATOR);
            Integer cboCode;
            OwnershipUnavailableReason_ ownershipUnavailableReason;

            for (String reasonCode : unavailableReasonCodeAray) {
                cboCode = Integer.parseInt(reasonCode.trim());
                ownershipUnavailableReason = new OwnershipUnavailableReason_();
                ownershipUnavailableReason.setCode(cboCode);
                if (null != ownershipUnavailableReason.getCode()) {
                    unavilableLst.add(ownershipUnavailableReason);
                }
                beneficiary.setOwnershipUnavailableReason(unavilableLst);
            }
        }
    }

    /**
     * Method to populate Beneficiary personal Details
     *
     * @param beneficiary
     * @param mandatoryItem
     * @param entry
     */
    private static final void populateBeneficiaryPersonalDetails(final Beneficiary beneficiary, final Map<String, Object> nodeMap) {
        if (nodeMap.containsKey(C.NAME)) {
            beneficiary.setName((nodeMap.get(C.NAME)).toString());
        }
        if (nodeMap.containsKey(C.DUNS_NUMBER)) {
            beneficiary.setDuns((nodeMap.get(C.DUNS_NUMBER)).toString());
        }
        if (nodeMap.containsKey(C.PERSON_ID)) {
            beneficiary.setPersonId((nodeMap.get(C.PERSON_ID)).toString());
        }
        if (nodeMap.containsKey(C.NATIONALITY_FOR_OP)) {
            beneficiary.setNationality((nodeMap.get(C.NATIONALITY_FOR_OP)).toString());
        }
        if (nodeMap.containsKey(C.BIRTH_DATE) && null != (nodeMap.get(C.BIRTH_DATE))) {
            beneficiary.setDob(RequestValidator.parseDate((nodeMap.get(C.BIRTH_DATE)).toString()));
        }
    }

    /**
     * Method to populate Beneficiary Address Details
     *
     * @param beneficiaryAddress
     * @param beneficiaryAddressCountry
     * @param entry
     */
    private static final void populateBenificaryAddressDetails(final BeneficiaryAddress beneficiaryAddress, final AddressCountry__ beneficiaryAddressCountry, final Map<String, Object> nodeMap) {
        if (nodeMap.containsKey(C.ADDRESS_LINE_1)) {
            beneficiaryAddress.setAddressLine1(nodeMap.get(C.ADDRESS_LINE_1).toString());
        }
        if (nodeMap.containsKey(C.ADDRESS_LINE_2)) {
            beneficiaryAddress.setAddressLine2(nodeMap.get(C.ADDRESS_LINE_2).toString());
        }
        if (nodeMap.containsKey(C.ADDRESS_LINE_3)) {
            beneficiaryAddress.setAddressLine3(nodeMap.get(C.ADDRESS_LINE_3).toString());
        }
        if (nodeMap.containsKey(C.PRIMARY_TOWN)) {
            beneficiaryAddress.setPrimaryTown(nodeMap.get(C.PRIMARY_TOWN).toString());
        }
        if (nodeMap.containsKey(C.POSTAL_CODE)) {
            beneficiaryAddress.setZipOrPostalCode(nodeMap.get(C.POSTAL_CODE).toString());
        }
        if (nodeMap.containsKey(C.PROVINCE_STATE)) {
            beneficiaryAddress.setProvinceOrState(nodeMap.get(C.PROVINCE_STATE).toString());
        }
        if (nodeMap.containsKey(C.ISO_2_COUNTRY_CODE)) {
            beneficiaryAddressCountry.setIsoAlpha2Code(nodeMap.get(C.ISO_2_COUNTRY_CODE).toString());
            beneficiaryAddress.setAddressCountry(beneficiaryAddressCountry);
        }
        if (nodeMap.containsKey(C.COUNTY)) {
            beneficiaryAddress.setCounty(nodeMap.get(C.COUNTY).toString());
        }
    }

    /**
     * Method to populate Beneficiary Out Of Business Details
     *
     * @param beneficiary
     * @param businessEntityType
     * @param entry
     */
    private static final void populateBeneficiaryBusinessDetails(final Beneficiary beneficiary, final BusinessEntityType__ businessEntityType, final Map<String, Object> nodeMap) {
        if (nodeMap.containsKey(C.BUSINESS_ENTITY_TYPE)) {
            businessEntityType.setCode(Integer.parseInt(nodeMap.get(C.BUSINESS_ENTITY_TYPE).toString()));
            beneficiary.setBusinessEntityType(businessEntityType);
        }
        if (nodeMap.containsKey(C.OUT_OF_BUSINESS) && null != nodeMap.get(C.OUT_OF_BUSINESS)) {
            if ((C.OUT_OF_BUSINESS_TRUE_CODE1).equals(nodeMap.get(C.OUT_OF_BUSINESS).toString()) || (C.OUT_OF_BUSINESS_TRUE_CODE2).equals(nodeMap.get(C.OUT_OF_BUSINESS).toString())
                            || (C.OUT_OF_BUSINESS_TRUE_CODE3).equals(nodeMap.get(C.OUT_OF_BUSINESS).toString())) {
                beneficiary.setIsOutofBusiness(true);
            } else {
                beneficiary.setIsOutofBusiness(false);
            }
        }
    }

    /**
     * To populate Structure response
     *
     * @param relationshipValueList
     * @param nodeValueList
     * @param calculationResponse
     * @param organization
     * @param targetValueList
     * @param linkageValues
     * @param productResponse
     * @param dunsNbr
     * @throws ApplicationException
     */
    public static final TransactionStatus populateProductResponse(final ProductResponse productResponse, final Map<String, Map<Long, Object>> calculationResponse, final List<Map<String, Object>> linkageValues,
                    final Map<String, Object> reqDetails, final Map<String, Object> nodesAndRelationshipList) {

        // Fetch NodeList
        final List<Member> memberList = getMemberList((List) nodesAndRelationshipList.get(C.DETAILS), calculationResponse, (String) reqDetails.get(C.DUNSPADDED));

        // Fetch relationship list
        final List<Relationship> relationshipList = getRelationshipList(nodesAndRelationshipList);

        // Fetch ownership Summary
        OwnershipSummary ownershipSummary = JsonBuilderSupport.getOwnershipSummary(memberList, relationshipList.size(), (String) reqDetails.get(C.DUNSPADDED));

        // Fetch Target Duns and populate the details
        final Organization organization = new Organization();
        final Long targetDunsValue = (Long) nodesAndRelationshipList.get(C.TARGET_DUNS);
        if (null != targetDunsValue) {
            for (Node targetNode : (List<Node>) (nodesAndRelationshipList.get(C.DETAILS))) {
                if (targetNode.id() == targetDunsValue) {
                    JsonBuilderHelper.populateTargetDunsDetails(targetNode, organization);
                }
            }
        }

        // Filtering for nodes
        JsonBuilderHelper.filterOperationforMember(memberList, (String) reqDetails.get(C.DUNSPADDED), (String) reqDetails.get(C.OWNERSHIP_TYPE), (Double) reqDetails.get(C.OWNERSHIP_PERCENTAGE));
        // Filtering for relationships
        JsonBuilderHelper.filterOperationforRelationship(memberList, relationshipList);

        // To Round off Percentages
        JsonBuilderHelper.setRoundOffPctMember(memberList);

        // Populate Ownership details to ownnershipStructure and then to
        // organization
        final OwnershipStructure ownershipStucture = new OwnershipStructure();
        ownershipStucture.setMembers(memberList);
        ownershipStucture.setRelationships(relationshipList);
        organization.setOwnershipStructure(ownershipStucture);

        // Populate Corporate Linkage
        CorporateLinkage corporateLinkage = new CorporateLinkage();
        if (!linkageValues.isEmpty()) {
            corporateLinkage = JsonBuilderHelper.getCorporateLinkage(linkageValues);
        }
        organization.setCorporateLinkage(corporateLinkage);
        if (organization.getBeneficiaries().isEmpty()) {
            organization.setBeneficiaries(null);
        }

        // Set details to Product response
        productResponse.setOrganization(organization);
        productResponse.getOrganization().setOwnershipSummary(ownershipSummary);

        // Set transaction status
        final TransactionStatus transactionStatus = new TransactionStatus();
        transactionStatus.setStatusCode(C.UB001);
        transactionStatus.setStatusMessage(C.SUCCESS);
        transactionStatus.setIsFailure(false);

        if (null != reqDetails.get(C.DEPTH)) {

            transactionStatus.setStatusCode(C.UB003);
            transactionStatus.setStatusMessage(C.MSGFORDEPTH);
            transactionStatus.setIsFailure(false);
        }

        // Return Transaction status
        return transactionStatus;

    }

    /**
     * To populate Beneficiary list
     *
     * @param nodeValueList
     * @param calculationResponseMap
     * @param organization
     * @param linkageValuesList
     * @param productResponse
     * @param targetDunsValue
     * @param reqDetails
     * @throws ApplicationException
     */
    public static final TransactionStatus populateListResponse(final Map<String, Map<Long, Object>> calculationResponseMap, final List<Map<String, Object>> linkageValuesList, final ProductResponse productResponse,
                    final Map<String, Object> reqDetails, final Map<String, Object> nodesAndRelationshipList

    ) throws ApplicationException {

        // set the beneficiary list
        List<Beneficiary> beneficiaryList = new ArrayList<>();
        beneficiaryList = populateBeneficiary(calculationResponseMap, (List) nodesAndRelationshipList.get(C.DETAILS), (String) reqDetails.get(C.DUNSPADDED));

        // Fetching Target DUNS
        final Organization organization = new Organization();
        final Long targetDunsValue = (Long) nodesAndRelationshipList.get(C.TARGET_DUNS);
        if (null != targetDunsValue) {
            for (Node targetNode : (List<Node>) nodesAndRelationshipList.get(C.DETAILS)) {
                if (targetNode.id() == targetDunsValue) {
                    JsonBuilderHelper.populateTargetDunsDetails(targetNode, organization);
                }
            }
        }

        // populate Ownership Summary
        if (!beneficiaryList.isEmpty()) {
            // Get relationshipValueList
            List relationshipValueList = new ArrayList<>();
            relationshipValueList = (List) nodesAndRelationshipList.get("rels");

            populateOwnershipSummaryforList(beneficiaryList, organization, relationshipValueList.size());
        }

        // Filtering starts here
        beneficiaryList = JsonBuilderHelper.filterOperation(beneficiaryList, (Boolean) reqDetails.get(C.EXCLUDE_UNDISCLOSED_HOLDINGS), (String) reqDetails.get(C.OWNERSHIP_TYPE),
                        (Double) reqDetails.get(C.OWNERSHIP_PERCENTAGE));

        // To Round off Percentages
        JsonBuilderHelper.setRoundOffPctBeneficiary(beneficiaryList);

        // No ownership data meets criteria specified in filters
        final TransactionStatus transactionStatus = new TransactionStatus();

        // Fetching Corporate Linkage

        CorporateLinkage corporateLinkage = new CorporateLinkage();
        if (!linkageValuesList.isEmpty()) {
            corporateLinkage = JsonBuilderHelper.getCorporateLinkage(linkageValuesList);
        }
        // Fix to display linkage for UB021
        organization.setCorporateLinkage(corporateLinkage);

        if (beneficiaryList.isEmpty()) {

            transactionStatus.setStatusCode(C.UB021);
            transactionStatus.setStatusMessage(C.FILTERNOOWNERSHIP);
            organization.setBeneficiaries(null);

            // log error
            LOGGER.error(C.ERROR + C.VERSIONV4 + "  " + C.FILTERNOOWNERSHIP + " " + C.DUNS + " " + reqDetails.get(C.DUNSNBR) + " " + C.TRANSACTION_ID + " " + reqDetails.get(C.TRANSACTION_ID));
        } else {
            // Set beneficiary
            organization.setBeneficiaries(beneficiaryList);

            // Set transaction status
            transactionStatus.setStatusCode(C.UB001);
            transactionStatus.setStatusMessage(C.SUCCESS);

        }
        // set the product response
        productResponse.setOrganization(organization);
        transactionStatus.setIsFailure(false);
        if (null != reqDetails.get(C.DEPTH)) {
            transactionStatus.setStatusCode(C.UB003);
            transactionStatus.setStatusMessage(C.MSGFORDEPTH);
            transactionStatus.setIsFailure(false);
        }
        return transactionStatus;
    }

    /**
     * To populate beneficiary type in response
     *
     * @param nodeTypes
     * @param beneficiaryType
     */
    public static final void populateBeneficiaryType(final List<String> nodeTypes, final BeneficiaryType beneficiaryType) {
        if (!nodeTypes.isEmpty()) {
            if ((C.ORGANISATION).equalsIgnoreCase(nodeTypes.get(0))) {
                beneficiaryType.setCode(C.MEBR_TYPE_CODE_BUSINESS);
            } else if ((C.INDIVIDUAL).equalsIgnoreCase(nodeTypes.get(0))) {
                beneficiaryType.setCode(C.MEBR_TYPE_CODE_INDIVIDUAL);
            }
        }
    }

    /**
     * To populate ownership summary details in list
     *
     * @param List
     *            <Beneficiary> beneficiaryList
     * @param Organization
     *            organization
     */
    private static final void populateOwnershipSummaryforList(final List<Beneficiary> beneficiaryList, final Organization organization, final int relationSize) {
        OwnershipSummary ownershipSummary = new OwnershipSummary();
        NodeSummaryByGeography geographySum = new NodeSummaryByGeography();
        Map<String, Integer> countryMap = new HashMap<>();
        int maxDegree = C.ZERO;
        int unknownCount = C.ZERO;
        int organizationCount = C.ZERO;
        int individualCount = C.ZERO;
        int entityCount = C.ZERO;
        int cboCount = C.ZERO;
        Double totalBen = C.ZEROPER;

        for (Beneficiary beneficiary : beneficiaryList) {

            // Depth
            if (null != beneficiary.getDepth() && beneficiary.getDepth() > maxDegree) {
                maxDegree = beneficiary.getDepth();
            }

            // Unknown count
            unknownCount = JsonBuilderHelper.getUnknownCount(countryMap, unknownCount, beneficiary);

            // CBO
            if (!beneficiary.getOwnershipUnavailableReason().isEmpty()) {
                cboCount++;
            }

            // Allocated Beneficial percentage
            if (null != beneficiary.getBeneficialOwnershipPercentage()) {
                totalBen += Math.round(beneficiary.getBeneficialOwnershipPercentage() * C.HUNDRED) / C.HUNDRED;
            }

            // Organisation count
            if (null != beneficiary.getBeneficiaryType().getCode() && (C.MEBR_TYPE_CODE_BUSINESS == beneficiary.getBeneficiaryType().getCode())) {
                organizationCount++;
            } else if (null != beneficiary.getBeneficiaryType().getCode() && (C.MEBR_TYPE_CODE_INDIVIDUAL == beneficiary.getBeneficiaryType().getCode())) {
                // Individual count
                individualCount++;
            } else {
                // Entity count
                entityCount++;
            }

        }

        //Control Type Summary
        JsonBuilderHelper.setControlTypeSummary(beneficiaryList,ownershipSummary);

              // Country summary
        JsonBuilderSupport.setCountrySummary(geographySum, countryMap, unknownCount);

        // Populate ownership Summary with the details
        ownershipSummary.setNodeCount(beneficiaryList.size());
        ownershipSummary.setRelationshipCount(relationSize);
        ownershipSummary.setMaxDepth(new Integer(maxDegree));
        ownershipSummary.setTotalAllocatedBeneficialOwnership(Math.round(totalBen * C.HUNDRED) / C.HUNDRED);
        ownershipSummary.setOrganizationCount(organizationCount);
        ownershipSummary.setIndividualCount(individualCount);
        ownershipSummary.setEntityCount(entityCount);
        ownershipSummary.setNodeSummaryByGeography(geographySum);
        ownershipSummary.setCorporateBeneficiaryCount(cboCount);

        // Population ownership summary to organisation
        organization.setOwnershipSummary(ownershipSummary);
    }



}