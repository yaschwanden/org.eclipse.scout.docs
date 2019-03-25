package org.eclipse.scout.contacts.client.edu.notification;

import org.eclipse.scout.contacts.client.common.AbstractDirtyFormHandler;
import org.eclipse.scout.contacts.client.edu.notification.TimerNotificationForm.MainBox.MessageTimeField;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.Order;

/**
 * <h3>{@link TimerNotificationForm}</h3>
 *
 * @author aho
 */
public class TimerNotificationForm extends AbstractForm {
  /**
   *
   */
  public TimerNotificationForm() {
    setHandler(new ViewHandler());
  }

  @Override
  protected String getConfiguredTitle() {
    return "Demo";
  }

  @Override
  protected int getConfiguredDisplayHint() {
    return IForm.DISPLAY_HINT_VIEW;
  }

  @Override
  protected String getConfiguredDisplayViewId() {
    return VIEW_ID_E;
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  public MessageTimeField getMessageTimeField() {
    return getFieldByClass(MessageTimeField.class);
  }

  @Order(1)
  public class MainBox extends AbstractGroupBox {

    @Order(-1000)
    public class MessageTimeField extends AbstractStringField {

      @Override
      protected String getConfiguredLabel() {
        return "Message Time";
      }

      @Override
      protected boolean getConfiguredMultilineText() {
        return true;
      }

      @Override
      protected boolean getConfiguredEnabled() {
        return false;
      }

      @Override
      protected int getConfiguredGridH() {
        return 2;
      }

    }

    @Order(101)
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class ViewHandler extends AbstractDirtyFormHandler {
  }
}
