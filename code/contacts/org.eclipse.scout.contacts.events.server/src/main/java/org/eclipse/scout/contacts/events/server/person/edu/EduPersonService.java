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
package org.eclipse.scout.contacts.events.server.person.edu;

import org.eclipse.scout.contacts.events.server.person.PersonServiceExtension;
import org.eclipse.scout.contacts.shared.person.PersonFormData;
import org.eclipse.scout.rt.platform.Replace;

/**
 * Person service extension that logs P.O. Box value
 */
@Replace
public class EduPersonService extends PersonServiceExtension {

  @Override
  public PersonFormData store(PersonFormData formData) {
    // TODO 5.07 Extensibility: Log contents of P.O. Box field
    return super.store(formData);
  }
}
