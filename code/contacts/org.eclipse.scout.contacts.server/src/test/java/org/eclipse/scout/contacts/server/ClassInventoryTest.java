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
package org.eclipse.scout.contacts.server;

import static org.eclipse.scout.contacts.server.test.util.CollectionMatchers.hasEntryMatching;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.util.Set;

import org.eclipse.scout.contacts.shared.organization.IOrganizationService;
import org.eclipse.scout.rt.platform.Bean;
import org.eclipse.scout.rt.platform.inventory.ClassInventory;
import org.eclipse.scout.rt.platform.inventory.IClassInfo;
import org.eclipse.scout.rt.platform.inventory.IClassInventory;
import org.junit.Test;

/**
 * Tests to demonstrate usage of {@link ClassInventory}
 */
public class ClassInventoryTest extends AbstractCourseTest {

  /**
   * TODO 1.1 ClassInventory: Browsing the ClassInventory It is possible to find all subclasses with
   * {@link IClassInventory#getAllKnownSubClasses(Class)}
   */
  @Test
  public void testFindKnownSubclasses() {
    Set<IClassInfo> coServices = ClassInventory.get().getAllKnownSubClasses(IOrganizationService.class);
    assertThat(coServices, hasSize(1));
    Class<?> expectedClass = null; //fix: specify the expected class
    assertThat(coServices, hasEntryMatching(e -> e.resolveClass().equals(expectedClass)));
  }

  /**
   * TODO 1.2 ClassInventory: Finding annotated classes Create a new class 'MyService' annotated with {@link Bean}. <br>
   * The ClassInventory only collects classes in projects with a resource called META-INF/scout.xml. <br>
   * See what happens, if you delete the scout.xml file.
   */
  @Test
  public void testFindAnnotations() {
    Set<IClassInfo> beanClasses = ClassInventory.get().getKnownAnnotatedTypes(Bean.class);
    assertThat(beanClasses, hasEntryMatching(e -> e.name().endsWith("MyService")));
  }

}
