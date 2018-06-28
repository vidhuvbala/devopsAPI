package com.dnb.ubo.v3.controller;

import static org.junit.Assert.assertEquals;

import java.nio.charset.Charset;
import java.util.Properties;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.dnb.ubo.v3.constants.C;
import com.dnb.ubo.v3.exception.ApplicationException;
import com.dnb.ubo.v3.exception.ExceptionControllerAdvice;
import com.dnb.ubo.v3.jsonbeans.ApiRequest;
import com.dnb.ubo.v3.jsonbeans.ProductRequest;
import com.dnb.ubo.v3.jsonbeans.TransactionInfo;
import com.dnb.ubo.v3.response.JsonBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * @Name: UBOAPIControllerTest.java
 * @Class_Description: The class UBOAPIControllerTest contains Junit tests
 *
 * @author: Cognizant Technology Solutions
 * @Created_On: Feb 17, 2016
 *
 *              Confidential and Proprietary. Copyright (C) 2016 D&B. All Rights
 *              Reserved.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/test/resources/spring/config/test-uboapi-config.xml"})
@WebAppConfiguration
@ComponentScan(basePackages = "com.dnb.ubo.v3")
public class ControllerTest {

    @Autowired
    private Controller UBOAPIController;

    /** The mock mvc. */
    private MockMvc mockMvc;
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @BeforeClass
    public static void setSystemProperty() {
        Properties properties = System.getProperties();
        properties.setProperty("spring.profiles.active", "qa");
        properties.setProperty("configLocation", "file:${CATALINA_HOME}/conf");
    }

    @Before
    public void setUp() throws Exception {

        mockMvc = MockMvcBuilders.standaloneSetup(this.UBOAPIController).setControllerAdvice(new ExceptionControllerAdvice()).build();
    }

    @Test
    public void getOwnerShipStructure() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.request(HttpMethod.GET, "/v3/organizations/434503766/beneficialownershipstructure").param("transactionId", "1").param("productCode", "30636")
                        .param("ownershipType", "BENF_OWRP");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse content = result.getResponse();
        assertEquals(String.valueOf(content.getStatus()), "200");
    }

    @Test
    public void getOwnerListBeneficieries() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.request(HttpMethod.GET, "/v3/organizations/434503766/beneficialownershiplist").param("transactionId", "1").param("productCode", "30637");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse content = result.getResponse();
        assertEquals(String.valueOf(content.getStatus()), "200");
    }

    @Test
    public void getOwnerListBeneficieriesNoOwnershipmeetsFilerCriteria() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.request(HttpMethod.GET, "/v3/organizations/434503766/beneficialownershiplist").param("transactionId", "1").param("productCode", "30637")
                        .param("ownershipType", "IDIR_OWRP").param("ownershipPercentage", "25");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse content = result.getResponse();
        assertEquals(String.valueOf(content.getStatus()), "200");
    }

    @Test
    public void getOwnerListBeneficieriesOwnershipmeetsFilerCriteria() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.request(HttpMethod.GET, "/v3/organizations/434503766/beneficialownershiplist").param("transactionId", "1").param("productCode", "30637")
                        .param("ownershipType", "IDIR_OWRP").param("ownershipPercentage", ".01");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse content = result.getResponse();
        assertEquals(String.valueOf(content.getStatus()), "200");
    }

    @Test
    public void getTargetDunsNotFound() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.request(HttpMethod.GET, "/v3/organizations/01010/beneficialownershiplist").param("transactionId", "1").param("productCode", "30637");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse content = result.getResponse();
        assertEquals(String.valueOf(content.getStatus()), "200");
    }

    @Test
    public void getOwnerListBeneficieriesFilter() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.request(HttpMethod.GET, "/v3/organizations/434503766/beneficialownershiplist").param("transactionId", "1").param("productCode", "30637")
                        .param("ownershipType", "DIRC_OWRP");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse content = result.getResponse();
        assertEquals(String.valueOf(content.getStatus()), "200");
    }

    @Test
    public void getListBeneficieriesInvalidDUNS() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.request(HttpMethod.GET, "/v3/organizations/1213232323232/beneficialownershiplist").param("transactionId", "1").param("productCode", "30637");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse content = result.getResponse();
        assertEquals(String.valueOf(content.getStatus()), "400");
        JsonBuilder.getExceptionBeneficiariesOperation(new ApplicationException(C.STRUCTURE, C.UB014));

    }

    @Test
    public void getListBeneficieriesInvalidProductCode() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.request(HttpMethod.GET, "/v3/organizations/434503766/beneficialownershiplist").param("transactionId", "1").param("productCode", "30636");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse content = result.getResponse();
        assertEquals(String.valueOf(content.getStatus()), "422");
        JsonBuilder.getExceptionBeneficiariesOperation(new ApplicationException(C.STRUCTURE, C.UB014));

    }

    @Test
    public void getStructureInvalidCombinationforFilter() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.request(HttpMethod.GET, "/v3/organizations/434503766/beneficialownershipstructure").param("transactionId", "1").param("productCode", "30636")
                        .param("excludeUndisclosedHoldings", "true");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse content = result.getResponse();
        assertEquals(String.valueOf(content.getStatus()), "200");
        JsonBuilder.getExceptionBeneficiariesOperation(new ApplicationException(C.STRUCTURE, C.UB014));

    }

    @Test
    public void getOwnerStructureFilterCriteria() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.request(HttpMethod.GET, "/v3/organizations/434503766/beneficialownershipstructure").param("transactionId", "1").param("productCode", "30636")
                        .param("ownershipPercentage", ".01");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse content = result.getResponse();
        assertEquals(String.valueOf(content.getStatus()), "200");
    }

    @Test
    public void getOwnerShipInvalidDUNS() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.request(HttpMethod.GET, "/v3/organizations/1213232323232/beneficialownershipstructure").param("transactionId", "1").param("productCode", "30636");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse content = result.getResponse();
        assertEquals(String.valueOf(content.getStatus()), "400");
        JsonBuilder.getExceptionBeneficiariesOperation(new ApplicationException(C.STRUCTURE, C.UB014));

    }

    @Test
    public void getOwnerShipSpecialCharduns() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.request(HttpMethod.GET, "/v3/organizations/12345678*/beneficialownershipstructure").param("transactionId", "1").param("productCode", "30636")
                        .param(C.COUNTRY_CODE, "GB");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse content = result.getResponse();
        assertEquals(String.valueOf(content.getStatus()), "400");
        JsonBuilder.getExceptionBeneficiariesOperation(new ApplicationException(C.UB003, C.UB003));

    }

    @Test
    public void getListBenefInvalidPercentage() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.request(HttpMethod.GET, "/v3/organizations/434503766/beneficialownershiplist").param("transactionId", "1").param("productCode", "30637")
                        .param("ownershipPercentage", "1213.123");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse content = result.getResponse();
        assertEquals(String.valueOf(content.getStatus()), "400");
        JsonBuilder.getExceptionBeneficiariesOperation(new ApplicationException(C.UB003, C.UB003));

    }

    @Test
    public void getOwnerShipInvalidownershipType() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.request(HttpMethod.GET, "/v3/organizations/434503766/beneficialownershiplist").param("transactionId", "1").param("ownershipType", "123")
                        .param("productCode", "30637");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse content = result.getResponse();
        assertEquals(String.valueOf(content.getStatus()), "400");
        JsonBuilder.getExceptionBeneficiariesOperation(new ApplicationException(C.SERVICE_UNAVAILABLE_TEXT, C.UB015));

    }

    @Test
    public void getListBenefUndisclosed() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.request(HttpMethod.GET, "/v3/organizations/434503766/beneficialownershiplist").param("transactionId", "1").param("excludeUndisclosedHoldings", "true")
                        .param("productCode", "30637");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse content = result.getResponse();
        assertEquals(String.valueOf(content.getStatus()), "200");

    }

    @Test
    public void getListBenefInvalidProductCode() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.request(HttpMethod.GET, "/v3/organizations/434503766/beneficialownershiplist").param("transactionId", "1").param("isoAlpha2CountryCode", "AE");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse content = result.getResponse();
        assertEquals(String.valueOf(content.getStatus()), "422");
        JsonBuilder.getExceptionBeneficiariesOperation(new ApplicationException(C.SERVICE_UNAVAILABLE_TEXT, C.UB015));
    }

    @Test
    public void getListBenefInvalidTransactionTimestamp() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.request(HttpMethod.GET, "/v3/organizations/434503766/beneficialownershiplist").param("transactionId", "1").param("productCode", "30637")
                        .param("transactionTimestamp", "2013-09-29T18:43:19");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse content = result.getResponse();
        assertEquals(String.valueOf(content.getStatus()), "200");
        JsonBuilder.getExceptionBeneficiariesOperation(new ApplicationException(C.SERVICE_UNAVAILABLE_TEXT, C.UB015));

    }

    @Test
    public void getListBenefInvalidTransactionId() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.request(HttpMethod.GET, "/v3/organizations/434503766/beneficialownershiplist").param("transactionId", "").param("productCode", "30637");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse content = result.getResponse();
        assertEquals(String.valueOf(content.getStatus()), "422");
        JsonBuilder.getExceptionBeneficiariesOperation(new ApplicationException(C.SERVICE_UNAVAILABLE_TEXT, C.UB015));

    }

    @Test
    public void getListBenefInvalidCountryCode() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.request(HttpMethod.GET, "/v3/organizations/434503766/beneficialownershiplist").param("transactionId", "1").param("productCode", "30637")
                        .param("isoAlpha2CountryCode", "aes");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse content = result.getResponse();
        assertEquals(String.valueOf(content.getStatus()), "400");
        JsonBuilder.getExceptionBeneficiariesOperation(new ApplicationException(C.SERVICE_UNAVAILABLE_TEXT, C.UB015));
    }

    @Test
    public void getListBenefInvalidOwnershipPercentage() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.request(HttpMethod.GET, "/v3/organizations/434503766/beneficialownershiplist").param("transactionId", "1").param("productCode", "30637")
                        .param("ownershipPercentage", "zx");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse content = result.getResponse();
        assertEquals(String.valueOf(content.getStatus()), "422");
        JsonBuilder.getExceptionBeneficiariesOperation(new ApplicationException(C.SERVICE_UNAVAILABLE_TEXT, C.UB015));
    }

    @Test
    public void getOwnerShipListPost() throws Exception {
        ApiRequest apiRequest = new ApiRequest();
        ProductRequest productRequest = new ProductRequest();
        TransactionInfo transactionInfo = new TransactionInfo();
        transactionInfo.setTransactionId("1");
        apiRequest.setProductRequest(productRequest);
        apiRequest.setTransactionInfo(transactionInfo);
        productRequest.setProductCode(30637);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(apiRequest);

        // mockMvc.perform(post("/v1/organizations/434503766/beneficialownershiplist").contentType(APPLICATION_JSON_UTF8).content(requestJson)).andExpect(status().isOk());
        RequestBuilder requestBuilder = MockMvcRequestBuilders.request(HttpMethod.POST, "/v3/organizations/434503766/beneficialownershiplist").contentType(APPLICATION_JSON_UTF8).content(requestJson);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse content = result.getResponse();
        assertEquals(String.valueOf(content.getStatus()), "200");
    }

    @Test
    public void getOwnerShipListProductCodeNotPresent() throws Exception {
        ApiRequest apiRequest = new ApiRequest();
        ProductRequest productRequest = new ProductRequest();
        TransactionInfo transactionInfo = new TransactionInfo();
        transactionInfo.setTransactionId("1");
        apiRequest.setProductRequest(productRequest);
        apiRequest.setTransactionInfo(transactionInfo);
        // productRequest.setProductCode(30636);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(apiRequest);

        // mockMvc.perform(post("/v1/organizations/434503766/beneficialownershiplist").contentType(APPLICATION_JSON_UTF8).content(requestJson)).andExpect(status().isOk());
        RequestBuilder requestBuilder = MockMvcRequestBuilders.request(HttpMethod.POST, "/v3/organizations/434503766/beneficialownershiplist").contentType(APPLICATION_JSON_UTF8).content(requestJson);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse content = result.getResponse();
        assertEquals(String.valueOf(content.getStatus()), "422");
    }

    @Test
    public void getOwnerShipStructurePost() throws Exception {
        ApiRequest apiRequest = new ApiRequest();
        ProductRequest productRequest = new ProductRequest();
        TransactionInfo transactionInfo = new TransactionInfo();
        transactionInfo.setTransactionId("1");
        apiRequest.setProductRequest(productRequest);
        apiRequest.setTransactionInfo(transactionInfo);
        productRequest.setProductCode(30636);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(apiRequest);

        // mockMvc.perform(post("/v1/organizations/434503766/beneficialownershiplist").contentType(APPLICATION_JSON_UTF8).content(requestJson)).andExpect(status().isOk());
        RequestBuilder requestBuilder = MockMvcRequestBuilders.request(HttpMethod.POST, "/v3/organizations/434503766/beneficialownershipstructure").contentType(APPLICATION_JSON_UTF8).content(requestJson);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse content = result.getResponse();
        assertEquals(String.valueOf(content.getStatus()), "200");
    }

    @Test
    public void getOwnerShipStructureProductCodeNotPresent() throws Exception {
        ApiRequest apiRequest = new ApiRequest();
        ProductRequest productRequest = new ProductRequest();
        TransactionInfo transactionInfo = new TransactionInfo();
        transactionInfo.setTransactionId("1");
        apiRequest.setProductRequest(productRequest);
        apiRequest.setTransactionInfo(transactionInfo);
        // productRequest.setProductCode(30636);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(apiRequest);

        // mockMvc.perform(post("/v1/organizations/434503766/beneficialownershiplist").contentType(APPLICATION_JSON_UTF8).content(requestJson)).andExpect(status().isOk());
        RequestBuilder requestBuilder = MockMvcRequestBuilders.request(HttpMethod.POST, "/v3/organizations/434503766/beneficialownershipstructure").contentType(APPLICATION_JSON_UTF8).content(requestJson);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse content = result.getResponse();
        assertEquals(String.valueOf(content.getStatus()), "422");
    }

    @Test
    public void getOwnerShipStructInvalidCombination() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.request(HttpMethod.GET, "/v3/organizations/372840736/beneficialownershipstructure").param("transactionId", "1").param("productCode", "30636")
                        .param("ownershipType", "IDIR_OWRP").param("excludeUndisclosedHoldings", "true");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse content = result.getResponse();
        assertEquals(String.valueOf(content.getStatus()), "200");
        JsonBuilder.getExceptionBeneficiariesOperation(new ApplicationException(C.STRUCTURE, C.UB014));

    }
}