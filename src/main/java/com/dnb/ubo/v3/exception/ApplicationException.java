package com.dnb.ubo.v3.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dnb.ubo.v3.constants.C;

/**
 * @Name: UBOAPIException.java
 * @Class_Description: This class holds the ApplicationException in the cpsweb
 *                     application
 *
 * @author: Cognizant Technology Solutions
 * @Created_On: Jan 5, 2016
 *
 *              Confidential and Proprietary. Copyright (C) 2016 D&B. All Rights
 *              Reserved.
 */

public class ApplicationException extends Exception {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationException.class);
    private static final long serialVersionUID = 1L;

    private final Exception baseException;

    private final String errorCode;

    private final String errorMsg;

    private final String exResponseType;


    /**
     *
     */
    public ApplicationException() {
        LOGGER.info("Initialized the Exception ");
        this.baseException = new Exception();
        this.errorCode = C.EMPTY;
        this.errorMsg = C.ERRMSG;
        this.exResponseType = C.ERRMSG;

    }

    /**
     * Constructor for Application Exceptions
     *
     * @param appMessage
     *            Exception Message
     */
    public ApplicationException(final String appMessage) {
        super(C.EMPTY + appMessage);
        this.errorMsg = appMessage;
        this.baseException = new Exception();
        this.errorCode = C.EMPTY;
        this.exResponseType = C.ERRMSG;
    }

    /**
     * Constructor for Application Exceptions
     *
     * @param appMessage
     *            Exception Message
     * @param errorCode
     *            Error Code
     */
    public ApplicationException(final String exResponseType, final String errorCode) {
        super(C.EMPTY + C.EMPTY + errorCode + C.ERRMSG + C.EMPTY + exResponseType);
        this.errorCode = errorCode;
        this.errorMsg = exResponseType;
        this.baseException = new Exception();
        this.exResponseType = C.ERRMSG;
    }

    /**
     * Constructor for exceptions thrown by JVM
     *
     * @param errorCode
     *            Error Code
     * @param ex
     *            Actual Exception Obtained
     * @param errorMessage
     *            Exception Message
     */

    public ApplicationException(final String errorCode, final Exception ex, final String exResponseType) {
        this.errorCode = errorCode;
        this.exResponseType = exResponseType;
        this.baseException = new Exception();
        this.errorMsg = C.ERRMSG;
        logException(ex, errorCode);
    }

    /**
     * @return errorCode Error Code
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * This method will return the base exception thrown by the application.
     *
     * @return Exception Exception Object
     */
    public Exception getBaseException() {
        return baseException;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public String getExResponseType() {
        return exResponseType;
    }

    private void logException(final Exception e,final  String errorCode) {
        LOGGER.error(C.ERROR + errorCode, e);
    }
}
