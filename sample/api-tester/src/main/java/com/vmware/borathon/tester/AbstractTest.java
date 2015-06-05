/* Copyright VMware, Inc. All rights reserved. -- VMware Confidential */
package com.vmware.borathon.tester;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

/**
 * Base Class providing Spring configuration to the inheriting classes.
 *
 * This classes is not meant to become a repository more or less shared chunk of logic. Please
 * prefer creation of dedicated Beans over inheritance.
 *
 */
@ContextConfiguration(classes = SpringTestConfig.class)
@TestExecutionListeners(listeners = {
      DependencyInjectionTestExecutionListener.class })
public abstract class AbstractTest extends AbstractTestNGSpringContextTests {

}
