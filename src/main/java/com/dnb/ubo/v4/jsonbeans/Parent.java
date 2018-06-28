
package com.dnb.ubo.v4.jsonbeans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "parentDuns",
    "parentName"
})
public class Parent {

    @JsonProperty("parentDuns")
    private String parentDuns;
    @JsonProperty("parentName")
    private String parentName;

    /**
     *
     * @return
     *     The parentDuns
     */
    @JsonProperty("parentDuns")
    public String getParentDuns() {
        return parentDuns;
    }

    /**
     *
     * @param parentDuns
     *     The parentDuns
     */
    @JsonProperty("parentDuns")
    public void setParentDuns(String parentDuns) {
        this.parentDuns = parentDuns;
    }

    /**
     *
     * @return
     *     The parentName
     */
    @JsonProperty("parentName")
    public String getParentName() {
        return parentName;
    }

    /**
     *
     * @param parentName
     *     The parentName
     */
    @JsonProperty("parentName")
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(parentDuns).append(parentName).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Parent) == false) {
            return false;
        }
        Parent rhs = ((Parent) other);
        return new EqualsBuilder().append(parentDuns, rhs.parentDuns).append(parentName, rhs.parentName).isEquals();
    }

}
