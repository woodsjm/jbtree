package com.github.woodsjm.jbtree;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.Test;

public class TreeCloneTest {

  final int REPETITIONS = 20;

  BtreeBuilder<TreeBuilder> tb = new TreeBuilder();

  @Test
  public void testTreeCloneWithNumbers() {
    for (int dummy = 0; dummy < REPETITIONS; dummy++) {
      int height = ThreadLocalRandom.current().nextInt(10);
      boolean isPerfect = ThreadLocalRandom.current().nextBoolean();
      boolean letters = false;

      this.tb.reset();
      Node<Integer> root =
          isPerfect
              ? this.tb.setHeight(height).makePerfect().create()
              : this.tb.setHeight(height).create();

      assertNotNull(root);

      Node<Integer> clone = root.deepCopy();
      assertEquals(clone.values(), root.values());
      assertTrue(root.equals(clone));
      assertTrue(clone.equals(root));
      assertEquals(clone.properties(), root.properties());
    }
  }

  @Test
  public void testTreeCloneWithLetters() {
    for (int dummy = 0; dummy < REPETITIONS; dummy++) {
      int height = ThreadLocalRandom.current().nextInt(10);
      boolean isPerfect = ThreadLocalRandom.current().nextBoolean();
      boolean letters = true;

      this.tb.reset();
      Node<String> root =
          isPerfect
              ? this.tb.setHeight(height).makePerfect().withLetters().create()
              : this.tb.setHeight(height).withLetters().create();

      assertNotNull(root);

      Node<String> clone = root.deepCopy();
      assertEquals(clone.values(), root.values());
      assertTrue(root.equals(clone));
      assertTrue(clone.equals(root));
      assertEquals(clone.properties(), root.properties());
    }
  }
}
