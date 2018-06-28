
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
    "memberId",
    "memberName",
    "duns",
    "personId",
    "memberType",
    "businessEntityType",
    "memberAddress",
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
    "isFilteredOut"
})
public class Member {

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("memberId")
    private Integer memberId;
    @JsonProperty("memberName")
    private String memberName;
    @JsonProperty("duns")
    private String duns;
    @JsonProperty("personId")
    private String personId;
    /**
     *
     */
    @JsonProperty("memberType")
    private MemberType memberType;
    /**
     *
     */
    @JsonProperty("businessEntityType")
    private BusinessEntityType_ businessEntityType;
    @JsonProperty("memberAddress")
    private MemberAddress memberAddress;
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
    private List<OwnershipUnavailableReason> ownershipUnavailableReason = new ArrayList<OwnershipUnavailableReason>();
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
    @JsonProperty("isFilteredOut")
    private Boolean isFilteredOut;

    /**
     *
     * (Required)
     *
     * @return
     *     The memberId
     */
    @JsonProperty("memberId")
    public Integer getMemberId() {
        return memberId;
    }

    /**
     *
     * (Required)
     *
     * @param memberId
     *     The memberId
     */
    @JsonProperty("memberId")
    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    /**
     *
     * @return
     *     The memberName
     */
    @JsonProperty("memberName")
    public String getMemberName() {
        return memberName;
    }

    /**
     *
     * @param memberName
     *     The memberName
     */
    @JsonProperty("memberName")
    public void setMemberName(String memberName) {
        this.memberName = memberName;
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
     *     The memberType
     */
    @JsonProperty("memberType")
    public MemberType getMemberType() {
        return memberType;
    }

    /**
     *
     * @param memberType
     *     The memberType
     */
    @JsonProperty("memberType")
    public void setMemberType(MemberType memberType) {
        this.memberType = memberType;
    }

    /**
     *
     * @return
     *     The businessEntityType
     */
    @JsonProperty("businessEntityType")
    public BusinessEntityType_ getBusinessEntityType() {
        return businessEntityType;
    }

    /**
     *
     * @param businessEntityType
     *     The businessEntityType
     */
    @JsonProperty("businessEntityType")
    public void setBusinessEntityType(BusinessEntityType_ businessEntityType) {
        this.businessEntityType = businessEntityType;
    }

    /**
     *
     * @return
     *     The memberAddress
     */
    @JsonProperty("memberAddress")
    public MemberAddress getMemberAddress() {
        return memberAddress;
    }

    /**
     *
     * @param memberAddress
     *     The memberAddress
     */
    @JsonProperty("memberAddress")
    public void setMemberAddress(MemberAddress memberAddress) {
        this.memberAddress = memberAddress;
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
    public List<OwnershipUnavailableReason> getOwnershipUnavailableReason() {
        return ownershipUnavailableReason;
    }

    /**
     *
     * @param ownershipUnavailableReason
     *     The ownershipUnavailableReason
     */
    @JsonProperty("ownershipUnavailableReason")
    public void setOwnershipUnavailableReason(List<OwnershipUnavailableReason> ownershipUnavailableReason) {
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
        return new HashCodeBuilder().append(memberId).append(memberName).append(duns).append(personId).append(memberType).append(businessEntityType).append(memberAddress).append(nationality).append(dob).append(directOwnershipPercentage).append(indirectOwnershipPercentage).append(beneficialOwnershipPercentage).append(isBeneficiary).append(ownershipUnavailableReason).append(isMinority).append(isOutofBusiness).append(depth).append(isFilteredOut).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Member) == false) {
            return false;
        }
        Member rhs = ((Member) other);
        return new EqualsBuilder().append(memberId, rhs.memberId).append(memberName, rhs.memberName).append(duns, rhs.duns).append(personId, rhs.personId).append(memberType, rhs.memberType).append(businessEntityType, rhs.businessEntityType).append(memberAddress, rhs.memberAddress).append(nationality, rhs.nationality).append(dob, rhs.dob).append(directOwnershipPercentage, rhs.directOwnershipPercentage).append(indirectOwnershipPercentage, rhs.indirectOwnershipPercentage).append(beneficialOwnershipPercentage, rhs.beneficialOwnershipPercentage).append(isBeneficiary, rhs.isBeneficiary).append(ownershipUnavailableReason, rhs.ownershipUnavailableReason).append(isMinority, rhs.isMinority).append(isOutofBusiness, rhs.isOutofBusiness).append(depth, rhs.depth).append(isFilteredOut, rhs.isFilteredOut).isEquals();
    }

}
