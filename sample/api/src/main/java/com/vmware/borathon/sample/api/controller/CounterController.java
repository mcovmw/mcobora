package com.vmware.borathon.sample.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vmware.borathon.sample.api.domain.Counter;
import com.vmware.borathon.sample.api.repository.CounterRepository;

@RestController
@RequestMapping("api/counters")
public class CounterController {

   @Autowired
   private CounterRepository repo;

   @RequestMapping(method = RequestMethod.GET)
   public @ResponseBody List<Counter> getAll() {
      return repo.findAll();
   }

   @RequestMapping(value = "/{id}", method = RequestMethod.GET)
   public @ResponseBody Counter get(@PathVariable String id) {
      return repo.findOne(id);
   }

   @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
   public @ResponseBody Counter put(@PathVariable String id, @Valid @RequestBody Counter counter) {
      return repo.save(counter);
   }

   @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
   public @ResponseBody void delete(@PathVariable String id) {
      repo.delete(id);
   }
}
