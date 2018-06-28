/**
 * @Name: ApiController.java
 * @Class_Description: This is controller class for UBO API operations
 *
 * @author: Cognizant Technology Solutions
 * @Created_On: Sep 5, 2016
 *
 *              Confidential and Proprietary. Copyright (C) 2016 D&B. All Rights
 *              Reserved.
 */

package com.dnb.ubo.v3.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dnb.ubo.v3.constants.C;
import com.dnb.ubo.v3.exception.ApplicationException;
import com.dnb.ubo.v3.exception.ApplicationLogger;
import com.dnb.ubo.v3.jsonbeans.ApiRequest;
import com.dnb.ubo.v3.jsonbeans.ApiResponse;
import com.dnb.ubo.v3.jsonbeans.ProductRequest;
import com.dnb.ubo.v3.jsonbeans.TransactionInfo;
import com.dnb.ubo.v3.manager.Manager;
import com.dnb.ubo.v3.request.RequestHelper;
import com.dnb.ubo.v3.response.ResponseWrapper;

@RestController("V3")
@RequestMapping(value = "/v3/organizations")
public class Controller {

    @Autowired
    private Manager uboAPIManager;

    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

    /**
     * End point method for Get Ownership operation
     *
     * @param dunsNumber
     * @param countryCode
     * @return
     * @throws ApplicationException
     */
    @RequestMapping(value = "/{duns}/beneficialownershipstructure", method = RequestMethod.GET, produces = {C.JSONAPP})
    @ResponseBody
    public ApiResponse getOwnershipStructure(@RequestHeader HttpHeaders httpHeader, @RequestParam(value = C.TRANSACTION_ID, required = true) String transactionId, @RequestParam(value = C.TRANSACTION_TIME_STAMP,
                    required = false) String transactionTimestamp, @PathVariable(value = C.DUNS) String dunsNumber, @RequestParam(value = C.COUNTRY_CODE, required = false) String countryCode,
                    @RequestParam(value = C.PRODUCT_CODE, required = true) String productCode, @RequestParam(value = C.OWNERSHIP_TYPE, required = false) String ownershipType,
                    @RequestParam(value = C.OWNERSHIP_PERCENTAGE, required = false) Double ownershipPercentage, @RequestParam(value = C.INCLUDE_UNKNOWN_HOLDINGS, required = false) Boolean includeUnknownHoldings,
                    @RequestParam(value = C.EXCLUDE_UNDISCLOSED_HOLDINGS, required = false) Boolean excludeUndisclosedHoldings, HttpServletResponse response) throws ApplicationException {

        try {
            // start the timer
            final Instant start = Instant.now();

            // generate request
            final Map<String, Object> apiRequestDetails = generateRequestDetailsMap(dunsNumber, countryCode, productCode, ownershipType, ownershipPercentage, includeUnknownHoldings, excludeUndisclosedHoldings);
            apiRequestDetails.put(C.TRANSACTION_ID, transactionId);
            apiRequestDetails.put(C.TRANSACTION_TIME_STAMP, transactionTimestamp);

            // get the response
            ResponseWrapper apiResponseWrapper = uboAPIManager.getOwnershipStructure(apiRequestDetails);
            ApiResponse apiResponse = apiResponseWrapper.getApiResponse();

            // get the codes
            RequestHelper.setHttpCodes(apiResponse, response);

            // end the timer
            final Instant end = Instant.now();

            // log the time
            apiResponseWrapper.getResponseTimes().put(C.TOTAL_TIME_IN_API, Duration.between(start, end).toMillis());
            ApplicationLogger.logAPIDetails(transactionId, dunsNumber, apiResponseWrapper, C.STRUCTURE_OWNERSHIP, httpHeader);
            //Removed Country Code detail for UB002
            if ((C.UB002).equals(apiResponse.getTransactionStatus().getStatusCode())) {
                apiResponse.setProductResponse(null);
                apiResponseWrapper.setApiResponse(apiResponse);
            }
            // return the response
            return apiResponseWrapper.getApiResponse();
        } catch (final ApplicationException e) {
            LOGGER.error(C.ERROR + " Exception occured in Ownership structure");
            throw new ApplicationException(e.getErrorCode(), e, e.getErrorMsg());
        }
    }

    /**
     * End point method for Get Ownership operation
     *
     * @param dunsNumber
     * @param countryCode
     * @return
     * @throws ApplicationException
     */
    @RequestMapping(value = "/{duns}/beneficialownershipstructure", method = RequestMethod.POST, headers = "Accept=application/json", consumes = {C.JSONAPP})
    @ResponseBody
    public ApiResponse getOwnershipStructurePost(@RequestHeader HttpHeaders httpHeader, @PathVariable(value = C.DUNS) String dunsNumber, @RequestBody ApiRequest apiRequest) throws ApplicationException {

        try {
            // start the timer
            final Instant start = Instant.now();

            // Set the duns number
            final ProductRequest productRequest = apiRequest.getProductRequest();
            productRequest.setDuns(dunsNumber);

            // Get the product code
            String productCode = (null != productRequest.getProductCode()) ? productRequest.getProductCode().toString() : null;

            // generate the request
            Map<String, Object> apiRequestDetails = generateRequestDetailsMap(productRequest.getDuns(), null, productCode, null, null, null, null);

            // get the transaction info and populate the request details
            final TransactionInfo transactionInfo = apiRequest.getTransactionInfo();
            apiRequestDetails.put(C.TRANSACTION_ID, transactionInfo.getTransactionId());
            apiRequestDetails.put(C.TRANSACTION_TIME_STAMP, null);

            // get the response wrapper
            final ResponseWrapper apiResponseWrapper = uboAPIManager.getOwnershipStructure(apiRequestDetails);

            // get the response
            final ApiResponse apiResponse = apiResponseWrapper.getApiResponse();

            // end the timer
            Instant end = Instant.now();

            // populate the time
            apiResponseWrapper.getResponseTimes().put(C.TOTAL_TIME_IN_API, Duration.between(start, end).toMillis());
            ApplicationLogger.logAPIDetails(transactionInfo.getTransactionId(), dunsNumber, apiResponseWrapper, C.STRUCTURE_OWNERSHIP, httpHeader);
            //Removed Country Code detail for UB002
            if ((C.UB002).equals(apiResponse.getTransactionStatus().getStatusCode())) {
                apiResponse.setProductResponse(null);
                apiResponseWrapper.setApiResponse(apiResponse);
            }
            // return the response
            return apiResponse;
        } catch (final ApplicationException e) {
            LOGGER.error(C.ERROR + "Exception occured in Ownership structure");
            throw new ApplicationException(e.getErrorCode(), e, e.getErrorMsg());
        }
    }

    /**
     * End point method for Get List of Beneficiaries operation
     *
     * @param dunsNumber
     * @param countryCode
     * @return
     * @throws ApplicationException
     */
    @RequestMapping(value = "/{duns}/beneficialownershiplist", method = RequestMethod.GET, produces = {C.JSONAPP})
    @ResponseBody
    public ApiResponse getListOfBeneficiaries(@RequestHeader HttpHeaders httpHeader, @RequestParam(value = C.TRANSACTION_ID, required = true) String transactionId, @RequestParam(value = C.TRANSACTION_TIME_STAMP,
                    required = false) String transactionTimestamp, @PathVariable(value = C.DUNS) String dunsNumber, @RequestParam(value = C.COUNTRY_CODE, required = false) String countryCode,
                    @RequestParam(value = C.PRODUCT_CODE, required = true) String productCode, @RequestParam(value = C.OWNERSHIP_TYPE, required = false) String ownershipType,
                    @RequestParam(value = C.OWNERSHIP_PERCENTAGE, required = false) Double ownershipPercentage, @RequestParam(value = C.INCLUDE_UNKNOWN_HOLDINGS, required = false) Boolean includeUnknownHoldings,
                    @RequestParam(value = C.EXCLUDE_UNDISCLOSED_HOLDINGS, required = false) Boolean excludeUndisclosedHoldings, HttpServletResponse response) throws ApplicationException {

        try {

            // start the timer
            final Instant start = Instant.now();

            // generate request
            Map<String, Object> apiRequestDetails = generateRequestDetailsMap(dunsNumber, countryCode, productCode, ownershipType, ownershipPercentage, includeUnknownHoldings, excludeUndisclosedHoldings);

            // populate the transaction ids
            apiRequestDetails.put(C.TRANSACTION_ID, transactionId);
            apiRequestDetails.put(C.TRANSACTION_TIME_STAMP, transactionTimestamp);

            // get the wrapper
            ResponseWrapper apiResponseWrapper = uboAPIManager.getListOfBeneficiaries(apiRequestDetails);

            // get the response from wrapper
            ApiResponse apiResponse = apiResponseWrapper.getApiResponse();

            // set the codes
            RequestHelper.setHttpCodes(apiResponse, response);

            // end the timer
            Instant end = Instant.now();

            // log the time
            apiResponseWrapper.getResponseTimes().put(C.TOTAL_TIME_IN_API, Duration.between(start, end).toMillis());
            ApplicationLogger.logAPIDetails(transactionId, dunsNumber, apiResponseWrapper, C.LIST_OWNERSHIP, httpHeader);
            //Removed Country Code detail for UB002
            if ((C.UB002).equals(apiResponse.getTransactionStatus().getStatusCode())) {
                apiResponse.setProductResponse(null);
                apiResponseWrapper.setApiResponse(apiResponse);
            }
            // return the response
            return apiResponse;
        } catch (final ApplicationException e) {
            LOGGER.error(C.ERROR + " Exception occured in List Of Beneficiaries");
            throw new ApplicationException(e.getErrorCode(), e, e.getErrorMsg());
        }
    }

    /**
     * End point method for Get List of Beneficiaries operation
     *
     * @param dunsNumber
     * @param countryCode
     * @return
     * @throws ApplicationException
     */
    @RequestMapping(value = "/{duns}/beneficialownershiplist", method = RequestMethod.POST, headers = "Accept=application/json", consumes = {C.JSONAPP})
    @ResponseBody
    public ApiResponse getListOfBeneficiariesPost(@RequestHeader HttpHeaders httpHeader, @PathVariable(value = C.DUNS) String dunsNumber, @RequestBody ApiRequest apiRequest) throws ApplicationException {

        try {

            // start the timer
            final Instant start = Instant.now();

            // Get the product code
            ProductRequest productRequest = apiRequest.getProductRequest();
            productRequest.setDuns(dunsNumber);
            String productCode = (null != productRequest.getProductCode()) ? productRequest.getProductCode().toString() : null;

            // generate the request
            Map<String, Object> apiRequestDetails = generateRequestDetailsMap(productRequest.getDuns(), null, productCode, null, null, null, null);

            // get the transaction info
            TransactionInfo transactionInfo = apiRequest.getTransactionInfo();
            apiRequestDetails.put(C.TRANSACTION_ID, transactionInfo.getTransactionId());
            apiRequestDetails.put(C.TRANSACTION_TIME_STAMP, null);

            // get the wrapper response for list beneficiary
            ResponseWrapper apiResponseWrapper = uboAPIManager.getListOfBeneficiaries(apiRequestDetails);

            // get the response
            final ApiResponse apiResponse = apiResponseWrapper.getApiResponse();

            // end the time
            Instant end = Instant.now();

            // log the time
            apiResponseWrapper.getResponseTimes().put(C.TOTAL_TIME_IN_API, Duration.between(start, end).toMillis());
            ApplicationLogger.logAPIDetails(transactionInfo.getTransactionId(), dunsNumber, apiResponseWrapper, C.LIST_OWNERSHIP, httpHeader);
            //Removed Country Code detail for UB002
            if ((C.UB002).equals(apiResponse.getTransactionStatus().getStatusCode())) {
                apiResponse.setProductResponse(null);
                apiResponseWrapper.setApiResponse(apiResponse);
            }
            // return the response
            return apiResponseWrapper.getApiResponse();
        } catch (final ApplicationException e) {
            LOGGER.error(C.ERROR + " Exception occured in List Of Beneficiaries");
            throw new ApplicationException(e.getErrorCode(), e, e.getErrorMsg());
        }
    }

    /**
     * @param dunsNumber
     * @param countryCode
     * @param productCode
     * @param ownershipType
     * @param ownershipPercentage
     * @param includeUnknownHoldings
     * @param excludeUndisclosedHoldings
     * @return
     */
    private Map<String, Object> generateRequestDetailsMap(final String dunsNumber, final String countryCode, final String productCode, final String ownershipType, final Double ownershipPercentage,
                    final Boolean includeUnknownHoldings, final Boolean excludeUndisclosedHoldings) {

        final Map<String, Object> requestDetailsMap = new HashMap<>();

        // Set the values for request details
        requestDetailsMap.put(C.DUNSNBR, dunsNumber);
        requestDetailsMap.put(C.IND_COUNTRY, countryCode);
        requestDetailsMap.put(C.PRODUCT_CODE, productCode);
        requestDetailsMap.put(C.OWNERSHIP_TYPE, ownershipType);
        requestDetailsMap.put(C.OWNERSHIP_PERCENTAGE, ownershipPercentage);
        requestDetailsMap.put(C.INCLUDE_UNKNOWN_HOLDINGS, includeUnknownHoldings);
        requestDetailsMap.put(C.EXCLUDE_UNDISCLOSED_HOLDINGS, excludeUndisclosedHoldings);

        return requestDetailsMap;
    }
}
