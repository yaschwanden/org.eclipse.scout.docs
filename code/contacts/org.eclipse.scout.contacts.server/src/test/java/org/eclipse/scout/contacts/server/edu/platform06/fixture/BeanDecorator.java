package org.eclipse.scout.contacts.server.edu.platform06.fixture;

import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.scout.rt.platform.interceptor.IBeanDecorator;
import org.eclipse.scout.rt.platform.interceptor.IBeanInvocationContext;

/**
 * <h3>{@link BeanDecorator}</h3>
 */
public class BeanDecorator<T> implements IBeanDecorator<T> {
  private static AtomicInteger instanceCount = new AtomicInteger();
  private static AtomicInteger switchOnCount = new AtomicInteger();
  private static AtomicInteger switchOffCount = new AtomicInteger();

  public BeanDecorator() {
    instanceCount.incrementAndGet();
  }

  @Override
  public Object invoke(IBeanInvocationContext<T> paramIBeanInvocationContext) {
    if ("switchOn".equals(paramIBeanInvocationContext.getTargetMethod().getName())) {
      switchOnCount.incrementAndGet();
    }
    else if ("switchOff".equals(paramIBeanInvocationContext.getTargetMethod().getName())) {
      switchOffCount.incrementAndGet();
    }
    return paramIBeanInvocationContext.proceed();
  }

  public static int getInstanceCount() {
    return instanceCount.get();
  }

  public static int getSwitchOffCount() {
    return switchOffCount.get();
  }

  public static int getSwitchOnCount() {
    return switchOnCount.get();
  }

  public static void resetStatistic() {
    switchOnCount.set(0);
    switchOffCount.set(0);
    instanceCount.set(0);
  }
}
