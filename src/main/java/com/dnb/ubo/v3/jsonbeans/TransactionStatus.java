
package com.dnb.ubo.v3.jsonbeans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "isFailure",
    "statusCode",
    "statusMessage",
    "infoURL"
})
public class TransactionStatus {

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("isFailure")
    private Boolean isFailure;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("statusCode")
    private String statusCode;
    @JsonProperty("statusMessage")
    private String statusMessage;
    @JsonProperty("infoURL")
    private String infoURL;

    /**
     *
     * (Required)
     *
     * @return
     *     The isFailure
     */
    @JsonProperty("isFailure")
    public Boolean getIsFailure() {
        return isFailure;
    }

    /**
     *
     * (Required)
     *
     * @param isFailure
     *     The isFailure
     */
    @JsonProperty("isFailure")
    public void setIsFailure(Boolean isFailure) {
        this.isFailure = isFailure;
    }

    /**
     *
     * (Required)
     *
     * @return
     *     The statusCode
     */
    @JsonProperty("statusCode")
    public String getStatusCode() {
        return statusCode;
    }

    /**
     *
     * (Required)
     *
     * @param statusCode
     *     The statusCode
     */
    @JsonProperty("statusCode")
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    /**
     *
     * @return
     *     The statusMessage
     */
    @JsonProperty("statusMessage")
    public String getStatusMessage() {
        return statusMessage;
    }

    /**
     *
     * @param statusMessage
     *     The statusMessage
     */
    @JsonProperty("statusMessage")
    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    /**
     *
     * @return
     *     The infoURL
     */
    @JsonProperty("infoURL")
    public String getInfoURL() {
        return infoURL;
    }

    /**
     *
     * @param infoURL
     *     The infoURL
     */
    @JsonProperty("infoURL")
    public void setInfoURL(String infoURL) {
        this.infoURL = infoURL;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(isFailure).append(statusCode).append(statusMessage).append(infoURL).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof TransactionStatus) == false) {
            return false;
        }
        TransactionStatus rhs = ((TransactionStatus) other);
        return new EqualsBuilder().append(isFailure, rhs.isFailure).append(statusCode, rhs.statusCode).append(statusMessage, rhs.statusMessage).append(infoURL, rhs.infoURL).isEquals();
    }

}
