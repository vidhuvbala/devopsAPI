
package com.dnb.ubo.v3.jsonbeans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "transactionInfo",
    "transactionStatus",
    "inquiryDetail",
    "productResponse"
})
public class ApiResponse {

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("transactionInfo")
    private TransactionInfo_ transactionInfo;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("transactionStatus")
    private TransactionStatus transactionStatus;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("inquiryDetail")
    private InquiryDetail inquiryDetail;
    @JsonProperty("productResponse")
    private ProductResponse productResponse;

    /**
     *
     * (Required)
     *
     * @return
     *     The transactionInfo
     */
    @JsonProperty("transactionInfo")
    public TransactionInfo_ getTransactionInfo() {
        return transactionInfo;
    }

    /**
     *
     * (Required)
     *
     * @param transactionInfo
     *     The transactionInfo
     */
    @JsonProperty("transactionInfo")
    public void setTransactionInfo(TransactionInfo_ transactionInfo) {
        this.transactionInfo = transactionInfo;
    }

    /**
     *
     * (Required)
     *
     * @return
     *     The transactionStatus
     */
    @JsonProperty("transactionStatus")
    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    /**
     *
     * (Required)
     *
     * @param transactionStatus
     *     The transactionStatus
     */
    @JsonProperty("transactionStatus")
    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    /**
     *
     * (Required)
     *
     * @return
     *     The inquiryDetail
     */
    @JsonProperty("inquiryDetail")
    public InquiryDetail getInquiryDetail() {
        return inquiryDetail;
    }

    /**
     *
     * (Required)
     *
     * @param inquiryDetail
     *     The inquiryDetail
     */
    @JsonProperty("inquiryDetail")
    public void setInquiryDetail(InquiryDetail inquiryDetail) {
        this.inquiryDetail = inquiryDetail;
    }

    /**
     *
     * @return
     *     The productResponse
     */
    @JsonProperty("productResponse")
    public ProductResponse getProductResponse() {
        return productResponse;
    }

    /**
     *
     * @param productResponse
     *     The productResponse
     */
    @JsonProperty("productResponse")
    public void setProductResponse(ProductResponse productResponse) {
        this.productResponse = productResponse;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(transactionInfo).append(transactionStatus).append(inquiryDetail).append(productResponse).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ApiResponse) == false) {
            return false;
        }
        ApiResponse rhs = ((ApiResponse) other);
        return new EqualsBuilder().append(transactionInfo, rhs.transactionInfo).append(transactionStatus, rhs.transactionStatus).append(inquiryDetail, rhs.inquiryDetail).append(productResponse, rhs.productResponse).isEquals();
    }

}
