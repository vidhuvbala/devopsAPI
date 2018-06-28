package com.dnb.ubo.v4.exception;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dnb.ubo.v4.constants.C;
import com.dnb.ubo.v4.constants.ErrorCodes;
import com.dnb.ubo.v4.jsonbeans.ApiResponse;
import com.dnb.ubo.v4.jsonbeans.InquiryDetail;
import com.dnb.ubo.v4.jsonbeans.TransactionInfo_;
import com.dnb.ubo.v4.request.RequestHelper;
import com.dnb.ubo.v4.response.JsonBuilder;

@ControllerAdvice(basePackages = {"com.dnb.ubo.v4"})
@Component("apiExceptionControllerAdviceV4")
public class ExceptionControllerAdvice {

    /**
     *
     * @param exception
     * @param response
     * @param request
     * @return
     * @throws IOException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws ParseException
     */
    @ExceptionHandler(ApplicationException.class)
    @ResponseBody
    public ApiResponse handleServiceException(ApplicationException exception, HttpServletResponse response, HttpServletRequest request) {

        final ApiResponse uboresponse = JsonBuilder.getExceptionBeneficiariesOperation(exception);
        setHttpCodes(uboresponse, response);
        final InquiryDetail inquriydetails = RequestHelper.inquriydetails(request);
        final TransactionInfo_ transactionInfo = RequestHelper.transactionInfo(request);
        uboresponse.setInquiryDetail(inquriydetails);
        uboresponse.setTransactionInfo(transactionInfo);
        ApplicationLogger.logErrorDetails(uboresponse, request);
        return uboresponse;

    }

    /**
     *
     * @param exception
     * @param response
     * @param request
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws ParseException
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ApiResponse handleGenericException(final Exception exception, final HttpServletResponse response, final HttpServletRequest request) {
        final ApplicationException uboException = new ApplicationException("", exception, exception.getMessage());

        exception.getLocalizedMessage();
        ApiResponse uboresponse = JsonBuilder.getExceptionBeneficiariesOperation(uboException);
        setHttpCodes(uboresponse, response);
        final InquiryDetail inquriydetails = RequestHelper.inquriydetails(request);
        final TransactionInfo_ transactionInfo = RequestHelper.transactionInfo(request);
        uboresponse.setInquiryDetail(inquriydetails);
        uboresponse.setTransactionInfo(transactionInfo);
        ApplicationLogger.logErrorDetails(uboresponse, request);
        return uboresponse;
    }

    /**
     * Method to set Http Status Code
     *
     * @param response
     * @param uboresponse
     */
    private void setHttpCodes(final ApiResponse uboresponse, final HttpServletResponse response) {
        response.setHeader(C.X_FRAME_OPTIONS, C.DENY);
        response.setHeader(C.CONTENTSECURITY,C.DEFAULTSRC);
        response.setHeader(C.XSS,C.BLOCK);
        response.setHeader(C.CONTENTTYPE,C.NOSNIFF);
        response.setHeader(C.TRNASPORTSECURITY,C.MAXAGE);
        response.setHeader(C.CACHECONTROL,C.CACHECONTROLVAL);
        response.setHeader(C.EXPIRES,C.EXPIRESVAL);
        response.setHeader(C.PRGMA,C.PRAGMAVAL);

        final String statusCode = uboresponse.getTransactionStatus().getStatusCode();

        if (ErrorCodes.UB008.toString().equals(statusCode)) {
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        }
        if (ErrorCodes.UB011.toString().equals(statusCode)) {
            response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }
        if (ErrorCodes.UB012.toString().equals(statusCode)) {
            response.setStatus(C.HTTP422);
        }
        if (ErrorCodes.UB014.toString().equals(statusCode)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        if (ErrorCodes.UB017.toString().equals(uboresponse.getTransactionStatus().getStatusCode())) {
            response.setStatus(HttpServletResponse.SC_GATEWAY_TIMEOUT);
        }
        if (ErrorCodes.UB019.toString().toString().equals(uboresponse.getTransactionStatus().getStatusCode())) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        if(ErrorCodes.UB016.toString().equals(uboresponse.getTransactionStatus().getStatusCode())){
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        }
    }
}
