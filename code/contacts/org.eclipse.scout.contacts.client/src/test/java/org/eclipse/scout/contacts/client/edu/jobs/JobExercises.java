package org.eclipse.scout.contacts.client.edu.jobs;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

import javax.security.auth.Subject;

import org.eclipse.scout.contacts.client.edu.EduUtility;
import org.eclipse.scout.contacts.client.edu.EduUtility.Logger;
import org.eclipse.scout.rt.platform.context.RunContext;
import org.eclipse.scout.rt.platform.context.RunContexts;
import org.eclipse.scout.rt.platform.holders.Holder;
import org.eclipse.scout.rt.platform.holders.IHolder;
import org.eclipse.scout.rt.platform.job.FixedDelayScheduleBuilder;
import org.eclipse.scout.rt.platform.job.IExecutionSemaphore;
import org.eclipse.scout.rt.platform.job.IFuture;
import org.eclipse.scout.rt.platform.job.JobInput;
import org.eclipse.scout.rt.platform.job.JobState;
import org.eclipse.scout.rt.platform.job.Jobs;
import org.eclipse.scout.rt.platform.job.internal.JobManager;
import org.eclipse.scout.rt.platform.nls.NlsLocale;
import org.eclipse.scout.rt.platform.util.SleepUtil;
import org.eclipse.scout.rt.platform.util.concurrent.IRunnable;
import org.eclipse.scout.rt.testing.platform.runner.PlatformTestRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.SimpleScheduleBuilder;

/**
 * Exercises for the new Job API in Eclipse Scout 6.1.x.
 */
@RunWith(PlatformTestRunner.class)
public class JobExercises {

  private static final Subject JOHN = EduUtility.newSubject("john");

  /**
   * This example may help to solve the following exercises.
   */
  public void example() {
    // 1. (optional) Determine in which semantic context to run the job
    RunContext runContext = RunContexts.empty()
        .withSubject(JOHN)
        .withCorrelationId("abc")
        .withLocale(Locale.GERMAN);

    // 2. (optional) Determine the maximum concurrency level among jobs
    IExecutionSemaphore semaphore = Jobs.newExecutionSemaphore(2);

    // 3. Schedule the job
    Jobs.schedule(() -> {
      // doing something in another thread
    }, Jobs.newInput()
        .withRunContext(runContext) // (optional) instruments to run on behalf of this context (subject, session, thread-locals, ...)
        .withExecutionHint("computation") // (optional) assigns the job to a job family, and which can be used to cancel all jobs belonging to the same family
        .withExecutionTrigger(Jobs.newExecutionTrigger() // (optional) specifies when and how often to run the job
            .withStartIn(1, TimeUnit.MINUTES) // (optional) e.g. to execute the job in a minute
            .withSchedule(SimpleScheduleBuilder.repeatMinutelyForever())) // (optional) ... and to repeat execution every subsequent minute
        .withExecutionSemaphore(semaphore)); // (optional) controls the maximum concurrency level among job with that semaphore

  }

  /**
   * TODO 3.01 Jobs: Schedule a job that runs once
   *
   * @throws Exception
   */
  @Test
  public void exercise_1() throws Exception {
    Logger logger = Mockito.spy(new Logger());

    IRunnable r = () -> logger.log("exercise_1");
    // your code here
    Jobs.schedule(r, Jobs.newInput());

    // assertions
    waitForAllJobs();
    Mockito.verify(logger, Mockito.times(1)).log(Mockito.anyString());
  }

  /**
   * TODO 3.02 Jobs: Schedule a job that runs once, and wait for the job to complete
   */
  @Test
  public void exercise_2() {
    Logger logger = Mockito.spy(new Logger());
    final IHolder<IFuture<?>> futureHolder = new Holder<IFuture<?>>();
    IRunnable runnable = () -> {
      futureHolder.setValue(IFuture.CURRENT.get());
      SleepUtil.sleepSafe(1, TimeUnit.SECONDS);
      logger.log("exercise_2");
    };

    // code here
    Jobs.schedule(runnable, Jobs.newInput()).awaitDone();

    // assertions
    assertNotNull(futureHolder.getValue());
    assertEquals(futureHolder.getValue().getState(), JobState.DONE);
  }

  /**
   * TODO 3.03 Jobs: Schedule a job that runs once, and which returns a result to the submitter. Then, print the result
   * to the console.
   */
  @Test
  public void exercise_3() {
    Logger logger = Mockito.spy(new Logger());
    Callable<String> callable = () -> {
      logger.log("exercise_3");
      return "doing something";
    };

    // code here
    String result = Jobs.schedule(callable, Jobs.newInput())
        .awaitDoneAndGet();

    // assertions
    Mockito.verify(logger, Mockito.times(1)).log(Mockito.anyString());
    Assert.assertEquals("doing something", result);
  }

  /**
   * TODO 3.04 Jobs: Schedule 2 jobs and wait for their completion.
   * <p>
   * Hints:<br>
   * - Mark the jobs with an execution hint ({@link JobInput#withExecutionHint(String)}).<br>
   * - Wait for all jobs with that execution hint are done {@link Jobs#getJobManager()},
   * {@link JobManager#awaitDone(java.util.function.Predicate, long, TimeUnit)}, {@link Jobs#newFutureFilterBuilder()}
   */
  @Test
  public void exercise_4() {
    Logger logger = Mockito.spy(new Logger());
    IRunnable runnable1 = () -> logger.log("exercise_4: job-1");
    IRunnable runnable2 = () -> logger.log("exercise_4: job-2");

    // code here
    Jobs.schedule(runnable1,
        Jobs.newInput()
            .withExecutionHint("my-family"));
    Jobs.schedule(runnable2,
        Jobs.newInput()
            .withExecutionHint("my-family"));

    // wait for completion
    Jobs.getJobManager().awaitDone(
        Jobs.newFutureFilterBuilder()
            .andMatchExecutionHint("my-family")
            .toFilter(),
        10, TimeUnit.SECONDS);

    // assertions
    Mockito.verify(logger, Mockito.times(2)).log(Mockito.anyString());
  }

  /**
   * TODO 3.05 Jobs: Schedule a job that runs as JOHN with a 'US Locale' and wait for completion.
   * <p>
   * Hint: Use the Subject JOHN as declared by this class.
   */
  @Test
  public void exercise_5() {
    Logger logger = Mockito.spy(new Logger());
    Holder<Subject> subject = new Holder<Subject>();
    Holder<Locale> locale = new Holder<Locale>();

    IRunnable runnable = () -> {
      logger.log("exercise_5");
      subject.setValue(Subject.getSubject(AccessController.getContext()));
      locale.setValue(NlsLocale.CURRENT.get());
    };

    // code here
    Jobs.schedule(runnable, Jobs.newInput()
        .withRunContext(RunContexts.empty()
            .withSubject(JOHN)
            .withLocale(Locale.US)))
        .awaitDone();

    // assertions
    Mockito.verify(logger, Mockito.times(1)).log(Mockito.anyString());
    Assert.assertEquals(JOHN, subject.getValue());
    Assert.assertEquals(Locale.US, locale.getValue());
  }

  /**
   * TODO 3.06 Jobs: Schedule 3 jobs and let the jobs execute in sequence in the order as scheduled.
   * <p>
   * Hint: Use an execution semaphore (Jobs.newExecutionSemaphore()) to achieve mutual exclusion.
   */
  @Test
  public void exercise_6() {
    Logger logger = Mockito.spy(new Logger());
    List<Integer> executionList = new ArrayList<>();

    IExecutionSemaphore mutex = Jobs.newExecutionSemaphore(1 /* 1=mutual exclusion */);

    IRunnable runnable1 = () -> {
      logger.log("exercise_6: job-1");
      executionList.add(1);
      SleepUtil.sleepSafe(500, TimeUnit.MILLISECONDS);
    };
    IRunnable runnable2 = () -> {
      logger.log("exercise_6: job-2");
      executionList.add(2);
      SleepUtil.sleepSafe(500, TimeUnit.MILLISECONDS);
    };

    IRunnable runnable3 = () -> {
      logger.log("exrcise_6: job-3");
      executionList.add(3);
      SleepUtil.sleepSafe(500, TimeUnit.MILLISECONDS);
    };

    // code here assign the futures
    // Schedule the first job
    IFuture<Void> future1 = Jobs.schedule(runnable1,
        Jobs.newInput()
            .withExecutionSemaphore(mutex));

    // Schedule the second job
    IFuture<Void> future2 = Jobs.schedule(runnable2,
        Jobs.newInput()
            .withExecutionSemaphore(mutex));

    // Schedule the third job
    IFuture<Void> future3 = Jobs.schedule(runnable3,
        Jobs.newInput()
            .withExecutionSemaphore(mutex));

    // wait for all
    Jobs.getJobManager().awaitDone(
        Jobs.newFutureFilterBuilder()
            .andMatchFuture(future1, future2, future3).toFilter(),
        10, TimeUnit.SECONDS);

    // assertions
    Mockito.verify(logger, Mockito.times(3)).log(Mockito.anyString());
    Assert.assertArrayEquals(new Integer[]{1, 2, 3}, executionList.toArray());
  }

  /**
   * TODO 3.07 Jobs: Schedule a repetitive job which starts in 3 seconds and runs 4 times (total) at every two second.
   * <p>
   * Hint: Use an execution trigger (Jobs.newExecutionTrigger())<br>
   * Hint: Use {@link SimpleScheduleBuilder} as schedule (static factory methods)<br>
   *
   * @throws InterruptedException
   */
  @Test
  public void exercise_7() throws InterruptedException {
    Logger logger = Mockito.spy(new Logger());
    CountDownLatch waitLatch = new CountDownLatch(3);

    IRunnable runnable = () -> {
      logger.log("exercise_7 (sleeps 1s)");
      SleepUtil.sleepSafe(1, TimeUnit.SECONDS);
    };

    IFuture<Void> future = Jobs.schedule(runnable, Jobs.newInput()
        // code here
        .withExecutionTrigger(Jobs.newExecutionTrigger()
            .withStartIn(3, TimeUnit.SECONDS) // first execution in 3s
            .withSchedule(SimpleScheduleBuilder.repeatSecondlyForTotalCount(4, 2))));

    future.whenDone((result) -> waitLatch.countDown(), RunContexts.copyCurrent());
    waitLatch.await(15, TimeUnit.SECONDS);

    // assertions
    Mockito.verify(logger, Mockito.times(4)).log(Mockito.anyString());
  }

  /**
   * TODO 3.08 Jobs: Schedule a repetitive job which starts in 3 seconds and runs 5 times with a fixed delay of 2
   * seconds between executions.
   * <p>
   * Hint: Use an execution trigger (Jobs.newExecutionTrigger())<br>
   * Hint: Use {@link FixedDelayScheduleBuilder} as schedule (static factory methods)<br>
   *
   * @throws InterruptedException
   */
  @Test
  public void exercise_8() throws InterruptedException {
    Logger logger = Mockito.spy(new Logger());
    CountDownLatch waitLatch = new CountDownLatch(5);

    IRunnable runnable = () -> {
      logger.log("exercise_8 (sleeps 1s)");
      SleepUtil.sleepSafe(500, TimeUnit.MILLISECONDS);
    };

    // code here
    IFuture<Void> future = Jobs.schedule(runnable, Jobs.newInput()
        // code here
        .withExecutionTrigger(Jobs.newExecutionTrigger()
            .withStartIn(3, TimeUnit.SECONDS) // first execution in 3s
            .withSchedule(FixedDelayScheduleBuilder.repeatForTotalCount(5, 2, TimeUnit.SECONDS))));

    future.whenDone((result) -> waitLatch.countDown(), RunContexts.copyCurrent());
    waitLatch.await(15, TimeUnit.SECONDS);
    // assertions
    Mockito.verify(logger, Mockito.times(5)).log(Mockito.anyString());
  }

  /**
   * TODO 3.09 Jobs: Schedule a job which runs every 10 seconds from Monday to Friday.
   * <p>
   * Hint: Use an execution trigger (Jobs.newExecutionTrigger())<br>
   * Hint: Use {@link CronScheduleBuilder} as schedule (static factory methods)<br>
   * Hint: See {@link CronExpression} to specify the CRON expression
   *
   * @throws InterruptedException
   */
  @Test
  public void exercise_9() throws InterruptedException {
    Logger logger = Mockito.spy(new Logger());
    CountDownLatch waitLatch = new CountDownLatch(2);

    IRunnable runnable = () -> logger.log("exercise_9");

    IFuture<Void> future = Jobs.schedule(runnable, Jobs.newInput()
        // code here
        .withExecutionTrigger(Jobs.newExecutionTrigger()
            .withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * ? * MON-FRI"))));

    future.whenDone((result) -> waitLatch.countDown(), RunContexts.copyCurrent());

    waitLatch.await(20, TimeUnit.SECONDS);
    future.cancel(true);
    // assertions
    Mockito.verify(logger, Mockito.atLeast(2)).log(Mockito.anyString());
  }

  /**
   * TODO 3.10 Jobs: Schedule a job which runs at 10:15am every Monday, Tuesday, Wednesday, Thursday and Friday.
   * <p>
   * Hint: Use an execution trigger (Jobs.newExecutionTrigger())<br>
   * Hint: Use {@link CronScheduleBuilder} as schedule (static factory methods)<br>
   * Hint: See {@link CronExpression} to specify the CRON expression<br>
   */
  @Test
  public void exercise_10() {
    Logger logger = Mockito.spy(new Logger());

    IRunnable runnable = () -> logger.log("exercise_10");
    IFuture<Void> future = Jobs.schedule(runnable,
        Jobs.newInput()
            // your code here
            .withExecutionTrigger(Jobs.newExecutionTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule("0 15 10 ? * MON-FRI"))));

    assertFalse(future.isSingleExecution());
    assertThat(future.getState(), is(JobState.PENDING));
    aboardAllJobs();
  }

  /**
   * TODO 3.11 Jobs: Limit the maximal concurrency level to 5 concurrently executing jobs.<br>
   * Hint: See {@link Jobs#newExecutionSemaphore(int)}
   */
  @Test
  public void exercise_11() {
    Logger logger = Mockito.spy(new Logger());
    final IExecutionSemaphore semaphore = Jobs.newExecutionSemaphore(5);

    for (int i = 0; i < 10; i++) {
      // code here schedule the runnable with a job '.withName("ex11-{}", i)'
      Jobs.schedule(() -> {
        logger.log("Start job '" + IFuture.CURRENT.get().getJobInput().getName() + "'");
        assertTrue(Jobs.getJobManager().getFutures(f -> f.getState() == JobState.RUNNING).size() < 6);
        Thread.sleep(200);
      },
          Jobs.newInput()
              .withName("j{}", i)
              .withExecutionSemaphore(semaphore)
      // code here
      );
    }
    // wait for all
    waitForAllJobs();
  }

  /**
   * TODO 3.12 Jobs: In this example there are 10 jobs which load model data concurrently.
   * <p>
   * Your task is to update the model via 'updateModel' method. But, access to the model is limited to a single writing
   * thread.
   * <p>
   * Hint: Do not use synchronization nor locking<br>
   * Hint: Do not wait for futures to complete<br>
   * Hint: Use 'whenDoneSchedule' to update the model upon loading model data.<br>
   * Hint: Use an execution semaphore ({@link Jobs#newExecutionSemaphore(int)}) to achieve mutual exclusion.
   */
  @Test
  public void exercise_12() {
    Logger logger = Mockito.spy(new Logger());
    final IExecutionSemaphore modelMutex = Jobs.newExecutionSemaphore(1);

    Callable<String> dataLoader = () -> {
      logger.log(IFuture.CURRENT.get().getJobInput().getName() + ": loadData");
      // this might take time
      SleepUtil.sleepSafe(1, TimeUnit.SECONDS);
      return "";
    };

    BiConsumer<String, Throwable> modelUpdater = (String result, Throwable t) -> {
      logger.log(IFuture.CURRENT.get().getJobInput().getName() + ": updateModel");
      SleepUtil.sleepSafe(500, TimeUnit.MILLISECONDS);
      assertTrue(Jobs.getJobManager().getFutures(
          Jobs.newFutureFilterBuilder().andMatchExecutionHint("model")
              .andMatchState(JobState.RUNNING)
              .toFilter())
          .size() == 1);

    };

    for (int i = 0; i < 10; i++) {
      Jobs.schedule(dataLoader, Jobs.newInput()
          .withName("loader {}" + i)
          .withExecutionHint("data"))
          .whenDoneSchedule(modelUpdater, Jobs.newInput()
              .withExecutionHint("model")
              .withName("modelUpdater {}", i)
              .withExecutionSemaphore(modelMutex)
          // code here
          );
    }
    waitForAllJobs(20, TimeUnit.SECONDS);
  }

  private void waitForAllJobs() {
    waitForAllJobs(10, TimeUnit.SECONDS);
  }

  private void waitForAllJobs(long timeout, TimeUnit timeUnit) {
    Jobs.getJobManager().awaitFinished(
        Jobs.newFutureFilterBuilder()
            .toFilter(),
        timeout, timeUnit);
  }

  private void aboardAllJobs() {
    Jobs.getJobManager().getFutures(f -> true).forEach(f -> f.cancel(true));
  }
}
