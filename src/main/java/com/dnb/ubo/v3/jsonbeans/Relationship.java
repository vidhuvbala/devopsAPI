
package com.dnb.ubo.v3.jsonbeans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "relationshipId",
    "fromMember",
    "toMember",
    "relationshipType",
    "sharePercentage",
    "isFilteredOut"
})
public class Relationship {

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("relationshipId")
    private Integer relationshipId;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("fromMember")
    private Integer fromMember;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("toMember")
    private Integer toMember;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("relationshipType")
    private String relationshipType;
    @JsonProperty("sharePercentage")
    private Double sharePercentage;
    @JsonProperty("isFilteredOut")
    private Boolean isFilteredOut;

    /**
     *
     * (Required)
     *
     * @return
     *     The relationshipId
     */
    @JsonProperty("relationshipId")
    public Integer getRelationshipId() {
        return relationshipId;
    }

    /**
     *
     * (Required)
     *
     * @param relationshipId
     *     The relationshipId
     */
    @JsonProperty("relationshipId")
    public void setRelationshipId(Integer relationshipId) {
        this.relationshipId = relationshipId;
    }

    /**
     *
     * (Required)
     *
     * @return
     *     The fromMember
     */
    @JsonProperty("fromMember")
    public Integer getFromMember() {
        return fromMember;
    }

    /**
     *
     * (Required)
     *
     * @param fromMember
     *     The fromMember
     */
    @JsonProperty("fromMember")
    public void setFromMember(Integer fromMember) {
        this.fromMember = fromMember;
    }

    /**
     *
     * (Required)
     *
     * @return
     *     The toMember
     */
    @JsonProperty("toMember")
    public Integer getToMember() {
        return toMember;
    }

    /**
     *
     * (Required)
     *
     * @param toMember
     *     The toMember
     */
    @JsonProperty("toMember")
    public void setToMember(Integer toMember) {
        this.toMember = toMember;
    }

    /**
     *
     * (Required)
     *
     * @return
     *     The relationshipType
     */
    @JsonProperty("relationshipType")
    public String getRelationshipType() {
        return relationshipType;
    }

    /**
     *
     * (Required)
     *
     * @param relationshipType
     *     The relationshipType
     */
    @JsonProperty("relationshipType")
    public void setRelationshipType(String relationshipType) {
        this.relationshipType = relationshipType;
    }

    /**
     *
     * @return
     *     The sharePercentage
     */
    @JsonProperty("sharePercentage")
    public Double getSharePercentage() {
        return sharePercentage;
    }

    /**
     *
     * @param sharePercentage
     *     The sharePercentage
     */
    @JsonProperty("sharePercentage")
    public void setSharePercentage(Double sharePercentage) {
        this.sharePercentage = sharePercentage;
    }

    /**
     *
     * @return
     *     The isFilteredOut
     */
    @JsonProperty("isFilteredOut")
    public Boolean getIsFilteredOut() {
        return isFilteredOut;
    }

    /**
     *
     * @param isFilteredOut
     *     The isFilteredOut
     */
    @JsonProperty("isFilteredOut")
    public void setIsFilteredOut(Boolean isFilteredOut) {
        this.isFilteredOut = isFilteredOut;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(relationshipId).append(fromMember).append(toMember).append(relationshipType).append(sharePercentage).append(isFilteredOut).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Relationship) == false) {
            return false;
        }
        Relationship rhs = ((Relationship) other);
        return new EqualsBuilder().append(relationshipId, rhs.relationshipId).append(fromMember, rhs.fromMember).append(toMember, rhs.toMember).append(relationshipType, rhs.relationshipType).append(sharePercentage, rhs.sharePercentage).append(isFilteredOut, rhs.isFilteredOut).isEquals();
    }

}
