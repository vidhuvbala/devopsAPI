
package com.dnb.ubo.v3.jsonbeans;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "members",
    "relationships"
})
public class OwnershipStructure {

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("members")
    private List<Member> members = new ArrayList<Member>();
    @JsonProperty("relationships")
    private List<Relationship> relationships = new ArrayList<Relationship>();

    /**
     *
     * (Required)
     *
     * @return
     *     The members
     */
    @JsonProperty("members")
    public List<Member> getMembers() {
        return members;
    }

    /**
     *
     * (Required)
     *
     * @param members
     *     The members
     */
    @JsonProperty("members")
    public void setMembers(List<Member> members) {
        this.members = members;
    }

    /**
     *
     * @return
     *     The relationships
     */
    @JsonProperty("relationships")
    public List<Relationship> getRelationships() {
        return relationships;
    }

    /**
     *
     * @param relationships
     *     The relationships
     */
    @JsonProperty("relationships")
    public void setRelationships(List<Relationship> relationships) {
        this.relationships = relationships;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(members).append(relationships).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof OwnershipStructure) == false) {
            return false;
        }
        OwnershipStructure rhs = ((OwnershipStructure) other);
        return new EqualsBuilder().append(members, rhs.members).append(relationships, rhs.relationships).isEquals();
    }

}
