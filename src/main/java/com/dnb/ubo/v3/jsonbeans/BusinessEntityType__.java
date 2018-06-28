
package com.dnb.ubo.v3.jsonbeans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "code",
    "description"
})
public class BusinessEntityType__ {

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("code")
    private Integer code;
    @JsonProperty("description")
    private String description;

    /**
     *
     * (Required)
     *
     * @return
     *     The code
     */
    @JsonProperty("code")
    public Integer getCode() {
        return code;
    }

    /**
     *
     * (Required)
     *
     * @param code
     *     The code
     */
    @JsonProperty("code")
    public void setCode(Integer code) {
        this.code = code;
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
        return new HashCodeBuilder().append(code).append(description).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof BusinessEntityType__) == false) {
            return false;
        }
        BusinessEntityType__ rhs = ((BusinessEntityType__) other);
        return new EqualsBuilder().append(code, rhs.code).append(description, rhs.description).isEquals();
    }

}
