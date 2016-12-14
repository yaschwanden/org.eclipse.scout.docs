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
import org.eclipse.scout.contacts.events.shared.person.edu.EduPersonFormExtensionData;
import org.eclipse.scout.contacts.shared.person.PersonFormData;
import org.eclipse.scout.rt.platform.Replace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Person service extension that logs P.O. Box value
 */
@Replace
public class EduPersonService extends PersonServiceExtension {

  private static final Logger LOG = LoggerFactory.getLogger(EduPersonService.class);

  @Override
  public PersonFormData store(PersonFormData formData) {
    EduPersonFormExtensionData data = formData.getContribution(EduPersonFormExtensionData.class);
    String poBox = data.getPoBox().getValue();
    LOG.info("store person with P.O. Box '{}'", poBox);
    return super.store(formData);
  }
}
