package org.eclipse.scout.contacts.server.edu.platform06.fixture;

import org.eclipse.scout.rt.platform.ApplicationScoped;

/**
 * <h3>{@link ILamp}</h3>
 */
@LoggedBean
@ApplicationScoped
public interface ILamp {

  void switchOn();

  void switchOff();

  boolean isOn();
}
