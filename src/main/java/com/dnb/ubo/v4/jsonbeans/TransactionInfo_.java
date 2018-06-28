
package com.dnb.ubo.v4.jsonbeans;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "transactionId",
    "transactionTimestamp",
    "requestId",
    "requestTimestamp"
})
public class TransactionInfo_ {

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("transactionId")
    private String transactionId;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("transactionTimestamp")
    private String transactionTimestamp;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("requestId")
    private String requestId;
    @JsonProperty("requestTimestamp")
    private String requestTimestamp;

    /**
     *
     * (Required)
     *
     * @return
     *     The transactionId
     */
    @JsonProperty("transactionId")
    public String getTransactionId() {
        return transactionId;
    }

    /**
     *
     * (Required)
     *
     * @param transactionId
     *     The transactionId
     */
    @JsonProperty("transactionId")
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    /**
     *
     * (Required)
     *
     * @return
     *     The transactionTimestamp
     */
    @JsonProperty("transactionTimestamp")
    public String getTransactionTimestamp() {
        return transactionTimestamp;
    }

    /**
     *
     * (Required)
     *
     * @param transactionTimestamp
     *     The transactionTimestamp
     */
    @JsonProperty("transactionTimestamp")
    public void setTransactionTimestamp(String transactionTimestamp) {
        this.transactionTimestamp = transactionTimestamp;
    }

    /**
     *
     * (Required)
     *
     * @return
     *     The requestId
     */
    @JsonProperty("requestId")
    public String getRequestId() {
        return requestId;
    }

    /**
     *
     * (Required)
     *
     * @param requestId
     *     The requestId
     */
    @JsonProperty("requestId")
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    /**
     *
     * @return
     *     The requestTimestamp
     */
    @JsonProperty("requestTimestamp")
    public String getRequestTimestamp() {
        return requestTimestamp;
    }

    /**
     *
     * @param requestTimestamp
     *     The requestTimestamp
     */
    @JsonProperty("requestTimestamp")
    public void setRequestTimestamp(String requestTimestamp) {
        this.requestTimestamp = requestTimestamp;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(transactionId).append(transactionTimestamp).append(requestId).append(requestTimestamp).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof TransactionInfo_) == false) {
            return false;
        }
        TransactionInfo_ rhs = ((TransactionInfo_) other);
        return new EqualsBuilder().append(transactionId, rhs.transactionId).append(transactionTimestamp, rhs.transactionTimestamp).append(requestId, rhs.requestId).append(requestTimestamp, rhs.requestTimestamp).isEquals();
    }

}
