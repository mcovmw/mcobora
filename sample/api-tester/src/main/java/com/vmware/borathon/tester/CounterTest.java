/* Copyright VMware, Inc. All rights reserved. -- VMware Confidential */

package com.vmware.borathon.tester;

import static org.testng.AssertJUnit.assertEquals;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.testng.annotations.Test;

@Component
public class CounterTest extends AbstractTest {

   @Autowired
   private RestClient restClient;

   @Value("${test.server.url}")
   private String testServerUrl;

   @Test
   public void testCounter_basic() {
      Long count = System.currentTimeMillis();
      Counter counter = new Counter();
      counter.setCount(count);

      counter.setId(UUID.randomUUID().toString());
      counter = restClient.saveCounter(counter);
      assertEquals(count, counter.getCount());
   }
}
