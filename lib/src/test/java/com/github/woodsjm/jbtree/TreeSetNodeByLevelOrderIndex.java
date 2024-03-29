package com.github.woodsjm.jbtree;

import static com.github.stefanbirkner.systemlambda.SystemLambda.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;
import nl.altindag.log.LogCaptor;
import nl.altindag.log.model.LogEvent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class TreeSetNodeByLevelOrderIndexTest {

  private static Node<Integer> root;

  private static Node<Integer> newNode1;
  private static Node<Integer> newNode2;
  private static Node<Integer> newNode3;

  @BeforeAll
  public static void init() {
    root = new Node<>(1);
    root.setLeft(new Node<>(2));
    root.setRight(new Node<>(3));
    root.getLeft().setLeft(new Node<>(4));
    root.getLeft().setRight(new Node<>(5));
    root.getLeft().getRight().setLeft(new Node<>(6));

    newNode1 = new Node<>(7);
    newNode2 = new Node<>(8);
    newNode3 = new Node<>(9);
  }

  @Test
  void testTreeSetNodeByLevelOrderIndex() {
    root.set(10, newNode1);
    assertEquals(Integer.valueOf(1), root.getVal());
    assertEquals(Integer.valueOf(2), root.getLeft().getVal());
    assertEquals(Integer.valueOf(3), root.getRight().getVal());
    assertEquals(Integer.valueOf(4), root.getLeft().getLeft().getVal());
    assertEquals(Integer.valueOf(5), root.getLeft().getRight().getVal());
    assertEquals(Integer.valueOf(6), root.getLeft().getRight().getLeft().getVal());
    assertSame(newNode1, root.getLeft().getRight().getRight());

    root.set(4, newNode2);
    assertEquals(Integer.valueOf(1), root.getVal());
    assertEquals(Integer.valueOf(2), root.getLeft().getVal());
    assertEquals(Integer.valueOf(3), root.getRight().getVal());
    assertEquals(Integer.valueOf(4), root.getLeft().getLeft().getVal());
    assertEquals(Integer.valueOf(8), root.getLeft().getRight().getVal());
    assertNull(root.getLeft().getRight().getLeft());
    assertNull(root.getLeft().getRight().getRight());

    root.set(1, newNode3);
    root.set(2, newNode2);
    assertSame(newNode3, root.getLeft());
    assertSame(newNode2, root.getRight());
  }

  @ParameterizedTest
  @MethodSource("nodeIndexAndExceptionProvider")
  void testTreeSetNodeByLevelOrderIndexWithException(int index, Class exception, String message)
      throws Exception {
    LogCaptor logCaptor = LogCaptor.forClass(Node.class);

    int statusCode =
        catchSystemExit(
            () -> {
              root.set(index, newNode1);
            });
    assertEquals(0, statusCode);

    LogEvent capturedLogEvent = logCaptor.getLogEvents().get(0);

    assertTrue(capturedLogEvent.getThrowable().get().getClass() == exception);
    assertTrue(capturedLogEvent.getThrowable().get().getMessage().contains(message));
  }

  static Stream<Arguments> nodeIndexAndExceptionProvider() {
    return Stream.of(
        Arguments.arguments(
            0, BtreeException.NodeModifyException.class, "cannot modify the root node"),
        Arguments.arguments(
            -1, BtreeException.NodeIndexException.class, "node index must be a non-negative int"),
        Arguments.arguments(
            100, BtreeException.NodeNotFoundException.class, "parent node missing at index 49"));
  }
}
