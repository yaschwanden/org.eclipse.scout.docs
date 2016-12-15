package org.eclipse.scout.contacts.edu.mom;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.scout.rt.mom.api.IMessage;
import org.eclipse.scout.rt.mom.api.IMessageListener;
import org.eclipse.scout.rt.mom.api.IRequestListener;
import org.eclipse.scout.rt.mom.api.MOM;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.BeanMetaData;
import org.eclipse.scout.rt.platform.IgnoreBean;
import org.eclipse.scout.rt.platform.Replace;
import org.eclipse.scout.rt.platform.util.ObjectUtility;

/**
 * Starts an embedded broker and replies to 'ping-requests' and prints 'news'.
 */
public class ServerApplication {

  public static void main(String[] args) throws InterruptedException {
    init();

    // Subscribe for the news topic
    MOM.subscribe(DataMom.class, IDataMomDestinations.NEWS_TOPIC, new IMessageListener<String>() {

      @Override
      public void onMessage(IMessage<String> message) {
        System.out.printf("[%s] %s", now(), message.getTransferObject()).println();
      }
    });

    // Install replier to answer ping requests
    MOM.reply(DataMom.class, IDataMomDestinations.PING_QUEUE, new IRequestListener<String, String>() {

      @Override
      public String onRequest(IMessage<String> request) {
        String ping = ObjectUtility.nvl(request.getTransferObject(), "<null> ping");
        return String.format("[%s] %s", now(), ping.toUpperCase());
      }
    });

    System.out.println("Listening for messages and requests ...");
    Thread.sleep(Long.MAX_VALUE);
  }

  private static void init() {
    System.setProperty("jandex.rebuild", Boolean.toString(true));
    BEANS.getBeanManager().registerBean(new BeanMetaData(MasterDataMomEnvironmentProperty.class));
    BEANS.getBeanManager().registerBean(new BeanMetaData(DataMomImplementorPropertyProperty.class));
  }

  private static String now() {
    return new SimpleDateFormat("HH:mm:ss").format(new Date());
  }

  @IgnoreBean
  @Replace
  private static class MasterDataMomEnvironmentProperty extends DataMom.DataMomEnvironmentProperty {

    @Override
    public String getKey() {
      return "scout.mom.data.master.environment";
    }
  }

  @IgnoreBean
  @Replace
  private static class DataMomImplementorPropertyProperty extends DataMom.DataMomImplementorProperty {

    @Override
    public String getKey() {
      return "scout.mom.data.master.implementor";
    }
  }
}
