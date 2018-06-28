package com.dnb.ubo.v4.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.apache.commons.lang.StringUtils;

import com.dnb.ubo.v4.constants.C;
import com.dnb.ubo.v4.jsonbeans.Beneficiary;
import com.dnb.ubo.v4.jsonbeans.CountrySummary;
import com.dnb.ubo.v4.jsonbeans.Member;
import com.dnb.ubo.v4.jsonbeans.NodeSummaryByGeography;
import com.dnb.ubo.v4.jsonbeans.OwnershipSummary;

/*Supporting class for JSON builder & helper. Broke the existing class due to sonar issue */

public class JsonBuilderSupport {

    private JsonBuilderSupport(){

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
     * To populate ownership summary details in structure
     *
     * @param List
     *            <Member> memberList
     * @param int relationSize
     * @param String
     *            targetDuns
     * @return
     */
    public static final OwnershipSummary getOwnershipSummary(final List<Member> memberList, int relationSize, String targetDuns) {

        int cboCount = C.ZERO;
        Double totalBen = C.ZEROPER;
        int organizationCount = C.ZERO;
        int individualCount = C.ZERO;
        int entityCount = C.ZERO;
        NodeSummaryByGeography geographySum = new NodeSummaryByGeography();
        Map<String, Integer> countryMap = new HashMap<>();
        int unknownCount = C.ZERO;
        int maxDegree = C.ZERO;

        for (Member member : memberList) {
            if (null != member.getDepth() && member.getDepth() > maxDegree) {
                maxDegree = member.getDepth();
            }
            if (!member.getOwnershipUnavailableReason().isEmpty()) {
                cboCount++;
            }
            if (null != member.getBeneficialOwnershipPercentage()) {
                totalBen += Math.round(member.getBeneficialOwnershipPercentage() * C.HUNDRED) / C.HUNDRED;
            }
            if (null != member.getMemberType().getCode()) {
                if ((C.MEBR_TYPE_CODE_BUSINESS == member.getMemberType().getCode()) && !(member.getDuns().equals(targetDuns))) {
                    organizationCount++;
                } else if (C.MEBR_TYPE_CODE_INDIVIDUAL == member.getMemberType().getCode()) {
                    individualCount++;
                }
            } else {
                entityCount++;
            }
            unknownCount = getCountryCount(countryMap, unknownCount, member, targetDuns);
        }

        final OwnershipSummary ownershipSummary = new OwnershipSummary();

        //Set Control Type summary
        setMemberControlTypeSummary(ownershipSummary,memberList,targetDuns);

        // set Country summary
        setCountrySummary(geographySum, countryMap, unknownCount);

        // populate the details to ownershipSummary section

        ownershipSummary.setTotalAllocatedBeneficialOwnership(Math.round(totalBen * C.HUNDRED) / C.HUNDRED);
        ownershipSummary.setNodeCount(memberList.size() - 1);
        ownershipSummary.setRelationshipCount(relationSize);
        ownershipSummary.setCorporateBeneficiaryCount(cboCount);
        ownershipSummary.setOrganizationCount(organizationCount);
        ownershipSummary.setIndividualCount(individualCount);
        ownershipSummary.setEntityCount(entityCount);
        ownershipSummary.setNodeSummaryByGeography(geographySum);
        ownershipSummary.setMaxDepth(new Integer(maxDegree));

        // return summary
        return ownershipSummary;

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
     * Sets the structure control type summary.
     *
     * @param ownershipSummary the ownership summary
     * @param memberList the member list
     */
    public static void setMemberControlTypeSummary(final OwnershipSummary ownershipSummary, final List<Member> memberList,final String targetDuns) {

        int stateOwned = C.ZERO;
        int govtEntity = C.ZERO;
        int publicComp = C.ZERO;
        int privateComp = C.ZERO;

        for (Member member : memberList) {

            if (null != member.getDuns() && !member.getDuns().trim().equals(targetDuns.trim()) && null != member.getControlType() && null != member.getControlType().getCode()) {

                if (member.getControlType().getCode() == C.STATE_OWNED_ENTERPRISE   ) {
                    stateOwned++;
                }
                if (member.getControlType().getCode() == C.GOVT_ENTITY) {
                    govtEntity++;
                }
                if (member.getControlType().getCode() == C.PUBLICLY_TRADED) {
                    publicComp++;
                }
                if (member.getControlType().getCode() == C.PRIVATELY_OWNED) {
                    privateComp++;
                }
            }

        }
        ownershipSummary.setStateOwnedOrganizationCount(stateOwned);
        ownershipSummary.setGovernmentOrganizationCount(govtEntity);
        ownershipSummary.setPrivatelyHeldOrganizationCount(privateComp);
        ownershipSummary.setPubliclyTradingOrganizationCount(publicComp);

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

}
