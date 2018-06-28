package com.dnb.ubo.v3.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.apache.commons.lang.StringUtils;
import org.neo4j.driver.internal.InternalNode;
import org.neo4j.driver.v1.types.Node;

import com.dnb.ubo.v3.constants.C;
import com.dnb.ubo.v3.jsonbeans.Address;
import com.dnb.ubo.v3.jsonbeans.AddressCountry;
import com.dnb.ubo.v3.jsonbeans.Beneficiary;
import com.dnb.ubo.v3.jsonbeans.BusinessEntityType;
import com.dnb.ubo.v3.jsonbeans.CorporateLinkage;
import com.dnb.ubo.v3.jsonbeans.CountrySummary;
import com.dnb.ubo.v3.jsonbeans.DomesticUltimate;
import com.dnb.ubo.v3.jsonbeans.GlobalUltimate;
import com.dnb.ubo.v3.jsonbeans.Member;
import com.dnb.ubo.v3.jsonbeans.NodeSummaryByGeography;
import com.dnb.ubo.v3.jsonbeans.Organization;
import com.dnb.ubo.v3.jsonbeans.OwnershipUnavailableReason;
import com.dnb.ubo.v3.jsonbeans.Parent;
import com.dnb.ubo.v3.jsonbeans.PrimarySIC;
import com.dnb.ubo.v3.jsonbeans.Relationship;
import com.dnb.ubo.v3.request.RequestValidator;

public class JsonBuilderHelper {

    /**
     * Method to populate the TargetDuns
     *
     * @param targetDunsValue
     * @param organization
     * @return
     */
    private JsonBuilderHelper() {

    }

    public static final void populateTargetDunsDetails(final Node targetDunsValue, final Organization organization) {

        // Initialize

        final PrimarySIC primarySIC = new PrimarySIC();
        final BusinessEntityType businessEntityType = new BusinessEntityType();
        final InternalNode nod = (InternalNode) targetDunsValue;
        final Map<String, Object> nodeMap = nod.asMap();

        // Check if nodeMap is not null
        if (null != nodeMap) {

            if (nodeMap.containsKey(C.DUNS_NUMBER)) {
                organization.setDuns(nodeMap.get(C.DUNS_NUMBER).toString());
            }
            if (nodeMap.containsKey(C.PRIMARY_SIC)) {
                primarySIC.setSicCode(nodeMap.get(C.PRIMARY_SIC).toString());
                organization.setPrimarySIC(primarySIC);
            }
            if (nodeMap.containsKey(C.NAME)) {
                organization.setOrganizationName(nodeMap.get(C.NAME).toString());
            }
            if (nodeMap.containsKey(C.BUSINESS_ENTITY_TYPE)) {
                businessEntityType.setCode(Integer.parseInt(nodeMap.get(C.BUSINESS_ENTITY_TYPE).toString()));
                organization.setBusinessEntityType(businessEntityType);
            }
            if (nodeMap.containsKey(C.OUT_OF_BUSINESS)) {
                populateOutOfBusinessDetails(organization, nodeMap.get(C.OUT_OF_BUSINESS).toString());
            }

            // create the address details of organization & populate
            final Address organisationAddress = getOrganisationAddress(nodeMap);
            organization.setAddress(organisationAddress);

        }
    }

    /**
     * To set IsOutOFBusiness flag for target duns
     *
     * @param organization
     * @param entry
     */
    public static final void populateOutOfBusinessDetails(final Organization organization, final String oobCode) {
        if (null != oobCode) {
            if ((C.OUT_OF_BUSINESS_TRUE_CODE1).equals(oobCode) || (C.OUT_OF_BUSINESS_TRUE_CODE2).equals(oobCode) || (C.OUT_OF_BUSINESS_TRUE_CODE3).equals(oobCode)) {
                organization.setIsOutofBusiness(true);
            } else {
                organization.setIsOutofBusiness(false);
            }
        }
    }

    /**
     * To populate address for target DUNS
     *
     * @param organisationAddress
     * @param organisationAddressCountry
     * @param entry
     * @return
     */
    private static final Address getOrganisationAddress(final Map<String, Object> entry) {
        final AddressCountry organisationAddressCountry = new AddressCountry();
        final Address organisationAddress = new Address();
        if (entry.containsKey(C.ADDRESS_LINE_1)) {
            organisationAddress.setAddressLine1(entry.get(C.ADDRESS_LINE_1).toString());
        }
        if (entry.containsKey(C.ADDRESS_LINE_2)) {
            organisationAddress.setAddressLine2(entry.get(C.ADDRESS_LINE_2).toString());
        }
        if (entry.containsKey(C.ADDRESS_LINE_3)) {
            organisationAddress.setAddressLine3(entry.get(C.ADDRESS_LINE_3).toString());
        }
        if (entry.containsKey(C.POSTAL_CODE)) {
            organisationAddress.setZipOrPostalCode(entry.get(C.POSTAL_CODE).toString());
        }
        if (entry.containsKey(C.ISO_2_COUNTRY_CODE)) {
            organisationAddressCountry.setIsoAlpha2Code(entry.get(C.ISO_2_COUNTRY_CODE).toString());
            organisationAddress.setAddressCountry(organisationAddressCountry);
        }
        if (entry.containsKey(C.PRIMARY_TOWN)) {
            organisationAddress.setPrimaryTown(entry.get(C.PRIMARY_TOWN).toString());
        }
        if (entry.containsKey(C.PROVINCE_STATE)) {
            organisationAddress.setProvinceOrState(entry.get(C.PROVINCE_STATE).toString());
        }
        if (entry.containsKey(C.COUNTY)) {
            organisationAddress.setCounty(entry.get(C.COUNTY).toString());
        }
        return organisationAddress;
    }

    /**
     * Method for all the filter operation
     *
     * @param beneficiaryList
     * @param excludeUndisclosedHoldings
     * @param ownershipType
     * @param ownershipPercentage
     * @return
     */
    public static final List<Beneficiary> filterOperation(final List<Beneficiary> beneficiaryList, final Boolean excludeUndisclosedHoldings, final String ownershipType, final Double ownershipPercentage) {

        List<Beneficiary> beneficiaryFilteredList = beneficiaryList;

        // Undisclosed Holdings Filter
        if (null != excludeUndisclosedHoldings && excludeUndisclosedHoldings && !beneficiaryFilteredList.isEmpty()) {
            beneficiaryFilteredList = undisclosedFilter(beneficiaryFilteredList);
        }

        // Ownership Type Filter
        if (StringUtils.isNotBlank(ownershipType) && !beneficiaryFilteredList.isEmpty()) {
            beneficiaryFilteredList = ownershipTypeFilter(ownershipType, beneficiaryFilteredList, excludeUndisclosedHoldings);
        }

        // Ownership Percentage Filter
        if (null != ownershipPercentage && ownershipPercentage != 0 && !beneficiaryFilteredList.isEmpty()) {
            beneficiaryFilteredList = ownershipPercentageFilter(ownershipType, ownershipPercentage, beneficiaryFilteredList, excludeUndisclosedHoldings);

        }

        return beneficiaryFilteredList;
    }

    /**
     * Method for Ownership Type Filter
     *
     * @param ownershipType
     * @param beneficiaryList
     * @param excludeUndisclosedHoldings
     * @return
     */
    private static final List<Beneficiary> ownershipTypeFilter(final String ownershipType, final List<Beneficiary> beneficiaryList, final Boolean excludeUndisclosedHoldings) {

        List<Beneficiary> beneficiaryFilteredList = new ArrayList<>();

        for (Beneficiary beneficiary : beneficiaryList) {
            applyFilter(beneficiary, ownershipType, beneficiaryFilteredList);
        }

        // Adding undisclosed value
        if (null == excludeUndisclosedHoldings || !excludeUndisclosedHoldings) {
            beneficiaryFilteredList.addAll(getUndisclosedOnes(beneficiaryList));
        }

        return beneficiaryFilteredList;
    }

    /**
     * Removing undisclosed
     *
     * @param beneficiaryList
     * @return
     */
    private static final List<Beneficiary> undisclosedFilter(final List<Beneficiary> beneficiaryList) {

        final List<Beneficiary> beneficiaryFilteredList = new ArrayList<>();

        for (Beneficiary beneficiary : beneficiaryList) {
            boolean directFlag = false;
            boolean indirectFlag = false;
            boolean beneficialFlag = false;
            if (notNullOrZeroPercentage(beneficiary.getDirectOwnershipPercentage())) {
                directFlag = true;
            }
            if (notNullOrZeroPercentage(beneficiary.getIndirectOwnershipPercentage())) {
                indirectFlag = true;
            }
            if (notNullOrZeroPercentage(beneficiary.getBeneficialOwnershipPercentage())) {
                beneficialFlag = true;
            }
            // to remove undisclosed values
            if (directFlag || indirectFlag || beneficialFlag) {
                beneficiaryFilteredList.add(beneficiary);
            }

        }
        return beneficiaryFilteredList;
    }

    /**
     * To check type of Ownership for beneficiary for filter purpose
     *
     * @param beneficiary
     * @param ownershipType
     * @param beneficiaryFilteredList
     */
    private static final void applyFilter(final Beneficiary beneficiary, final String ownershipType, final List<Beneficiary> beneficiaryFilteredList) {

        if (C.BENEFICIARIES_OWNERSHIP.equals(ownershipType) && notNullOrZeroPercentage(beneficiary.getBeneficialOwnershipPercentage())) {
            beneficiaryFilteredList.add(beneficiary);
        } else if (C.INDIRECT_OWNERSHIP.equals(ownershipType) && notNullOrZeroPercentage(beneficiary.getIndirectOwnershipPercentage())) {
            beneficiaryFilteredList.add(beneficiary);
        } else if (C.DIRECT_OWNERSHIP.equals(ownershipType) && notNullOrZeroPercentage(beneficiary.getDirectOwnershipPercentage())) {
            beneficiaryFilteredList.add(beneficiary);
        } else if (C.CONTROLLED_OWNERSHIP.equals(ownershipType) && (notNullOrZeroPercentage(beneficiary.getIndirectOwnershipPercentage()) || notNullOrZeroPercentage(beneficiary.getDirectOwnershipPercentage()))) {
            beneficiaryFilteredList.add(beneficiary);
        }

    }

    /**
     * Started filter operation for member
     *
     * @param memberList
     * @param dunsNumber
     * @param ownershipType
     * @param ownershipPercentage
     */
    public static final void filterOperationforMember(final List<Member> memberList, final String dunsNumber, final String ownershipType, final Double ownershipPercentage) {
        if (null != ownershipType) {
            ownershipTypeFilterforMember(memberList);
        }
        if (null != ownershipPercentage) {
            ownershipPercentageFilterforMember(ownershipPercentage, memberList);
        }
        removeIsFilteredFlagfromTargetDun(dunsNumber, memberList);

    }

    /**
     * Removing isFilterdOut flag from target duns
     *
     * @param dunsNumber
     * @param memberList
     */
    private static final void removeIsFilteredFlagfromTargetDun(final String dunsNumber, final List<Member> memberList) {
        for (Member member : memberList) {
            if (null != member.getDuns() && member.getDuns().equals(dunsNumber)) {
                member.setIsFilteredOut(null);
            }
        }
    }

    /**
     * Ownership Type filter for member
     *
     * @param memberList
     */
    private static final void ownershipTypeFilterforMember(final List<Member> memberList) {

        for (Member member : memberList) {
            boolean isFilteredOut = false;
            if (null == member.getBeneficialOwnershipPercentage()) {
                if (checknullforMember(member.getBeneficialOwnershipPercentage()) && checknullforMember(member.getIndirectOwnershipPercentage()) && checknullforMember(member.getDirectOwnershipPercentage())) {
                    isFilteredOut = false;
                } else {
                    isFilteredOut = true;
                }
            }
            member.setIsFilteredOut(isFilteredOut);
        }

    }

    /**
     * Checking undisclosed values in member
     *
     * @param ownershipPercentage
     * @return
     */
    public static final boolean checknullforMember(final Double ownershipPercentage) {
        boolean isUndisclosed = false;
        if (null == ownershipPercentage) {
            isUndisclosed = true;
        } else if (ownershipPercentage.equals(C.ZEROPER)) {
            isUndisclosed = true;
        }
        return isUndisclosed;
    }

    /**
     * Ownership percentage filter for member
     *
     * @param ownershipPercentage
     * @param memberList
     */
    private static final void ownershipPercentageFilterforMember(final Double ownershipPercentage, final List<Member> memberList) {

        for (Member member : memberList) {
            boolean isFilteredOut = false;
            if (null == member.getBeneficialOwnershipPercentage()
                            || (null != member.getBeneficialOwnershipPercentage() && (Math.round(member.getBeneficialOwnershipPercentage() * C.HUNDRED) / C.HUNDRED) < ownershipPercentage)) {
                if (checknullforMember(member.getIndirectOwnershipPercentage()) && checknullforMember(member.getDirectOwnershipPercentage())) {
                    isFilteredOut = false;
                } else if (null != member.getBeneficialOwnershipPercentage() && C.ZEROPER.equals(member.getBeneficialOwnershipPercentage())) {
                    isFilteredOut = false;
                } else {
                    isFilteredOut = true;
                }
            }
            member.setIsFilteredOut(isFilteredOut);
        }

    }

    /**
     * Filter operation for relationship
     *
     * @param memberList
     * @param relationshipList
     */
    public static final void filterOperationforRelationship(final List<Member> memberList, final List<com.dnb.ubo.v3.jsonbeans.Relationship> relationshipList) {

        for (Member member : memberList) {
            for (Relationship relationship : relationshipList) {
                if (relationship.getToMember().equals(member.getMemberId()) && null != member.getIsFilteredOut()) {
                    relationship.setIsFilteredOut(member.getIsFilteredOut());
                }
            }
        }

    }

    public static final boolean notNullOrZeroPercentage(Double percentage) {
        return null != percentage && percentage > C.ZEROPER;
    }

    /**
     * To populate linkage values
     *
     * @param linkageValues
     * @return
     */
    public static final CorporateLinkage getCorporateLinkage(List<Map<String, Object>> linkageValues) {

        final DomesticUltimate domesticUltimate = new DomesticUltimate();
        final GlobalUltimate globalUltimate = new GlobalUltimate();
        final Parent parent = new Parent();

        for (Map<String, Object> linkage : linkageValues) {
            if ((C.PARENT).equals(linkage.get(C.TYPE))) {
                parent.setParentDuns((String) linkage.get(C.LINKAGE_DUNS));
                parent.setParentName((String) linkage.get(C.LINKAGE_NAME));

            }
        
            if ((C.DOMESTIC).equals(linkage.get(C.TYPE))){
                domesticUltimate.setDomesticUltimateDuns((String) linkage.get(C.LINKAGE_DUNS));
                domesticUltimate.setDomesticUltimateName((String) linkage.get(C.LINKAGE_NAME));

    }
            if ((C.ULTIMATE).equals(linkage.get(C.TYPE))) {
                globalUltimate.setGlobalUltimateDuns((String) linkage.get(C.LINKAGE_DUNS));
                globalUltimate.setGlobalUltimateName((String) linkage.get(C.LINKAGE_NAME));

    }
    }
        final CorporateLinkage corporateLinkage = new CorporateLinkage();
        corporateLinkage.setDomesticUltimate(domesticUltimate);
        corporateLinkage.setGlobalUltimate(globalUltimate);
        corporateLinkage.setParent(parent);

        return corporateLinkage;
  }

    /**
     * To round off percentages to two decimal places
     *
     * @param List
     *            <Beneficiary>beneficiaryList
     */
    public static final void setRoundOffPctBeneficiary(List<Beneficiary> beneficiaryList) {
        for (Beneficiary beneficiary : beneficiaryList) {
            if (null != beneficiary.getBeneficialOwnershipPercentage()) {

                beneficiary.setBeneficialOwnershipPercentage(Math.round(beneficiary.getBeneficialOwnershipPercentage() * C.HUNDRED) / C.HUNDRED);
            }
            if (null != beneficiary.getDirectOwnershipPercentage()) {

                beneficiary.setDirectOwnershipPercentage(Math.round(beneficiary.getDirectOwnershipPercentage() * C.HUNDRED) / C.HUNDRED);
            }
            if (null != beneficiary.getIndirectOwnershipPercentage()) {

                beneficiary.setIndirectOwnershipPercentage(Math.round(beneficiary.getIndirectOwnershipPercentage() * C.HUNDRED) / C.HUNDRED);
            }
            beneficiary.setBeneficialOwnershipCommentary(null);
        }
    }

    /**
     * To round off percentages to two decimal places in member
     *
     * @param List
     *            <Member> memberList
     */
    public static final void setRoundOffPctMember(final List<Member> memberList) {
        for (Member member : memberList) {
            if (null != member.getBeneficialOwnershipPercentage()) {
                member.setBeneficialOwnershipPercentage(Math.round(member.getBeneficialOwnershipPercentage() * C.HUNDRED) / C.HUNDRED);
            }
            if (null != member.getDirectOwnershipPercentage()) {
                member.setDirectOwnershipPercentage(Math.round(member.getDirectOwnershipPercentage() * C.HUNDRED) / C.HUNDRED);
            }
            if (null != member.getIndirectOwnershipPercentage()) {
                member.setIndirectOwnershipPercentage(Math.round(member.getIndirectOwnershipPercentage() * C.HUNDRED) / C.HUNDRED);
            }
        }
    }

    /**
     * To set summary details for country in structure
     *
     * @param Map
     *            <String, Integer> countryMap
     * @param int unknownCount
     * @param Beneficiary
     *            beneficiary
     * @return int
     */
    public static final int getCountryCount(final Map<String, Integer> countryMap, final int unknownCount, final Member member, String targetDuns) {

        int countryUnknown = unknownCount;
        if (null != member.getDuns() && !member.getDuns().trim().equals(targetDuns.trim()) || null == member.getDuns()) {
            if (null != member.getMemberAddress() && null != member.getMemberAddress().getAddressCountry() && null != member.getMemberAddress().getAddressCountry().getIsoAlpha2Code()) {
                String country = member.getMemberAddress().getAddressCountry().getIsoAlpha2Code();
                if (countryMap.containsKey(country)) {
                    int count = countryMap.get(country);
                    count++;
                    countryMap.put(country, count);
                } else {
                    countryMap.put(country, C.ONE);
                }
            } else {
                countryUnknown++;
            }
        }
        return countryUnknown;
    }

    /**
     * To populate Member personal details in structure
     *
     * @param member
     * @param populationHelperObj
     * @param nodeDetailsMap
     */
    public static final void populateMemberPersonalDetails(final Member member, final Map<String, Object> nodeDetailsMap) {

        if (nodeDetailsMap.containsKey(C.NAME)) {
            member.setMemberName(nodeDetailsMap.get(C.NAME).toString());
        }
        if (nodeDetailsMap.containsKey(C.DUNS_NUMBER)) {
            member.setDuns(nodeDetailsMap.get(C.DUNS_NUMBER).toString());
        }
        if (nodeDetailsMap.containsKey(C.PERSON_ID)) {
            member.setPersonId(nodeDetailsMap.get(C.PERSON_ID).toString());
        }
        if (nodeDetailsMap.containsKey(C.NATIONALITY_FOR_OP)) {
            member.setNationality(nodeDetailsMap.get(C.NATIONALITY_FOR_OP).toString());
        }
        if (nodeDetailsMap.containsKey(C.BIRTH_DATE) && null != nodeDetailsMap.get(C.BIRTH_DATE)) {
            member.setDob(RequestValidator.parseDate(nodeDetailsMap.get(C.BIRTH_DATE).toString()));
        }
    }

    /**
     * To set ownership unavailable reason code to members
     *
     * @param Map
     *            <Integer, String> cboHashCodeMap
     * @param Map
     *            .Entry<String, Object> entry
     * @param Member
     *            member
     */
    public static final void setMemberCBOCode(final Map<String, Object> nodeMap, final Member member) {
        if (nodeMap.containsKey(C.OWNERSHIP_UNAVAILABLE_REASON_CODE)) {

            // Gets the object & Converts to String
            final String unavailableReasonCode = nodeMap.get(C.OWNERSHIP_UNAVAILABLE_REASON_CODE).toString();

            // Removes the brackets
            final String unavailableReasonCodeStr = unavailableReasonCode.replace(C.OPEN_SQUARE_BRACKET, "").replace(C.CLOSE_SQUARE_BRACKET, "");
            // Splits based on ","
            final String[] unavailableReasonCodeAray = unavailableReasonCodeStr.split(C.COMMA_SEPERATOR);

            // Needed to create the CBO code.
            Integer cboCode;
            OwnershipUnavailableReason ownershipUnavailableReason;
            final List<OwnershipUnavailableReason> unavilableLst = new ArrayList<OwnershipUnavailableReason>();

            for (String reasonCode : unavailableReasonCodeAray) {
                cboCode = Integer.parseInt(reasonCode.trim());
                ownershipUnavailableReason = new OwnershipUnavailableReason();
                ownershipUnavailableReason.setCode(cboCode);
                unavilableLst.add(ownershipUnavailableReason);
                member.setOwnershipUnavailableReason(unavilableLst);
            }
        }
    }

    /**
     * To set country summary
     *
     * @param NodeSummaryByGeography
     *            geographySum
     * @param Map
     *            <String, Integer> countryMap
     * @param int unknownCount
     * @param List
     *            <CountrySummary>countryLst
     */
    public static final void setCountrySummary(final NodeSummaryByGeography geographySum, final Map<String, Integer> countryMap, int unknownCount) {

        final List<CountrySummary> countryLst = new ArrayList<CountrySummary>();
        CountrySummary countrySum;

        for (Map.Entry<String, Integer> entry : countryMap.entrySet()) {
            countrySum = new CountrySummary();
            countrySum.setIsoAlpha2Code(entry.getKey());
            countrySum.setNodeCount(entry.getValue());
            countryLst.add(countrySum);
        }

        if (!countryLst.isEmpty()) {
            geographySum.setCountrySummary(countryLst);
        } else {
            geographySum.setCountrySummary(null);
        }

        if (unknownCount != C.ZERO) {
            geographySum.setUnknownCount(unknownCount);
        }
    }

    /**
     * @param ownershipType
     * @param ownershipPercentage
     * @param beneficiaryList
     * @param excludeUndisclosedHoldings
     * @return
     */
    public static final List<Beneficiary> ownershipPercentageFilter(final String ownershipType, final Double ownershipPercentage, final List<Beneficiary> beneficiaryList, final Boolean excludeUndisclosedHoldings) {

        final List<Beneficiary> beneficiaryFilteredList = new ArrayList<>();
        for (Beneficiary beneficiary : beneficiaryList) {

            double beneficialPercentage = 0;
            double indirectPercentage = 0;
            double directPercentage = 0;

            if (null != beneficiary.getBeneficialOwnershipPercentage() && beneficiary.getBeneficialOwnershipPercentage() > C.ZEROPER) {
                beneficialPercentage = Math.round(beneficiary.getBeneficialOwnershipPercentage() * C.HUNDRED) / C.HUNDRED;
            }
            if (null != beneficiary.getDirectOwnershipPercentage() && beneficiary.getDirectOwnershipPercentage() > C.ZEROPER) {
                directPercentage = Math.round(beneficiary.getDirectOwnershipPercentage() * C.HUNDRED) / C.HUNDRED;
            }
            if (null != beneficiary.getIndirectOwnershipPercentage() && beneficiary.getIndirectOwnershipPercentage() > C.ZEROPER) {
                indirectPercentage = Math.round(beneficiary.getIndirectOwnershipPercentage() * C.HUNDRED) / C.HUNDRED;
            }

            // Filter
            ownershipPercentageFilterCheck(beneficiaryFilteredList, ownershipType, beneficialPercentage, ownershipPercentage, indirectPercentage, directPercentage, beneficiary);
        }

        // Adding undisclosed values to the list
        if (null == excludeUndisclosedHoldings || !excludeUndisclosedHoldings) {
            beneficiaryFilteredList.addAll(getUndisclosedOnes(beneficiaryList));

        }
        return beneficiaryFilteredList;
    }

    /**
     * Filter based on ownership %
     *
     * @param ownershipType
     * @param beneficialPercentage
     * @param ownershipPercentage
     * @param beneficiaryFilteredList
     * @param indirectPercentage
     * @param directPercentage
     * @param beneficiary
     * @return
     */
    private static final void ownershipPercentageFilterCheck(final List<Beneficiary> beneficiaryFilteredList, final String ownershipType, final double beneficialPercentage, final Double ownershipPercentage,
                    final double indirectPercentage, final double directPercentage, final Beneficiary beneficiary) {

        if (C.BENEFICIARIES_OWNERSHIP.equals(ownershipType) && beneficialPercentage >= ownershipPercentage) {
            beneficiaryFilteredList.add(beneficiary);
        } else if (C.INDIRECT_OWNERSHIP.equals(ownershipType) && indirectPercentage >= ownershipPercentage) {
            beneficiaryFilteredList.add(beneficiary);
        } else if (C.DIRECT_OWNERSHIP.equals(ownershipType) && directPercentage >= ownershipPercentage) {
            beneficiaryFilteredList.add(beneficiary);
        } else if (controlledOwnershipfilter(ownershipType, indirectPercentage, directPercentage, ownershipPercentage)) {
            beneficiaryFilteredList.add(beneficiary);
        }
    }

    /**
     * Filter based on controlled ownership %
     *
     * @param ownershipType
     * @param indirectPercentage
     * @param directPercentage
     * @param ownershipPercentage
     * @return
     */
    private static final boolean controlledOwnershipfilter(final String ownershipType, final double indirectPercentage, final double directPercentage, final Double ownershipPercentage) {
        return (StringUtils.isBlank(ownershipType) || C.CONTROLLED_OWNERSHIP.equals(ownershipType)) && (indirectPercentage >= ownershipPercentage || directPercentage >= ownershipPercentage);
    }

    /**
     * Fetching undisclosed List
     *
     * @param beneficiaryList
     * @return
     */
    public static final List<Beneficiary> getUndisclosedOnes(final List<Beneficiary> beneficiaryList) {

        final List<Beneficiary> undisclosedList = new ArrayList<>();

        for (Beneficiary beneficiary : beneficiaryList) {
            boolean directFlag = false;
            boolean indirectFlag = false;
            boolean beneficialFlag = false;
            if (null == beneficiary.getDirectOwnershipPercentage() || Double.valueOf(beneficiary.getDirectOwnershipPercentage()).equals(C.ZEROPER)) {
                directFlag = true;
            }
            if (null == beneficiary.getIndirectOwnershipPercentage() || Double.valueOf(beneficiary.getIndirectOwnershipPercentage()).equals(C.ZEROPER)) {
                indirectFlag = true;
            }
            if (null == beneficiary.getBeneficialOwnershipPercentage() || Double.valueOf(beneficiary.getBeneficialOwnershipPercentage()).equals(C.ZEROPER)) {
                beneficialFlag = true;
            }
            addUndisclosedList(undisclosedList, beneficiary, directFlag, indirectFlag, beneficialFlag);
        }
        return undisclosedList;

    }

    /**
     * @param undisclosedList
     * @param beneficiary
     * @param directFlag
     * @param indirectFlag
     * @param beneficialFlag
     */
    private static final void addUndisclosedList(final List<Beneficiary> undisclosedList, final Beneficiary beneficiary, final boolean directFlag, final boolean indirectFlag, final boolean beneficialFlag) {
        if (directFlag && indirectFlag && beneficialFlag) {
            undisclosedList.add(beneficiary);
        }
    }
    /**
     * @param beneficiary
     * @param beneficiaryList
     * @param dunsNbr
     */
    public static final void removeTargetDuns(final List<Beneficiary> beneficiaryList, final String dunsNbr) {

        // Remove the target duns alone
        Predicate<Beneficiary> beneficiaryPredicate = p -> dunsNbr.equals(p.getDuns());
        beneficiaryList.removeIf(beneficiaryPredicate);
    }
    
    /**
     * To get the error message
     *
     * @param text
     * @param value
     * @return
     */
    public static final String getErrorvalue(final String text, final String value) {
        String errorValue = value;
        if (text.contains(C.DUNS)) {
            errorValue = C.DUNS;
        } else if (text.contains(C.PRODUCT_CODE)) {
            errorValue = C.PRODUCT_CODE;
        } else if (text.contains(C.COUNTRY_CODE)) {
            errorValue = C.COUNTRY_CODE;
        } else if (text.contains(C.OWNERSHIP_TYPE)) {
            errorValue = C.OWNERSHIP_TYPE;
        } else if (text.contains(C.OWNERSHIP_PERCENTAGE)) {
            errorValue = C.OWNERSHIP_PERCENTAGE;
        } else if (text.contains(C.INCLUDE_UNKNOWN_HOLDINGS)) {
            errorValue = C.INCLUDE_UNKNOWN_HOLDINGS;
        } else if (text.contains(C.EXCLUDE_UNDISCLOSED_HOLDINGS)) {
            errorValue = C.EXCLUDE_UNDISCLOSED_HOLDINGS;
        } else if (text.contains(C.TRANSACTION_ID)) {
            errorValue = C.TRANSACTION_ID;
        } else if (text.contains(C.INVALID_DATE)) {
            errorValue = C.TRANSACTION_TIME_STAMP;
        }
        return errorValue;
    }
}
