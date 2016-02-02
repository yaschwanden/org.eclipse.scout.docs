package org.eclipse.scout.contacts.jobs;

import static org.eclipse.scout.contacts.jobs.helper.Helpers.getCurrentJobType;

import java.security.AccessController;
import java.util.concurrent.TimeUnit;

import javax.security.auth.Subject;

import org.eclipse.scout.contacts.jobs.helper.Helpers;
import org.eclipse.scout.contacts.jobs.helper.SubjectRule;
import org.eclipse.scout.rt.client.context.ClientRunContexts;
import org.eclipse.scout.rt.client.job.ModelJobs;
import org.eclipse.scout.rt.platform.job.DoneEvent;
import org.eclipse.scout.rt.platform.job.FixedDelayScheduleBuilder;
import org.eclipse.scout.rt.platform.job.IBlockingCondition;
import org.eclipse.scout.rt.platform.job.IDoneHandler;
import org.eclipse.scout.rt.platform.job.IExecutionSemaphore;
import org.eclipse.scout.rt.platform.job.IFuture;
import org.eclipse.scout.rt.platform.job.Jobs;
import org.eclipse.scout.rt.platform.util.concurrent.IRunnable;
import org.eclipse.scout.rt.server.transaction.ITransaction;
import org.eclipse.scout.rt.shared.ISession;
import org.eclipse.scout.rt.shared.job.filter.future.SessionFutureFilter;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.quartz.CronScheduleBuilder;
import org.quartz.SimpleScheduleBuilder;

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
    Jobs.schedule(new IRunnable() {

      @Override
      public void run() throws Exception {
        printCurrentJobInfo();
      }
    }, Jobs.newInput()
        .withExecutionTrigger(Jobs.newExecutionTrigger()
            .withStartIn(1, TimeUnit.MINUTES)
            .withSchedule(SimpleScheduleBuilder.repeatMinutelyForTotalCount(59))))
        .awaitDone();

  }

  /**
   * Schedule a repetitive job at a fixed delay of 10 seconds.
   */
  @Test
  public void exercise_2() {
    // TODO Jobs: Schedule a repetitive job at a fixed delay.
    Jobs.schedule(new IRunnable() {

      @Override
      public void run() throws Exception {
        printCurrentJobInfo();
      }
    }, Jobs.newInput()
        .withExecutionTrigger(Jobs.newExecutionTrigger()
            .withSchedule(FixedDelayScheduleBuilder.repeatForever(10, TimeUnit.SECONDS))));
  }

  /**
   * Schedule a repetitive job which runs 60 times, but waits 1 minute between the termination of the previous and the
   * commencement of the next execution.
   */
  @Test
  public void exercise_3() {
    // TODO Jobs: Schedule a repetitive job which runs 60 times, but waits 1 minute between the termination of the previous and the commencement of the next execution
    Jobs.schedule(new IRunnable() {

      @Override
      public void run() throws Exception {
        printCurrentJobInfo();
      }
    }, Jobs.newInput()
        .withExecutionTrigger(Jobs.newExecutionTrigger()
            .withSchedule(FixedDelayScheduleBuilder.repeatForTotalCount(60, 1, TimeUnit.MINUTES))))
        .awaitDone();

  }

  /**
   * Schedule a job which runs at 10:15am every Monday, Tuesday, Wednesday, Thursday and Friday.
   * <p>
   * Hint: Use a {@link CronScheduleBuilder}.
   */
  @Test
  public void exercise_4() {
    // TODO Jobs: Schedule a job which runs at 10:15am every Monday, Tuesday, Wednesday, Thursday and Friday.
    Jobs.schedule(new IRunnable() {

      @Override
      public void run() throws Exception {
        printCurrentJobInfo();
      }
    }, Jobs.newInput()
        .withExecutionTrigger(Jobs.newExecutionTrigger()
            .withSchedule(CronScheduleBuilder.cronSchedule("0 15 10 ? * MON-FRI"))));
  }

  /**
   * Limit the maximal concurrency level among jobs.
   */
  @Test
  public void exercise_5() {
    // TODO Jobs: Change this snippet so that maximal 5 jobs are running at the same time.
    IExecutionSemaphore semaphore = Jobs.newExecutionSemaphore(5);

    for (int i = 0; i < 100; i++) {
      Jobs.schedule(new IRunnable() {

        @Override
        public void run() throws Exception {
          printCurrentJobInfo();
          Thread.sleep(TimeUnit.SECONDS.toMillis(5));
        }
      }, Jobs.newInput()
          .withName("job-{}", i)
          .withExecutionSemaphore(semaphore));
    }
  }

  /**
   * Cancel all jobs of the current session.
   */
  @Test
  public void exercise_6() {
    // TODO Jobs: Cancel all jobs of the current session
    Jobs.getJobManager().cancel(Jobs.newFutureFilterBuilder()
        .andMatch(new SessionFutureFilter(ISession.CURRENT.get()))
        .toFilter(), true);
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

        final IBlockingCondition operationCompleted = Jobs.newBlockingCondition(true);

        ModelJobs.schedule(new IRunnable() {

          @Override
          public void run() throws Exception {
            printCurrentJobInfo();
          }
        }, ModelJobs
            .newInput(ClientRunContexts.copyCurrent()))
            .whenDone(new IDoneHandler<Void>() {

          @Override
          public void onDone(DoneEvent<Void> event) {
            operationCompleted.setBlocking(false);
          }
        }, null);

        // TODO Jobs: Wait by releasing the current model job permit.
        // Hint: use a blocking condition.

        operationCompleted.waitFor();
      }
    }, ModelJobs
        .newInput(ClientRunContexts.copyCurrent()))
        .awaitDoneAndGet();
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
