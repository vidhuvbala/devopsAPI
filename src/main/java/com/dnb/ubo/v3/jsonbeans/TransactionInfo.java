
package com.dnb.ubo.v3.jsonbeans;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * transactionInfo
 * <p>
 *
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "transactionId",
    "transactionTimestamp"
})
public class TransactionInfo {

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("transactionId")
    private String transactionId;
    @JsonProperty("transactionTimestamp")
    private String transactionTimestamp;

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
     * @return
     *     The transactionTimestamp
     */
    @JsonProperty("transactionTimestamp")
    public String getTransactionTimestamp() {
        return transactionTimestamp;
    }

    /**
     *
     * @param transactionTimestamp
     *     The transactionTimestamp
     */
    @JsonProperty("transactionTimestamp")
    public void setTransactionTimestamp(String transactionTimestamp) {
        this.transactionTimestamp = transactionTimestamp;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(transactionId).append(transactionTimestamp).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof TransactionInfo) == false) {
            return false;
        }
        TransactionInfo rhs = ((TransactionInfo) other);
        return new EqualsBuilder().append(transactionId, rhs.transactionId).append(transactionTimestamp, rhs.transactionTimestamp).isEquals();
    }

}
