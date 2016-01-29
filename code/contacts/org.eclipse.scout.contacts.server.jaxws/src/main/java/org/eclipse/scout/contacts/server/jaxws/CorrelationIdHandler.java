package org.eclipse.scout.contacts.server.jaxws;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.eclipse.scout.rt.platform.ApplicationScoped;

/**
 * Handlers which sets the correlation ID.
 */
@ApplicationScoped
public class CorrelationIdHandler implements SOAPHandler<SOAPMessageContext> {

  @Override
  public boolean handleMessage(final SOAPMessageContext context) {
    final String cid = UUID.randomUUID().toString();
    // TODO: Add correlation ID to the context to be accessible by subsequent handlers, and the port type.
    return true;
  }

  @Override
  public boolean handleFault(final SOAPMessageContext context) {
    return true;
  }

  @Override
  public void close(final MessageContext context) {
  }

  @Override
  public Set<QName> getHeaders() {
    return Collections.emptySet();
  }
}
