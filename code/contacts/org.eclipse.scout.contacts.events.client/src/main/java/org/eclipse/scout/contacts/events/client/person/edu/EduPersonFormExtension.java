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

import org.eclipse.scout.contacts.client.person.PersonForm;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.extension.ui.form.AbstractFormExtension;
import org.eclipse.scout.rt.client.extension.ui.form.FormChains.FormInitFormChain;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.extension.Extends;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.dimension.IDimensions;

/*
 * TODO 5.04 Extensibility: uncomment extended class declaration, fix compile errors and add field
 *                         user TEXTS.get("POBox"); for its label
 *
 * TODO 5.05 Extensibility: annotate class so that an extension data is created
 *
 * TODO 5.06 Extensibility: set DateOfBirthField invisible
 */
@Data(org.eclipse.scout.contacts.events.shared.person.edu.EduPersonFormExtensionData.class) // Solution Exercise 1.2
public class EduPersonFormExtension extends AbstractFormExtension<PersonForm> {

  public EduPersonFormExtension(PersonForm ownerForm) {
    super(ownerForm);
  }

  public PoBoxField getPoBoxField() {
    return getOwner().getFieldByClass(PoBoxField.class);
  }

  /**
   * Solution Exercise 3
   */
  @Override
  public void execInitForm(FormInitFormChain chain) {
    chain.execInitForm();
    getOwner().getDateOfBirthField().setVisible(false, IDimensions.VISIBLE_CUSTOM);
  }

  /**
   * Solution Exercise 1.1
   */
  @Order(15)
  @Extends(PersonForm.MainBox.DetailsBox.ContactInfoBox.AddressBox.class)
  public class PoBoxField extends AbstractStringField {
    @Override
    protected String getConfiguredLabel() {
      return TEXTS.get("POBox");
    }
  }
}
