
package com.dnb.ubo.v4.jsonbeans;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "countrySummary",
    "unknownCount"
})
public class NodeSummaryByGeography {

    /**
     * Records information on the count of nodes per country within the ownership structure for the subject company .
     * Records information on the count of nodes per country within the ownership structure for the subject company .
     *
     *
     */
    @JsonProperty("countrySummary")
    @JsonPropertyDescription("")
    private List<CountrySummary> countrySummary = new ArrayList<CountrySummary>();
    /**
     * The number of nodes that do not have a country code within the ownership structure
     *
     *
     */
    @JsonProperty("unknownCount")
    @JsonPropertyDescription("")
    private Integer unknownCount;

    /**
     * Records information on the count of nodes per country within the ownership structure for the subject company .
     * Records information on the count of nodes per country within the ownership structure for the subject company .
     *
     *
     * @return
     *     The countrySummary
     */
    @JsonProperty("countrySummary")
    public List<CountrySummary> getCountrySummary() {
        return countrySummary;
    }

    /**
     * Records information on the count of nodes per country within the ownership structure for the subject company .
     * Records information on the count of nodes per country within the ownership structure for the subject company .
     *
     *
     * @param countrySummary
     *     The countrySummary
     */
    @JsonProperty("countrySummary")
    public void setCountrySummary(List<CountrySummary> countrySummary) {
        this.countrySummary = countrySummary;
    }

    /**
     * The number of nodes that do not have a country code within the ownership structure
     *
     *
     * @return
     *     The unknownCount
     */
    @JsonProperty("unknownCount")
    public Integer getUnknownCount() {
        return unknownCount;
    }

    /**
     * The number of nodes that do not have a country code within the ownership structure
     *
     *
     * @param unknownCount
     *     The unknownCount
     */
    @JsonProperty("unknownCount")
    public void setUnknownCount(Integer unknownCount) {
        this.unknownCount = unknownCount;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(countrySummary).append(unknownCount).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof NodeSummaryByGeography) == false) {
            return false;
        }
        NodeSummaryByGeography rhs = ((NodeSummaryByGeography) other);
        return new EqualsBuilder().append(countrySummary, rhs.countrySummary).append(unknownCount, rhs.unknownCount).isEquals();
    }

}
