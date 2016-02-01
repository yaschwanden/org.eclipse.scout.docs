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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.eclipse.scout.contacts.server.test.email.EmailService;
import org.eclipse.scout.contacts.server.test.email.IEmailService;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Replace;
import org.junit.Test;

public class ReplacingBeansTest {

  /**
   * TODO Platform 3.1 Replacing Beans: A bean can be replaced, by using the annotation {@link Replace}. A bean
   * annotated with replace replaces its superclass.<br>
   * Fix the test without changing {@link EmailService} or {@link IEmailService}
   */
  @Test
  public void replaceBean() {
    assertThat(BEANS.get(IEmailService.class).getName(), is("SpecialEmailService"));
    assertThat(BEANS.get(EmailService.class).getName(), is("SpecialEmailService"));
  }

}
