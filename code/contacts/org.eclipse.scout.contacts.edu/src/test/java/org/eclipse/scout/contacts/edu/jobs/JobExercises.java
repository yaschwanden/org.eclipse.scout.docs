package org.eclipse.scout.contacts.edu.jobs;

import java.security.AccessController;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.security.auth.Subject;

import org.eclipse.scout.contacts.edu.util.Helpers;
import org.eclipse.scout.rt.platform.context.RunContext;
import org.eclipse.scout.rt.platform.context.RunContexts;
import org.eclipse.scout.rt.platform.job.FixedDelayScheduleBuilder;
import org.eclipse.scout.rt.platform.job.IExecutionSemaphore;
import org.eclipse.scout.rt.platform.job.Jobs;
import org.eclipse.scout.rt.platform.util.Assertions;
import org.eclipse.scout.rt.platform.util.SleepUtil;
import org.eclipse.scout.rt.platform.util.concurrent.IBiConsumer;
import org.eclipse.scout.rt.platform.util.concurrent.IRunnable;
import org.eclipse.scout.rt.shared.ISession;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.SimpleScheduleBuilder;

/**
 * Exercises for the new Job API in Eclipse Scout 6.1.x.
 */
public class JobExercises {

  private static final Subject JOHN = Helpers.newSubject("john");

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
   * TODO Jobs: Schedule a job that runs once
   */
  @Test
  public void exercise_1() {
    IRunnable runnable = () -> log("exercise_1");
  }

  /**
   * TODO Jobs: Schedule a job that runs once, and wait for the job to complete
   */
  @Test
  public void exercise_2() {
    IRunnable runnable = () -> log("exercise_2");
  }

  /**
   * TODO Jobs: Schedule a job that runs once, and which returns a result to the submitter. Then, print the result to
   * the console.
   */
  @Test
  public void exercise_3() {
    Callable<String> callable = () -> {
      log("exercise_3");
      return "doing something";
    };
  }

  /**
   * TODO Jobs: Schedule 2 jobs and wait for their completion.
   * <p>
   * Hint: Mark the jobs with an execution hint and wait for all jobs that comply with that hint.
   */
  @Test
  public void exercise_4() {
    IRunnable runnable1 = () -> log("exercise_4: job-1");
    IRunnable runnable2 = () -> log("exercise_4: job-2");
  }

  /**
   * TODO Jobs: Schedule a job that runs as JOHN with a 'US Locale'.
   * <p>
   * Hint: Use the Subject JOHN as declared by this class.
   */
  @Test
  public void exercise_5() {
    IRunnable runnable = () -> log("exercise_5");
  }

  /**
   * TODO Jobs: Schedule 3 jobs and let the jobs execute in sequence in the order as scheduled.
   * <p>
   * Hint: Use an execution semaphore (Jobs.newExecutionSemaphore()) to achieve mutual exclusion.
   */
  @Test
  public void exercise_6() {
    IExecutionSemaphore mutex = Jobs.newExecutionSemaphore(1 /* 1=mutual exclusion */);

    IRunnable runnable1 = () -> {
      log("exercise_6: job-1");
      Thread.sleep(2000);
    };
    IRunnable runnable2 = () -> {
      log("exercise_6: job-2");
      Thread.sleep(2000);
    };

    IRunnable runnable3 = () -> {
      log("exercise_6: job-3");
      Thread.sleep(2000);
    };
  }

  /**
   * TODO Jobs: Schedule a repetitive job which starts in 3 seconds and runs 5 times at every two second.
   * <p>
   * Hint: Use an execution trigger (Jobs.newExecutionTrigger())<br>
   * Hint: Use {@link SimpleScheduleBuilder} as schedule (static factory methods)<br>
   */
  @Test
  public void exercise_7() {
    IRunnable runnable = () -> {
      log("exercise_7 (sleeps 1s)");
      Thread.sleep(1_000);
    };
  }

  /**
   * TODO Jobs: Schedule a repetitive job which starts in 3 seconds and runs 5 times with a fixed delay of 2 seconds
   * between executions.
   * <p>
   * Hint: Use an execution trigger (Jobs.newExecutionTrigger())<br>
   * Hint: Use {@link FixedDelayScheduleBuilder} as schedule (static factory methods)<br>
   */
  @Test
  public void exercise_8() {
    IRunnable runnable = () -> {
      log("exercise_8 (sleeps 1s)");
      Thread.sleep(1_000);
    };
  }

  /**
   * TODO Schedule a job which runs every 10 seconds from Monday to Friday.
   * <p>
   * Hint: Use an execution trigger (Jobs.newExecutionTrigger())<br>
   * Hint: Use {@link CronScheduleBuilder} as schedule (static factory methods)<br>
   * Hint: See {@link CronExpression} to specify the CRON expression
   */
  @Test
  public void exercise_9() {
    IRunnable runnable = () -> log("exercise_9");
  }

  /**
   * TODO Schedule a job which runs at 10:15am every Monday, Tuesday, Wednesday, Thursday and Friday.
   * <p>
   * Hint: Use an execution trigger (Jobs.newExecutionTrigger())<br>
   * Hint: Use {@link CronScheduleBuilder} as schedule (static factory methods)<br>
   * Hint: See {@link CronExpression} to specify the CRON expression
   */
  @Test
  public void exercise_10() {
    IRunnable runnable = () -> log("exercise_10");
  }

  /**
   * TODO Jobs: Limit the maximal concurrency level to 5 concurrently executing jobs.
   */
  @Test
  public void exercise_11() {
    IExecutionSemaphore semaphore = Jobs.newExecutionSemaphore(5);

    for (int i = 0; i < 100; i++) {
      IRunnable runnable = () -> {
        log("exercise_11");
        Thread.sleep(TimeUnit.SECONDS.toMillis(5));
      };
    }
  }

  /**
   * TODO Jobs: In this example there are 10 jobs which load model data concurrently.
   * <p>
   * Your task is to update the model via 'updateModel' method. But, access to the model is limited to a single writing
   * thread.
   * <p>
   * Hint: Do not use synchronization nor locking<br>
   * Hint: Do not wait for futures to complete<br>
   * Hint: Use 'whenDoneSchedule' to update the model upon loading model data.<br>
   * Hint: Use an execution semaphore to achieve mutual exclusion.
   */
  @Test
  public void exercise_12() {
    final IExecutionSemaphore modelMutex = Jobs.newExecutionSemaphore(1);

    for (int i = 0; i < 10; i++) {
      Callable<String> dataLoader = newModelDataLoader();
      IBiConsumer<String, Throwable> updater = (String result, Throwable t) -> updateModel(result);
    }
  }

  @BeforeClass
  public static void beforeClass() {
    System.setProperty("jandex.rebuild", Boolean.toString(true));
  }

  @After
  public void after() {
    Jobs.getJobManager().awaitDone(null, 1, TimeUnit.MINUTES);
    ISession.CURRENT.remove();
  }

  private void log(String label) {
    String now = new SimpleDateFormat("HH:mm:ss").format(new Date());

    System.out.printf("[%s] %s [subject=%s]",
        now, label, Subject.getSubject(AccessController.getContext()))
        .println();
  }

  private Callable<String> newModelDataLoader() {
    return new Callable<String>() {

      @Override
      public String call() throws Exception {
        Thread.sleep(5_000);
        return UUID.randomUUID().toString();
      }
    };
  }

  private AtomicInteger m_modelAccessCount = new AtomicInteger();

  private void updateModel(String result) {
    Assertions.assertEquals(m_modelAccessCount.incrementAndGet(), 1, "concurrent invocation");
    try {
      SleepUtil.sleepSafe(3, TimeUnit.SECONDS);
      System.out.println("updating model: " + result);
    }
    finally {
      m_modelAccessCount.decrementAndGet();
    }
  }
}
