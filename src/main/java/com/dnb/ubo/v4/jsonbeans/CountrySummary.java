
package com.dnb.ubo.v4.jsonbeans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "isoAlpha2Code",
    "nodeCount"
})
public class CountrySummary {

    /**
     * The two-letter country code, defined in the scheme published by International Organization for Standardization (ISO) identifying the country.
     *
     * (Required)
     *
     */
    @JsonProperty("isoAlpha2Code")
    @JsonPropertyDescription("")
    private String isoAlpha2Code;
    /**
     * The number of nodes for the Country Code
     *
     * (Required)
     *
     */
    @JsonProperty("nodeCount")
    @JsonPropertyDescription("")
    private Integer nodeCount;

    /**
     * The two-letter country code, defined in the scheme published by International Organization for Standardization (ISO) identifying the country.
     *
     * (Required)
     *
     * @return
     *     The isoAlpha2Code
     */
    @JsonProperty("isoAlpha2Code")
    public String getIsoAlpha2Code() {
        return isoAlpha2Code;
    }

    /**
     * The two-letter country code, defined in the scheme published by International Organization for Standardization (ISO) identifying the country.
     *
     * (Required)
     *
     * @param isoAlpha2Code
     *     The isoAlpha2Code
     */
    @JsonProperty("isoAlpha2Code")
    public void setIsoAlpha2Code(String isoAlpha2Code) {
        this.isoAlpha2Code = isoAlpha2Code;
    }

    /**
     * The number of nodes for the Country Code
     *
     * (Required)
     *
     * @return
     *     The nodeCount
     */
    @JsonProperty("nodeCount")
    public Integer getNodeCount() {
        return nodeCount;
    }

    /**
     * The number of nodes for the Country Code
     *
     * (Required)
     *
     * @param nodeCount
     *     The nodeCount
     */
    @JsonProperty("nodeCount")
    public void setNodeCount(Integer nodeCount) {
        this.nodeCount = nodeCount;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(isoAlpha2Code).append(nodeCount).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CountrySummary) == false) {
            return false;
        }
        CountrySummary rhs = ((CountrySummary) other);
        return new EqualsBuilder().append(isoAlpha2Code, rhs.isoAlpha2Code).append(nodeCount, rhs.nodeCount).isEquals();
    }

}
