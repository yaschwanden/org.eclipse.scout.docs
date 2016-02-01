package org.eclipse.scout.contacts.server.timer;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.job.Jobs;
import org.eclipse.scout.rt.server.clientnotification.ClientNotificationRegistry;
import org.quartz.SimpleScheduleBuilder;

/**
 * Simple service sending client notifications every second
 */
@ApplicationScoped
public class TimerService {

  public void start() {
    Jobs.schedule(() -> {
      //TODO ClientNotification 1.1: publish the current date as a client notification for all sessions.
      BEANS.get(ClientNotificationRegistry.class).putForAllSessions(new Date());
    }, Jobs.newInput()
        .withName("Timer")
        .withExecutionTrigger(
            Jobs.newExecutionTrigger()
                .withStartIn(10, TimeUnit.SECONDS)
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever())));
  }

}
