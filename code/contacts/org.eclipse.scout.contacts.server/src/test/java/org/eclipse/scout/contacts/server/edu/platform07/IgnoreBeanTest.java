package org.eclipse.scout.contacts.server.edu.platform07;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.scout.contacts.server.edu.platform07.fixture.bird.IBird;
import org.eclipse.scout.contacts.server.edu.platform07.fixture.bird.IDragon;
import org.eclipse.scout.contacts.server.edu.platform07.fixture.fish.IFish;
import org.eclipse.scout.contacts.server.edu.platform07.fixture.fish.Tuna;
import org.eclipse.scout.contacts.server.edu.util.TODO;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.IgnoreBean;
import org.junit.Test;

/**
 * <h3>{@link IgnoreBeanTest}</h3>
 */
public class IgnoreBeanTest {

  /**
   * {@link IgnoreBean} annotation is not inherited. <br>
   * <br>
   * TODO 1.16 Platform: fix the assertions and explain the behavior.
   */
  @Test
  public void test() {
    List<String> birdNames = BEANS.all(IBird.class).stream().map(b -> b.getClass().getSimpleName()).collect(Collectors.toList());
    List<String> dragonNames = BEANS.all(IDragon.class).stream().map(d -> d.getClass().getSimpleName()).collect(Collectors.toList());

    assertThat(birdNames, hasSize(-1));
    assertEquals(birdNames, Arrays.asList(TODO.class.getSimpleName()));

    assertThat(dragonNames, hasSize(-1));
    assertEquals(dragonNames, Arrays.asList(TODO.class.getSimpleName()));
  }

  /**
   * {@link IgnoreBean} annotation is not inherited. <br>
   * TODO 1.17 Platform: Add {@link IgnoreBean} annotation to the beans to make the test passing. Do not change anything
   * in the test method.
   */
  @Test
  public void fishTest() {
    List<String> fishNames = BEANS.all(IFish.class).stream().map(f -> f.getClass().getSimpleName()).collect(Collectors.toList());

    assertThat(fishNames, hasSize(1));
    assertEquals(fishNames, Arrays.asList(Tuna.class.getSimpleName()));

  }
}
