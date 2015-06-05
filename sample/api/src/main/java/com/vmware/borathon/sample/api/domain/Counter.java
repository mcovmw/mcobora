package com.vmware.borathon.sample.api.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Counter {

   @NotNull
   @Id
   @JsonProperty
   private String id;

   @NotNull
   @JsonProperty
   private Long count;

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public Long getCount() {
      return count;
   }

   public void setCount(Long count) {
      this.count = count;
   }

}
