
package com.dnb.ubo.v4.jsonbeans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "isoAlpha2Code",
    "isoName"
})
public class AddressCountry_ {

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("isoAlpha2Code")
    private String isoAlpha2Code;
    @JsonProperty("isoName")
    private String isoName;

    /**
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
     *
     * @return
     *     The isoName
     */
    @JsonProperty("isoName")
    public String getIsoName() {
        return isoName;
    }

    /**
     *
     * @param isoName
     *     The isoName
     */
    @JsonProperty("isoName")
    public void setIsoName(String isoName) {
        this.isoName = isoName;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(isoAlpha2Code).append(isoName).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof AddressCountry_) == false) {
            return false;
        }
        AddressCountry_ rhs = ((AddressCountry_) other);
        return new EqualsBuilder().append(isoAlpha2Code, rhs.isoAlpha2Code).append(isoName, rhs.isoName).isEquals();
    }

}
