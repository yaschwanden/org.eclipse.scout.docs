package org.eclipse.scout.contacts.edu.mom;

import org.eclipse.scout.rt.mom.api.AbstractBiDestinationConfigProperty;
import org.eclipse.scout.rt.mom.api.AbstractDestinationConfigProperty;
import org.eclipse.scout.rt.mom.api.IBiDestination;
import org.eclipse.scout.rt.mom.api.IDestination;
import org.eclipse.scout.rt.mom.api.IDestination.DestinationType;
import org.eclipse.scout.rt.mom.api.IDestination.IDestinationType;
import org.eclipse.scout.rt.mom.api.IMomDestinations;
import org.eclipse.scout.rt.platform.config.CONFIG;

public interface IDataMomDestinations extends IMomDestinations {

  IDestination<String> NEWS_TOPIC = CONFIG.getPropertyValue(NewsTopic.class);
  IBiDestination<String, String> PING_QUEUE = CONFIG.getPropertyValue(PingQueue.class);

  class NewsTopic extends AbstractDestinationConfigProperty<String> {

    @Override
    public String getKey() {
      return "scout.mom.cluster.destination.NewsTopic";
    }

    @Override
    protected IDestinationType getType() {
      return DestinationType.TOPIC;
    }
  }

  class PingQueue extends AbstractBiDestinationConfigProperty<String, String> {

    @Override
    public String getKey() {
      return "scout.mom.cluster.destination.PingQueue";
    }

    @Override
    protected IDestinationType getType() {
      return DestinationType.QUEUE;
    }
  }
}
