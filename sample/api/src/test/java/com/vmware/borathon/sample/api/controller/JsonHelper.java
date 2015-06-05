package com.vmware.borathon.sample.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class JsonHelper {

   private static final ObjectWriter OW = new ObjectMapper().writer().withDefaultPrettyPrinter();

   public static String toJsonString(Object object) {
      try {
         return OW.writeValueAsString(object);
      } catch (JsonProcessingException e) {
         // log it somewhere
         return null;
      }
   }
}
