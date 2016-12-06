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
package org.eclipse.scout.contacts.server.edu.platform02;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.eclipse.scout.contacts.server.edu.platform02.fixture.Chair;
import org.eclipse.scout.contacts.server.edu.platform02.fixture.IFurniture;
import org.eclipse.scout.contacts.server.edu.platform02.fixture.IKitchenFurniture;
import org.eclipse.scout.contacts.server.edu.platform02.fixture.INonExistingBean;
import org.eclipse.scout.contacts.server.edu.platform02.fixture.KitchenTable;
import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Bean;
import org.eclipse.scout.rt.platform.IBeanManager;
import org.eclipse.scout.rt.platform.util.Assertions.AssertionException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests to demonstrate usage of {@link IBeanManager} <br>
 * Finding a single bean <br>
 * {@link BEAN} , {@link ApplicationScoped}, {@link BEANS#get(Class)}
 */
public class BeanManagerTest {

  /**
   * The {@link IBeanManager} allows register classes, generating instances of these classes and querying the classes
   * and instances. This is useful for resolving dependencies.
   * <p>
   * By default, all classes annotated with {@link Bean} are automatically registered.
   * </p>
   * TODO Platform 2.1: provide the {@link IFurniture} to the bean context.
   */
  @Test
  public void getBean() {
    assertThat(BEANS.get(Chair.class), notNullValue());
    assertThat(BEANS.get(KitchenTable.class), notNullValue());
  }

  /**
   * By default, a new instance is created every time {@link BEANS#get(Class)} is called. You can create a singleton by
   * using the annotation {@link ApplicationScoped}
   * <p>
   * TODO Platform 2.2: ensure {@link Chair} has singleton character.
   */
  @Test
  public void getBeanSingleton() {
    assertThat(BEANS.get(Chair.class), is(BEANS.get(Chair.class)));
    assertThat(BEANS.get(KitchenTable.class), not(is(BEANS.get(KitchenTable.class))));
  }

  /**
   * Bean lookup also works with an interface, which is usually the preferred way.
   * <p>
   * TODO Platform 2.3
   */
  @Test
  public void lookupByInterface() {
    IKitchenFurniture bean = BEANS.get(IKitchenFurniture.class);
    Assert.assertEquals(bean.getClass(), KitchenTable.class);
  }

  /**
   * If there are multiple instances available in a hierarchy, querying a specific class with {@link BEANS#get(Class)}
   * always returns an instance of that class.
   * <p>
   * TODO Platform 2.4: fix the bean lookup. Use a specific class.
   */
  @Test
  public void multipleInstancesByClass() {
    IFurniture a = BEANS.get(Chair.class);
    assertThat(a, instanceOf(Chair.class));
  }

  /**
   * Querying by interface with {@link BEANS#get(Class)} is supposed to always find exactly one instance. Otherwise, an
   * exception will be thrown.
   * <p>
   * TODO Platform 2.5: fix the test below.
   */
  @Test(expected = AssertionException.class)
  public void multipleInstancesByInterface() {
    BEANS.get(IFurniture.class);
  }

  /**
   * {@link BEANS#opt(Class)} returns null if the is no bean available under this query or the bean if it exists. If
   * there are several beans registered with the same order (or none) under the query an {@link AssertionException} is
   * thrown.
   * <p>
   * TODO:
   */
  @Test
  public void optionalGet() {
    assertThat(BEANS.opt(INonExistingBean.class), nullValue());
  }

}
