/**
 * @Name: ApiRemoveEmptyFields.java
 * @Class_Description: This is the response framing class for all the UBO API
 *                     operations
 * @author: Cognizant Technology Solutions
 * @Created_On: Sep 5, 2016
 *
 *              Confidential and Proprietary. Copyright (C) 2016 D&B. All Rights
 *              Reserved.
 */
package com.dnb.ubo.v3.response;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dnb.ubo.v3.constants.C;
import com.dnb.ubo.v3.exception.ApplicationException;
import com.dnb.ubo.v3.jsonbeans.ApiResponse;
import com.dnb.ubo.v3.jsonbeans.Beneficiary;
import com.dnb.ubo.v3.jsonbeans.CorporateLinkage;
import com.dnb.ubo.v3.jsonbeans.Member;
import com.dnb.ubo.v3.jsonbeans.Organization;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonCleaner {


    private static final Logger LOGGER = LoggerFactory.getLogger(JsonCleaner.class);
    private JsonCleaner(){

    }
    /**
     *
     * @param response
     * @throws ApplicationException
     */
    public static void removeEmptyField(ApiResponse response) throws ApplicationException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_EMPTY);
        try {
            if (null != response.getProductResponse().getOrganization()) {
                removeEmptyFieldsForOrganization(mapper, response.getProductResponse().getOrganization());
                if (isEmptyField(mapper, response.getProductResponse().getOrganization())) {
                    response.getProductResponse().setOrganization(null);
                }
            }
            if (isEmptyField(mapper, response.getProductResponse())) {
                response.setProductResponse(null);
            }
        } catch (Exception ex) {
            LOGGER.error(C.ERROR+ex.getMessage(), ex);
            throw new ApplicationException(C.UB019, ex, "");
        }

    }

    /**
     *
     * @param mapper
     * @param corporateLinkage
     */
    private static void removeEmptyFieldforCorporateLinkage(ObjectMapper mapper, CorporateLinkage corporateLinkage) {
        if (null != corporateLinkage.getDomesticUltimate() && isEmptyField(mapper, corporateLinkage.getDomesticUltimate())) {
            corporateLinkage.setDomesticUltimate(null);
        }
        if (null != corporateLinkage.getGlobalUltimate() && isEmptyField(mapper, corporateLinkage.getGlobalUltimate())) {
            corporateLinkage.setGlobalUltimate(null);
        }
        if (null != corporateLinkage.getParent() && isEmptyField(mapper, corporateLinkage.getParent())) {
            corporateLinkage.setParent(null);
        }
    }

    /**
     *
     * @param mapper
     * @param beneficiaries
     */
     static void removeEmptyFieldforbeneficiaries(ObjectMapper mapper, List<Beneficiary> beneficiaries) {
        List<Beneficiary> beneficiaryList = new ArrayList<>();
        for (Beneficiary beneficiary : beneficiaries) {
            if (isEmptyField(mapper, beneficiary.getBeneficiaryAddress())) {
                beneficiary.setBeneficiaryAddress(null);
            }
            if (isEmptyField(mapper, beneficiary.getBusinessEntityType())) {
                beneficiary.setBusinessEntityType(null);
            }
            if (isEmptyField(mapper, beneficiary.getBeneficiaryType())) {
                beneficiary.setBeneficiaryType(null);
            }
            if (isEmptyField(mapper, beneficiary.getOwnershipUnavailableReason())) {
                beneficiary.setOwnershipUnavailableReason(null);
            }
            beneficiaryList.add(beneficiary);
        }
    }
    /**
     *
     * @param mapper
     * @param members
     */
     static void removeEmptyFieldforMembers(ObjectMapper mapper, List<Member> members) {
        List<Member> memberList = new ArrayList<>();
        for (Member member : members) {
            if (isEmptyField(mapper, member.getMemberAddress())) {
                member.setMemberAddress(null);
            }
            if (isEmptyField(mapper, member.getBusinessEntityType())) {
                member.setBusinessEntityType(null);
            }
            if (isEmptyField(mapper, member.getMemberType())) {
                member.setMemberType(null);
            }
            if (isEmptyField(mapper, member.getOwnershipUnavailableReason())) {
                member.setOwnershipUnavailableReason(null);
            }
            memberList.add(member);
        }

    }

    /**
     *
     * @param mapper
     * @param organization
     */
    private static void removeEmptyFieldsForOrganization(ObjectMapper mapper, Organization organization) {

        if (null != organization.getCorporateLinkage()) {
            removeEmptyFieldforCorporateLinkage(mapper, organization.getCorporateLinkage());
            if (isEmptyField(mapper, organization.getCorporateLinkage())) {
                organization.setCorporateLinkage(null);
            }
        }
        if (null != organization.getOwnershipStructure()) {
            removeEmptyFieldforMembers(mapper, organization.getOwnershipStructure().getMembers());
        }
        if (null != organization.getBeneficiaries()) {
            removeEmptyFieldforbeneficiaries(mapper, organization.getBeneficiaries());
        } else {
            organization.setBeneficiaries(null);
        }
        if (null != organization.getPrimarySIC() && isEmptyField(mapper, organization.getPrimarySIC())) {
            organization.setPrimarySIC(null);
        }
        if (null != organization.getBusinessEntityType() && isEmptyField(mapper, organization.getBusinessEntityType())) {
            organization.setBusinessEntityType(null);
        }
        if (null != organization.getAddress()) {
            removeEmptyforOrganisationAddress(mapper, organization);
        }

    }

    /**
     *
     * @param mapper
     * @param organization
     */
    private static void removeEmptyforOrganisationAddress(ObjectMapper mapper, Organization organization) {

        if (isEmptyField(mapper, organization)) {
            organization.setAddress(null);
        }
        if (null != organization.getAddress().getAddressCountry() && isEmptyField(mapper, organization.getAddress().getAddressCountry())) {
            organization.getAddress().setAddressCountry(null);
        }

    }

    /**
     *
     * @param mapper
     * @param productResponse
     */
     static boolean isEmptyField(ObjectMapper mapper, Object object) {
        String response = null;
        boolean empty = false;
        try {
            response = mapper.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            LOGGER.error(C.ERROR+ex.getMessage(), ex);
        }
        if (null != response && (response.equals(C.EMPTY_STRING) || response.equals(C.EMPTY_ARRAY))) {
            empty = true;
        }
        return empty;
    }

}
