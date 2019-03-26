package org.eclipse.scout.contacts.client.edu.notification;

import java.util.Optional;

import org.eclipse.scout.contacts.client.edu.notification.TimerNotificationForm.MainBox.MessageTimeField;
import org.eclipse.scout.contacts.shared.edu.notification.TimeNotification;
import org.eclipse.scout.rt.client.IClientSession;
import org.eclipse.scout.rt.client.job.ModelJobs;
import org.eclipse.scout.rt.client.ui.desktop.IDesktop;
import org.eclipse.scout.rt.platform.ApplicationScoped;

/**
 * <h3>{@link TimerNotificationHelper}</h3>
 */
@ApplicationScoped
public class TimerNotificationHelper {

  public void updateTimerNotificationForm(TimeNotification notification) {
    if (!ModelJobs.isModelThread()) {
      throw new RuntimeException("This method must be called in a model thread.");
    }
    TimerNotificationForm form = findOrCreateForm();
    if (form != null) {
      MessageTimeField messageTimeField = form.getMessageTimeField();

      StringBuilder b = new StringBuilder(Optional.ofNullable(messageTimeField.getValue()).orElse(""));
      b.append("\n").append(notification.getDate());

      messageTimeField.setValue(b.toString());
    }

  }

  private TimerNotificationForm findOrCreateForm() {
    TimerNotificationForm timerForm = findTimerForm();
    if (timerForm != null) {
      return timerForm;
    }
    else {
      TimerNotificationForm f = new TimerNotificationForm();
      f.start();
      return f;
    }
  }

  public TimerNotificationForm findTimerForm() {
    IDesktop desktop = ((IClientSession) IClientSession.CURRENT.get()).getDesktop();
    if (desktop != null && desktop.isOpened()) {
      TimerNotificationForm form = desktop.findForm(TimerNotificationForm.class);
      if (form != null) {
        return form;
      }
    }
    return null;
  }
}
