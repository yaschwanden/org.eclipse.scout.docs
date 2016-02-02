package org.eclipse.scout.contacts.jobs;

import static org.eclipse.scout.contacts.jobs.helper.Helpers.getCurrentJobType;
import static org.eclipse.scout.contacts.jobs.helper.Helpers.newClientSession;
import static org.eclipse.scout.contacts.jobs.helper.Helpers.newServerSession;

import java.security.AccessController;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import javax.security.auth.Subject;

import org.eclipse.scout.contacts.jobs.helper.Helpers;
import org.eclipse.scout.contacts.jobs.helper.SubjectRule;
import org.eclipse.scout.rt.client.context.ClientRunContexts;
import org.eclipse.scout.rt.client.job.ModelJobs;
import org.eclipse.scout.rt.platform.context.RunMonitor;
import org.eclipse.scout.rt.platform.job.FixedDelayScheduleBuilder;
import org.eclipse.scout.rt.platform.job.IFuture;
import org.eclipse.scout.rt.platform.job.Jobs;
import org.eclipse.scout.rt.platform.util.concurrent.IRunnable;
import org.eclipse.scout.rt.server.context.ServerRunContexts;
import org.eclipse.scout.rt.server.transaction.ITransaction;
import org.eclipse.scout.rt.shared.ISession;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

/**
 * Exercises to migrate from the old to the new Job API shipped with Eclipse Scout Neon.
 */
public class JobMigrationExercises {

  @Rule
  public TestName m_testName = new TestName();
  @Rule
  public SubjectRule m_subjectRule = new SubjectRule("john");

  /**
   * Migrate the following snippet from Eclipse Scout release <= 5.0.x.
   * <p>
   * Exercise: Schedule a 'raw' job.
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

    // TODO Jobs: implement with new Job API of Eclipse Scout 'N' release.
    Jobs.schedule(new IRunnable() {

      @Override
      public void run() throws Exception {
        printCurrentJobInfo();
      }
    }, Jobs.newInput()
        .withName("job-name"));
  }

  /**
   * Migrate the following snippet from Eclipse Scout release <= 5.0.x.
   * <p>
   * Exercise: Schedule a server job on behalf of the current session.
   */
  @Test
  public void migrationExercise_2() {
    ISession.CURRENT.set(newServerSession());

    /*
    new ServerJob("job-name", ServerJob.getCurrentSession()) {
    
      @Override
      protected IStatus runTransaction(IProgressMonitor monitor) throws Exception {
        // do something
        return Status.OK_STATUS;
      }
    }.schedule();
     */

    // TODO Jobs: implement with new Job API of Eclipse Scout 'N' release.
    Jobs.schedule(new IRunnable() {

      @Override
      public void run() throws Exception {
        printCurrentJobInfo();
      }
    }, Jobs.newInput()
        .withName("job-name")
        .withRunContext(ServerRunContexts.copyCurrent()));
  }

  /**
   * Migrate the following snippet from Eclipse Scout release <= 5.0.x.
   * <p>
   * Exercise: Run an action in a new transaction via ServerJob.runNow(...​) on behalf of the current session.
   */
  @Test
  public void migrationExercise_3() {
    ISession.CURRENT.set(newServerSession());
    /*
    new ServerJob("job-name", ServerJob.getCurrentSession()) {
    
      @Override
      protected IStatus runTransaction(IProgressMonitor monitor) throws Exception {
        // do something
        return Status.OK_STATUS;
      }
    }.runNow(new NullProgressMonitor());
    */

    // TODO Jobs: implement with new Job API of Eclipse Scout 'N' release.
    ServerRunContexts.copyCurrent().run(new IRunnable() {

      @Override
      public void run() throws Exception {
        printCurrentJobInfo();
      }
    });
  }

  /**
   * Migrate the following snippet from Eclipse Scout release <= 5.0.x.
   * <p>
   * Exercise: Run a server job on behalf of the current session, but with another Subject.
   */
  @Test
  public void migrationExercise_4() {
    ISession.CURRENT.set(newServerSession());
    /*
    new ServerJob("job-name", ServerJob.getCurrentSession(), subject) {
    
      @Override
      protected IStatus runTransaction(IProgressMonitor monitor) throws Exception {
        // do something
        return Status.OK_STATUS;
      }
    }.schedule();
    */

    // TODO Jobs: implement with new Job API of Eclipse Scout 'N' release.
    Jobs.schedule(new IRunnable() {

      @Override
      public void run() throws Exception {
        printCurrentJobInfo();
      }
    }, Jobs.newInput()
        .withName("job-name")
        .withRunContext(ServerRunContexts.copyCurrent()
            .withSubject(Helpers.newSubject("john"))));
  }

  /**
   * Migrate the following snippet from Eclipse Scout release <= 5.0.x.
   * <p>
   * Exercise: Run a ClientSyncJob on behalf of the current session.
   */
  @Test
  public void migrationExercise_5() {
    ISession.CURRENT.set(newClientSession());

    /*
    new ClientSyncJob("job-name", ClientSessionProvider.currentSession()) {
    
      @Override
      protected void runVoid(IProgressMonitor monitor) throws Throwable {
        // do something
      }
    }.schedule();
     */

    // TODO Jobs: implement with new Job API of Eclipse Scout 'N' release.
    ModelJobs.schedule(new IRunnable() {

      @Override
      public void run() throws Exception {
        printCurrentJobInfo();
      }
    }, ModelJobs.newInput(ClientRunContexts.copyCurrent())
        .withName("job-name"));
  }

  /**
   * Migrate the following snippet from Eclipse Scout release <= 5.0.x.
   * <p>
   * Exercise: Run a ClientAsyncJob on behalf of the current session.
   */
  @Test
  public void migrationExercise_6() {
    ISession.CURRENT.set(newClientSession());

    /*
    new ClientAsyncJob("job-name", ClientSessionProvider.currentSession()) {
    
      @Override
      protected void runVoid(IProgressMonitor monitor) throws Throwable {
        // do something
      }
    }.schedule();
     */

    // TODO Jobs: implement with new Job API of Eclipse Scout 'N' release.
    Jobs.schedule(new IRunnable() {

      @Override
      public void run() throws Exception {
        printCurrentJobInfo();
      }
    }, Jobs.newInput()
        .withName("job-name")
        .withRunContext(ClientRunContexts.copyCurrent()));
  }

  /**
   * Migrate the following snippet from Eclipse Scout release <= 5.0.x.
   * <p>
   * Exercise: Schedule a job with a delay of 5 seconds.
   */
  @Test
  public void migrationExercise_7() {
    /*
    new Job("job-name") {
    
      @Override
      protected IStatus run(IProgressMonitor monitor) {
        // do something
      }
    }.schedule(5_000);
     */

    // TODO Jobs: implement with new Job API of Eclipse Scout 'N' release.
    Jobs.schedule(new IRunnable() {

      @Override
      public void run() throws Exception {
        printCurrentJobInfo();
      }
    }, Jobs.newInput()
        .withName("job-name")
        .withExecutionTrigger(Jobs.newExecutionTrigger()
            .withStartIn(5, TimeUnit.SECONDS)));
  }

  /**
   * Migrate the following snippet from Eclipse Scout release <= 5.0.x.
   * <p>
   * Exercise: Schedule a repeatedly execution with a fixed delay.
   */
  @Test
  public void migrationExercise_8() {
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

    // TODO Jobs: implement with new Job API of Eclipse Scout 'N' release.
    Jobs.schedule(new IRunnable() {

      @Override
      public void run() throws Exception {
        printCurrentJobInfo();
      }
    }, Jobs.newInput()
        .withName("job-name")
        .withExecutionTrigger(Jobs.newExecutionTrigger()
            .withStartIn(5, TimeUnit.SECONDS)
            .withSchedule(FixedDelayScheduleBuilder.repeatForTotalCount(3, 5, TimeUnit.SECONDS))));
  }

  /**
   * Migrate the following snippet from Eclipse Scout release <= 5.0.x.
   * <p>
   * Exercise: Check for cancellation.
   */
  @Test
  public void migrationExercise_9() {
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

    // TODO Jobs: implement with new Job API of Eclipse Scout 'N' release.;

    Jobs.schedule(new IRunnable() {

      @Override
      public void run() throws Exception {
        // do first chunk of work
        if (RunMonitor.CURRENT.get().isCancelled()) {
          return;
        }
        // do second chunk of work
        if (RunMonitor.CURRENT.get().isCancelled()) {
          return;
        }
        // do third chunk of work
      }
    }, Jobs.newInput()
        .withName("job-name"));
  }

  /**
   * Migrate the following snippet from Eclipse Scout release <= 5.0.x.
   * <p>
   * Exercise: Wait on a job to complete.
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
    job.join();
     */

    // TODO Jobs: implement with new Job API of Eclipse Scout 'N' release.
    IFuture<Void> future = Jobs.schedule(new IRunnable() {

      @Override
      public void run() throws Exception {
        printCurrentJobInfo();
      }
    }, Jobs.newInput()
        .withName("job-name"));

    future.awaitDone();
  }

  /**
   * Migrate the following snippet from Eclipse Scout release <= 5.0.x.
   * <p>
   * Exercise: Join job with a maximal wait time.
   */
  @Test
  public void migrationExercise_11() {
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

    // TODO Jobs: implement with new Job API of Eclipse Scout 'N' release.
    IFuture<Void> future = Jobs.schedule(new IRunnable() {

      @Override
      public void run() throws Exception {
        printCurrentJobInfo();
      }
    }, Jobs.newInput()
        .withName("job-name"));

    future.awaitDone(5, TimeUnit.SECONDS);
  }

  /**
   * Migrate the following snippet from Eclipse Scout release <= 5.0.x.
   * <p>
   * Exercise: Wait on a job to complete, and get its computation result.
   */
  @Test
  public void migrationExercise_12() {
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

    // TODO Jobs: implement with new Job API of Eclipse Scout 'N' release.
    IFuture<String> future = Jobs.schedule(new Callable<String>() {

      @Override
      public String call() throws Exception {
        printCurrentJobInfo();
        return "abc";
      }
    }, Jobs.newInput()
        .withName("job-name"));

    String result = future.awaitDoneAndGet();
    System.out.println(result);
  }

  @BeforeClass
  public static void beforeClass() {
    System.setProperty("jandex.rebuild", Boolean.toString(true));
  }

  @After
  public void after() {
    Jobs.getJobManager().awaitDone(null, 10, TimeUnit.SECONDS);
    ISession.CURRENT.remove();
  }

  private void printCurrentJobInfo() {
    System.out.printf("test: %s", m_testName.getMethodName()).println();
    System.out.printf("  jobType: %s", getCurrentJobType()).println();
    System.out.printf("  future: %s", IFuture.CURRENT.get()).println();
    System.out.printf("  transaction: %s", ITransaction.CURRENT.get()).println();
    System.out.printf("  session: %s", ISession.CURRENT.get()).println();
    System.out.printf("  subject: %s", Subject.getSubject(AccessController.getContext())).println();
    System.out.println();
  }
}
