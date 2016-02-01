package org.eclipse.scout.contacts.client.timer;

import org.eclipse.scout.contacts.client.common.AbstractDirtyFormHandler;
import org.eclipse.scout.contacts.client.timer.TimerDemoForm.MainBox.MessageTimeField;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.platform.Order;

/**
 * TODO ClientNotification 1.2: Create a client notification handler that updates the message field, whenever a
 * notification arrives.
 * <ul>
 * <li>If the form is already open, it should find and update it</li>
 * <li>If not, it should open it</li>
 * </ul>
 * TODO ClientNotification 1.3: As always, make sure that the new notification handler is unit tested!
 */
public class TimerDemoForm extends AbstractForm {

  @Override
  protected String getConfiguredTitle() {
    return "Demo";
  }

  @Override
  protected int getConfiguredDisplayHint() {
    return IForm.DISPLAY_HINT_VIEW;
  }

  public void startNew() {
    startInternal(new NewHandler());
  }

  public MessageTimeField getMessageTimeField() {
    return getFieldByClass(MessageTimeField.class);
  }

  @Order(1)
  public class MainBox extends AbstractGroupBox {

    @Order(-1000)
    public class MessageTimeField extends AbstractDateField {

      @Override
      protected boolean getConfiguredHasTime() {
        return true;
      }

      @Override
      protected boolean getConfiguredHasDate() {
        return false;
      }

      @Override
      protected boolean getConfiguredEnabled() {
        return false;
      }

      @Override
      protected String getConfiguredFormat() {
        return "HH:mm:ss";
      }

      @Override
      protected String getConfiguredLabel() {
        return "Message Time";
      }
    }

    @Order(100)
    public class OkButton extends AbstractOkButton {
    }

    @Order(101)
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class NewHandler extends AbstractDirtyFormHandler {
  }

}
