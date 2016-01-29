package org.eclipse.scout.contacts.jobs.helper;

import org.eclipse.scout.rt.server.AbstractServerSession;

/**
 * Server Session used for job exercises.
 */
public class ServerSession extends AbstractServerSession {

  private static final long serialVersionUID = 1L;

  public ServerSession() {
    super(true);
  }
}
