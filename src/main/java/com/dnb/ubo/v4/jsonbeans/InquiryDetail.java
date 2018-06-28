
package com.dnb.ubo.v4.jsonbeans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "duns",
    "isoAlpha2CountryCode",
    "productCode",
    "ownershipType",
    "ownershipPercentage",
    "includeUnknownHoldings",
    "excludeUndisclosedHoldings",
    "depth"
})
public class InquiryDetail {

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("duns")
    private String duns;
    @JsonProperty("isoAlpha2CountryCode")
    private String isoAlpha2CountryCode;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("productCode")
    private Integer productCode;
    @JsonProperty("ownershipType")
    private String ownershipType;
    @JsonProperty("ownershipPercentage")
    private Double ownershipPercentage;
    @JsonProperty("includeUnknownHoldings")
    private Boolean includeUnknownHoldings;
    @JsonProperty("excludeUndisclosedHoldings")
    private Boolean excludeUndisclosedHoldings;
    @JsonProperty("depth")
    private Integer depth;

    /**
     *
     * (Required)
     *
     * @return
     *     The duns
     */
    @JsonProperty("duns")
    public String getDuns() {
        return duns;
    }

    /**
     *
     * (Required)
     *
     * @param duns
     *     The duns
     */
    @JsonProperty("duns")
    public void setDuns(String duns) {
        this.duns = duns;
    }

    /**
     *
     * @return
     *     The isoAlpha2CountryCode
     */
    @JsonProperty("isoAlpha2CountryCode")
    public String getIsoAlpha2CountryCode() {
        return isoAlpha2CountryCode;
    }

    /**
     *
     * @param isoAlpha2CountryCode
     *     The isoAlpha2CountryCode
     */
    @JsonProperty("isoAlpha2CountryCode")
    public void setIsoAlpha2CountryCode(String isoAlpha2CountryCode) {
        this.isoAlpha2CountryCode = isoAlpha2CountryCode;
    }

    /**
     *
     * (Required)
     *
     * @return
     *     The productCode
     */
    @JsonProperty("productCode")
    public Integer getProductCode() {
        return productCode;
    }

    /**
     *
     * (Required)
     *
     * @param productCode
     *     The productCode
     */
    @JsonProperty("productCode")
    public void setProductCode(Integer productCode) {
        this.productCode = productCode;
    }

    /**
     *
     * @return
     *     The ownershipType
     */
    @JsonProperty("ownershipType")
    public String getOwnershipType() {
        return ownershipType;
    }

    /**
     *
     * @param ownershipType
     *     The ownershipType
     */
    @JsonProperty("ownershipType")
    public void setOwnershipType(String ownershipType) {
        this.ownershipType = ownershipType;
    }

    /**
     *
     * @return
     *     The ownershipPercentage
     */
    @JsonProperty("ownershipPercentage")
    public Double getOwnershipPercentage() {
        return ownershipPercentage;
    }

    /**
     *
     * @param ownershipPercentage
     *     The ownershipPercentage
     */
    @JsonProperty("ownershipPercentage")
    public void setOwnershipPercentage(Double ownershipPercentage) {
        this.ownershipPercentage = ownershipPercentage;
    }

    /**
     *
     * @return
     *     The includeUnknownHoldings
     */
    @JsonProperty("includeUnknownHoldings")
    public Boolean getIncludeUnknownHoldings() {
        return includeUnknownHoldings;
    }

    /**
     *
     * @param includeUnknownHoldings
     *     The includeUnknownHoldings
     */
    @JsonProperty("includeUnknownHoldings")
    public void setIncludeUnknownHoldings(Boolean includeUnknownHoldings) {
        this.includeUnknownHoldings = includeUnknownHoldings;
    }

    /**
     *
     * @return
     *     The excludeUndisclosedHoldings
     */
    @JsonProperty("excludeUndisclosedHoldings")
    public Boolean getExcludeUndisclosedHoldings() {
        return excludeUndisclosedHoldings;
    }

    /**
     *
     * @param excludeUndisclosedHoldings
     *     The excludeUndisclosedHoldings
     */
    @JsonProperty("excludeUndisclosedHoldings")
    public void setExcludeUndisclosedHoldings(Boolean excludeUndisclosedHoldings) {
        this.excludeUndisclosedHoldings = excludeUndisclosedHoldings;
    }

    /**
     * 
     * @return
     *     The depth
     */
    @JsonProperty("depth")
    public Integer getDepth() {
        return depth;
    }

    /**
     * 
     * @param depth
     *     The depth
     */
    @JsonProperty("depth")
    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(duns).append(isoAlpha2CountryCode).append(productCode).append(ownershipType).append(ownershipPercentage).append(includeUnknownHoldings).append(excludeUndisclosedHoldings).append(depth).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof InquiryDetail) == false) {
            return false;
        }
        InquiryDetail rhs = ((InquiryDetail) other);
        return new EqualsBuilder().append(duns, rhs.duns).append(isoAlpha2CountryCode, rhs.isoAlpha2CountryCode).append(productCode, rhs.productCode).append(ownershipType, rhs.ownershipType).append(ownershipPercentage, rhs.ownershipPercentage).append(includeUnknownHoldings, rhs.includeUnknownHoldings).append(excludeUndisclosedHoldings, rhs.excludeUndisclosedHoldings).append(depth, rhs.depth).isEquals();
    }

}
