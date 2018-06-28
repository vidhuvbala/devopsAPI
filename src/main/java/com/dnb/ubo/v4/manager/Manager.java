/**
 * @Name: UBOOperationsManager.java
 * @Class_Description: This is the manager interface for all the UBO API
 *                     operations
 *
 * @author: Cognizant Technology Solutions
 * @Created_On: Sep 5, 2016
 *
 *              Confidential and Proprietary. Copyright (C) 2016 D&B. All Rights
 *              Reserved.
 */
package com.dnb.ubo.v4.manager;

import java.util.Map;

import com.dnb.ubo.v4.exception.ApplicationException;
import com.dnb.ubo.v4.response.ResponseWrapper;

public interface Manager {

    /**
     * Method to get the ownership structure
     *
     * @param dunsNumber
     * @param countryCode
     * @return
     * @throws ApplicationException
     */
    ResponseWrapper getOwnershipStructure(  Map<String,Object> rqeDetails) throws ApplicationException;

    /**
     * End point method for Get List of Beneficiaries operation
     *
     * @param dunsNumber
     * @param countryCode
     * @return
     * @throws ApplicationException
     */
    ResponseWrapper getListOfBeneficiaries( Map<String,Object> reqDetails) throws ApplicationException;

}
