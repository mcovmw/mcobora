/* Copyright VMware, Inc. All rights reserved. -- VMware Confidential */
package com.vmware.borathon.sample.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vmware.borathon.sample.api.domain.Counter;

@Repository
public interface CounterRepository extends JpaRepository<Counter, String> {
}
