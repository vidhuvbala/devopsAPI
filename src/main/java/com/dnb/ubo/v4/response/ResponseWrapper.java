package com.dnb.ubo.v4.response;

import java.util.HashMap;
import java.util.Map;

import com.dnb.ubo.v4.jsonbeans.ApiResponse;

/**
 * Wrapper class for Api response. Holds the api response and induvidual
 * response times
 *
 */
public class ResponseWrapper {

    // API reponse instance
    private ApiResponse apiResponse;

    // Holds the induvidual response times for different methods
    private Map<String, Long> responseTimes = new HashMap<>();

    /**
     * getApiResponse
     *
     * @return
     */
    public ApiResponse getApiResponse() {
        return apiResponse;
    }

    /**
     * setApiResponse
     *
     * @param apiResponse
     */
    public void setApiResponse(ApiResponse apiResponse) {
        this.apiResponse = apiResponse;
    }

    /**
     * getResponseTimes
     *
     * @return
     */
    public Map<String, Long> getResponseTimes() {
        return responseTimes;
    }

    /**
     * setResponseTimes
     *
     * @param responseTimes
     */
    public void setResponseTimes(Map<String, Long> responseTimes) {
        this.responseTimes = responseTimes;
    }

}
