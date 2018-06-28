
package com.dnb.ubo.v4.jsonbeans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "addressLine1",
    "addressLine2",
    "addressLine3",
    "primaryTown",
    "county",
    "zipOrPostalCode",
    "provinceOrState",
    "addressCountry"
})
public class Address {

    @JsonProperty("addressLine1")
    private String addressLine1;
    @JsonProperty("addressLine2")
    private String addressLine2;
    @JsonProperty("addressLine3")
    private String addressLine3;
    @JsonProperty("primaryTown")
    private String primaryTown;
    @JsonProperty("county")
    private String county;
    @JsonProperty("zipOrPostalCode")
    private String zipOrPostalCode;
    @JsonProperty("provinceOrState")
    private String provinceOrState;
    /**
     *
     */
    @JsonProperty("addressCountry")
    private AddressCountry addressCountry;

    /**
     *
     * @return The addressLine1
     */
    @JsonProperty("addressLine1")
    public String getAddressLine1() {
        return addressLine1;
    }

    /**
     *
     * @param addressLine1
     *     The addressLine1
     */
    @JsonProperty("addressLine1")
    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    /**
     *
     * @return The addressLine2
     */
    @JsonProperty("addressLine2")
    public String getAddressLine2() {
        return addressLine2;
    }

    /**
     *
     * @param addressLine2
     *     The addressLine2
     */
    @JsonProperty("addressLine2")
    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    /**
     *
     * @return The addressLine3
     */
    @JsonProperty("addressLine3")
    public String getAddressLine3() {
        return addressLine3;
    }

    /**
     *
     * @param addressLine3
     *     The addressLine3
     */
    @JsonProperty("addressLine3")
    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    /**
     *
     * @return
     *     The primaryTown
     */
    @JsonProperty("primaryTown")
    public String getPrimaryTown() {
        return primaryTown;
    }

    /**
     *
     * @param primaryTown
     *     The primaryTown
     */
    @JsonProperty("primaryTown")
    public void setPrimaryTown(String primaryTown) {
        this.primaryTown = primaryTown;
    }

    /**
     *
     * @return
     *     The county
     */
    @JsonProperty("county")
    public String getCounty() {
        return county;
    }

    /**
     *
     * @param county
     *     The county
     */
    @JsonProperty("county")
    public void setCounty(String county) {
        this.county = county;
    }

    /**
     *
     * @return The zipOrPostalCode
     */
    @JsonProperty("zipOrPostalCode")
    public String getZipOrPostalCode() {
        return zipOrPostalCode;
    }

    /**
     *
     * @param zipOrPostalCode
     *     The zipOrPostalCode
     */
    @JsonProperty("zipOrPostalCode")
    public void setZipOrPostalCode(String zipOrPostalCode) {
        this.zipOrPostalCode = zipOrPostalCode;
    }

    /**
     *
     * @return The provinceOrState
     */
    @JsonProperty("provinceOrState")
    public String getProvinceOrState() {
        return provinceOrState;
    }

    /**
     *
     * @param provinceOrState
     *     The provinceOrState
     */
    @JsonProperty("provinceOrState")
    public void setProvinceOrState(String provinceOrState) {
        this.provinceOrState = provinceOrState;
    }

    /**
     *
     * @return The addressCountry
     */
    @JsonProperty("addressCountry")
    public AddressCountry getAddressCountry() {
        return addressCountry;
    }

    /**
     *
     * @param addressCountry
     *     The addressCountry
     */
    @JsonProperty("addressCountry")
    public void setAddressCountry(AddressCountry addressCountry) {
        this.addressCountry = addressCountry;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(addressLine1).append(addressLine2).append(addressLine3).append(primaryTown).append(county).append(zipOrPostalCode).append(provinceOrState).append(addressCountry).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Address) == false) {
            return false;
        }
        Address rhs = ((Address) other);
        return new EqualsBuilder().append(addressLine1, rhs.addressLine1).append(addressLine2, rhs.addressLine2).append(addressLine3, rhs.addressLine3).append(primaryTown, rhs.primaryTown).append(county, rhs.county).append(zipOrPostalCode, rhs.zipOrPostalCode).append(provinceOrState, rhs.provinceOrState).append(addressCountry, rhs.addressCountry).isEquals();
    }

}
