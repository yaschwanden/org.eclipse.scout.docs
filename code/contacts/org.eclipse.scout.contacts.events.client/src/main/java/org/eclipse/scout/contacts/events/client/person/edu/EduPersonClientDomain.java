/*******************************************************************************
 * Copyright (c) 2016 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.contacts.events.client.person.edu;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.IPlatform.State;
import org.eclipse.scout.rt.platform.IPlatformListener;
import org.eclipse.scout.rt.platform.PlatformEvent;
import org.eclipse.scout.rt.shared.extension.IExtensionRegistry;

/**
 * Mock-up of person client domain.
 */
public class EduPersonClientDomain implements IPlatformListener {

  @Override
  public void stateChanged(PlatformEvent event) {
    if (event.getState() == State.BeanManagerValid) {
      IExtensionRegistry extensionRegistry = BEANS.get(IExtensionRegistry.class);
      initializeExtensions(extensionRegistry);
    }
  }

  protected void initializeExtensions(IExtensionRegistry registry) {
    // model extensions
    // TODO 5.01 Extensibility: register form extension

    // move field
    // TODO 5.02 Extensibility: register move
  }
}
