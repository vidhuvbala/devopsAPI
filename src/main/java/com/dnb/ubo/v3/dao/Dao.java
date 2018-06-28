/**
 * @Name: UBOAPIDao.java
 * @Class_Description: This is the interface for all the UBO API DAO operations
 *
 * @author: Cognizant Technology Solutions
 * @Created_On: Sep 5, 2016 Confidential and Proprietary. Copyright (C) 2016
 *              D&B. All Rights Reserved.
 */
package com.dnb.ubo.v3.dao;

import java.util.Map;

import com.dnb.ubo.v3.exception.ApplicationException;
import com.dnb.ubo.v3.response.ResponseWrapper;

public interface Dao {

    /**
     * Interface method for OwnershipStructure *
     *
     * @param transactionId
     * @param transactionTimestamp
     * @param dunsNumber
     * @param countryCode
     * @param ownershipType
     * @param ownershipPercentage
     * @param includeUnknownHoldings
     * @param excludeUndisclosedHoldings
     * @return
     * @throws ApplicationException
     */
    ResponseWrapper getBeneficiaryOps( final Map<String,Object> reqDetails,  String operationFlag) throws ApplicationException;

}
