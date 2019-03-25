package org.eclipse.scout.contacts.server.edu.platform06.fixture;

import org.eclipse.scout.rt.platform.IBean;
import org.eclipse.scout.rt.platform.Replace;
import org.eclipse.scout.rt.platform.SimpleBeanDecorationFactory;
import org.eclipse.scout.rt.platform.interceptor.IBeanDecorator;

/**
 * <h3>{@link BeanDocorationFactory}</h3>
 * <p>
 * The decoration factory must replace the {@link SimpleBeanDecorationFactory}. There is only one valid factory!
 */
@Replace
public class BeanDocorationFactory extends SimpleBeanDecorationFactory {

  @Override
  public <T> IBeanDecorator<T> decorate(IBean<T> paramIBean, Class<? extends T> paramClass) {
    if (paramIBean.getBeanAnnotation(LoggedBean.class) != null) {
      return new BeanDecorator<T>();
    }
    return super.decorate(paramIBean, paramClass);
  }

}
