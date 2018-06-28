
package com.dnb.ubo.v4.jsonbeans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "nodeCount",
    "relationshipCount",
    "maxDepth",
    "totalAllocatedBeneficialOwnership",
    "organizationCount",
    "individualCount",
    "entityCount",
    "corporateBeneficiaryCount",
    "stateOwnedOrganizationCount",
    "governmentOrganizationCount",
    "publiclyTradingOrganizationCount",
    "privatelyHeldOrganizationCount",
    "nodeSummaryByGeography"
})
public class OwnershipSummary {

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("nodeCount")
    private Integer nodeCount;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("relationshipCount")
    private Integer relationshipCount;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("maxDepth")
    private Integer maxDepth;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("totalAllocatedBeneficialOwnership")
    private Double totalAllocatedBeneficialOwnership;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("organizationCount")
    private Integer organizationCount;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("individualCount")
    private Integer individualCount;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("entityCount")
    private Integer entityCount;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("corporateBeneficiaryCount")
    private Integer corporateBeneficiaryCount;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("stateOwnedOrganizationCount")
    private Integer stateOwnedOrganizationCount;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("governmentOrganizationCount")
    private Integer governmentOrganizationCount;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("publiclyTradingOrganizationCount")
    private Integer publiclyTradingOrganizationCount;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("privatelyHeldOrganizationCount")
    private Integer privatelyHeldOrganizationCount;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("nodeSummaryByGeography")
    private NodeSummaryByGeography nodeSummaryByGeography;

    /**
     *
     * (Required)
     *
     * @return
     *     The nodeCount
     */
    @JsonProperty("nodeCount")
    public Integer getNodeCount() {
        return nodeCount;
    }

    /**
     *
     * (Required)
     *
     * @param nodeCount
     *     The nodeCount
     */
    @JsonProperty("nodeCount")
    public void setNodeCount(Integer nodeCount) {
        this.nodeCount = nodeCount;
    }

    /**
     *
     * (Required)
     *
     * @return
     *     The relationshipCount
     */
    @JsonProperty("relationshipCount")
    public Integer getRelationshipCount() {
        return relationshipCount;
    }

    /**
     *
     * (Required)
     *
     * @param relationshipCount
     *     The relationshipCount
     */
    @JsonProperty("relationshipCount")
    public void setRelationshipCount(Integer relationshipCount) {
        this.relationshipCount = relationshipCount;
    }

    /**
     *
     * (Required)
     *
     * @return
     *     The maxDepth
     */
    @JsonProperty("maxDepth")
    public Integer getMaxDepth() {
        return maxDepth;
    }

    /**
     *
     * (Required)
     *
     * @param maxDepth
     *     The maxDepth
     */
    @JsonProperty("maxDepth")
    public void setMaxDepth(Integer maxDepth) {
        this.maxDepth = maxDepth;
    }

    /**
     *
     * (Required)
     *
     * @return
     *     The totalAllocatedBeneficialOwnership
     */
    @JsonProperty("totalAllocatedBeneficialOwnership")
    public Double getTotalAllocatedBeneficialOwnership() {
        return totalAllocatedBeneficialOwnership;
    }

    /**
     *
     * (Required)
     *
     * @param totalAllocatedBeneficialOwnership
     *     The totalAllocatedBeneficialOwnership
     */
    @JsonProperty("totalAllocatedBeneficialOwnership")
    public void setTotalAllocatedBeneficialOwnership(Double totalAllocatedBeneficialOwnership) {
        this.totalAllocatedBeneficialOwnership = totalAllocatedBeneficialOwnership;
    }

    /**
     *
     * (Required)
     *
     * @return
     *     The organizationCount
     */
    @JsonProperty("organizationCount")
    public Integer getOrganizationCount() {
        return organizationCount;
    }

    /**
     *
     * (Required)
     *
     * @param organizationCount
     *     The organizationCount
     */
    @JsonProperty("organizationCount")
    public void setOrganizationCount(Integer organizationCount) {
        this.organizationCount = organizationCount;
    }

    /**
     *
     * (Required)
     *
     * @return
     *     The individualCount
     */
    @JsonProperty("individualCount")
    public Integer getIndividualCount() {
        return individualCount;
    }

    /**
     *
     * (Required)
     *
     * @param individualCount
     *     The individualCount
     */
    @JsonProperty("individualCount")
    public void setIndividualCount(Integer individualCount) {
        this.individualCount = individualCount;
    }

    /**
     *
     * (Required)
     *
     * @return
     *     The entityCount
     */
    @JsonProperty("entityCount")
    public Integer getEntityCount() {
        return entityCount;
    }

    /**
     *
     * (Required)
     *
     * @param entityCount
     *     The entityCount
     */
    @JsonProperty("entityCount")
    public void setEntityCount(Integer entityCount) {
        this.entityCount = entityCount;
    }

    /**
     *
     * (Required)
     *
     * @return
     *     The corporateBeneficiaryCount
     */
    @JsonProperty("corporateBeneficiaryCount")
    public Integer getCorporateBeneficiaryCount() {
        return corporateBeneficiaryCount;
    }

    /**
     *
     * (Required)
     *
     * @param corporateBeneficiaryCount
     *     The corporateBeneficiaryCount
     */
    @JsonProperty("corporateBeneficiaryCount")
    public void setCorporateBeneficiaryCount(Integer corporateBeneficiaryCount) {
        this.corporateBeneficiaryCount = corporateBeneficiaryCount;
    }

    /**
     *
     * (Required)
     *
     * @return
     *     The stateOwnedOrganizationCount
     */
    @JsonProperty("stateOwnedOrganizationCount")
    public Integer getStateOwnedOrganizationCount() {
        return stateOwnedOrganizationCount;
    }

    /**
     * 
     * (Required)
     * 
     * @param stateOwnedOrganizationCount
     *     The stateOwnedOrganizationCount
     */
    @JsonProperty("stateOwnedOrganizationCount")
    public void setStateOwnedOrganizationCount(Integer stateOwnedOrganizationCount) {
        this.stateOwnedOrganizationCount = stateOwnedOrganizationCount;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The governmentOrganizationCount
     */
    @JsonProperty("governmentOrganizationCount")
    public Integer getGovernmentOrganizationCount() {
        return governmentOrganizationCount;
    }

    /**
     * 
     * (Required)
     * 
     * @param governmentOrganizationCount
     *     The governmentOrganizationCount
     */
    @JsonProperty("governmentOrganizationCount")
    public void setGovernmentOrganizationCount(Integer governmentOrganizationCount) {
        this.governmentOrganizationCount = governmentOrganizationCount;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The publiclyTradingOrganizationCount
     */
    @JsonProperty("publiclyTradingOrganizationCount")
    public Integer getPubliclyTradingOrganizationCount() {
        return publiclyTradingOrganizationCount;
    }

    /**
     * 
     * (Required)
     * 
     * @param publiclyTradingOrganizationCount
     *     The publiclyTradingOrganizationCount
     */
    @JsonProperty("publiclyTradingOrganizationCount")
    public void setPubliclyTradingOrganizationCount(Integer publiclyTradingOrganizationCount) {
        this.publiclyTradingOrganizationCount = publiclyTradingOrganizationCount;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The privatelyHeldOrganizationCount
     */
    @JsonProperty("privatelyHeldOrganizationCount")
    public Integer getPrivatelyHeldOrganizationCount() {
        return privatelyHeldOrganizationCount;
    }

    /**
     * 
     * (Required)
     * 
     * @param privatelyHeldOrganizationCount
     *     The privatelyHeldOrganizationCount
     */
    @JsonProperty("privatelyHeldOrganizationCount")
    public void setPrivatelyHeldOrganizationCount(Integer privatelyHeldOrganizationCount) {
        this.privatelyHeldOrganizationCount = privatelyHeldOrganizationCount;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The nodeSummaryByGeography
     */
    @JsonProperty("nodeSummaryByGeography")
    public NodeSummaryByGeography getNodeSummaryByGeography() {
        return nodeSummaryByGeography;
    }

    /**
     *
     * (Required)
     *
     * @param nodeSummaryByGeography
     *     The nodeSummaryByGeography
     */
    @JsonProperty("nodeSummaryByGeography")
    public void setNodeSummaryByGeography(NodeSummaryByGeography nodeSummaryByGeography) {
        this.nodeSummaryByGeography = nodeSummaryByGeography;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(nodeCount).append(relationshipCount).append(maxDepth).append(totalAllocatedBeneficialOwnership).append(organizationCount).append(individualCount).append(entityCount).append(corporateBeneficiaryCount).append(stateOwnedOrganizationCount).append(governmentOrganizationCount).append(publiclyTradingOrganizationCount).append(privatelyHeldOrganizationCount).append(nodeSummaryByGeography).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof OwnershipSummary) == false) {
            return false;
        }
        OwnershipSummary rhs = ((OwnershipSummary) other);
        return new EqualsBuilder().append(nodeCount, rhs.nodeCount).append(relationshipCount, rhs.relationshipCount).append(maxDepth, rhs.maxDepth).append(totalAllocatedBeneficialOwnership, rhs.totalAllocatedBeneficialOwnership).append(organizationCount, rhs.organizationCount).append(individualCount, rhs.individualCount).append(entityCount, rhs.entityCount).append(corporateBeneficiaryCount, rhs.corporateBeneficiaryCount).append(stateOwnedOrganizationCount, rhs.stateOwnedOrganizationCount).append(governmentOrganizationCount, rhs.governmentOrganizationCount).append(publiclyTradingOrganizationCount, rhs.publiclyTradingOrganizationCount).append(privatelyHeldOrganizationCount, rhs.privatelyHeldOrganizationCount).append(nodeSummaryByGeography, rhs.nodeSummaryByGeography).isEquals();
    }

}
