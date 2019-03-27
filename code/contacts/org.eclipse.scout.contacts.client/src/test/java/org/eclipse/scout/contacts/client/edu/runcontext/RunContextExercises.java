package org.eclipse.scout.contacts.client.edu.runcontext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.AccessController;
import java.util.Locale;

import javax.security.auth.Subject;

import org.eclipse.scout.contacts.client.edu.EduUtility;
import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.context.PropertyMap;
import org.eclipse.scout.rt.platform.context.RunContext;
import org.eclipse.scout.rt.platform.context.RunContexts;
import org.eclipse.scout.rt.platform.context.RunMonitor;
import org.eclipse.scout.rt.platform.nls.NlsLocale;
import org.eclipse.scout.rt.platform.transaction.ITransaction;
import org.eclipse.scout.rt.platform.transaction.ITransactionMember;
import org.eclipse.scout.rt.platform.transaction.TransactionScope;
import org.eclipse.scout.rt.platform.util.concurrent.IRunnable;
import org.eclipse.scout.rt.testing.platform.runner.PlatformTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Exercises for {@link RunContext}.
 */
@RunWith(PlatformTestRunner.class)
public class RunContextExercises {

  private static final Subject JOHN = EduUtility.newSubject("john");

  /**
   * TODO 2.01 RunContext: Run the {@link Runnable} as 'JOHN' and with US Locale.
   */
  @Test
  public void exercise_1() {
    IRunnable runnable = () -> {
      assertEquals(JOHN, Subject.getSubject(AccessController.getContext()));
      assertEquals(Locale.US, NlsLocale.CURRENT.get());
    };

    RunContexts.empty()
        .withSubject(JOHN)
        .withLocale(Locale.US)
        .run(runnable);

  }

  /**
   * TODO 2.02 RunContext: Run the {@link Runnable} with the property 'message' set to 'Hello World'.
   */
  @Test
  public void exercise_2() {
    String PROPERTY_NAME = "message";
    IRunnable runnable = () -> assertEquals("Hello World", PropertyMap.CURRENT.get().get(PROPERTY_NAME));

    RunContexts.empty()
        .withProperty(PROPERTY_NAME, "Hello World")
        .run(runnable);
  }

  /**
   * TODO 2.03 RunContext: Run the {@link Runnable} with the {@link ThreadLocal} set to 'Hello World'.
   */
  @Test
  public void exercise_3() {
    ThreadLocal<String> threadLocal = new ThreadLocal<String>();
    IRunnable runnable = () -> assertEquals("Hello World", threadLocal.get());

    RunContexts.empty()
        .withThreadLocal(threadLocal, "Hello World")
        .run(runnable);
  }

  /**
   * TODO 2.04 RunContext: Run the {@link Runnable} in a new transaction.<br>
   * Hint: use {@link RunContext#withTransactionScope(TransactionScope)}
   */
  @Test
  public void exercise_4() {
    ITransaction outherTransaction = ITransaction.CURRENT.get();
    assertNotNull(outherTransaction);
    RunContexts.copyCurrent()
        .withTransactionScope(TransactionScope.REQUIRES_NEW)
        // your code here
        .run(() -> {
          ITransaction innerTransaction = ITransaction.CURRENT.get();
          assertNotNull(innerTransaction);
          assertNotSame(outherTransaction, innerTransaction);
        });
  }

  /**
   * TODO 2.05 RunContext: Run the {@link Runnable} with the TX-Member installed.
   */
  @Test
  public void exercise_5() {
    ITransactionMember txMember = mock(ITransactionMember.class);
    when(txMember.getMemberId()).thenReturn("tx-memeber");
    when(txMember.needsCommit()).thenReturn(true);
    when(txMember.commitPhase1()).thenReturn(true);

    RunContexts.empty()
        .withTransactionMember(txMember)
        // add code here
        .run(() -> {
        });

    verify(txMember).commitPhase2();
  }

  /**
   * TODO 2.06 RunContext: Run the {@link Runnable} with a attached {@link RunMonitor} so that execution is cancelled.
   */
  @Test
  public void exercise_6() {
    RunContexts.empty().run(() -> {
      RunMonitor runMonitor = RunMonitor.CURRENT.get();
      runMonitor.cancel(false);

      RunContexts.empty()
          // write code here
          .withRunMonitor(runMonitor)
          .run(() -> assertTrue(RunMonitor.CURRENT.get().isCancelled()));
    });
  }

  /**
   * TODO 2.07 RunContext: Run the {@link Runnable} with a detached {@link RunMonitor} so that execution is not
   * cancelled.<br>
   * Hint: {@link RunMonitor} is a non {@link ApplicationScoped} bean.
   */
  @Test
  public void exercise_7() {
    RunContexts.empty().run(() -> {
      RunMonitor.CURRENT.get().cancel(false);

      RunContexts.copyCurrent()
          .withRunMonitor(BEANS.get(RunMonitor.class))
          // your code here
          .run(() -> assertFalse(RunMonitor.CURRENT.get().isCancelled()));
    });
  }
}
