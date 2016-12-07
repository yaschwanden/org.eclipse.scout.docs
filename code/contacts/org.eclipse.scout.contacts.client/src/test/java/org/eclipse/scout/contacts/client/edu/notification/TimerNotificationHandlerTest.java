package org.eclipse.scout.contacts.client.edu.notification;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Method;

import org.eclipse.scout.contacts.client.edu.notification.TimerNotificationForm.MainBox.MessageTimeField;
import org.eclipse.scout.contacts.shared.edu.notification.TimeNotification;
import org.eclipse.scout.rt.client.job.ModelJobs;
import org.eclipse.scout.rt.client.testenvironment.TestEnvironmentClientSession;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.testing.client.runner.ClientTestRunner;
import org.eclipse.scout.rt.testing.client.runner.RunWithClientSession;
import org.eclipse.scout.rt.testing.platform.runner.RunWithSubject;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * <h3>{@link TimerNotificationHandlerTest}</h3>
 */
@RunWith(ClientTestRunner.class)
@RunWithClientSession(TestEnvironmentClientSession.class)
@RunWithSubject("default")
public class TimerNotificationHandlerTest {

  @After
  public void cleanup() {
    TimerNotificationForm form = BEANS.get(TimerNotificationHelper.class).findTimerForm();
    form.doClose();
  }

  /**
   * TODO Client notification 4.1: publish a new notification and expect the date will be shown in the
   * {@link MessageTimeField}.<br>
   * Hint: use the {@link TimerNotificationHandler} to publish.
   */
  @Test
  public void receiveAndDisplayTimerNotification() throws Exception {

    TimeNotification timeNotification = new TimeNotification();

    TimerNotificationHandler h = new TimerNotificationHandler();
    // via reflection due to the method is part of the exercise.
    Method testMethod = TimerNotificationHandler.class.getMethod("handleNotification", TimeNotification.class);
    testMethod.invoke(h, timeNotification);

    ModelJobs.yield();
    TimerNotificationForm form = BEANS.get(TimerNotificationHelper.class).findTimerForm();
    assertNotNull(form);
    assertEquals(timeNotification.getDate(), form.getMessageTimeField().getValue());
  }

}
