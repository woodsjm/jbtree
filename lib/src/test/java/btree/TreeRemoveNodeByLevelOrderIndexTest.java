package btree.java;

import static com.github.stefanbirkner.systemlambda.SystemLambda.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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


public class TreeRemoveNodeByLevelOrderIndexTest {

  private static Btree.Node root;

  @BeforeAll
  public static void init() {
    root = new Btree.Node(1);
    root.setLeft(new Btree.Node(2));
    root.setRight(new Btree.Node(3));
    root.getLeft().setLeft(new Btree.Node(4));
    root.getLeft().setRight(new Btree.Node(5));
    root.getLeft().getRight().setLeft(new Btree.Node(6));
  }

  @Test
  void testTreeRemoveNodeByLevelOrderIndex() {
    root.remove(3);
    assertNull(root.getLeft().getLeft());
    assertEquals(Integer.valueOf(2), root.getLeft().getVal());
    assertEquals(Integer.valueOf(5), root.getLeft().getRight().getVal());
    assertNull(root.getLeft().getRight().getRight());
    assertEquals(Integer.valueOf(6), root.getLeft().getRight().getLeft().getVal());
    assertNull(root.getLeft().getRight().getLeft().getLeft());
    assertNull(root.getLeft().getRight().getLeft().getRight());
    assertEquals(Integer.valueOf(3), root.getRight().getVal());
    assertNull(root.getRight().getLeft());
    assertNull(root.getRight().getRight());
    assertEquals(Integer.valueOf(5), root.size());

    root.remove(2);
    assertNull(root.getLeft().getLeft());
    assertEquals(Integer.valueOf(2), root.getLeft().getVal());
    assertEquals(Integer.valueOf(5), root.getLeft().getRight().getVal());
    assertNull(root.getLeft().getRight().getRight());
    assertEquals(Integer.valueOf(6), root.getLeft().getRight().getLeft().getVal());
    assertNull(root.getLeft().getRight().getLeft().getLeft());
    assertNull(root.getLeft().getRight().getLeft().getRight());
    assertNull(root.getRight());
    assertEquals(Integer.valueOf(4), root.size());

    root.remove(4);
    assertNull(root.getLeft().getLeft());
    assertNull(root.getLeft().getRight());
    assertNull(root.getRight());
    assertEquals(Integer.valueOf(2), root.size());

    root.remove(1);
    assertNull(root.getLeft());
    assertNull(root.getRight());
    assertEquals(Integer.valueOf(1), root.size());
  }

  @ParameterizedTest
  @MethodSource("nodeIndexAndExceptionProvider")
  void testTreeRemoveNodeByLevelOrderIndexWithException(int index, Class exception, String message) throws Exception {
    LogCaptor logCaptor = LogCaptor.forClass(Btree.class);

    int statusCode = catchSystemExit(() -> {
      root.remove(index);
    });
    assertEquals(0, statusCode);

    LogEvent capturedLogEvent = logCaptor.getLogEvents().get(0);

    assertTrue(capturedLogEvent.getThrowable().get().getClass() == exception);
    assertTrue(capturedLogEvent.getThrowable().get().getMessage().contains(message));
  }

  static Stream<Arguments> nodeIndexAndExceptionProvider() {
    return Stream.of(
      Arguments.arguments(0, BtreeException.NodeModifyException.class, "cannot remove the root node"),
      Arguments.arguments(-1, BtreeException.NodeIndexException.class, "node index must be a non-negative int"),
      Arguments.arguments(10, BtreeException.NodeNotFoundException.class, "no node to remove at index 10"),
      Arguments.arguments(100, BtreeException.NodeNotFoundException.class, "no node to remove at index 100")
    );
  }
}