package org.eclipse.scout.contacts.client.edu;

import java.security.AccessController;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.security.auth.Subject;

import org.eclipse.scout.rt.platform.context.RunContexts;
import org.eclipse.scout.rt.platform.job.DoneEvent;
import org.eclipse.scout.rt.platform.job.IDoneHandler;
import org.eclipse.scout.rt.platform.job.IFuture;
import org.eclipse.scout.rt.platform.job.JobInput;
import org.eclipse.scout.rt.platform.job.Jobs;
import org.eclipse.scout.rt.platform.security.SimplePrincipal;
import org.eclipse.scout.rt.platform.util.concurrent.IRunnable;

/**
 * <h3>{@link EduUtility}</h3>
 */
public final class EduUtility {
  private EduUtility() {
  }

  /**
   * Creates a new {@link Subject} of the given name.
   */
  public static Subject newSubject(final String name) {
    final Subject subject = new Subject();
    subject.getPrincipals().add(new SimplePrincipal(name));
    subject.setReadOnly();
    return subject;
  }

  /**
   * schedule a job and keep it for 20 seconds running. Used for chron job tests.
   *
   * @param runnable
   * @param input
   * @return
   */
  public static IFuture<Void> schedule(IRunnable runnable, JobInput input) {
    CountDownLatch waitLatch = new CountDownLatch(1);

    IFuture<Void> future = Jobs.schedule(runnable, input);
    future.whenDone(new IDoneHandler<Void>() {
      @Override
      public void onDone(DoneEvent<Void> event) {
        waitLatch.countDown();
      }
    }, RunContexts.empty());

    try {
      waitLatch.await(20l, TimeUnit.SECONDS);
      future.cancel(true);
    }
    catch (InterruptedException e) {
      // void
    }
    return future;
  }

  public static class Logger {
    public void log(String label) {
      String jobName = null;
      if (IFuture.CURRENT.get() != null &&
          IFuture.CURRENT.get().getJobInput() != null) {
        jobName = IFuture.CURRENT.get().getJobInput().getName();
      }

      String now = new SimpleDateFormat("HH:mm:ss,SSS").format(new Date());

      System.out.printf("[%s] %s [subject=%s, jobname=%s]",
          now, label, Subject.getSubject(AccessController.getContext()), jobName)
          .println();
    }
  }

}
