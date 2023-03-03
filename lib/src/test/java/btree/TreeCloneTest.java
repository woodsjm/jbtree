package btree.java;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.List;


public class TreeCloneTest {
  
  final int REPETITIONS = 20;

  @Test
  public void testTreeCloneWithNumbers() {
    for (int dummy = 0; dummy < REPETITIONS; dummy++) {
      int height = ThreadLocalRandom.current().nextInt(10);
      boolean isPerfect = ThreadLocalRandom.current().nextBoolean();
      boolean letters = false;

      Btree.Node<Integer> root = Btree.tree(height, isPerfect, letters);
      assertNotNull(root);
      Btree.Node<Integer> clone = root.deepCopy();
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

      Btree.Node<String> root = Btree.tree(height, isPerfect, letters);
      assertNotNull(root);
      Btree.Node<String> clone = root.deepCopy();
      assertEquals(clone.values(), root.values());
      assertTrue(root.equals(clone));
      assertTrue(clone.equals(root));
      assertEquals(clone.properties(), root.properties());
    }
  }
}