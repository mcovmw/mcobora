/* Copyright VMware, Inc. All rights reserved. -- VMware Confidential */
package com.vmware.borathon.tester;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@PropertySources({
      @PropertySource(value = "classpath:default.properties"),
      @PropertySource(value = "classpath:test.properties", ignoreResourceNotFound = true) })
public class SpringTestConfig {

   @Bean
   public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
      return new PropertySourcesPlaceholderConfigurer();
   }

   @Bean
   public ObjectMapper objectMapper() {
      return new ObjectMapper();
   }

   @Bean
   public RestClient restClient() {
      return new RestClient();
   }
}
