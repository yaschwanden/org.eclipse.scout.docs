package org.eclipse.scout.contacts.jobs;

import static org.eclipse.scout.contacts.jobs.helper.Helpers.getCurrentJobType;

import java.security.AccessController;
import java.util.concurrent.TimeUnit;

import javax.security.auth.Subject;

import org.eclipse.scout.contacts.jobs.helper.Helpers;
import org.eclipse.scout.contacts.jobs.helper.SubjectRule;
import org.eclipse.scout.rt.client.context.ClientRunContexts;
import org.eclipse.scout.rt.client.job.ModelJobs;
import org.eclipse.scout.rt.platform.job.IFuture;
import org.eclipse.scout.rt.platform.job.Jobs;
import org.eclipse.scout.rt.platform.util.concurrent.IRunnable;
import org.eclipse.scout.rt.server.transaction.ITransaction;
import org.eclipse.scout.rt.shared.ISession;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.quartz.CronScheduleBuilder;

/**
 * Exercises for the new Job API shipped with Eclipse Scout Neon.
 */
public class AdvancedJobExercises {

  @Rule
  public TestName m_testName = new TestName();
  @Rule
  public SubjectRule m_subjectRule = new SubjectRule("john");

  /**
   * Schedule a repetitive job which runs 60 times at every minute.
   */
  @Test
  public void exercise_1() {
    // TODO Jobs: Schedule a repetitive job which runs 60 times at every minute
  }

  /**
   * Schedule a repetitive job at a fixed delay of 10 seconds.
   */
  @Test
  public void exercise_2() {
    // TODO Jobs: Schedule a repetitive job at a fixed delay.
  }

  /**
   * Schedule a repetitive job which runs 60 times, but waits 1 minute between the termination of the previous and the
   * commencement of the next execution.
   */
  @Test
  public void exercise_3() {
    // TODO Jobs: Schedule a repetitive job which runs 60 times, but waits 1 minute between the termination of the previous and the commencement of the next execution
  }

  /**
   * Schedule a job which runs at 10:15am every Monday, Tuesday, Wednesday, Thursday and Friday.
   * <p>
   * Hint: Use a {@link CronScheduleBuilder}.
   */
  @Test
  public void exercise_4() {
    // TODO Jobs: Schedule a job which runs at 10:15am every Monday, Tuesday, Wednesday, Thursday and Friday.
  }

  /**
   * Limit the maximal concurrency level among jobs.
   */
  @Test
  public void exercise_5() {
    // TODO Jobs: Change this snippet so that maximal 5 jobs are running at the same time.

    for (int i = 0; i < 100; i++) {
      Jobs.schedule(new IRunnable() {

        @Override
        public void run() throws Exception {
          printCurrentJobInfo();
          Thread.sleep(TimeUnit.SECONDS.toMicros(5));
        }
      }, Jobs.newInput()
          .withName("job-{}", i));
    }
  }

  /**
   * Cancel all jobs of the current session.
   */
  @Test
  public void exercise_6() {
    // TODO Jobs: Cancel all jobs of the current session
  }

  /**
   * Release current semaphore permit while executing.
   */
  @Test
  public void exercise_7() {
    ISession.CURRENT.set(Helpers.newClientSession());

    ModelJobs.schedule(new IRunnable() {

      @Override
      public void run() throws Exception {
        printCurrentJobInfo();

        final IFuture<Void> future = ModelJobs.schedule(new IRunnable() {

          @Override
          public void run() throws Exception {
            printCurrentJobInfo();
          }
        }, ModelJobs.newInput(ClientRunContexts.copyCurrent()));

        // TODO Jobs: Wait by releasing the current model job permit.
        // Hint: use a blocking condition.

        future.awaitDone();
      }
    }, ModelJobs.newInput(ClientRunContexts.copyCurrent())).awaitDoneAndGet();
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
