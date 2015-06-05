/* Copyright VMware, Inc. All rights reserved. -- VMware Confidential */

package com.vmware.borathon.tester;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RestClient {

   @Autowired
   private ObjectMapper objectMapper;
   
   @Value("${test.server.url}")
   private String testServerUrl;

   @Value("${test.server.port}")
   private String testServePort;

   private RestTemplate restTemplate;
   private final String COUNTER_ENDPOINT = "/sample-api/api/counters/";

   public RestClient() {
   }

   public Counter getCounter(String id) {
      restTemplate = new RestTemplate();
      return restTemplate.getForObject(getUrl() + id, Counter.class);
   }

   public Counter saveCounter(Counter counterToSet) {
      try {
         restTemplate = new RestTemplate();
         restTemplate.put(getUrl() + counterToSet.getId(),
               (counterToSet));
      } catch (RestClientException e) {
         e.printStackTrace();
      }
      return getCounter(counterToSet.getId());
   }

   private String getUrl() {
      String cat = testServerUrl + ":" + testServePort + COUNTER_ENDPOINT;
      return cat;
   }
}
