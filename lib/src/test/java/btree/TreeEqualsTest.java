package btree.java;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TreeEqualsTest {

  @Test public void testTreeEqualsWithIntegers() {
    Btree.Node<Integer> root1 = new Btree.Node<>(1);
    Btree.Node<Integer> root2 = new Btree.Node<>(1);
    assertFalse(root1.equals(null));
    assertFalse(root1.equals(Integer.valueOf(1)));
    assertFalse(root1.equals(new Btree.Node<>(2)));
    assertTrue(root1.equals(root2));
    assertTrue(root2.equals(root1));

    root1.setLeft(new Btree.Node<>(2));
    assertFalse(root1.equals(root2));
    assertFalse(root2.equals(root1));

    root2.setLeft(new Btree.Node<>(2));
    assertTrue(root1.equals(root2));
    assertTrue(root2.equals(root1));

    root1.setRight(new Btree.Node<>(3));
    assertFalse(root1.equals(root2));
    assertFalse(root2.equals(root1));

    root2.setRight(new Btree.Node<>(3));
    assertTrue(root1.equals(root2));
    assertTrue(root2.equals(root1));

    root1.getRight().setLeft(new Btree.Node<>(4));
    assertFalse(root1.equals(root2));
    assertFalse(root2.equals(root1));

    root2.getRight().setLeft(new Btree.Node<>(4));
    assertTrue(root1.equals(root2));
    assertTrue(root2.equals(root1));
  }

  @Test public void testTreeEqualsWithFloats() {
    Btree.Node<Float> root1 = new Btree.Node<>(1.50f);
    Btree.Node<Float> root2 = new Btree.Node<>(1.50f);
    assertFalse(root1.equals(null));
    assertFalse(root1.equals(Float.valueOf(1.50f)));
    assertFalse(root1.equals(new Btree.Node<>(2.50f)));
    assertTrue(root1.equals(root2));
    assertTrue(root2.equals(root1));

    root1.setLeft(new Btree.Node<>(2.50f));
    assertFalse(root1.equals(root2));
    assertFalse(root2.equals(root1));

    root2.setLeft(new Btree.Node<>(2.50f));
    assertTrue(root1.equals(root2));
    assertTrue(root2.equals(root1));

    root1.setRight(new Btree.Node<>(3.50f));
    assertFalse(root1.equals(root2));
    assertFalse(root2.equals(root1));

    root2.setRight(new Btree.Node<>(3.50f));
    assertTrue(root1.equals(root2));
    assertTrue(root2.equals(root1));

    root1.getRight().setLeft(new Btree.Node<>(4.50f));
    assertFalse(root1.equals(root2));
    assertFalse(root2.equals(root1));

    root2.getRight().setLeft(new Btree.Node<>(4.50f));
    assertTrue(root1.equals(root2));
    assertTrue(root2.equals(root1));
  }

  @Test public void testTreeEqualsWithLetters() {
    Btree.Node<String> root1 = new Btree.Node<>("A");
    Btree.Node<String> root2 = new Btree.Node<>("A");
    assertFalse(root1.equals(null));
    assertFalse(root1.equals("A"));
    assertFalse(root1.equals(new Btree.Node<>("B")));
    assertTrue(root1.equals(root2));
    assertTrue(root2.equals(root1));

    root1.setLeft(new Btree.Node<>("B"));
    assertFalse(root1.equals(root2));
    assertFalse(root2.equals(root1));

    root2.setLeft(new Btree.Node<>("B"));
    assertTrue(root1.equals(root2));
    assertTrue(root2.equals(root1));

    root1.setRight(new Btree.Node<>("C"));
    assertFalse(root1.equals(root2));
    assertFalse(root2.equals(root1));

    root2.setRight(new Btree.Node<>("C"));
    assertTrue(root1.equals(root2));
    assertTrue(root2.equals(root1));

    root1.getRight().setLeft(new Btree.Node<>("D"));
    assertFalse(root1.equals(root2));
    assertFalse(root2.equals(root1));

    root2.getRight().setLeft(new Btree.Node<>("D"));
    assertTrue(root1.equals(root2));
    assertTrue(root2.equals(root1));
  }
}