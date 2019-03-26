package org.eclipse.scout.contacts.server.edu.notification;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.eclipse.scout.contacts.shared.edu.notification.TimeNotification;
import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.eclipse.scout.rt.platform.CreateImmediately;
import org.eclipse.scout.rt.platform.job.Jobs;
import org.eclipse.scout.rt.server.clientnotification.ClientNotificationRegistry;
import org.quartz.SimpleScheduleBuilder;

/**
 * <h3>{@link TimerNotificationSender}</h3>
 */
@ApplicationScoped
@CreateImmediately
public class TimerNotificationSender {

  @PostConstruct
  public void setup() {
    Jobs.schedule(() -> {
      sendDateToAllSessions();
    }, Jobs.newInput()
        .withName("Timer")
        .withExecutionTrigger(
            Jobs.newExecutionTrigger()
                .withStartIn(10, TimeUnit.SECONDS)
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(5))));
  }

  /**
   * TODO 4.02 Client notification: Publish a new {@link TimeNotification} to all sessions.<br>
   * Hint: use the {@link ClientNotificationRegistry} bean.
   */
  private void sendDateToAllSessions() {

  }

}
