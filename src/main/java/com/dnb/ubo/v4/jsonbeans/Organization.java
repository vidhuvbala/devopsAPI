
package com.dnb.ubo.v4.jsonbeans;

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
    "duns",
    "organizationName",
    "address",
    "businessEntityType",
    "primarySIC",
    "isOutofBusiness",
    "controlType",
    "ownershipSummary",
    "ownershipStructure",
    "beneficiaries",
    "corporateLinkage"
})
public class Organization {

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("duns")
    private String duns;
    @JsonProperty("organizationName")
    private String organizationName;
    @JsonProperty("address")
    private Address address;
    /**
     *
     */
    @JsonProperty("businessEntityType")
    private BusinessEntityType businessEntityType;
    /**
     *
     */
    @JsonProperty("primarySIC")
    private PrimarySIC primarySIC;
    @JsonProperty("isOutofBusiness")
    private Boolean isOutofBusiness;
    /**
     *
     */
    @JsonProperty("controlType")
    private ControlType controlType;
    /**
     * 
     * (Required)
     *
     */
    @JsonProperty("ownershipSummary")
    private OwnershipSummary ownershipSummary;
    /**
     *
     */
    @JsonProperty("ownershipStructure")
    private OwnershipStructure ownershipStructure;
    @JsonProperty("beneficiaries")
    private List<Beneficiary> beneficiaries = new ArrayList<Beneficiary>();
    @JsonProperty("corporateLinkage")
    private CorporateLinkage corporateLinkage;

    /**
     *
     * (Required)
     *
     * @return
     *     The duns
     */
    @JsonProperty("duns")
    public String getDuns() {
        return duns;
    }

    /**
     *
     * (Required)
     *
     * @param duns
     *     The duns
     */
    @JsonProperty("duns")
    public void setDuns(String duns) {
        this.duns = duns;
    }

    /**
     *
     * @return
     *     The organizationName
     */
    @JsonProperty("organizationName")
    public String getOrganizationName() {
        return organizationName;
    }

    /**
     *
     * @param organizationName
     *     The organizationName
     */
    @JsonProperty("organizationName")
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    /**
     *
     * @return
     *     The address
     */
    @JsonProperty("address")
    public Address getAddress() {
        return address;
    }

    /**
     *
     * @param address
     *     The address
     */
    @JsonProperty("address")
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     *
     * @return
     *     The businessEntityType
     */
    @JsonProperty("businessEntityType")
    public BusinessEntityType getBusinessEntityType() {
        return businessEntityType;
    }

    /**
     *
     * @param businessEntityType
     *     The businessEntityType
     */
    @JsonProperty("businessEntityType")
    public void setBusinessEntityType(BusinessEntityType businessEntityType) {
        this.businessEntityType = businessEntityType;
    }

    /**
     *
     * @return
     *     The primarySIC
     */
    @JsonProperty("primarySIC")
    public PrimarySIC getPrimarySIC() {
        return primarySIC;
    }

    /**
     *
     * @param primarySIC
     *     The primarySIC
     */
    @JsonProperty("primarySIC")
    public void setPrimarySIC(PrimarySIC primarySIC) {
        this.primarySIC = primarySIC;
    }

    /**
     *
     * @return
     *     The isOutofBusiness
     */
    @JsonProperty("isOutofBusiness")
    public Boolean getIsOutofBusiness() {
        return isOutofBusiness;
    }

    /**
     *
     * @param isOutofBusiness
     *     The isOutofBusiness
     */
    @JsonProperty("isOutofBusiness")
    public void setIsOutofBusiness(Boolean isOutofBusiness) {
        this.isOutofBusiness = isOutofBusiness;
    }

    /**
     *
     * @return
     *     The controlType
     */
    @JsonProperty("controlType")
    public ControlType getControlType() {
        return controlType;
    }

    /**
     * 
     * @param controlType
     *     The controlType
     */
    @JsonProperty("controlType")
    public void setControlType(ControlType controlType) {
        this.controlType = controlType;
    }

    /**
     * 
     * (Required)
     *
     * @return
     *     The ownershipSummary
     */
    @JsonProperty("ownershipSummary")
    public OwnershipSummary getOwnershipSummary() {
        return ownershipSummary;
    }

    /**
     *
     * (Required)
     *
     * @param ownershipSummary
     *     The ownershipSummary
     */
    @JsonProperty("ownershipSummary")
    public void setOwnershipSummary(OwnershipSummary ownershipSummary) {
        this.ownershipSummary = ownershipSummary;
    }

    /**
     *
     * @return
     *     The ownershipStructure
     */
    @JsonProperty("ownershipStructure")
    public OwnershipStructure getOwnershipStructure() {
        return ownershipStructure;
    }

    /**
     *
     * @param ownershipStructure
     *     The ownershipStructure
     */
    @JsonProperty("ownershipStructure")
    public void setOwnershipStructure(OwnershipStructure ownershipStructure) {
        this.ownershipStructure = ownershipStructure;
    }

    /**
     *
     * @return
     *     The beneficiaries
     */
    @JsonProperty("beneficiaries")
    public List<Beneficiary> getBeneficiaries() {
        return beneficiaries;
    }

    /**
     *
     * @param beneficiaries
     *     The beneficiaries
     */
    @JsonProperty("beneficiaries")
    public void setBeneficiaries(List<Beneficiary> beneficiaries) {
        this.beneficiaries = beneficiaries;
    }

    /**
     *
     * @return
     *     The corporateLinkage
     */
    @JsonProperty("corporateLinkage")
    public CorporateLinkage getCorporateLinkage() {
        return corporateLinkage;
    }

    /**
     *
     * @param corporateLinkage
     *     The corporateLinkage
     */
    @JsonProperty("corporateLinkage")
    public void setCorporateLinkage(CorporateLinkage corporateLinkage) {
        this.corporateLinkage = corporateLinkage;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(duns).append(organizationName).append(address).append(businessEntityType).append(primarySIC).append(isOutofBusiness).append(controlType).append(ownershipSummary).append(ownershipStructure).append(beneficiaries).append(corporateLinkage).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Organization) == false) {
            return false;
        }
        Organization rhs = ((Organization) other);
        return new EqualsBuilder().append(duns, rhs.duns).append(organizationName, rhs.organizationName).append(address, rhs.address).append(businessEntityType, rhs.businessEntityType).append(primarySIC, rhs.primarySIC).append(isOutofBusiness, rhs.isOutofBusiness).append(controlType, rhs.controlType).append(ownershipSummary, rhs.ownershipSummary).append(ownershipStructure, rhs.ownershipStructure).append(beneficiaries, rhs.beneficiaries).append(corporateLinkage, rhs.corporateLinkage).isEquals();
    }

}
