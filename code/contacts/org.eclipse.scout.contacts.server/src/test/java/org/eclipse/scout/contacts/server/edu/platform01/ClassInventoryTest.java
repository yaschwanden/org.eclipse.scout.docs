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
package org.eclipse.scout.contacts.server.edu.platform01;

import static org.eclipse.scout.contacts.server.util.CollectionMatchers.hasEntryMatching;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.util.Set;

import org.eclipse.scout.contacts.server.edu.platform01.fixture.Beer;
import org.eclipse.scout.contacts.server.edu.platform01.fixture.CoronaBean;
import org.eclipse.scout.contacts.server.edu.platform01.fixture.IBeerBean;
import org.eclipse.scout.rt.platform.inventory.ClassInventory;
import org.eclipse.scout.rt.platform.inventory.IClassInfo;
import org.eclipse.scout.rt.platform.inventory.IClassInventory;
import org.junit.Test;

/**
 * Tests to demonstrate usage of {@link ClassInventory}
 */
public class ClassInventoryTest {

  /**
   * Browsing the ClassInventory It is possible to find all subclasses with
   * {@link IClassInventory#getAllKnownSubClasses(Class)}
   * <p>
   * TODO Platform 1.1: fix test.
   */
  @Test
  public void testFindKnownSubclasses() {
    Set<IClassInfo> subClassInfo = ClassInventory.get().getAllKnownSubClasses(IBeerBean.class);
    assertThat(subClassInfo, hasSize(1));
    Class<?> expectedSubClass = Object.class; // provide the correct class
    assertThat(subClassInfo, hasEntryMatching(e -> e.name().equals(expectedSubClass.getName())));
  }

  /**
   * The ClassInventory only collects classes in projects with a resource called META-INF/scout.xml. <br>
   * See what happens, if you delete the scout.xml file.
   * <p>
   * TODO Platform 1.2: Fix test. Hint: add a {@link Beer} annotation.<br>
   */
  @Test
  public void testFindAnnotations() {
    Set<IClassInfo> beanClasses = ClassInventory.get().getKnownAnnotatedTypes(Beer.class);
    assertThat(beanClasses, hasEntryMatching(e -> CoronaBean.class.getName().equals(e.name())));
  }

}
