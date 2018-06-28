/**
 * APIResponseCalculation.java
 */
package com.dnb.ubo.v3.calculation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dnb.ubo.v3.constants.C;

public class ResponseCalculation {

    private final Map<String, Double> actualRelations, allRel;
    private final Map<Long, Object>  beneficialSum, direct, indirect, unDisclosed,depth,isBeneficiary;
    private final Map<Long, Double> organisations, unknowns,  passHolder, prevPassHolder, ratioHolder ;
    private Long target = -1L;

    public ResponseCalculation() {
        actualRelations = new HashMap<>();
        allRel = new HashMap<>();
        organisations = new HashMap<>();
        beneficialSum = new HashMap<>();
        unDisclosed = new HashMap<>();
        passHolder = new HashMap<>();
        unknowns = new HashMap<>();
        prevPassHolder = new HashMap<>();
        ratioHolder = new HashMap<>();
        direct = new HashMap<>();
        indirect = new HashMap<>();
        depth = new HashMap<>();
        isBeneficiary = new HashMap<>();

    }

    /**
     * Entry point of calculations
     *
     * @param List
     *            <Map<String, Object>> dbData
     * @return Map<String, Map<Long, Double>>
     */
    public Map<String, Map<Long, Object>> calculatebeneficialOwnershipList(Map<String, Object> dbData) {

        Map<String,Map<Long, Object>> calcRespo = new HashMap<>();
        prepareRow(dbData);
        createUnknowns();
        quickScan();
        findUndisclosed();
        findDepth();
        findIsBeneficiary();
        calcRespo.put(C.BENEFECIALSUM, beneficialSum);
        calcRespo.put(C.DIRECT, direct);
        calcRespo.put(C.INDIRECT, indirect);
        calcRespo.put(C.UNDISCLOSED, unDisclosed);
        calcRespo.put(C.DEPTH, depth);
        calcRespo.put(C.ISBENFECIARY, isBeneficiary);

        return calcRespo;

    }

    /**
     * Find is beneficiary.
     */
    public void findIsBeneficiary() {
        for (Long key : beneficialSum.keySet()) {
            if (key > C.ZERO) {
                isBeneficiary.put(key, true);
            }
        }
        for (Long key : unDisclosed.keySet()) {
            if (!organisations.containsKey(key)) {
                isBeneficiary.put(key, true);
            }
        }
    }

    /**
     * Find depth.
     */
    public void findDepth() {
        // Initialise depth holder
        for (String key : allRel.keySet()) {
            int index = key.lastIndexOf(C.PIPE);
            Long to = new Long(key.substring(index + C.ONE));
            Long from = new Long(key.substring(C.ZERO, index));
            if (target.equals(to)) {
                depth.put(to, C.ZERO);
            } else {
                depth.put(to, -1);
            }
            if (target.equals(from)) {
                depth.put(from, C.ZERO);
            } else {
                depth.put(from, -1);
            }
        }
        // Distribute FROM value to TO value each time incrementing and make
        // sure the existing value is always smaller
        for (int p = C.ZERO; p < C.QUICK_SCAN_COUNT; p++) {
            for (String key : allRel.keySet()) {
                int index = key.lastIndexOf(C.PIPE);
                Long from = new Long(key.substring(C.ZERO, index));
                Long to = new Long(key.substring(index + C.ONE));
                int fromVal = (Integer)depth.get(from);
                int toVal = (Integer)depth.get(to);
                // Make sure to avoid self links
                if (!from.equals(to)) {
                  depthCont(to, fromVal, toVal);
                }
   }
        }

    }

    public void depthCont(Long to, int fromVal, int toVal) {
        if (fromVal == C.ZERO) {
            depth.put(to, fromVal + C.ONE);
        }
        if (fromVal > C.ZERO) {
        if (toVal >= C.ZERO) {
            if (toVal > fromVal + C.ONE) {
                depth.put(to, fromVal + C.ONE);
            }
        } else {
            depth.put(to, fromVal + C.ONE);
        }
    }
    }

    /**
     * To identify unknown cases where allocations is less than 100
     */
    public void createUnknowns() {
        unknowns.putAll(organisations);
        for (Long org : organisations.keySet()) {
            for (Map.Entry<String, Double> entry : actualRelations.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue();
                if (key.contains(org + C.PIPE)) {
                    unknowns.put(org, unknowns.get(org) + value);
                }
            }
        }
        // append missing % to actualRelations
        for (Map.Entry<Long, Double> entry : unknowns.entrySet()) {
            Long key = entry.getKey();
            Double value = entry.getValue();
            if ((C.HUNDREDNBR - value) > C.ZERO) {
                actualRelations.put(key + C.PIPE + (-key), C.HUNDREDNBR - value);
            }
        }
    }

    /**
     * To identify undisclosed elements in the group
     */
    public void findUndisclosed() {
        passHolder.clear();
        prevPassHolder.clear();
        for (int p = C.ZERO; p < C.QUICK_SCAN_COUNT; p++) {
            for (String key : allRel.keySet()) {
                Double share = allRel.get(key);
                int index = key.lastIndexOf(C.PIPE);
                Long from = new Long(key.substring(C.ZERO, index));
                Long to = new Long(key.substring(index + C.ONE));
                if (p == C.ZERO) {
                    undisclosedSetting(share, to);
                } else {
                    undisclosedCont(from, to);
                }
            }
            prevPassHolder.putAll(passHolder);
        }
        // copy -1 vals from passHolder map to final map
        for (Map.Entry<Long, Double> entry : passHolder.entrySet()) {
            Long key = entry.getKey();
            Double value = entry.getValue();
            if (value < C.ZEROPER) {
                unDisclosed.put(key, value);
            }
        }
    }

    public void undisclosedSetting(Double share, Long to) {
        if (passHolder.containsKey(to)) {
            if (passHolder.get(to) > C.ZEROPER) {
                passHolder.put(to, share);
            }
        } else {
            passHolder.put(to, share);
        }
    }

    public void undisclosedCont(Long from, Long to) {
        if (prevPassHolder.containsKey(from) && passHolder.containsKey(to)) {
            double fromVal = prevPassHolder.get(from);
            double toVal = passHolder.get(to);
            if (toVal > C.ZEROPER) {
                passHolder.put(to, fromVal);
            }
        }
    }

    /**
     * Start euclid's calculation
     */
    public void quickScan() {
        // run passes
        for (int p = C.ZERO; p < C.QUICK_SCAN_COUNT; p++) {
            for (String key : actualRelations.keySet()) {
                runPasses(p, key);
            }
            updateOrg();
            // Check the first non-zero value of each node to identify indirect
            // shareholder from second pass onwards
            if (p > C.ZERO) {
                indirectShareHolder();
            }

            passHolder.clear();
        }

        if (!check()) {
            // Determine if ratios are > 1 as that lead to infinite series
            boolean isRatioConstant = false;
            for (Map.Entry<Long, Double> entry : ratioHolder.entrySet()) {
                Long key = entry.getKey();
                if (ratioHolder.get(key) >= C.ONE) {
                    isRatioConstant = true;
                    break;
                }
            }

            if (isRatioConstant) {
                // Apply brute force as ratios or oscillating > 1
                bruteForce();

            } else {
                // Apply Geometric progression formula
                geoProgFormula();
            }
        }
    }

    /**
     * To identify indirect percentages
     */
    public void indirectShareHolder() {
        for (Long to : passHolder.keySet()) {
            boolean flag = false;
            if (to > C.ZERO && passHolder.get(to) > C.ZEROPER && !direct.containsKey(to)) {
                flag = true;
                if (!to.equals(target) && !indirect.containsKey(to) && flag) {
                    indirect.put(to, passHolder.get(to));
                }

            }
        }
    }

    /**
     * To run passes for calculation
     *
     * @param int p
     * @param String
     *            key
     */
    public void runPasses(int p, String key) {
        Double share = actualRelations.get(key);
        int index = key.lastIndexOf(C.PIPE);
        Long from = new Long(key.substring(C.ZERO, index));
        Long to = new Long(key.substring(index + C.ONE));
        // handle first pass by setting target ApiConstants.HUNDREDNBR%
        if (p == C.ZERO) {
            if (from.equals(target)) {
                // First pass distribution is only for direct
                // shareholder of the target, so identify them here
                identifydirect(share, to);
            } else {
                if (!passHolder.containsKey(to)) {
                    passHolder.put(to, C.ZEROPER);
                }
            }
        } else {
            bruteForceCon(share, from, to);
        }
    }

    /**
     * Identify direct share holders
     *
     * @param Double
     *            share
     * @param Long
     *            to
     */
    public void identifydirect(Double share, Long to) {
        if (to > C.ZERO && !to.equals(target)) {
            direct.put(to, C.HUNDREDNBR * (share / C.HUNDREDNBR));
        }
        // set target to ApiConstants.HUNDREDNBR%
        passHolder.put(to, C.HUNDREDNBR * (share / C.HUNDREDNBR));
        if (!organisations.containsKey(to)) {
            beneficialSum.put(to, C.HUNDREDNBR * (share / C.HUNDREDNBR));
        }
    }

    /**
     * To calculate using geometric progression formula
     */
    public void geoProgFormula() {
        for (Map.Entry<Long, Double> entry : prevPassHolder.entrySet()) {
            Long key = entry.getKey();
            Double r = ratioHolder.get(key);
            if (beneficialSum.containsKey(key) && ratioHolder.containsKey(key)) {
                Double a = entry.getValue();
                // Subtract a as its a previous term
                beneficialSum.put(key, (Double)beneficialSum.get(key) + (geometricSum(C.NINELAKH, a, r) - a));
            }

        }
    }

    /**
     * Brute force calculation
     */
    public void bruteForce() {
    	Double prevSum=0.0;
        for (int p = C.QUICK_SCAN_COUNT; p < (C.BRUTEFORCECOUNT + C.QUICK_SCAN_COUNT); p++) {
            for (String key : actualRelations.keySet()) {
                Double share = actualRelations.get(key);
                int index = key.lastIndexOf(C.PIPE);
                Long from = new Long(key.substring(C.ZERO, index));
                Long to = new Long(key.substring(index + C.ONE));
                // handle first pass by setting target ApiConstants.HUNDREDNBR%
                if (p == C.ZERO) {
                    firstPassHandling(share, from, to);
                } else {
                    bruteForceCon(share, from, to);
                }
            }
            updateOrg();
            passHolder.clear();
            // Try to escape brute force
            if (p % C.HUNDREDNBR == C.ZERO) {
                Double sum = C.ZEROPER;
                for (Long key : beneficialSum.keySet()) {
                    sum = sum + (Double)beneficialSum.get(key);
                }
                //Added for huge structures - start
                if( sum-prevSum < 0.000001) {
                    break;
                }
              //Added for huge structures - end
                if (sum > C.NINENTYNINE) {
                    break;
                }
                //Added for huge structures - start
                prevSum = sum;
                //Added for huge structures - end
            }
        }
    }

    public void firstPassHandling(Double share, Long from, Long to) {
        if (from.equals(target)) {
            // set target to ApiConstants.HUNDREDNBR%
            passHolder.put(to, C.HUNDREDNBR * (share / C.HUNDREDNBR));
            if (!organisations.containsKey(to)) {
                beneficialSum.put(to, C.HUNDREDNBR * (share / C.HUNDREDNBR));
            }
        } else {
            if (!passHolder.containsKey(to)) {
                passHolder.put(to, C.ZEROPER);
            }
        }
    }

    public void updateOrg() {
        for (Long key : organisations.keySet()) {
            if (passHolder.containsKey(key)) {
                organisations.put(key, passHolder.get(key));
            }
        }
    }

    /**
     * Continue brute force calculation
     *
     * @param Double
     *            share
     * @param Long
     *            from
     * @param Long
     *            to
     */
    public void bruteForceCon(Double share, Long from, Long to) {

        Double calcShare = organisations.get(from) * (share / C.HUNDREDNBR);
        if (passHolder.containsKey(to)) {
            passHolder.put(to, passHolder.get(to) + calcShare);
        } else {
            passHolder.put(to, calcShare);
        }
        if (!organisations.containsKey(to)) {
            if (beneficialSum.containsKey(to)) {
                beneficialSum.put(to, (Double)beneficialSum.get(to) + calcShare);
            } else {
                beneficialSum.put(to, calcShare);
            }
            if (calcShare > C.ZERO) {
                if (prevPassHolder.containsKey(to)) {
                    ratioHolder.put(to, calcShare / prevPassHolder.get(to));
                }
                prevPassHolder.put(to, calcShare);
            }
        }
    }

    /**
     * Preparing data for calculation
     *
     * @param List
     *            <Map<String, Object>> data
     */
    public void prepareRow(final Map<String, Object> data) {
        target = (Long) data.get(C.TARGET_DUNS);
        List rels = (List) data.get(C.RELATIONS);
            for (int i = C.ZERO; i < rels.size(); i++) {
                List rel = (List) rels.get(i);
                String from = (String) rel.get(C.ZERO);
                String to = (String) rel.get(C.ONE);
                Double share = (Double) rel.get(C.TWO);

                int index = from.lastIndexOf(C.PIPE);
                Long fromId = new Long(from.substring(C.ZERO, index));
                String fromType = (String) from.substring(index + C.ONE);

                index = to.lastIndexOf(C.PIPE);
                Long toId = new Long(to.substring(C.ZERO, index));
                String toType = (String) to.substring(index + C.ONE);

                if (("O").equals(fromType)) {
                    organisations.put(fromId, C.ZEROPER);
                }
                if (("O").equals(toType)) {
                    organisations.put(toId, C.ZEROPER);
                }

                if (share != null && !share.equals(C.ZEROPER)) {
                    actualRelations.put(fromId + C.PIPE + toId, share);
                    allRel.put(fromId + C.PIPE + toId, 1.0);
                } else {
                    allRel.put(fromId + C.PIPE + toId, -1.0);
                }
   }
    }

    /**
     * GP formula
     *
     * @param int n
     * @param Double
     *            a
     * @param Double
     *            r
     * @return Double
     */
    public static Double geometricSum(int n, Double a, Double r) {
        // Sum of Geometric Sequences is a(1-r^n)/(1-r) where a= starting
        // number, r= common ratio, n = nth term
        return a * ((C.ONE - Math.pow(r, n)) / (C.ONE - r));
    }

    /**
     * Check for over allocation
     *
     * @return boolean
     */
    public boolean check() {
        Double sum = C.ZEROPER;
        for (Long key : beneficialSum.keySet()) {
            sum = sum + (Double)beneficialSum.get(key);
        }
        if (sum > C.NINENTYNINE) {
            return true;
        }
        return false;
    }

}
