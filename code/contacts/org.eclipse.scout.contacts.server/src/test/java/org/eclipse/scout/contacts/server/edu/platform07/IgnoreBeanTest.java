package org.eclipse.scout.contacts.server.edu.platform07;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.scout.contacts.server.edu.platform07.fixture.bird.Falkor;
import org.eclipse.scout.contacts.server.edu.platform07.fixture.bird.IBird;
import org.eclipse.scout.contacts.server.edu.platform07.fixture.bird.IDragon;
import org.eclipse.scout.contacts.server.edu.platform07.fixture.bird.Sparrow;
import org.eclipse.scout.contacts.server.edu.platform07.fixture.fish.IFish;
import org.eclipse.scout.contacts.server.edu.platform07.fixture.fish.Tuna;
import org.eclipse.scout.contacts.server.edu.platform07.fixture.fish.Whale;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.IgnoreBean;
import org.junit.Test;

/**
 * <h3>{@link IgnoreBeanTest}</h3>
 *
 * @author aho
 */
public class IgnoreBeanTest {

  /**
   * {@link IgnoreBean} annotation is not inherited. <br>
   * <br>
   * TODO Platform 7.1: fix the assertions and explain the behavior.
   */
  @Test
  public void test() {
    List<String> birdNames = BEANS.all(IBird.class).stream().map(b -> b.getClass().getSimpleName()).collect(Collectors.toList());
    List<String> dragonNames = BEANS.all(IDragon.class).stream().map(d -> d.getClass().getSimpleName()).collect(Collectors.toList());

    assertThat(birdNames, hasSize(2));
    // write the matching hasItem assertions for each item in the list
    assertThat(birdNames, hasItem(Falkor.class.getSimpleName()));
    assertThat(birdNames, hasItem(Sparrow.class.getSimpleName()));

    assertThat(dragonNames, hasSize(1));
    // write the matching hasItem assertions for each item in the list
    assertThat(dragonNames, hasItem(Falkor.class.getSimpleName()));
  }

  /**
   * {@link IgnoreBean} annotation is not inherited. <br>
   * TODO Platform 7.2: Add {@link IgnoreBean} annotation to the beans to make the test passing. Do not change anything
   * in the test method.
   */
  @Test
  public void fishTest() {
    List<IFish> fish = BEANS.all(IFish.class);
    List<String> fishNames = fish.stream().map(f -> f.getClass().getSimpleName()).collect(Collectors.toList());

    assertThat(fish, hasSize(1));
    assertThat(fishNames, hasItem(Tuna.class.getSimpleName()));
    assertThat(fishNames, not(hasItem(Whale.class.getSimpleName())));

  }
}
