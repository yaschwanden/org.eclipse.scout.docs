package org.eclipse.scout.contacts.server.test;

import org.eclipse.scout.rt.platform.config.PlatformConfigProperties.JandexRebuildProperty;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * <h3>{@link AbstractCourseTest}</h3>
 *
 * @author mvi
 */
public abstract class AbstractCourseTest {

  private static final String JANDEX_PROPERTY_NAME = new JandexRebuildProperty().getKey();
  private static String m_originalPropertyValue;

  @BeforeClass
  public static void enableJandexRebuild() {
    m_originalPropertyValue = System.getProperty(JANDEX_PROPERTY_NAME);
    System.setProperty(JANDEX_PROPERTY_NAME, Boolean.toString(true));
  }

  @AfterClass
  public static void restoreJandexRebuildProperty() {
    if (m_originalPropertyValue == null) {
      System.clearProperty(JANDEX_PROPERTY_NAME);
    }
    else {
      System.setProperty(JANDEX_PROPERTY_NAME, m_originalPropertyValue);
    }
    m_originalPropertyValue = null;
  }
}
