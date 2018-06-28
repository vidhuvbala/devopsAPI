
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
    "name",
    "duns",
    "personId",
    "beneficiaryType",
    "businessEntityType",
    "beneficiaryAddress",
    "nationality",
    "dob",
    "directOwnershipPercentage",
    "indirectOwnershipPercentage",
    "beneficialOwnershipPercentage",
    "isBeneficiary",
    "ownershipUnavailableReason",
    "isMinority",
    "isOutofBusiness",
    "depth",
    "isUndisclosed",
    "beneficialOwnershipCommentary"
})
public class Beneficiary {

    @JsonProperty("name")
    private String name;
    @JsonProperty("duns")
    private String duns;
    @JsonProperty("personId")
    private String personId;
    /**
     *
     */
    @JsonProperty("beneficiaryType")
    private BeneficiaryType beneficiaryType;
    /**
     *
     */
    @JsonProperty("businessEntityType")
    private BusinessEntityType__ businessEntityType;
    @JsonProperty("beneficiaryAddress")
    private BeneficiaryAddress beneficiaryAddress;
    @JsonProperty("nationality")
    private String nationality;
    @JsonProperty("dob")
    private String dob;
    @JsonProperty("directOwnershipPercentage")
    private Double directOwnershipPercentage;
    @JsonProperty("indirectOwnershipPercentage")
    private Double indirectOwnershipPercentage;
    @JsonProperty("beneficialOwnershipPercentage")
    private Double beneficialOwnershipPercentage;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("isBeneficiary")
    private Boolean isBeneficiary;
    @JsonProperty("ownershipUnavailableReason")
    private List<OwnershipUnavailableReason_> ownershipUnavailableReason = new ArrayList<OwnershipUnavailableReason_>();
    @JsonProperty("isMinority")
    private Boolean isMinority;
    @JsonProperty("isOutofBusiness")
    private Boolean isOutofBusiness;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("depth")
    private Integer depth;
    @JsonProperty("isUndisclosed")
    private Boolean isUndisclosed;
    @JsonProperty("beneficialOwnershipCommentary")
    private List<BeneficialOwnershipCommentary> beneficialOwnershipCommentary = new ArrayList<BeneficialOwnershipCommentary>();

    /**
     *
     * @return
     *     The name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     *     The name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
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
     *     The personId
     */
    @JsonProperty("personId")
    public String getPersonId() {
        return personId;
    }

    /**
     *
     * @param personId
     *     The personId
     */
    @JsonProperty("personId")
    public void setPersonId(String personId) {
        this.personId = personId;
    }

    /**
     *
     * @return
     *     The beneficiaryType
     */
    @JsonProperty("beneficiaryType")
    public BeneficiaryType getBeneficiaryType() {
        return beneficiaryType;
    }

    /**
     *
     * @param beneficiaryType
     *     The beneficiaryType
     */
    @JsonProperty("beneficiaryType")
    public void setBeneficiaryType(BeneficiaryType beneficiaryType) {
        this.beneficiaryType = beneficiaryType;
    }

    /**
     *
     * @return
     *     The businessEntityType
     */
    @JsonProperty("businessEntityType")
    public BusinessEntityType__ getBusinessEntityType() {
        return businessEntityType;
    }

    /**
     *
     * @param businessEntityType
     *     The businessEntityType
     */
    @JsonProperty("businessEntityType")
    public void setBusinessEntityType(BusinessEntityType__ businessEntityType) {
        this.businessEntityType = businessEntityType;
    }

    /**
     *
     * @return
     *     The beneficiaryAddress
     */
    @JsonProperty("beneficiaryAddress")
    public BeneficiaryAddress getBeneficiaryAddress() {
        return beneficiaryAddress;
    }

    /**
     *
     * @param beneficiaryAddress
     *     The beneficiaryAddress
     */
    @JsonProperty("beneficiaryAddress")
    public void setBeneficiaryAddress(BeneficiaryAddress beneficiaryAddress) {
        this.beneficiaryAddress = beneficiaryAddress;
    }

    /**
     *
     * @return
     *     The nationality
     */
    @JsonProperty("nationality")
    public String getNationality() {
        return nationality;
    }

    /**
     *
     * @param nationality
     *     The nationality
     */
    @JsonProperty("nationality")
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    /**
     *
     * @return
     *     The dob
     */
    @JsonProperty("dob")
    public String getDob() {
        return dob;
    }

    /**
     *
     * @param dob
     *     The dob
     */
    @JsonProperty("dob")
    public void setDob(String dob) {
        this.dob = dob;
    }

    /**
     *
     * @return
     *     The directOwnershipPercentage
     */
    @JsonProperty("directOwnershipPercentage")
    public Double getDirectOwnershipPercentage() {
        return directOwnershipPercentage;
    }

    /**
     *
     * @param directOwnershipPercentage
     *     The directOwnershipPercentage
     */
    @JsonProperty("directOwnershipPercentage")
    public void setDirectOwnershipPercentage(Double directOwnershipPercentage) {
        this.directOwnershipPercentage = directOwnershipPercentage;
    }

    /**
     *
     * @return
     *     The indirectOwnershipPercentage
     */
    @JsonProperty("indirectOwnershipPercentage")
    public Double getIndirectOwnershipPercentage() {
        return indirectOwnershipPercentage;
    }

    /**
     *
     * @param indirectOwnershipPercentage
     *     The indirectOwnershipPercentage
     */
    @JsonProperty("indirectOwnershipPercentage")
    public void setIndirectOwnershipPercentage(Double indirectOwnershipPercentage) {
        this.indirectOwnershipPercentage = indirectOwnershipPercentage;
    }

    /**
     *
     * @return
     *     The beneficialOwnershipPercentage
     */
    @JsonProperty("beneficialOwnershipPercentage")
    public Double getBeneficialOwnershipPercentage() {
        return beneficialOwnershipPercentage;
    }

    /**
     *
     * @param beneficialOwnershipPercentage
     *     The beneficialOwnershipPercentage
     */
    @JsonProperty("beneficialOwnershipPercentage")
    public void setBeneficialOwnershipPercentage(Double beneficialOwnershipPercentage) {
        this.beneficialOwnershipPercentage = beneficialOwnershipPercentage;
    }

    /**
     *
     * (Required)
     *
     * @return
     *     The isBeneficiary
     */
    @JsonProperty("isBeneficiary")
    public Boolean getIsBeneficiary() {
        return isBeneficiary;
    }

    /**
     *
     * (Required)
     *
     * @param isBeneficiary
     *     The isBeneficiary
     */
    @JsonProperty("isBeneficiary")
    public void setIsBeneficiary(Boolean isBeneficiary) {
        this.isBeneficiary = isBeneficiary;
    }

    /**
     *
     * @return
     *     The ownershipUnavailableReason
     */
    @JsonProperty("ownershipUnavailableReason")
    public List<OwnershipUnavailableReason_> getOwnershipUnavailableReason() {
        return ownershipUnavailableReason;
    }

    /**
     *
     * @param ownershipUnavailableReason
     *     The ownershipUnavailableReason
     */
    @JsonProperty("ownershipUnavailableReason")
    public void setOwnershipUnavailableReason(List<OwnershipUnavailableReason_> ownershipUnavailableReason) {
        this.ownershipUnavailableReason = ownershipUnavailableReason;
    }

    /**
     *
     * @return
     *     The isMinority
     */
    @JsonProperty("isMinority")
    public Boolean getIsMinority() {
        return isMinority;
    }

    /**
     *
     * @param isMinority
     *     The isMinority
     */
    @JsonProperty("isMinority")
    public void setIsMinority(Boolean isMinority) {
        this.isMinority = isMinority;
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
     * (Required)
     *
     * @return
     *     The depth
     */
    @JsonProperty("depth")
    public Integer getDepth() {
        return depth;
    }

    /**
     *
     * (Required)
     *
     * @param depth
     *     The depth
     */
    @JsonProperty("depth")
    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    /**
     *
     * @return
     *     The isUndisclosed
     */
    @JsonProperty("isUndisclosed")
    public Boolean getIsUndisclosed() {
        return isUndisclosed;
    }

    /**
     *
     * @param isUndisclosed
     *     The isUndisclosed
     */
    @JsonProperty("isUndisclosed")
    public void setIsUndisclosed(Boolean isUndisclosed) {
        this.isUndisclosed = isUndisclosed;
    }

    /**
     *
     * @return
     *     The beneficialOwnershipCommentary
     */
    @JsonProperty("beneficialOwnershipCommentary")
    public List<BeneficialOwnershipCommentary> getBeneficialOwnershipCommentary() {
        return beneficialOwnershipCommentary;
    }

    /**
     *
     * @param beneficialOwnershipCommentary
     *     The beneficialOwnershipCommentary
     */
    @JsonProperty("beneficialOwnershipCommentary")
    public void setBeneficialOwnershipCommentary(List<BeneficialOwnershipCommentary> beneficialOwnershipCommentary) {
        this.beneficialOwnershipCommentary = beneficialOwnershipCommentary;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(duns).append(personId).append(beneficiaryType).append(businessEntityType).append(beneficiaryAddress).append(nationality).append(dob).append(directOwnershipPercentage).append(indirectOwnershipPercentage).append(beneficialOwnershipPercentage).append(isBeneficiary).append(ownershipUnavailableReason).append(isMinority).append(isOutofBusiness).append(depth).append(isUndisclosed).append(beneficialOwnershipCommentary).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Beneficiary) == false) {
            return false;
        }
        Beneficiary rhs = ((Beneficiary) other);
        return new EqualsBuilder().append(name, rhs.name).append(duns, rhs.duns).append(personId, rhs.personId).append(beneficiaryType, rhs.beneficiaryType).append(businessEntityType, rhs.businessEntityType).append(beneficiaryAddress, rhs.beneficiaryAddress).append(nationality, rhs.nationality).append(dob, rhs.dob).append(directOwnershipPercentage, rhs.directOwnershipPercentage).append(indirectOwnershipPercentage, rhs.indirectOwnershipPercentage).append(beneficialOwnershipPercentage, rhs.beneficialOwnershipPercentage).append(isBeneficiary, rhs.isBeneficiary).append(ownershipUnavailableReason, rhs.ownershipUnavailableReason).append(isMinority, rhs.isMinority).append(isOutofBusiness, rhs.isOutofBusiness).append(depth, rhs.depth).append(isUndisclosed, rhs.isUndisclosed).append(beneficialOwnershipCommentary, rhs.beneficialOwnershipCommentary).isEquals();
    }

}
