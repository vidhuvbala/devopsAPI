/**
 * @Name: UBOOperationsManagerImpl.java
 * @Class_Description: This is the manager implementation class for all the UBO
 *                     API operations
 *
 * @author: Cognizant Technology Solutions
 * @Created_On: Sep 5, 2016
 *
 *              Confidential and Proprietary. Copyright (C) 2016 D&B. All Rights
 *              Reserved.
 */
package com.dnb.ubo.v3.manager;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.dnb.ubo.v3.constants.C;
import com.dnb.ubo.v3.dao.Dao;
import com.dnb.ubo.v3.exception.ApplicationException;
import com.dnb.ubo.v3.request.RequestValidator;
import com.dnb.ubo.v3.response.ResponseWrapper;

@Service
@Component("apiManagerImplV3")
public class ManagerImpl implements Manager {

    @Autowired
    Dao uBOAPIDao;

    /**
     * Method to fetch the owner ship structure from database
     *
     * @param dunsNumber
     * @param countryCode
     * @return
     * @throws ApplicationException
     */
    @Override
    public ResponseWrapper getOwnershipStructure(final Map<String, Object> reqDetails) throws ApplicationException {

        // Populated duns
        final String paddedDunsNbr = StringUtils.leftPad((String) reqDetails.get(C.DUNSNBR), C.NINE, "0");
        reqDetails.put(C.DUNSPADDED, paddedDunsNbr);

        // populate response
        ResponseWrapper apiResponseWrapper = null;
        final boolean isValid = RequestValidator.requestValidator(reqDetails, C.PRODCODE_STRUCT);
        if (isValid) {
            String operationFlag = C.BENEF_STRUCTURE;
            apiResponseWrapper = uBOAPIDao.getBeneficiaryOps(reqDetails, operationFlag);
        }
        return apiResponseWrapper;
    }

    /**
     * Method to fetch the list Of Beneficiaries from database
     *
     * @param dunsNumber
     * @param countryCode
     * @return
     * @throws ApplicationException
     */
    @Override
    public ResponseWrapper getListOfBeneficiaries(Map<String, Object> reqDetails) throws ApplicationException {

        // paddedDuns
        final String paddedDunsNbr = StringUtils.leftPad((String) reqDetails.get(C.DUNSNBR), C.NINE, "0");
        reqDetails.put(C.DUNSPADDED, paddedDunsNbr);

        // populate response
        ResponseWrapper apiResponseWrapper = null;
        boolean isValid = RequestValidator.requestValidator(reqDetails, C.PRODCODE_LIST);
        if (isValid) {
            apiResponseWrapper = uBOAPIDao.getBeneficiaryOps(reqDetails, C.BENEF_LIST);
        }

        return apiResponseWrapper;
    }
}
