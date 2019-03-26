package org.eclipse.scout.contacts.server.edu.platform05.fixture;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.eclipse.scout.rt.platform.config.AbstractConfigProperty;
import org.eclipse.scout.rt.platform.config.AbstractPositiveLongConfigProperty;

/**
 * <h3>{@link ConfigProperties}</h3>
 */
public final class ConfigProperties {

  public static class MaxLinesConfigProperty extends AbstractPositiveLongConfigProperty {

    @Override
    public String getKey() {
      return "contacts.max.lines"; // fix to match the key used in the property
    }

    @Override
    public Long getDefaultValue() {
      return Long.valueOf(0);
    }

    @Override
    public String description() {
      return "Education max lines property.";
    }
  }

  // Birthday property here
  public static class BirthdayProperty extends AbstractConfigProperty<LocalDate, String> {

    @Override
    public String getKey() {
      return "contacts.edu.birthday";
    }

    @Override
    public String description() {
      return "birthday property";
    }

    @Override
    protected LocalDate parse(String value) {
      return LocalDate.parse(value, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

  }

}
