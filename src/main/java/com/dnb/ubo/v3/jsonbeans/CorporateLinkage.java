
package com.dnb.ubo.v3.jsonbeans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "domesticUltimate",
    "globalUltimate",
    "parent"
})
public class CorporateLinkage {

    @JsonProperty("domesticUltimate")
    private DomesticUltimate domesticUltimate;
    @JsonProperty("globalUltimate")
    private GlobalUltimate globalUltimate;
    @JsonProperty("parent")
    private Parent parent;

    /**
     *
     * @return
     *     The domesticUltimate
     */
    @JsonProperty("domesticUltimate")
    public DomesticUltimate getDomesticUltimate() {
        return domesticUltimate;
    }

    /**
     *
     * @param domesticUltimate
     *     The domesticUltimate
     */
    @JsonProperty("domesticUltimate")
    public void setDomesticUltimate(DomesticUltimate domesticUltimate) {
        this.domesticUltimate = domesticUltimate;
    }

    /**
     *
     * @return
     *     The globalUltimate
     */
    @JsonProperty("globalUltimate")
    public GlobalUltimate getGlobalUltimate() {
        return globalUltimate;
    }

    /**
     *
     * @param globalUltimate
     *     The globalUltimate
     */
    @JsonProperty("globalUltimate")
    public void setGlobalUltimate(GlobalUltimate globalUltimate) {
        this.globalUltimate = globalUltimate;
    }

    /**
     *
     * @return
     *     The parent
     */
    @JsonProperty("parent")
    public Parent getParent() {
        return parent;
    }

    /**
     *
     * @param parent
     *     The parent
     */
    @JsonProperty("parent")
    public void setParent(Parent parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(domesticUltimate).append(globalUltimate).append(parent).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CorporateLinkage) == false) {
            return false;
        }
        CorporateLinkage rhs = ((CorporateLinkage) other);
        return new EqualsBuilder().append(domesticUltimate, rhs.domesticUltimate).append(globalUltimate, rhs.globalUltimate).append(parent, rhs.parent).isEquals();
    }

}
