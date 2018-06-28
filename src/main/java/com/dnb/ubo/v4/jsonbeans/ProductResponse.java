
package com.dnb.ubo.v4.jsonbeans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "organization"
})
public class ProductResponse {

    /**
     *
     */
    @JsonProperty("organization")
    private Organization organization;

    /**
     *
     * @return
     *     The organization
     */
    @JsonProperty("organization")
    public Organization getOrganization() {
        return organization;
    }

    /**
     *
     * @param organization
     *     The organization
     */
    @JsonProperty("organization")
    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(organization).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ProductResponse) == false) {
            return false;
        }
        ProductResponse rhs = ((ProductResponse) other);
        return new EqualsBuilder().append(organization, rhs.organization).isEquals();
    }

}
