package org.eclipse.scout.contacts.server.ws;

import org.eclipse.scout.docs.ws.pingwebservice.PingWebServicePortType;
import org.eclipse.scout.rt.platform.ApplicationScoped;

@ApplicationScoped
public class PingWebServicePortTypeImpl implements PingWebServicePortType {

  @Override
  public String ping(String ping) {
    return ping;
  }
}
