package org.eclipse.scout.contacts.server.edu.platform05.fixture;

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
      return "contacts_max_lines"; // fix to match the key used in the property
    }

    @Override
    protected Long getDefaultValue() {
      return Long.valueOf(0);
    }
  }

}
