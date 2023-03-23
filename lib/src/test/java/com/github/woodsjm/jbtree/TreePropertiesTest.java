package com.github.woodsjm.jbtree;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class TreePropertiesTest {

  static Node<Integer> root;

  @ParameterizedTest
  @MethodSource("nodeValAndExpectedValsProvider")
  void testTreeProperties(int nodeVal, List expectedVals) {

    switch (nodeVal) {
      case 1:
        this.root = new Node<>(nodeVal);
        break;
      case 2:
        this.root.setLeft(new Node<>(nodeVal));
        break;
      case 3:
        this.root.setRight(new Node<>(nodeVal));
        break;
      case 4:
        this.root.getLeft().setLeft(new Node<>(nodeVal));
        break;
      case 5:
        this.root.getRight().setLeft(new Node<>(nodeVal));
        break;
      case 6:
        this.root.getRight().getLeft().setLeft(new Node<>(nodeVal));
        break;
      case 7:
        this.root.getLeft().getLeft().setLeft(new Node<>(nodeVal));
        break;
      default:
        break;
    }

    HashMap<String, Object> actualProperties = root.properties();
    final HashMap<String, Object> expectedProperties = new HashMap<>();
    expectedProperties.put("height", expectedVals.get(0));
    expectedProperties.put("isBalanced", expectedVals.get(1));
    expectedProperties.put("isBST", expectedVals.get(2));
    expectedProperties.put("isComplete", expectedVals.get(3));
    expectedProperties.put("isMaxHeap", expectedVals.get(4));
    expectedProperties.put("isMinHeap", expectedVals.get(5));
    expectedProperties.put("isPerfect", expectedVals.get(6));
    expectedProperties.put("isStrict", expectedVals.get(7));
    expectedProperties.put("isSymmetric", expectedVals.get(8));
    expectedProperties.put("leafCount", expectedVals.get(9));
    expectedProperties.put("maxLeafDepth", expectedVals.get(10));
    expectedProperties.put("maxNodeValue", expectedVals.get(11));
    expectedProperties.put("minLeafDepth", expectedVals.get(12));
    expectedProperties.put("minNodeValue", expectedVals.get(13));
    expectedProperties.put("size", expectedVals.get(14));

    assertEquals(expectedProperties.size(), actualProperties.size());
    assertTrue(actualProperties.keySet().equals(expectedProperties.keySet()));
    actualProperties.forEach(
        (k, v) -> {
          if (v instanceof Integer) {
            assertEquals((Integer) v, (Integer) expectedProperties.get(k));
          } else if (v instanceof Boolean) {
            assertEquals((Boolean) v, (Boolean) expectedProperties.get(k));
          }
        });

    assertEquals((Integer) expectedVals.get(0), (Integer) root.height());
    assertEquals((boolean) expectedVals.get(1), (boolean) root.isBalanced());
    assertEquals((boolean) expectedVals.get(2), (boolean) root.isBST());
    assertEquals((boolean) expectedVals.get(3), (boolean) root.isComplete());
    assertEquals((boolean) expectedVals.get(4), (boolean) root.isMaxHeap());
    assertEquals((boolean) expectedVals.get(5), (boolean) root.isMinHeap());
    assertEquals((boolean) expectedVals.get(6), (boolean) root.isPerfect());
    assertEquals((boolean) expectedVals.get(7), (boolean) root.isStrict());
    assertEquals((boolean) expectedVals.get(8), (boolean) root.isSymmetric());
    assertEquals((Integer) expectedVals.get(9), (Integer) root.leafCount());
    assertEquals((Integer) expectedVals.get(10), (Integer) root.maxLeafDepth());
    assertEquals((Integer) expectedVals.get(11), (Integer) root.maxNodeValue());
    assertEquals((Integer) expectedVals.get(12), (Integer) root.minLeafDepth());
    assertEquals((Integer) expectedVals.get(13), (Integer) root.minNodeValue());
    assertEquals((Integer) expectedVals.get(14), (Integer) root.size());
  }

  static Stream<Arguments> nodeValAndExpectedValsProvider() {
    return Stream.of(
        Arguments.arguments(
            1,
            Arrays.asList(
                0, true, true, // height, isBalanced, isBST
                true, true, true, // isComplete, isMaxHeap, isMinHeap
                true, true, true, // isPerfect, isStrict, isSymmetric
                1, 0, 1, // leafCount, maxLeafDepth, maxNodeValue
                0, 1, 1)), // minLeafDepth, minNodeValue, size
        Arguments.arguments(
            2,
            Arrays.asList(
                1, true, false, // height, isBalanced, isBST
                true, false, true, // isComplete, isMaxHeap, isMinHeap
                false, false, false, // isPerfect, isStrict, isSymmetric
                1, 1, 2, // leafCount, maxLeafDepth, maxNodeValue
                1, 1, 2)), // minLeafDepth, minNodeValue, size
        Arguments.arguments(
            3,
            Arrays.asList(
                1, true, false, // height, isBalanced, isBST
                true, false, true, // isComplete, isMaxHeap, isMinHeap
                true, true, false, // isPerfect, isStrict, isSymmetric
                2, 1, 3, // leafCount, maxLeafDepth, maxNodeValue
                1, 1, 3)), // minLeafDepth, minNodeValue, size
        Arguments.arguments(
            4,
            Arrays.asList(
                2, true, false, // height, isBalanced, isBST
                true, false, true, // isComplete, isMaxHeap, isMinHeap
                false, false, false, // isPerfect, isStrict, isSymmetric
                2, 2, 4, // leafCount, maxLeafDepth, maxNodeValue
                1, 1, 4)), // minLeafDepth, minNodeValue, size
        Arguments.arguments(
            5,
            Arrays.asList(
                2, true, false, // height, isBalanced, isBST
                false, false, false, // isComplete, isMaxHeap, isMinHeap
                false, false, false, // isPerfect, isStrict, isSymmetric
                2, 2, 5, // leafCount, maxLeafDepth, maxNodeValue
                2, 1, 5)), // minLeafDepth, minNodeValue, size
        Arguments.arguments(
            6,
            Arrays.asList(
                3, false, false, // height, isBalanced, isBST
                false, false, false, // isComplete, isMaxHeap, isMinHeap
                false, false, false, // isPerfect, isStrict, isSymmetric
                2, 3, 6, // leafCount, maxLeafDepth, maxNodeValue
                2, 1, 6)), // minLeafDepth, minNodeValue, size
        Arguments.arguments(
            7,
            Arrays.asList(
                3, false, false, // height, isBalanced, isBST
                false, false, false, // isComplete, isMaxHeap, isMinHeap
                false, false, false, // isPerfect, isStrict, isSymmetric
                2, 3, 7, // leafCount, maxLeafDepth, maxNodeValue
                3, 1, 7)) // minLeafDepth, minNodeValue, size
        );
  }
}
