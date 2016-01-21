/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.contacts.server.test;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.eclipse.scout.contacts.server.organization.OrganizationService;
import org.eclipse.scout.contacts.server.person.PersonService;
import org.eclipse.scout.contacts.server.test.address.AddressService;
import org.eclipse.scout.contacts.server.test.address.IAddressService;
import org.eclipse.scout.contacts.shared.person.IPersonService;
import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Bean;
import org.eclipse.scout.rt.platform.IBeanManager;
import org.eclipse.scout.rt.platform.util.Assertions.AssertionException;
import org.junit.Test;

/**
 * Tests to demonstrate usage of {@link IBeanManager} <br>
 * Finding a single bean <br>
 * {@link BEAN} , {@link ApplicationScoped}, {@link BEANS#get(Class)}
 */
public class BeanManagerTest extends AbstractTestWithJandexRebuild {

  /**
   * TODO 2.1 BEANS: The {@link IBeanManager} allows register classes, generating instances of these classes and
   * querying the classes and instances. This is useful for resolving dependencies.
   * <p>
   * By default, all classes annotated with {@link Bean} are automatically registered.
   * </p>
   * Make sure the AddressService is found by the bean manager by adding @Bean to the {@link IAddressService}
   */
  @Test
  public void getBean() {
    BEANS.get(OrganizationService.class);
    BEANS.get(AddressService.class);
  }

  /**
   * TODO 2.2 BEANS: By default, a new instance is created every time {@link BEANS#get(Class)} is called. You can create
   * a singleton by using the annotation {@link ApplicationScoped}
   */
  @Test
  public void getBeanSingleton() {
    AddressService b1 = BEANS.get(AddressService.class);
    AddressService b2 = BEANS.get(AddressService.class);
    assertThat(b1, is(b2));
  }

  /**
   * TODO 2.3 BEANS: Bean lookup also works with an interface, which is usually the preferred way.
   */
  @Test
  public void lookupByInterface() {
    IPersonService p = null;// BEANS.get(...); FIX
    assertThat(p, instanceOf(PersonService.class));
  }

  /**
   * TODO 2.4 BEANS: If there are multiple instances available in a hierarchy, querying a specific class with
   * {@link BEANS#get(Class)} always returns an instance of that class.
   */
  @Test
  public void multipleInstancesByClass() {
    IAddressService a = null; // BEANS.get(...); FIX
    assertThat(a, instanceOf(AddressService.class));
  }

  /**
   * TODO 2.5 BEANS: Querying by interface with {@link BEANS#get(Class)} is supposed to always find exactly one
   * instance. Otherwise, an exception will be thrown. Fix the tests below!
   */
  @Test(expected = AssertionException.class)
  public void multipleInstancesByInterface() {
    // BEANS.get(...); // FIX
  }

}
