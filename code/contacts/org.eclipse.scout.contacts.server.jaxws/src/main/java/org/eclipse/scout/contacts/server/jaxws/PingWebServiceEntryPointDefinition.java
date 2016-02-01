package org.eclipse.scout.contacts.server.jaxws;

import org.eclipse.scout.docs.ws.pingwebservice.PingWebServicePortType;
import org.eclipse.scout.rt.server.commons.authentication.ConfigFileCredentialVerifier;
import org.eclipse.scout.rt.server.jaxws.provider.annotation.Authentication;
import org.eclipse.scout.rt.server.jaxws.provider.annotation.Clazz;
import org.eclipse.scout.rt.server.jaxws.provider.annotation.WebServiceEntryPoint;
import org.eclipse.scout.rt.server.jaxws.provider.auth.method.BasicAuthenticationMethod;

@WebServiceEntryPoint(
    endpointInterface = PingWebServicePortType.class,
    authentication = @Authentication(
        method = @Clazz(BasicAuthenticationMethod.class) ,
        verifier = @Clazz(ConfigFileCredentialVerifier.class) ) )
public interface PingWebServiceEntryPointDefinition {
}
