package org.eclipse.scout.contacts.client.edu;

import javax.security.auth.Subject;

import org.eclipse.scout.rt.platform.security.SimplePrincipal;

/**
 * <h3>{@link EduUtility}</h3>
 *
 * @author aho
 */
public final class EduUtility {
  private EduUtility() {
  }

  /**
   * Creates a new {@link Subject} of the given name.
   */
  public static Subject newSubject(final String name) {
    final Subject subject = new Subject();
    subject.getPrincipals().add(new SimplePrincipal(name));
    subject.setReadOnly();
    return subject;
  }

}
