package org.eclipse.scout.contacts.server.jaxws;

import java.util.List;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.MessageContext;

import org.eclipse.scout.docs.ws.pingwebservice.PingWebService;
import org.eclipse.scout.docs.ws.pingwebservice.PingWebServicePortType;
import org.eclipse.scout.rt.platform.config.AbstractStringConfigProperty;
import org.eclipse.scout.rt.platform.config.IConfigProperty;
import org.eclipse.scout.rt.server.jaxws.consumer.AbstractWebServiceClient;
import org.eclipse.scout.rt.server.jaxws.consumer.auth.handler.BasicAuthenticationHandler;

public class PingWebServiceClient extends AbstractWebServiceClient<PingWebService, PingWebServicePortType> {

  @Override
  protected Class<? extends IConfigProperty<String>> getConfiguredEndpointUrlProperty() {
    return PingWebServiceEndpointUrlProperty.class;
  }

  @Override
  protected void execInstallHandlers(List<Handler<? extends MessageContext>> handlerChain) {
    handlerChain.add(new BasicAuthenticationHandler());
  }

  public static class PingWebServiceEndpointUrlProperty extends AbstractStringConfigProperty {

    @Override
    public String getKey() {
      return "jaxws.ping.url";
    }
  }
}
