package org.eclipse.scout.contacts.server.edu.platform06;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.eclipse.scout.contacts.server.edu.platform06.fixture.BeanDecorator;
import org.eclipse.scout.contacts.server.edu.platform06.fixture.ILamp;
import org.eclipse.scout.contacts.server.edu.platform06.fixture.TableLamp;
import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.eclipse.scout.rt.platform.BEANS;
import org.junit.Before;
import org.junit.Test;

/**
 * <h3>{@link BeanDecorationTest}</h3>
 *
 * @author aho
 */
public class BeanDecorationTest {

  @Before
  public void resetStatistics() {
    BeanDecorator.resetStatistic();
  }

  /**
   * TODO Platform 6.1: fix the assertions. And understand how decorations work.
   */
  @Test
  public void decorateBean() {
    ILamp lamp = BEANS.get(ILamp.class);
    assertThat(BeanDecorator.getInstanceCount(), is(-1));
    assertThat(BeanDecorator.getSwitchOnCount(), is(-1));
    assertThat(BeanDecorator.getSwitchOffCount(), is(-1));
    lamp.switchOn();
    assertThat(BeanDecorator.getInstanceCount(), is(-1));
    assertThat(BeanDecorator.getSwitchOnCount(), is(-1));
    assertThat(BeanDecorator.getSwitchOffCount(), is(-1));
    lamp.switchOff();
    assertThat(BeanDecorator.getInstanceCount(), is(-1));
    assertThat(BeanDecorator.getSwitchOnCount(), is(-1));
    assertThat(BeanDecorator.getSwitchOffCount(), is(-1));
  }

  /**
   * Decorations works only for {@link ApplicationScoped} beans looked up via an interface.
   * <p>
   * TODO Platform 6.2: fix the assertions.
   */
  @Test
  public void tryDecorateInstance() {
    ILamp lamp = BEANS.get(TableLamp.class);
    assertThat(BeanDecorator.getInstanceCount(), is(-1));
    assertThat(BeanDecorator.getSwitchOnCount(), is(-1));
    assertThat(BeanDecorator.getSwitchOffCount(), is(-1));
    lamp.switchOn();
    assertThat(BeanDecorator.getInstanceCount(), is(-1));
    assertThat(BeanDecorator.getSwitchOnCount(), is(-1));
    assertThat(BeanDecorator.getSwitchOffCount(), is(-1));

  }
}
