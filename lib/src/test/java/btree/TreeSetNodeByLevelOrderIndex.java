package btree.java;

import static com.github.stefanbirkner.systemlambda.SystemLambda.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nl.altindag.log.LogCaptor;
import nl.altindag.log.model.LogEvent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;


class TreeSetNodeByLevelOrderIndexTest {

  private static Btree.Node root;

  private static Btree.Node newNode1;
  private static Btree.Node newNode2;
  private static Btree.Node newNode3;

  @BeforeAll
  public static void init() {
    root = new Btree.Node(1);
    root.setLeft(new Btree.Node(2));
    root.setRight(new Btree.Node(3));
    root.getLeft().setLeft(new Btree.Node(4));
    root.getLeft().setRight(new Btree.Node(5));
    root.getLeft().getRight().setLeft(new Btree.Node(6));

    newNode1 = new Btree.Node(7);
    newNode2 = new Btree.Node(8);
    newNode3 = new Btree.Node(9);
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
  void testTreeSetNodeByLevelOrderIndexWithException(int index, Class exception, String message) throws Exception {
    LogCaptor logCaptor = LogCaptor.forClass(Btree.class);

    int statusCode = catchSystemExit(() -> {
      root.set(index, newNode1);
    });
    assertEquals(0, statusCode);

    LogEvent capturedLogEvent = logCaptor.getLogEvents().get(0);

    assertTrue(capturedLogEvent.getThrowable().get().getClass() == exception);
    assertTrue(capturedLogEvent.getThrowable().get().getMessage().contains(message));
  }

  static Stream<Arguments> nodeIndexAndExceptionProvider() {
    return Stream.of(
      Arguments.arguments(0, BtreeException.NodeModifyException.class, "cannot modify the root node"),
      Arguments.arguments(-1, BtreeException.NodeIndexException.class, "node index must be a non-negative int"),
      Arguments.arguments(100, BtreeException.NodeNotFoundException.class, "parent node missing at index 49")
    );
  }
}