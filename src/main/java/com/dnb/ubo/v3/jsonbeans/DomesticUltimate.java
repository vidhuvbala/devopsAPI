
package com.dnb.ubo.v3.jsonbeans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "domesticUltimateDuns",
    "domesticUltimateName"
})
public class DomesticUltimate {

    @JsonProperty("domesticUltimateDuns")
    private String domesticUltimateDuns;
    @JsonProperty("domesticUltimateName")
    private String domesticUltimateName;

    /**
     *
     * @return
     *     The domesticUltimateDuns
     */
    @JsonProperty("domesticUltimateDuns")
    public String getDomesticUltimateDuns() {
        return domesticUltimateDuns;
    }

    /**
     *
     * @param domesticUltimateDuns
     *     The domesticUltimateDuns
     */
    @JsonProperty("domesticUltimateDuns")
    public void setDomesticUltimateDuns(String domesticUltimateDuns) {
        this.domesticUltimateDuns = domesticUltimateDuns;
    }

    /**
     *
     * @return
     *     The domesticUltimateName
     */
    @JsonProperty("domesticUltimateName")
    public String getDomesticUltimateName() {
        return domesticUltimateName;
    }

    /**
     *
     * @param domesticUltimateName
     *     The domesticUltimateName
     */
    @JsonProperty("domesticUltimateName")
    public void setDomesticUltimateName(String domesticUltimateName) {
        this.domesticUltimateName = domesticUltimateName;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(domesticUltimateDuns).append(domesticUltimateName).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof DomesticUltimate) == false) {
            return false;
        }
        DomesticUltimate rhs = ((DomesticUltimate) other);
        return new EqualsBuilder().append(domesticUltimateDuns, rhs.domesticUltimateDuns).append(domesticUltimateName, rhs.domesticUltimateName).isEquals();
    }

}
