package org.eclipse.scout.contacts.edu.jobs;

import java.security.AccessController;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import javax.security.auth.Subject;

import org.eclipse.scout.contacts.edu.util.Helpers;
import org.eclipse.scout.contacts.edu.util.SubjectRule;
import org.eclipse.scout.rt.platform.context.RunContext;
import org.eclipse.scout.rt.platform.job.IFuture;
import org.eclipse.scout.rt.platform.job.Jobs;
import org.eclipse.scout.rt.platform.nls.NlsLocale;
import org.eclipse.scout.rt.platform.transaction.ITransaction;
import org.eclipse.scout.rt.platform.util.concurrent.IRunnable;
import org.eclipse.scout.rt.shared.ISession;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

/**
 * Exercises to migrate from the old to the new Job API.
 */
public class JobMigrationExercises {

  /**
   * Migrate the following snippet from Eclipse Scout release <= 5.0.x.
   * <p>
   * TODO Job-Migration: Schedule a 'raw' job.
   */
  @Test
  public void migrationExercise_1() {
    /*
    new Job("job-name") {
    
      @Override
      protected IStatus run(IProgressMonitor monitor) {
        // do something
      }
    }.schedule();
    */

    IRunnable runnable = () -> printCurrentJobInfo();
  }

  /**
   * Migrate the following snippet from Eclipse Scout release <= 5.0.x.
   * <p>
   * TODO Job-Migration: Schedule a server job on behalf of the current session.
   */
  @Test
  public void migrationExercise_2() {
    Helpers.simulateServerSession();

    /*
    new ServerJob("job-name", ServerJob.getCurrentSession()) {
    
      @Override
      protected IStatus runTransaction(IProgressMonitor monitor) throws Exception {
        // do something
        return Status.OK_STATUS;
      }
    }.schedule();
     */

    IRunnable runnable = () -> printCurrentJobInfo();
  }

  /**
   * Migrate the following snippet from Eclipse Scout release <= 5.0.x.
   * <p>
   * TODO Job-Migration: Run an action in a new transaction via ServerJob.runNow(...​) on behalf of the current session.
   */
  @Test
  public void migrationExercise_3() {
    Helpers.simulateServerSession();
    /*
    new ServerJob("job-name", ServerJob.getCurrentSession()) {
    
      @Override
      protected IStatus runTransaction(IProgressMonitor monitor) throws Exception {
        // do something
        return Status.OK_STATUS;
      }
    }.runNow(new NullProgressMonitor());
    */

    IRunnable runnable = () -> printCurrentJobInfo();
  }

  /**
   * Migrate the following snippet from Eclipse Scout release <= 5.0.x.
   * <p>
   * TODO Job-Migration: Run a ClientSyncJob on behalf of the current session.
   */
  @Test
  public void migrationExercise_4() {
    Helpers.simulateClientSession();

    /*
    new ClientSyncJob("job-name", ClientSessionProvider.currentSession()) {
    
      @Override
      protected void runVoid(IProgressMonitor monitor) throws Throwable {
        // do something
      }
    }.schedule();
     */

    IRunnable runnable = () -> printCurrentJobInfo();
  }

  /**
   * Migrate the following snippet from Eclipse Scout release <= 5.0.x.
   * <p>
   * TODO Job-Migration: Run a ClientAsyncJob on behalf of the current session.
   */
  @Test
  public void migrationExercise_5() {
    Helpers.simulateClientSession();

    /*
    new ClientAsyncJob("job-name", ClientSessionProvider.currentSession()) {
    
      @Override
      protected void runVoid(IProgressMonitor monitor) throws Throwable {
        // do something
      }
    }.schedule();
     */

    IRunnable runnable = () -> printCurrentJobInfo();
  }

  /**
   * Migrate the following snippet from Eclipse Scout release <= 5.0.x.
   * <p>
   * TODO Job-Migration: Schedule a job with a delay of 5 seconds.
   */
  @Test
  public void migrationExercise_6() {
    /*
    new Job("job-name") {
    
      @Override
      protected IStatus run(IProgressMonitor monitor) {
        // do something
      }
    }.schedule(5_000);
     */

    IRunnable runnable = () -> printCurrentJobInfo();
  }

  /**
   * Migrate the following snippet from Eclipse Scout release <= 5.0.x.
   * <p>
   * TODO Job-Migration: Schedule a repeatedly execution with a fixed delay.
   */
  @Test
  public void migrationExercise_7() {
    /*
    new Job("job-name") {
    
      private int m_count;
    
      @Override
      protected IStatus run(IProgressMonitor monitor) {
        m_count++;
        if (m_count == 3) {
          return Status.CANCEL_STATUS;
        }
    
        // do something
        schedule(5_000);
      }
    }.schedule(5_000);
     */

    IRunnable runnable = () -> printCurrentJobInfo();
  }

  /**
   * Migrate the following snippet from Eclipse Scout release <= 5.0.x.
   * <p>
   * TODO Job-Migration: Check for cancellation.
   */
  @Test
  public void migrationExercise_8() {
    /*
    new Job("job-name") {
    
      @Override
      protected IStatus run(IProgressMonitor monitor) {
        // do first chunk of work
        if (monitor.isCanceled()) {
            return Status.CANCEL_STATUS;
        }
        // do second chunk of work
        if (monitor.isCanceled()) {
          return Status.CANCEL_STATUS;
        }
        // do third chunk of work
        return Status.OK_STATUS;
      }
    }.schedule();
     */
  }

  /**
   * Migrate the following snippet from Eclipse Scout release <= 5.0.x.
   * <p>
   * TODO Job-Migration: Wait on a job to complete.
   */
  @Test
  public void migrationExercise_9() {
    /*
    Job job = new Job("job-name") {
    
      @Override
      protected IStatus run(IProgressMonitor monitor) {
        // do something
        return Status.OK_STATUS;
      }
    };
    job.schedule();
    job.join();
     */

    IRunnable runnable = () -> printCurrentJobInfo();
  }

  /**
   * Migrate the following snippet from Eclipse Scout release <= 5.0.x.
   * <p>
   * TODO Job-Migration: Join job with a maximal wait time.
   */
  @Test
  public void migrationExercise_10() {
    /*
    Job job = new Job("job-name") {
    
      @Override
      protected IStatus run(IProgressMonitor monitor) {
        // do something
        return Status.OK_STATUS;
      }
    };
    job.schedule();
    job.join(5_000, new NullProgressMonitor());
     */

    IRunnable runnable = () -> printCurrentJobInfo();
  }

  /**
   * Migrate the following snippet from Eclipse Scout release <= 5.0.x.
   * <p>
   * TODO Job-Migration: Wait on a job to complete, and get its computation result.
   */
  @Test
  public void migrationExercise_11() {
    /*
    final AtomicReference<String> result = new AtomicReference<>();
    
    Job job = new Job("job-name") {
    
      @Override
      protected IStatus run(IProgressMonitor monitor) {
        // do something
        result.set("abc");
        return Status.OK_STATUS;
      }
    };
    job.schedule();
    job.join();
    System.out.println(result);
     */

    Callable<String> callable = () -> {
      printCurrentJobInfo();
      return "abc";
    };
  }

  @BeforeClass
  public static void beforeClass() {
    System.setProperty("jandex.rebuild", Boolean.toString(true));
  }

  @After
  public void after() {
    Jobs.getJobManager().awaitDone(null, 10, TimeUnit.SECONDS);
    ISession.CURRENT.remove();
    RunContext.CURRENT.remove();
    NlsLocale.CURRENT.remove();
  }

  private void printCurrentJobInfo() {
    System.out.printf("test: %s", m_testName.getMethodName()).println();
    if (Helpers.getCurrentJobType() != null) {
      System.out.printf("  job type: %s", Helpers.getCurrentJobType()).println();
    }
    System.out.printf("  future: %s", IFuture.CURRENT.get()).println();
    System.out.printf("  transaction: %s", ITransaction.CURRENT.get()).println();
    System.out.printf("  session: %s", ISession.CURRENT.get()).println();
    System.out.printf("  subject: %s", Subject.getSubject(AccessController.getContext())).println();
    System.out.println();
  }

  @Rule
  public TestName m_testName = new TestName();
  @Rule
  public SubjectRule m_subjectRule = new SubjectRule("john");
}
