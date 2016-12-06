package org.eclipse.scout.contacts.server.edu.platform05.fixture;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.eclipse.scout.rt.platform.config.AbstractConfigProperty;
import org.eclipse.scout.rt.platform.config.AbstractPositiveLongConfigProperty;

/**
 * <h3>{@link ConfigProperties}</h3>
 *
 * @author aho
 */
public final class ConfigProperties {

  public static class MaxLinesConfigProperty extends AbstractPositiveLongConfigProperty {

    @Override
    public String getKey() {
      return "contacts.max.lines"; // fix to match the key used in the property
    }

    @Override
    protected Long getDefaultValue() {
      return Long.valueOf(0);
    }
  }

  public static class BirthdayProperty extends AbstractConfigProperty<LocalDate> {

    private static final String KEY = "contacts.test.birthday";

    @Override
    public String getKey() {
      return KEY;
    }

    @Override
    protected LocalDate parse(String paramString) {
      return LocalDate.parse(paramString, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Override
    protected LocalDate getDefaultValue() {
      return LocalDate.of(1900, 04, 25);
    }
  }
}
