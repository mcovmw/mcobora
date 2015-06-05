package com.vmware.borathon.sample.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.vmware.borathon.sample.api.config.DataConfig;
import com.vmware.borathon.sample.api.config.WebConfig;
import com.vmware.borathon.sample.api.domain.Counter;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { DataConfig.class, WebConfig.class })
public class CounterControllerTest {

   @Autowired
   protected WebApplicationContext wac;
   protected MockMvc mockMvc;

   private static final String COUNTER_API_URI = "/api/counters";

   private static final String TEST_COUNTER_ID = "testCounter_" + UUID.randomUUID();
   private static final Long TEST_COUNTER_COUNT = Long.valueOf(0);

   @Before
   public void setUp() throws Exception {
      if (mockMvc == null) {
         mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
      }
   }

   @Test
   public void testPutAndGet() throws Exception {
      Counter counter = new Counter();
      counter.setId(TEST_COUNTER_ID);
      counter.setCount(TEST_COUNTER_COUNT);

      // put
      mockMvc.perform(put(COUNTER_API_URI + "/{id}", TEST_COUNTER_ID)
            .content(JsonHelper.toJsonString(counter))
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(TEST_COUNTER_ID))
            .andExpect(jsonPath("$.count").value(TEST_COUNTER_COUNT.intValue()));

      // getOne
      mockMvc.perform(get(COUNTER_API_URI + "/{id}", TEST_COUNTER_ID)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(TEST_COUNTER_ID))
            .andExpect(jsonPath("$.count").value(TEST_COUNTER_COUNT.intValue()));

      // getAll
      mockMvc.perform(get(COUNTER_API_URI)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", Matchers.hasSize(1)))
            .andExpect(jsonPath("$[0].id").value(TEST_COUNTER_ID))
            .andExpect(jsonPath("$[0].count").value(TEST_COUNTER_COUNT.intValue()));

      // delete
      mockMvc.perform(delete(COUNTER_API_URI + "/{id}", TEST_COUNTER_ID)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());

      // getAll
      mockMvc.perform(get(COUNTER_API_URI)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", Matchers.hasSize(0)));
   }
}
