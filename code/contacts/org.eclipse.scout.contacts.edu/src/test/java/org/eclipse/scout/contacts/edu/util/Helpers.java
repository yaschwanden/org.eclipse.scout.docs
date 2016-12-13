package org.eclipse.scout.contacts.edu.util;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.security.AccessController;
import java.util.Locale;

import javax.security.auth.Subject;

import org.eclipse.scout.rt.client.IClientSession;
import org.eclipse.scout.rt.client.context.ClientRunContext;
import org.eclipse.scout.rt.client.context.ClientRunContexts;
import org.eclipse.scout.rt.client.job.ModelJobs;
import org.eclipse.scout.rt.platform.context.RunContext;
import org.eclipse.scout.rt.platform.job.IFuture;
import org.eclipse.scout.rt.platform.job.Jobs;
import org.eclipse.scout.rt.platform.nls.NlsLocale;
import org.eclipse.scout.rt.platform.security.SimplePrincipal;
import org.eclipse.scout.rt.server.IServerSession;
import org.eclipse.scout.rt.server.context.ServerRunContext;
import org.eclipse.scout.rt.server.context.ServerRunContexts;
import org.eclipse.scout.rt.shared.ISession;

/**
 * Helper class used to write job examples.
 */
public class Helpers {

  /**
   * Simulates to run in a server session.
   */
  public static void simulateServerSession() {
    IServerSession session = mock(IServerSession.class);

    ServerRunContext runContext = ServerRunContexts.empty()
        .withSubject(Subject.getSubject(AccessController.getContext()))
        .withLocale(new Locale("de", "CH"))
        .withSession(session);
    RunContext.CURRENT.set(runContext);
    ISession.CURRENT.set(runContext.getSession());
    NlsLocale.CURRENT.set(runContext.getLocale());
  }

  /**
   * Simulates to run in a client session.
   */
  public static void simulateClientSession() {
    IClientSession session = mock(IClientSession.class);
    when(session.getLocale()).thenReturn(new Locale("de", "CH"));
    when(session.getSubject()).thenReturn(Subject.getSubject(AccessController.getContext()));
    when(session.getModelJobSemaphore()).thenReturn(Jobs.newExecutionSemaphore(1).seal());

    ClientRunContext runContext = ClientRunContexts.empty()
        .withSession(session, true);

    RunContext.CURRENT.set(runContext);
    ISession.CURRENT.set(runContext.getSession());
    NlsLocale.CURRENT.set(runContext.getLocale());
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

  /**
   * Returns the current job type.
   */
  public static String getCurrentJobType() {
    final IFuture<?> currentFuture = IFuture.CURRENT.get();
    if (currentFuture == null) {
      return null;
    }
    else if (ModelJobs.isModelJob(currentFuture)) {
      return "ModelJob [runs in a 'ClientRunContext' with the session's model job permit acquired]";
    }
    else if (currentFuture.getJobInput().getRunContext() instanceof ClientRunContext) {
      return "ClientJob [runs in a 'ClientRunContext']";
    }
    else if (currentFuture.getJobInput().getRunContext() instanceof ServerRunContext) {
      return "ServerJob [runs in a 'ServerRunContext']";
    }
    else {
      return null;
    }
  }
}
