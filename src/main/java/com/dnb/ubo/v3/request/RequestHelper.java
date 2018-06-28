/**
 * APIResponseMandatoryItems.java This class takes the request items which are
 * mandatory in the response
 */
package com.dnb.ubo.v3.request;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dnb.ubo.v3.constants.C;
import com.dnb.ubo.v3.jsonbeans.ApiResponse;
import com.dnb.ubo.v3.jsonbeans.InquiryDetail;
import com.dnb.ubo.v3.jsonbeans.TransactionInfo_;

public class RequestHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestHelper.class);

    private RequestHelper() {

    }

    /**
     * Method to fit the inquiry details in the response
     *
     * @param request
     * @return
     */
    public static final InquiryDetail inquriydetails(HttpServletRequest request) {

        InquiryDetail inqdetls = new InquiryDetail();
        String opName = null;

        try {
            String pathInfo = request.getServletPath();
            String[] pathParts = pathInfo.split("/");

            inqdetls.setDuns(pathParts[C.THREE]);
            opName = pathParts[C.TWO];

            if (isEntityInRequest(request, C.COUNTRY_CODE)) {
                inqdetls.setIsoAlpha2CountryCode(request.getParameter(C.COUNTRY_CODE));
            }
            if (isEntityInRequest(request, C.PRODUCT_CODE)) {
                inqdetls.setProductCode(Integer.parseInt(request.getParameter(C.PRODUCT_CODE)));
            }
            if (isEntityInRequest(request, C.OWNERSHIP_TYPE)) {
                inqdetls.setOwnershipType(request.getParameter(C.OWNERSHIP_TYPE));
            }

            if (isEntityMatchingPattern(request, C.INCLUDE_UNKNOWN_HOLDINGS)) {
                inqdetls.setIncludeUnknownHoldings(Boolean.parseBoolean(request.getParameter(C.INCLUDE_UNKNOWN_HOLDINGS)));
            }
            if (isEntityMatchingPattern(request, C.EXCLUDE_UNDISCLOSED_HOLDINGS)) {
                inqdetls.setExcludeUndisclosedHoldings(Boolean.parseBoolean(request.getParameter(C.EXCLUDE_UNDISCLOSED_HOLDINGS)));
            }
            if (isEntityInRequest(request, C.OWNERSHIP_PERCENTAGE)) {
                inqdetls.setOwnershipPercentage(Double.parseDouble(request.getParameter(C.OWNERSHIP_PERCENTAGE)));
            }
        } catch (Exception e) {
            LOGGER.error(C.ERROR+e.getMessage(), e);
            if (C.BENEFICIARIES_STRUCT_OPNAME.equals(opName)) {
                inqdetls.setProductCode(Integer.parseInt(C.PRODCODE_STRUCT));
            } else if (C.LIST_BENEFICIARIES_OPANME.equals(opName)) {
                inqdetls.setProductCode(Integer.parseInt(C.PRODCODE_LIST));
            }
        }
        return inqdetls;
    }

    /**
     * Method to fit the inquiry details in the response
     *
     * @param request
     * @return
     */
    public static final TransactionInfo_ transactionInfo(HttpServletRequest request) {

        TransactionInfo_ transactionInfo = new TransactionInfo_();

        transactionInfo.setTransactionId(new SimpleDateFormat(C.TRANSACTION_ID_FORMAT).format(new Date()));
        transactionInfo.setTransactionTimestamp(new SimpleDateFormat(C.TIMESTAMP_GET_FORMAT).format(new Date()));
        if (null != request.getParameter(C.TRANSACTION_ID) && !request.getParameter(C.TRANSACTION_ID).isEmpty()) {
            transactionInfo.setRequestId(request.getParameter(C.TRANSACTION_ID));
        }
        try {
            if (null != request.getParameter(C.TRANSACTION_TIME_STAMP) && !request.getParameter(C.TRANSACTION_TIME_STAMP).isEmpty()) {
                transactionInfo.setRequestTimestamp(request.getParameter(C.TRANSACTION_TIME_STAMP));
            }
        } catch (Exception e) {
            LOGGER.error(C.ERROR+e.getMessage(), e);
            transactionInfo.setRequestTimestamp(request.getParameter(C.TRANSACTION_TIME_STAMP));
        }
        return transactionInfo;
    }

    /**
     * Method to set Http Status Code
     *
     * @param uboresponse
     * @param response
     */

    public static final void setHttpCodes(ApiResponse uboresponse, HttpServletResponse response) {
        response.setHeader(C.X_FRAME_OPTIONS, C.DENY);
        response.setHeader(C.CONTENTSECURITY,C.DEFAULTSRC);
        response.setHeader(C.XSS,C.BLOCK);
        response.setHeader(C.CONTENTTYPE,C.NOSNIFF);
        response.setHeader(C.TRNASPORTSECURITY,C.MAXAGE);
        response.setHeader(C.CACHECONTROL,C.CACHECONTROLVAL);
        response.setHeader(C.EXPIRES,C.EXPIRESVAL);
        response.setHeader(C.PRGMA,C.PRAGMAVAL);
        if (C.UB020.equals(uboresponse.getTransactionStatus().getStatusCode())) {
            response.setStatus(HttpServletResponse.SC_OK);
        }
        if (C.UB021.equals(uboresponse.getTransactionStatus().getStatusCode())) {
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    /**
     * @param request
     * @param entity
     * @return
     */
    private static final boolean isEntityInRequest(HttpServletRequest request, String entity) {
        return null != request.getParameter(entity) && !request.getParameter(entity).isEmpty();
    }

    /**
     * @param request
     * @param entity
     * @return
     */
    public static final boolean isEntityMatchingPattern(HttpServletRequest request, String entity) {

        return isEntityInRequest(request, entity) && Pattern.compile(C.REGEX_TRUEFALSE, Pattern.CASE_INSENSITIVE).matcher(request.getParameter(entity)).matches();
    }
}
