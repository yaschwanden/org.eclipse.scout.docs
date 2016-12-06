package org.eclipse.scout.contacts.server.edu.platform05;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.eclipse.scout.contacts.server.edu.platform05.fixture.ConfigProperties;
import org.eclipse.scout.contacts.server.edu.platform05.fixture.ConfigProperties.MaxLinesConfigProperty;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.junit.Test;

/**
 * <h3>{@link ConfigurationTest}</h3>
 * <p>
 * Properties used by this test are located in
 * '/org.eclipse.scout.contacts.server/src/test/resources/config.properties'.
 */
public class ConfigurationTest {

  /**
   * TODO Platform 6.1 Configuration: Ensure the class {@link MaxLinesConfigProperty} uses the value of the
   * config.properties file and correct the value in the properties file to fulfill the validation constraints.
   */
  @Test
  public void testConfig() {
    Long maxLines = CONFIG.getPropertyValue(MaxLinesConfigProperty.class);
    assertThat(maxLines, is(Long.valueOf(3000)));
  }

  /**
   * TODO: Platform 5.2: Create the inner class BirthdayProperty in {@link ConfigProperties}. This property parses a
   * {@link LocalDate} out of the existing config property. <br>
   * Hint: use {@link LocalDate#parse(CharSequence, DateTimeFormatter)} and {@link DateTimeFormatter#ofPattern(String)}
   * with the pattern 'dd.MM.yyyy'.
   */
  @Test
  public void testBirthdayConfigProperty() throws ClassNotFoundException {
    Class propertyClass = Class.forName("org.eclipse.scout.contacts.server.edu.platform05.fixture.ConfigProperties$BirthdayProperty");
    @SuppressWarnings("unchecked")
    LocalDate birthday = (LocalDate) CONFIG.getPropertyValue(propertyClass);
    assertThat(birthday.getDayOfMonth(), is(25));
    assertThat(birthday.getMonthValue(), is(12));
    assertThat(birthday.getYear(), is(2011));
  }

}
