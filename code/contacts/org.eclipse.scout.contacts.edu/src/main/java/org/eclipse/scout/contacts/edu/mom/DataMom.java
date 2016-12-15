package org.eclipse.scout.contacts.edu.mom;

import java.util.Map;

import org.eclipse.scout.rt.mom.api.AbstractMomTransport;
import org.eclipse.scout.rt.mom.api.IMomImplementor;
import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.eclipse.scout.rt.platform.config.AbstractClassConfigProperty;
import org.eclipse.scout.rt.platform.config.AbstractMapConfigProperty;
import org.eclipse.scout.rt.platform.config.CONFIG;

@ApplicationScoped
public class DataMom extends AbstractMomTransport {

  @Override
  protected Class<? extends IMomImplementor> getConfiguredImplementor() {
    return CONFIG.getPropertyValue(DataMomImplementorProperty.class);
  }

  @Override
  protected Map<String, String> getConfiguredEnvironment() {
    return CONFIG.getPropertyValue(DataMomEnvironmentProperty.class);
  }

  /**
   * Specifies the connection to the network.
   */
  public static class DataMomEnvironmentProperty extends AbstractMapConfigProperty {

    @Override
    public String getKey() {
      return "scout.mom.data.environment";
    }
  }

  /**
   * Specifies the MOM implementor.
   */
  public static class DataMomImplementorProperty extends AbstractClassConfigProperty<IMomImplementor> {

    @Override
    public String getKey() {
      return "scout.mom.data.implementor";
    }
  }
}
