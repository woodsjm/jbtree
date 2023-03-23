package com.github.woodsjm.jbtree;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import org.junit.jupiter.api.Test;

public class TreeTraversalTest {

  static Node<Integer> node1;
  static Node<Integer> node2;
  static Node<Integer> node3;
  static Node<Integer> node4;
  static Node<Integer> node5;

  @Test
  void testTreeTraversal() {
    node1 = new Node<>(1);

    checkNested(
        node1.levels(), // [[node1]]
        Arrays.asList(Arrays.asList(node1)));
    checkFlat(node1.leaves(), Arrays.asList(node1)); // [node1]
    checkFlat(node1.inorder(), Arrays.asList(node1)); // [node1]
    checkFlat(node1.preorder(), Arrays.asList(node1)); // [node1]
    checkFlat(node1.postorder(), Arrays.asList(node1)); // [node1]
    checkFlat(node1.levelorder(), Arrays.asList(node1)); // [node1]

    node2 = new Node<>(2);
    node1.setLeft(node2);

    checkNested(
        node1.levels(), // [[node1], [node2]]
        Arrays.asList(Arrays.asList(node1), Arrays.asList(node2)));
    checkFlat(node1.leaves(), Arrays.asList(node2)); // [node2]
    checkFlat(node1.inorder(), Arrays.asList(node2, node1)); // [node2, node1]
    checkFlat(node1.preorder(), Arrays.asList(node1, node2)); // [node1, node2]
    checkFlat(node1.postorder(), Arrays.asList(node2, node1)); // [node2, node1]
    checkFlat(node1.levelorder(), Arrays.asList(node1, node2)); // [node1, node2]

    node3 = new Node<>(3);
    node1.setRight(node3);

    checkNested(
        node1.levels(), // [[node1], [node2, node3]]
        Arrays.asList(Arrays.asList(node1), Arrays.asList(node2, node3)));
    checkFlat(node1.leaves(), Arrays.asList(node2, node3)); // [node2, node3]
    checkFlat(node1.inorder(), Arrays.asList(node2, node1, node3)); // [node2, node1, node3]
    checkFlat(node1.preorder(), Arrays.asList(node1, node2, node3)); // [node1, node2, node3]
    checkFlat(node1.postorder(), Arrays.asList(node2, node3, node1)); // [node2, node3, node1]
    checkFlat(node1.levelorder(), Arrays.asList(node1, node2, node3)); // [node1, node2, node3]

    node4 = new Node<>(4);
    node5 = new Node<>(5);
    node1.getLeft().setLeft(node4);
    node1.getLeft().setRight(node5);

    checkNested(
        node1.levels(), // [[node1], [node2, node3], [node4, node5]]
        Arrays.asList(
            Arrays.asList(node1), Arrays.asList(node2, node3), Arrays.asList(node4, node5)));
    checkFlat(node1.leaves(), Arrays.asList(node3, node4, node5)); // [node3, node4, node5]
    checkFlat(
        node1.inorder(),
        Arrays.asList(node4, node2, node5, node1, node3)); // [node4, node2, node5, node1, node3]
    checkFlat(
        node1.preorder(),
        Arrays.asList(node1, node2, node4, node5, node3)); // [node1, node2, node4, node5, node3]
    checkFlat(
        node1.postorder(),
        Arrays.asList(node4, node5, node2, node3, node1)); // [node4, node5, node2, node3, node1]
    checkFlat(
        node1.levelorder(),
        Arrays.asList(node1, node2, node3, node4, node5)); // [node1, node2, node3, node4, node5]
  }

  void checkFlat(List<Node<Integer>> actual, List<Node<Integer>> expected) {
    assertEquals(expected.size(), actual.size());

    ListIterator<Node<Integer>> actualIter = actual.listIterator();
    for (int i = 0; actualIter.hasNext(); i = 0 + actualIter.nextIndex()) {
      assertTrue(actualIter.next().equals(expected.get(i)));
    }
  }

  void checkNested(List<List<Node<Integer>>> actualOuter, List<List<Node<Integer>>> expectedOuter) {
    assertEquals(expectedOuter.size(), actualOuter.size());

    ListIterator<List<Node<Integer>>> actualOuterIter = actualOuter.listIterator();
    for (int i = 0; actualOuterIter.hasNext(); i = 0 + actualOuterIter.nextIndex()) {

      ListIterator<Node<Integer>> actualInnerIter = actualOuterIter.next().listIterator();
      for (int j = 0; actualInnerIter.hasNext(); j = 0 + actualInnerIter.nextIndex()) {
        assertTrue(actualInnerIter.next().equals(expectedOuter.get(i).get(j)));
      }
    }
  }
}
