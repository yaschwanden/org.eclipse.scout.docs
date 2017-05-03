package org.eclipse.scout.contacts.client.edu.runcontext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.AccessController;
import java.util.Locale;

import javax.security.auth.Subject;

import org.eclipse.scout.contacts.client.edu.EduUtility;
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
import org.junit.Test;

/**
 * Exercises for {@link RunContext}.
 */
public class RunContextExercises {

  private static final Subject JOHN = EduUtility.newSubject("john");

  /**
   * TODO RunContext: Run the {@link Runnable} as 'JOHN' and with US Locale.
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
   * TODO RunContext: Run the {@link Runnable} with the property 'message' set to 'Hello World'.
   */
  @Test
  public void exercise_2() {
    String prop = "message";
    IRunnable runnable = () -> assertEquals("Hello World", PropertyMap.CURRENT.get().get(prop));

    RunContexts.empty()
        .withProperty(prop, "Hello World")
        .run(runnable);
  }

  /**
   * TODO RunContext: Run the {@link Runnable} with the {@link ThreadLocal} set to 'Hello World'.
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
   * TODO RunContext: Run the {@link Runnable} in a new transaction.
   */
  @Test
  public void exercise_4() {
    assertNull(ITransaction.CURRENT.get());
    RunContexts.empty().run(() -> {
      ITransaction currentTx = ITransaction.CURRENT.get();
      assertNotNull(ITransaction.CURRENT.get());
      IRunnable runnable = () -> assertNotSame(currentTx, ITransaction.CURRENT.get());

      RunContexts.empty()
          .withTransactionScope(TransactionScope.REQUIRES_NEW)
          .run(runnable);
    });
  }

  /**
   * TODO RunContext: Run the {@link Runnable} with the TX-Member installed.
   */
  @Test
  public void exercise_5() {
    ITransactionMember txMember = mock(ITransactionMember.class);
    when(txMember.getMemberId()).thenReturn("tx-memeber");
    when(txMember.needsCommit()).thenReturn(true);
    when(txMember.commitPhase1()).thenReturn(true);

    RunContexts.empty()
        .withTransactionMember(txMember)
        .run(() -> {
        });

    verify(txMember).commitPhase2();
  }

  /**
   * TODO RunContext: Run the {@link Runnable} with a attached {@link RunMonitor} so that execution is cancelled.
   */
  @Test
  public void exercise_6() {
    RunContexts.empty().run(() -> {
      RunMonitor.CURRENT.get().cancel(false);

      IRunnable runnable = () -> assertTrue(RunMonitor.CURRENT.get().isCancelled());
      RunContexts.copyCurrent()
          .run(runnable);
    });
  }

  /**
   * TODO RunContext: Run the {@link Runnable} with a detached {@link RunMonitor} so that execution is not cancelled.
   */
  @Test
  public void exercise_7() {
    RunContexts.empty().run(() -> {
      RunMonitor.CURRENT.get().cancel(false);

      IRunnable runnable = () -> assertFalse(RunMonitor.CURRENT.get().isCancelled());
      RunContexts.copyCurrent()
          .withRunMonitor(BEANS.get(RunMonitor.class))
          .run(runnable);
    });
  }
}
