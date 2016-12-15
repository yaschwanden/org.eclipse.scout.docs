package org.eclipse.scout.contacts.edu.mom;

import org.eclipse.scout.rt.mom.api.MOM;
import org.eclipse.scout.rt.platform.Platform;

/**
 * Sends some news and runs a ping-request.
 */
public class ClientApplication {

  public static void main(String[] args) {
    System.setProperty("jandex.rebuild", Boolean.toString(true));

    // Send a news message
    MOM.publish(DataMom.class, IDataMomDestinations.NEWS_TOPIC, "Message from " + System.getProperty("user.name"));

    // Send a ping request
    String reply = MOM.request(DataMom.class, IDataMomDestinations.PING_QUEUE, "Hello");
    System.out.println("Ping received: " + reply);

    // Shutdown
    Platform.get().stop();
  }
}
