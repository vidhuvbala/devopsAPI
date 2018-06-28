package com.dnb.ubo.v4.exception;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

import com.dnb.ubo.v4.constants.C;
import com.dnb.ubo.v4.jsonbeans.ApiResponse;
import com.dnb.ubo.v4.jsonbeans.ProductResponse;
import com.dnb.ubo.v4.response.ResponseWrapper;

/**
 * servers as utility class for API COntroller
 *
 */
public class ApplicationLogger {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationLogger.class);

    ApplicationLogger() {

    }

    /**
     * logs all the api related details and response times
     *
     * @param transcationID
     * @param dunsNumber
     * @param countryCode
     * @param apiResponse
     * @param httpHeader
     */
    public static void logAPIDetails(final String transcationID, final String dunsNumber, final ResponseWrapper apiResponseWrapper, final String operationName,
                    final HttpHeaders httpHeader) {
        final  Map<String, Long> responseTimes = apiResponseWrapper.getResponseTimes();
        final ProductResponse prodRes = apiResponseWrapper.getApiResponse().getProductResponse();
        String countryCode = "-";
        if(null != prodRes && null != prodRes.getOrganization()  && null != prodRes.getOrganization().getAddress() && null != prodRes.getOrganization().getAddress().getAddressCountry()){
            countryCode = prodRes.getOrganization().getAddress().getAddressCountry().getIsoAlpha2Code();
        }
        String user = null;
        double graphTime = C.ZEROPER;
        double calculationTime = C.ZEROPER;
        double apiTime = C.ZEROPER;
        try {
            String authorization = httpHeader.getFirst("Authorization");
            user = getUserForLogging(user, authorization);
        } catch (Exception e) {
            LOGGER.error(C.ERROR+"User fetch error", e);
        }

        if (responseTimes.containsKey(C.TIME_FOR_GRAPHDB)) {
            graphTime = (double) (responseTimes.get(C.TIME_FOR_GRAPHDB));

        }
        if (responseTimes.containsKey(C.TIME_FOR_CALCULATIONS)) {
            calculationTime = (double) (responseTimes.get(C.TIME_FOR_CALCULATIONS));

        }

        if (responseTimes.containsKey(C.TOTAL_TIME_IN_API)) {
            apiTime = (double) (responseTimes.get(C.TOTAL_TIME_IN_API));

        }
        LOGGER.info("{} {} {} {} {} {} {} {} {}","V4", user, transcationID, dunsNumber, countryCode, operationName, round(graphTime, C.TWO), round(calculationTime, C.TWO), round(apiTime, C.TWO));

    }

    private static String getUserForLogging(String user, String authorization) {
        String userName = user;
        if (authorization != null && authorization.startsWith("Basic")) {
            String base64Credentials = authorization.substring("Basic".length()).trim();
            String credentials = new String(Base64.getDecoder().decode(base64Credentials), Charset.forName("UTF-8"));
            userName = credentials.split(":", C.TWO)[0];
        }
        return userName;
    }

    public static void logErrorDetails(final ApiResponse response, final HttpServletRequest request) {

        final StringBuilder builder = new StringBuilder();
        final String pathInfo = request.getServletPath();
        String user = null;
        String opName = null;
        try {
            user = request.getUserPrincipal().getName();
            String[] pathParts = pathInfo.split("/");
            opName = pathParts[C.TWO];
        } catch (Exception e) {
            LOGGER.error(C.ERROR+"User fetch error", e);
        }
        builder.append(C.ERROR);
        builder.append(C.USER).append(C.COLON).append(user);

        builder.append(C.PIPE).append(C.TRANSACTION_ID).append(C.COLON).append(response.getTransactionInfo().getRequestId());

        builder.append(C.PIPE).append(C.DUNS).append(C.COLON).append(response.getInquiryDetail().getDuns());

        builder.append(C.PIPE).append(C.IND_COUNTRY);
        builder.append(C.COLON).append(response.getInquiryDetail().getIsoAlpha2CountryCode());

        builder.append(C.PIPE).append(C.OPERATION);
        builder.append(C.COLON).append(opName);
        builder.append(C.PIPE).append(C.STATUS_CODE);
        builder.append(C.COLON).append(response.getTransactionStatus().getStatusCode());
        builder.append(C.PIPE).append(C.STATUS_MESSAGE);
        builder.append(C.COLON).append(response.getTransactionStatus().getStatusMessage());
        LOGGER.error(builder.toString());

    }

    /**
     *
     * @param value
     * @param places
     * @return
     */
    public static double round(final double value, final int places) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
