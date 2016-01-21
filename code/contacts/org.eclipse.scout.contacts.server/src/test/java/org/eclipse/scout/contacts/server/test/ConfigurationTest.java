package org.eclipse.scout.contacts.server.test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.eclipse.scout.rt.platform.config.AbstractPositiveLongConfigProperty;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.junit.Test;

public class ConfigurationTest extends AbstractTestWithJandexRebuild {

  /**
   * TODO 6.1 Configuration: Ensure the class {@link MaxLinesConfigProperty} uses the value of the config.properties
   * file and correct the value in the properties file to fulfill the validation constraints.
   */
  @Test
  public void testConfig() {
    Long maxLines = CONFIG.getPropertyValue(MaxLinesConfigProperty.class);
    assertThat(maxLines, is(Long.valueOf(3000)));
  }

  public static class MaxLinesConfigProperty extends AbstractPositiveLongConfigProperty {

    @Override
    public String getKey() {
      return "contacts_max_lines"; // fix to match the key used in the property
    }

    @Override
    protected Long getDefaultValue() {
      return Long.valueOf(0);
    }
  }
}
