package org.eclipse.scout.contacts.shared.ws;

import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
@ApplicationScoped
public interface IWebServiceInvoker {

  /**
   * Invokes ping webservice.
   */
  String invoke();
}
