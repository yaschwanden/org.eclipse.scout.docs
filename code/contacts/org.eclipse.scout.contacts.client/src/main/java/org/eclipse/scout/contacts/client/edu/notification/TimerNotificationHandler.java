package org.eclipse.scout.contacts.client.edu.notification;

import org.eclipse.scout.contacts.shared.edu.notification.TimeNotification;
import org.eclipse.scout.rt.client.context.ClientRunContexts;
import org.eclipse.scout.rt.client.job.ModelJobs;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.shared.notification.INotificationHandler;

/**
 * <h3>{@link TimerNotificationHandler}</h3>
 * <p>
 * TODO Client notification 1.2: create a handler for {@link TimeNotification}'s. <br>
 * Hint: schedule the notification handling as a model job ({@link ModelJobs}). Use {@link TimerNotificationHelper} bean
 * to bring the notification to the UI.
 */
public class TimerNotificationHandler implements INotificationHandler<TimeNotification> {

  @Override
  public void handleNotification(TimeNotification notification) {
    ModelJobs.schedule(
        () -> updateForm(notification),
        ModelJobs.newInput(ClientRunContexts.copyCurrent()));
  }

  /**
  
   */
  private void updateForm(TimeNotification notification) {
    BEANS.get(TimerNotificationHelper.class).updateTimerNotificationForm(notification);
  }

}
