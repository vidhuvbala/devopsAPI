
package com.dnb.ubo.v4.jsonbeans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "sicCode",
    "description"
})
public class PrimarySIC {

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("sicCode")
    private String sicCode;
    @JsonProperty("description")
    private String description;

    /**
     *
     * (Required)
     *
     * @return
     *     The sicCode
     */
    @JsonProperty("sicCode")
    public String getSicCode() {
        return sicCode;
    }

    /**
     *
     * (Required)
     *
     * @param sicCode
     *     The sicCode
     */
    @JsonProperty("sicCode")
    public void setSicCode(String sicCode) {
        this.sicCode = sicCode;
    }

    /**
     *
     * @return
     *     The description
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     *     The description
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(sicCode).append(description).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof PrimarySIC) == false) {
            return false;
        }
        PrimarySIC rhs = ((PrimarySIC) other);
        return new EqualsBuilder().append(sicCode, rhs.sicCode).append(description, rhs.description).isEquals();
    }

}
