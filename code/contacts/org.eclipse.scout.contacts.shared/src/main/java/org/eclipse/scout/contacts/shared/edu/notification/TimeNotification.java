package org.eclipse.scout.contacts.shared.edu.notification;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <h3>{@link TimeNotification}</h3>
 */
public class TimeNotification implements Serializable {
  private static final long serialVersionUID = 1L;

  private String m_date;

  public TimeNotification() {
    m_date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss dd.MM.yyyy"));
  }

  public String getDate() {
    return m_date;
  }
}
