package org.eclipse.scout.contacts.server.edu.platform04;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.scout.contacts.server.edu.platform04.fixture.IPizzaService;
import org.eclipse.scout.contacts.server.edu.platform04.fixture.MargaritaPizzaService;
import org.eclipse.scout.contacts.server.edu.platform04.fixture.ProsciuttoPizzaService;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.junit.Assert;
import org.junit.Test;

/**
 * <h3>{@link Bean_PizzaServiceTest}</h3>
 */
public class Bean_PizzaServiceTest {

  /**
   * {@link BEANS#all(Class)} finds all instances of an interface (except replaced classes). <br>
   * This is useful for resource providers such as image and text services.
   * <p>
   * TODO 1.09 Platform: Create a FunghiPizzaService
   */
  @Test
  public void createFunghiPizzaService() {
    List<String> serviceNames = BEANS.all(IPizzaService.class).stream().map(service -> service.getClass().getSimpleName()).collect(Collectors.toList());
    assertThat(serviceNames, hasSize(3));
    assertThat(serviceNames, hasItem("FunghiPizzaService"));
  }

  /**
   * Replaced instances are not found with BEANS.
   * <p>
   * TODO 1.10 Platform: Replace the {@link ProsciuttoPizzaService} with a HawaiiPizzaService, do not change any
   * assertions.
   */
  @Test
  public void replaceProciuttoService() {
    List<String> serviceNames = BEANS.all(IPizzaService.class).stream().map(service -> service.getClass().getSimpleName()).collect(Collectors.toList());
    assertThat(serviceNames, hasSize(3));
    assertThat(serviceNames, not(hasItem(ProsciuttoPizzaService.class.getSimpleName())));
    assertThat(serviceNames, hasItem("HawaiiPizzaService"));
  }

  /**
   * All Beans: Beans are ordered with {@link Order} annotation.
   * <p>
   * TODO 1.11 Platform bring the beans in the required order. Do not touch orders of {@link MargaritaPizzaService} and
   * {@link ProsciuttoPizzaService}.
   */
  @Test
  public void orderdBeans() {
    List<String> serviceNames = BEANS.all(IPizzaService.class).stream().map(service -> service.getClass().getSimpleName()).collect(Collectors.toList());
    Assert.assertArrayEquals(new String[]{"MargaritaPizzaService", "FunghiPizzaService", "HawaiiPizzaService"}, serviceNames.toArray());

  }
}
