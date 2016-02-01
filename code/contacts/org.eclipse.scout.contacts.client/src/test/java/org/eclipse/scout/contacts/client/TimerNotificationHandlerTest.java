package org.eclipse.scout.contacts.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.eclipse.scout.contacts.client.timer.TimerDemoForm;
import org.eclipse.scout.contacts.client.timer.TimerNotificationHandler;
import org.eclipse.scout.rt.client.job.ModelJobs;
import org.eclipse.scout.rt.client.testenvironment.TestEnvironmentClientSession;
import org.eclipse.scout.rt.testing.client.runner.ClientTestRunner;
import org.eclipse.scout.rt.testing.client.runner.RunWithClientSession;
import org.eclipse.scout.rt.testing.platform.runner.RunWithSubject;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * <h3>{@link TimerNotificationHandlerTest}</h3>
 *
 * @author jgu
 */
@RunWith(ClientTestRunner.class)
@RunWithClientSession(TestEnvironmentClientSession.class)
@RunWithSubject("default")
public class TimerNotificationHandlerTest {

  @After
  public void cleanup() {
    TimerDemoForm form = Desktop.CURRENT.get().findForm(TimerDemoForm.class);
    form.doClose();
  }

  @Test
  public void formOpen() {
    TimerNotificationHandler h = new TimerNotificationHandler();
    Date d = new Date();
    h.handleNotification(d);
    ModelJobs.yield();
    TimerDemoForm form = Desktop.CURRENT.get().findForm(TimerDemoForm.class);
    assertNotNull(form);
    assertEquals(d, form.getMessageTimeField().getValue());
  }

}
