/**
 * @Name: UBOAPIValidator.java
 * @Class_Description: This is UBOAPIValidator class for validating the request
 *                     inputs
 * @author: Cognizant Technology Solutions
 * @Created_On: Sep 5, 2016 Confidential and Proprietary. Copyright (C) 2016
 *              D&B. All Rights Reserved.
 */
package com.dnb.ubo.v3.request;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dnb.ubo.v3.constants.C;
import com.dnb.ubo.v3.exception.ApplicationException;

public class RequestValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestValidator.class);

    private RequestValidator(){

    }

    /**
     * @param transactionId
     * @param dunsNumber
     * @param productCode
     * @return
     * @throws ApplicationException
     */
    public static final boolean mandatoryFieldValidator(final String transactionId, final String dunsNumber, final String productCode) throws ApplicationException {

        boolean invalid = false;
        String invalidField = "";

        if (StringUtils.isBlank(transactionId)) {
            invalid = true;
            invalidField = C.TRANSACTION_ID;
        } else if (StringUtils.isBlank(dunsNumber)) {
            invalid = true;
            invalidField = C.DUNS;
        } else if (StringUtils.isBlank(productCode)) {
            invalid = true;
            invalidField = C.PRODUCT_CODE;
        }

        if (invalid) {
            throw new ApplicationException(invalidField, C.UB012);
        }

        return false;

    }

    /**
     * @param reqDetails
     * @param operationFlag
     * @return
     * @throws ApplicationException
     */
    public static final boolean requestValidator(final Map<String, Object> reqDetails, final String operationFlag) throws ApplicationException {

        final String[] productCodeValues = {C.PRODCODE_LIST, C.PRODCODE_STRUCT, C.PRODCODE_POINT};

        boolean invalid = mandatoryFieldValidator((String) reqDetails.get(C.TRANSACTION_ID), (String) reqDetails.get(C.DUNSNBR), (String) reqDetails.get(C.PRODUCT_CODE));

        if (checkDuns(invalid, (String)reqDetails.get(C.DUNSPADDED))) {
            invalid = true;
        } else if (!((String)reqDetails.get(C.PRODUCT_CODE)).equals(operationFlag)) {
            throw new ApplicationException(C.PRODUCT_CODE, C.UB012);
        } else if (countryChk(invalid, (String)reqDetails.get(C.IND_COUNTRY))) {
            invalid = true;
        } else if (timestampTypeandPercentageCheck(reqDetails, invalid, productCodeValues)) {
            invalid = true;
        }else if(excludeUndisclosedHoldingsforStructure((String)reqDetails.get(C.PRODUCT_CODE), (Boolean)reqDetails.get("excludeUndisclosedHoldings"))){
            invalid = true;
        }
        if (invalid) {
            throw new ApplicationException("", C.UB014);
        }
        return true;
    }

    /**
     * @param reqDetails
     * @param invalid
     * @param productCodeValues
     * @return
     * @throws ApplicationException
     */
    private static final boolean timestampTypeandPercentageCheck(final Map<String, Object> reqDetails, final boolean invalid, final String[] productCodeValues) throws ApplicationException {
        return transactionTimeandOwnershipTypeCheck((String)reqDetails.get(C.OWNERSHIP_TYPE),(String) reqDetails.get(C.PRODUCT_CODE), invalid)
                        || !Arrays.asList(productCodeValues).contains((String)reqDetails.get(C.PRODUCT_CODE)) || ownershipPercentageCheck((Double)reqDetails.get(C.OWNERSHIP_PERCENTAGE), invalid);
    }

    /**
     * @param invalid
     * @param countryCode
     * @return
     */
    public static final boolean countryChk(final boolean invalid, final String countryCode) {
        boolean notValid = invalid;
        if (!nullCheck(countryCode) && !Pattern.compile(C.REGEX_COUNTRYCODE).matcher(countryCode).matches()) {
            notValid = true;
        }
        return notValid;
    }

    /**
     * @param invalid
     * @param dunsNumber
     * @return
     */
    public static final boolean checkDuns(final boolean invalid, final String dunsNumber) {

        boolean notValid = invalid;
        if (!Pattern.compile(C.REGEX_DUNSNUMBER).matcher(dunsNumber).matches()) {
            notValid = true;
        }
        return notValid;
    }

    /**
     * @param valueToCheck
     * @return
     */
    public static final boolean nullCheck(final String valueToCheck) {
        boolean isempty = true;
        if ((null != valueToCheck) && !valueToCheck.isEmpty()) {
            isempty = false;
        }
        return isempty;
    }

    /**
     * @param ownershipType
     * @param productCode
     * @param invalid
     * @return
     * @throws ApplicationException
     */
    public static final boolean transactionTimeandOwnershipTypeCheck(final String ownershipType, final String productCode, final boolean invalid) throws ApplicationException {
        boolean notValid = invalid;
        final String[] ownershipTypeListValues = {C.BENEFICIARIES_OWNERSHIP, C.CONTROLLED_OWNERSHIP, C.DIRECT_OWNERSHIP, C.INDIRECT_OWNERSHIP};

        if (!nullCheck(ownershipType) && productCode.equals(C.PRODCODE_LIST) && !Arrays.asList(ownershipTypeListValues).contains(ownershipType)) {
            notValid = true;
        } else if (!nullCheck(ownershipType) && productCode.equals(C.PRODCODE_STRUCT) && !ownershipType.equals(C.BENEFICIARIES_OWNERSHIP)) {
            throw new ApplicationException("", C.UB022);
        }
        return notValid;
    }

    /**
     * @param ownershipPercentage
     * @param invalid
     * @return
     */
    public static final boolean ownershipPercentageCheck(final Double ownershipPercentage, final boolean invalid) {

        boolean notValid = invalid;
        if (null != ownershipPercentage && ownershipPercentage != 0) {
            String[] ownershipPercentageValueList = ownershipPercentage.toString().split("\\.");
            if (ownershipPercentageValueList[0].length() > C.THREE || ownershipPercentageValueList[1].length() > C.TWO) {
                notValid = true;
            }
        }
        return notValid;
    }

     /**
     * @param productCode
     * @param excludeUndisclosedHoldings
     * @return
     * @throws ApplicationException
     */
    public static final boolean excludeUndisclosedHoldingsforStructure(final String productCode, final Boolean excludeUndisclosedHoldings) throws ApplicationException {
         boolean inValid = false;

         if(productCode.equals(C.PRODCODE_STRUCT) && null != excludeUndisclosedHoldings && excludeUndisclosedHoldings){
             throw new ApplicationException("", C.UB022);
         }else{
             inValid = false;
         }
        return inValid;

     }

     /**
     *
     * @param dateString
     * @return
     */
    public static final String parseDate(final String date) {

        final SimpleDateFormat sdf1 = new SimpleDateFormat(C.DOB_FORMAT1);
        final SimpleDateFormat sdf2 = new SimpleDateFormat(C.DOB_FORMAT2);
        final SimpleDateFormat sdf3 = new SimpleDateFormat(C.DOB3);

        String dateString = "";
        String parsedDate = "";
        try {
            dateString = date.length() >= C.TEN ? date.substring(0, C.TEN) : date;
            if (dateString.matches(C.REGEX_DATE_OF_BIRTH1)) {
                parsedDate = sdf1.format(sdf1.parse(dateString));
            } else if (dateString.matches(C.REGEX_DATE_OF_BIRTH2)) {
                parsedDate = sdf2.format(sdf2.parse(dateString));
            } else if (dateString.matches(C.REGEX_DATE_OF_BIRTH3)) {
                parsedDate = sdf3.format(sdf3.parse(dateString));
            }
        } catch (ParseException ex) {
            LOGGER.error(C.ERROR+ex.getMessage());
        }
        return parsedDate;
    }
}
