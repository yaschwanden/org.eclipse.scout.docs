package org.eclipse.scout.contacts.server.edu.platform06.fixture;

/**
 * <h3>{@link TableLamp}</h3>
 */
public class TableLamp implements ILamp {

  private boolean m_on = false;

  @Override
  public void switchOn() {
    m_on = true;
  }

  @Override
  public void switchOff() {
    m_on = false;
  }

  @Override
  public boolean isOn() {
    return m_on;
  }

}
