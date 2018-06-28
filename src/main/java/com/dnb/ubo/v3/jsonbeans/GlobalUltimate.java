
package com.dnb.ubo.v3.jsonbeans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "globalUltimateDuns",
    "globalUltimateName"
})
public class GlobalUltimate {

    @JsonProperty("globalUltimateDuns")
    private String globalUltimateDuns;
    @JsonProperty("globalUltimateName")
    private String globalUltimateName;

    /**
     *
     * @return
     *     The globalUltimateDuns
     */
    @JsonProperty("globalUltimateDuns")
    public String getGlobalUltimateDuns() {
        return globalUltimateDuns;
    }

    /**
     *
     * @param globalUltimateDuns
     *     The globalUltimateDuns
     */
    @JsonProperty("globalUltimateDuns")
    public void setGlobalUltimateDuns(String globalUltimateDuns) {
        this.globalUltimateDuns = globalUltimateDuns;
    }

    /**
     *
     * @return
     *     The globalUltimateName
     */
    @JsonProperty("globalUltimateName")
    public String getGlobalUltimateName() {
        return globalUltimateName;
    }

    /**
     *
     * @param globalUltimateName
     *     The globalUltimateName
     */
    @JsonProperty("globalUltimateName")
    public void setGlobalUltimateName(String globalUltimateName) {
        this.globalUltimateName = globalUltimateName;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(globalUltimateDuns).append(globalUltimateName).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GlobalUltimate) == false) {
            return false;
        }
        GlobalUltimate rhs = ((GlobalUltimate) other);
        return new EqualsBuilder().append(globalUltimateDuns, rhs.globalUltimateDuns).append(globalUltimateName, rhs.globalUltimateName).isEquals();
    }

}
