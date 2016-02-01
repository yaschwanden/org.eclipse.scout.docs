package org.eclipse.scout.contacts.client.timer;

import java.util.Date;

import org.eclipse.scout.contacts.client.ClientSession;
import org.eclipse.scout.rt.client.context.ClientRunContexts;
import org.eclipse.scout.rt.client.job.ModelJobs;
import org.eclipse.scout.rt.client.ui.desktop.IDesktop;
import org.eclipse.scout.rt.shared.notification.INotificationHandler;

/**
 * <h3>{@link TimerNotificationHandler}</h3>
 *
 * @author jgu
 */
public class TimerNotificationHandler implements INotificationHandler<Date> {

  @Override
  public void handleNotification(Date notification) {
    ModelJobs.schedule(
        () -> updateForm(notification),
        ModelJobs.newInput(ClientRunContexts.copyCurrent()));
  }

  private void updateForm(Date notification) {
    TimerDemoForm f = findOrCreateForm();
    if (f != null) {
      f.getMessageTimeField().setValue(notification);
    }
  }

  private TimerDemoForm findOrCreateForm() {
    IDesktop desktop = ClientSession.get().getDesktop();
    if (desktop != null && desktop.isOpened()) {
      TimerDemoForm form = desktop.findForm(TimerDemoForm.class);
      if (form != null) {
        return form;
      }
      else {
        TimerDemoForm f = new TimerDemoForm();
        f.setModal(false);
        f.startNew();
        return f;
      }
    }
    return null;
  }

}
