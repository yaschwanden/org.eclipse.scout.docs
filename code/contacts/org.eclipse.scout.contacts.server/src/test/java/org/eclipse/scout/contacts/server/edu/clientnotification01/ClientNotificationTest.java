package org.eclipse.scout.contacts.server.edu.clientnotification01;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.context.RunContexts;
import org.eclipse.scout.rt.platform.job.IExecutionSemaphore;
import org.eclipse.scout.rt.platform.job.IFuture;
import org.eclipse.scout.rt.platform.job.Jobs;
import org.eclipse.scout.rt.server.clientnotification.ClientNotificationRegistry;
import org.eclipse.scout.rt.shared.clientnotification.ClientNotificationMessage;
import org.eclipse.scout.rt.shared.clientnotification.IClientNotificationService;
import org.junit.Assert;
import org.junit.Test;

/**
 * <h3>{@link ClientNotificationTest}</h3>
 */
public class ClientNotificationTest {

  private TestNotification m_notification01 = new TestNotification(1);
  private TestNotification m_notification02 = new TestNotification(2);
  private TestNotification m_notification03 = new TestNotification(3);

  /**
   * TODO 4.01 Client notification : fix assertions
   */
  @Test
  public void test() {
    BEANS.get(IClientNotificationService.class).registerSession("testNodeId", "testSessionId", "testUserId");
    IExecutionSemaphore exSemaphore = Jobs.newExecutionSemaphore(1);

    IFuture<Void> putNotificationFeature = Jobs.schedule(() -> {
      ClientNotificationRegistry clientNotificationRegistry = BEANS.get(ClientNotificationRegistry.class);
      clientNotificationRegistry.putForAllSessions(m_notification01);
      clientNotificationRegistry.putForSession("anOtherSession", m_notification02);
      clientNotificationRegistry.putForSession("testSessionId", m_notification03);
    }, Jobs.newInput().withRunContext(RunContexts.empty()).withExecutionSemaphore(exSemaphore));

    IFuture<List<TestNotification>> consumeFeature = Jobs.schedule(() -> {
      List<ClientNotificationMessage> notifications = BEANS.get(IClientNotificationService.class).getNotifications("testNodeId");
      return notifications.stream().map(cn -> (TestNotification) cn.getNotification()).collect(Collectors.toList());
    }, Jobs.newInput().withRunContext(RunContexts.empty()).withExecutionSemaphore(exSemaphore));

    List<TestNotification> receivedNotifications = consumeFeature.awaitDoneAndGet(2, TimeUnit.SECONDS);

    // assertions
    assertThat(receivedNotifications, hasSize(2));
    Assert.assertEquals(receivedNotifications, Arrays.asList());

    // appendix
    // wait for all jobs to complete
    Jobs.getJobManager().awaitDone(Jobs.newFutureFilterBuilder()
        .andMatchFuture(putNotificationFeature, consumeFeature)
        .toFilter(), 10, TimeUnit.SECONDS);
  }

  public static final class TestNotification implements Serializable {

    private static final long serialVersionUID = 1L;
    private int m_id;

    public TestNotification(int id) {
      m_id = id;

    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + m_id;
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      TestNotification other = (TestNotification) obj;
      if (m_id != other.m_id) {
        return false;
      }
      return true;
    }

  }

}
