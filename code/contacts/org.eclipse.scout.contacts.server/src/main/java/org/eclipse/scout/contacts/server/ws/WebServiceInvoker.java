package org.eclipse.scout.contacts.server.ws;

import java.util.Date;

import org.eclipse.scout.contacts.server.jaxws.PingWebServiceClient;
import org.eclipse.scout.contacts.shared.ws.IWebServiceInvoker;
import org.eclipse.scout.rt.platform.BEANS;

public class WebServiceInvoker implements IWebServiceInvoker {

  @Override
  public String invoke() {
    // TODO jaxws: invoke webservice via webservice client.
    return BEANS.get(PingWebServiceClient.class).newInvocationContext()
        .withUsername("scott")
        .withPassword("tiger")
        .getPort().ping("Hello world: " + new Date());
  }
}
