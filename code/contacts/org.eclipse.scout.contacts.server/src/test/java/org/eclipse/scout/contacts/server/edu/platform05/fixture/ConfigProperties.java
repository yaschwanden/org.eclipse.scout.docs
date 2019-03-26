package org.eclipse.scout.contacts.server.edu.platform05.fixture;

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

}
