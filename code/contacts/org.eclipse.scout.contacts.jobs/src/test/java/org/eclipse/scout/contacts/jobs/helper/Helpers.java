package org.eclipse.scout.contacts.jobs.helper;

import java.security.AccessController;
import java.util.Locale;

import javax.security.auth.Subject;

import org.eclipse.scout.rt.client.IClientSession;
import org.eclipse.scout.rt.client.context.ClientRunContext;
import org.eclipse.scout.rt.client.context.ClientRunContexts;
import org.eclipse.scout.rt.client.job.ModelJobs;
import org.eclipse.scout.rt.client.session.ClientSessionProvider;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.job.IFuture;
import org.eclipse.scout.rt.platform.security.SimplePrincipal;
import org.eclipse.scout.rt.server.IServerSession;
import org.eclipse.scout.rt.server.context.ServerRunContext;
import org.eclipse.scout.rt.server.context.ServerRunContexts;
import org.eclipse.scout.rt.server.session.ServerSessionProvider;

/**
 * Helper class used to write job examples.
 */
public class Helpers {

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
   * Creates a new {@link IServerSession}.
   */
  public static IServerSession newServerSession() {
    return BEANS.get(ServerSessionProvider.class).provide(ServerRunContexts.empty()
        .withSubject(Subject.getSubject(AccessController.getContext()))
        .withLocale(new Locale("de", "CH")));
  }

  /**
   * Creates a new {@link IClientSession}.
   */
  public static IClientSession newClientSession() {
    return BEANS.get(ClientSessionProvider.class).provide(ClientRunContexts.empty()
        .withSubject(Subject.getSubject(AccessController.getContext()))
        .withLocale(new Locale("de", "CH")));
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
      return "Normal Job";
    }
  }
}
