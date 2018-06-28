package com.dnb.ubo.config;

/**
 * @Name: UBOAPIPropertyConfig.java
 * @Class_Description: This is the property config class for the UBOAPI
 *                     appliaction
 *
 * @author: Cognizant Technology Solutions
 * @Created_On: Jan 5, 2016
 *
 *              Confidential and Proprietary. Copyright (C) 2016 D&B. All Rights
 *              Reserved.
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySources({
@PropertySource(value = "${configLocation}/application.properties")
})
public class ApiPropertyConfig {

    @Bean
    public  PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
