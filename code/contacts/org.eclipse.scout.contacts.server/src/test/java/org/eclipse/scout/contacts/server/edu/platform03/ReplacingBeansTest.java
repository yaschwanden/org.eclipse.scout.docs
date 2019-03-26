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
package org.eclipse.scout.contacts.server.edu.platform03;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.eclipse.scout.contacts.server.edu.platform03.fixture.IWaveService;
import org.eclipse.scout.contacts.server.edu.platform03.fixture.WaveService;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Replace;
import org.junit.Test;

public class ReplacingBeansTest {

  /**
   * A bean can be replaced, by using the annotation {@link Replace}. A bean annotated with replace replaces its
   * superclass.
   * <p>
   * TODO 1.08 Platform: Fix the test without changing {@link IWaveService} or {@link WaveService}. <br>
   * Hint: create the WaveAndWindService.
   */
  @Test
  public void replaceBean() {
    assertThat(BEANS.get(IWaveService.class).getClass().getSimpleName(), is("WaveAndWindService"));
    assertThat(BEANS.get(WaveService.class).getClass().getSimpleName(), is("WaveAndWindService"));
    assertThat(BEANS.all(IWaveService.class).size(), is(1));
  }

}
