package com.github.woodsjm.jbtree;

import static com.github.stefanbirkner.systemlambda.SystemLambda.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.altindag.log.LogCaptor;
import nl.altindag.log.model.LogEvent;
import org.junit.jupiter.api.Test;

public class ListRepresentation2Test {

  final int REPETITIONS = 20;

  @Test
  public void testListRepresentation2() {
    LogCaptor logCaptor = LogCaptor.forClass(Btree.class);

    List<Integer> treeAsList = new ArrayList<Integer>();

    Node<Integer> root = Btree.build2(new ArrayList()); // []
    assertNull(root);

    treeAsList.addAll(Arrays.asList(1));
    root = Btree.build2(treeAsList); // [1]
    assertNotNull(root);
    assertEquals(Integer.valueOf(1), root.getVal());
    assertNull(root.getLeft());
    assertNull(root.getRight());

    treeAsList.addAll(Arrays.asList(2));
    root = Btree.build2(treeAsList); // [1, 2]
    assertNotNull(root);
    assertEquals(Integer.valueOf(1), root.getVal());
    assertNotNull(root.getLeft());
    assertEquals(Integer.valueOf(2), root.getLeft().getVal());
    assertNull(root.getRight());

    treeAsList.addAll(Arrays.asList(3));
    root = Btree.build2(treeAsList); // [1, 2, 3]
    assertNotNull(root);
    assertEquals(Integer.valueOf(1), root.getVal());
    assertNotNull(root.getLeft());
    assertEquals(Integer.valueOf(2), root.getLeft().getVal());
    assertNotNull(root.getRight());
    assertEquals(Integer.valueOf(3), root.getRight().getVal());
    assertNull(root.getLeft().getLeft());
    assertNull(root.getLeft().getRight());
    assertNull(root.getRight().getLeft());
    assertNull(root.getRight().getRight());

    treeAsList.addAll(Arrays.asList(null, 4));
    root = Btree.build2(treeAsList); // [1, 2, 3, null, 4]
    assertNotNull(root);
    assertEquals(Integer.valueOf(1), root.getVal());
    assertNotNull(root.getLeft());
    assertEquals(Integer.valueOf(2), root.getLeft().getVal());
    assertNotNull(root.getRight());
    assertEquals(Integer.valueOf(3), root.getRight().getVal());
    assertNull(root.getLeft().getLeft());
    assertNotNull(root.getLeft().getRight());
    assertEquals(Integer.valueOf(4), root.getLeft().getRight().getVal());
    assertNull(root.getRight().getLeft());
    assertNull(root.getRight().getRight());
    assertNull(root.getLeft().getRight().getLeft());
    assertNull(root.getLeft().getRight().getRight());

    treeAsList.clear();
    treeAsList.addAll(Arrays.asList(1, null, 2, 3, 4));
    root = Btree.build2(treeAsList); // [1, null, 2, 3, 4]
    assertNotNull(root);
    assertEquals(Integer.valueOf(1), root.getVal());
    assertNull(root.getLeft());
    assertNotNull(root.getRight());
    assertEquals(Integer.valueOf(2), root.getRight().getVal());
    assertNotNull(root.getRight().getLeft());
    assertEquals(Integer.valueOf(3), root.getRight().getLeft().getVal());
    assertNotNull(root.getRight().getRight());
    assertEquals(Integer.valueOf(4), root.getRight().getRight().getVal());

    treeAsList.clear();
    treeAsList.addAll(Arrays.asList(2, 5, null, 3, null, 1, 4));
    root = Btree.build2(treeAsList); // [2, 5, null, 3, null, 1, 4]
    assertNotNull(root);
    assertEquals(Integer.valueOf(2), root.getVal());
    assertNotNull(root.getLeft());
    assertEquals(Integer.valueOf(5), root.getLeft().getVal());
    assertNull(root.getRight());
    assertNotNull(root.getLeft().getLeft());
    assertEquals(Integer.valueOf(3), root.getLeft().getLeft().getVal());
    assertNull(root.getLeft().getRight());
    assertNotNull(root.getLeft().getLeft().getLeft());
    assertEquals(Integer.valueOf(1), root.getLeft().getLeft().getLeft().getVal());
    assertNotNull(root.getLeft().getLeft().getRight());
    assertEquals(Integer.valueOf(4), root.getLeft().getLeft().getRight().getVal());

    treeAsList.clear();
    treeAsList.addAll(Arrays.asList(1));
    root = new Node(1);
    assertArrayEquals(treeAsList.toArray(), root.values2().toArray()); // [1]
    treeAsList.clear();
    treeAsList.addAll(Arrays.asList(1, null, 3));
    root.setRight(new Node(3));
    assertArrayEquals(treeAsList.toArray(), root.values2().toArray()); // [1, null, 3]
    treeAsList.set(1, 2);
    root.setLeft(new Node(2));
    assertArrayEquals(treeAsList.toArray(), root.values2().toArray()); // [1, 2, 3]
    treeAsList.addAll(Arrays.asList(null, null, 4));
    root.getRight().setLeft(new Node(4));
    assertArrayEquals(treeAsList.toArray(), root.values2().toArray()); // [1, 2, 3, null, null, 4]
    treeAsList.add(5);
    root.getRight().setRight(new Node(5));
    assertArrayEquals(
        treeAsList.toArray(), root.values2().toArray()); // [1, 2, 3, null, null, 4, 5]
    treeAsList.set(3, 6);
    root.getLeft().setLeft(new Node(6));
    assertArrayEquals(treeAsList.toArray(), root.values2().toArray()); // [1, 2, 3, 6, null, 4, 5]
    treeAsList.set(4, 7);
    root.getLeft().setRight(new Node(7));
    assertArrayEquals(treeAsList.toArray(), root.values2().toArray()); // [1, 2, 3, 6, 7, 4, 5]

    treeAsList.clear();
    treeAsList.addAll(Arrays.asList(1));
    root = new Node(1);
    assertArrayEquals(treeAsList.toArray(), root.values2().toArray()); // [1]
    treeAsList.add(2);
    root.setLeft(new Node(2));
    assertArrayEquals(treeAsList.toArray(), root.values2().toArray()); // [1, 2]
    treeAsList.add(3);
    root.setRight(new Node(3));
    assertArrayEquals(treeAsList.toArray(), root.values2().toArray()); // [1, 2, 3]
    treeAsList.add(2, null);
    root.setRight(null);
    root.getLeft().setLeft(new Node(3));
    assertArrayEquals(treeAsList.toArray(), root.values2().toArray()); // [1, 2, null, 3]
    treeAsList.add(null);
    treeAsList.add(4);
    root.getLeft().getLeft().setLeft(new Node(4));
    assertArrayEquals(treeAsList.toArray(), root.values2().toArray()); // [1, 2, null, 3, null, 4]
    treeAsList.add(5);
    root.getLeft().getLeft().setRight(new Node(5));
    assertArrayEquals(
        treeAsList.toArray(), root.values2().toArray()); // [1, 2, null, 3, null, 4, 5]

    for (int dummy = 0; dummy < REPETITIONS; dummy++) {
      Node<Integer> tree1 = Btree.tree();
      assertNotNull(tree1);

      Node<Integer> tree2 = Btree.build2(tree1.values2());
      assertNotNull(tree2);

      assertArrayEquals(tree2.values2().toArray(), tree1.values2().toArray());
    }
  }

  @Test
  public void testListRepresentation2EmptyRoot() throws Exception {
    LogCaptor logCaptor = LogCaptor.forClass(Btree.class);

    int statusCode =
        catchSystemExit(
            () -> {
              Node<Integer> root = Btree.build2(Arrays.asList(null, 1, 2)); // [null, 1, 2]
            });
    assertEquals(0, statusCode);

    LogEvent capturedLogEvent = logCaptor.getLogEvents().get(0);

    assertTrue(capturedLogEvent.getThrowable().get() instanceof BtreeException.NodeValueException);
    assertTrue(
        capturedLogEvent
            .getThrowable()
            .get()
            .getMessage()
            .contains("node value must be an Integer, a Float, or a String"));
  }
}
