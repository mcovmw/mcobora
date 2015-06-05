/* Copyright VMware, Inc. All rights reserved. -- VMware Confidential */
package com.vmware.borathon.sample.api.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vmware.borathon.sample.api.config.DataConfig;
import com.vmware.borathon.sample.api.domain.Counter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataConfig.class)
public class CounterRepositoryTest {

   private static final String TEST_COUNTER_ID = "testCounter_" + UUID.randomUUID();
   private static final Long ZERO = Long.valueOf(0);

   @Autowired
   private CounterRepository repository;

   @Test
   public void testCreateFindAndDelete() {
      List<Counter> list = repository.findAll();
      assertNotNull(list);
      assertEquals(0, list.size());

      Counter counter = new Counter();
      counter.setId(TEST_COUNTER_ID);
      counter.setCount(ZERO);

      counter = repository.save(counter);
      assertNotNull(counter);

      counter = repository.findOne(TEST_COUNTER_ID);
      assertNotNull(counter);
      assertEquals(counter.getId(), TEST_COUNTER_ID);
      assertEquals(counter.getCount(), ZERO);

      repository.delete(counter);
      list = repository.findAll();
      assertNotNull(list);
      assertEquals(0, list.size());
   }
}