package com.dnb.ubo.config;

/**
 * @Name: UBOAPIConfig.java
 * @Class_Description: This is the beans config class for the UBOAPI application
 *
 * @author: Cognizant Technology Solutions
 * @Created_On: Jan 5, 2016
 *
 *              Confidential and Proprietary. Copyright (C) 2016 D&B. All Rights
 *              Reserved.
 */

import java.util.concurrent.TimeUnit;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Config;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.amazonaws.services.kms.model.InvalidCiphertextException;
import com.dnb.ubo.security.kms.KMSDecryptor;

@Configuration
public class ApiConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiConfig.class);
    @Autowired
    private Environment environment;
    Driver driver = null;

    @Bean(name = "driver")
    public Driver getDriver() {
        try {
            String region = environment.getProperty("region");
            KMSDecryptor decryptor = new KMSDecryptor(region);
            String userName = decryptor.getDecryptedValue(environment.getProperty("usrNme"));
            String password = decryptor.getDecryptedValue(environment.getProperty("pswd"));
            Config config = Config.build().withConnectionLivenessCheckTimeout(Long.parseLong(environment.getProperty("connectionLivenessCheckTimeout")), TimeUnit.SECONDS).toConfig();
            driver = GraphDatabase.driver(environment.getProperty("driver_url"), AuthTokens.basic(userName, password), config);
            return driver;
        } catch(InvalidCiphertextException|IllegalArgumentException exception){
            LOGGER.error("Error: "+" Exception occured during decryption", exception);
            if (null != driver) {
                driver.close();
            }
            return driver;
        } catch (Exception  e) {
            LOGGER.error("Error: "+" Exception occured neo4j driver configurations", e);
            if (null != driver) {
                driver.close();
            }
            return driver;
    }

}
}
