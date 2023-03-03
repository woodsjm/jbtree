package btree.java;

import static com.github.stefanbirkner.systemlambda.SystemLambda.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nl.altindag.log.LogCaptor;
import nl.altindag.log.model.LogEvent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


class TreeGetNodeByLevelOrderIndexTest {

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
  public void testTreeGetNodeByLevelOrderIndex() {
    assertSame(root, root.get(0));
    assertSame(root.getLeft(), root.get(1));
    assertSame(root.getRight(), root.get(2));
    assertSame(root.getLeft().getLeft(), root.get(3));
    assertSame(root.getLeft().getRight(), root.get(4));
    assertSame(root.getLeft().getRight().getLeft(), root.get(9));     
  }

  @ParameterizedTest
  @ValueSource(ints = { 5, 6, 7, 8, 10 })
  public void testTreeGetNodeByLevelOrderIndexForMissingNode(int index) throws Exception {
    LogCaptor logCaptor = LogCaptor.forClass(Btree.class);

    int statusCode = catchSystemExit(() -> {
      root.get(index);
    });
    assertEquals(0, statusCode);

    LogEvent capturedLogEvent = logCaptor.getLogEvents().get(0);
    String thrownMessage = String.format("node missing at index %s", index);

    assertTrue(capturedLogEvent.getThrowable().get() instanceof BtreeException.NodeNotFoundException);
    assertTrue(capturedLogEvent.getThrowable().get().getMessage().contains(thrownMessage));
      
  }

  @ParameterizedTest
  @ValueSource(ints = { -1, -10 })
  public void testTreeGetNodeByLevelOrderIndexForInvalidIndex(int invalidIndex) throws Exception {
    LogCaptor logCaptor = LogCaptor.forClass(Btree.class);

    int statusCode = catchSystemExit(() -> {
      root.get(invalidIndex);
    });
    assertEquals(0, statusCode);

    LogEvent capturedLogEvent = logCaptor.getLogEvents().get(0);
    String thrownMessage = String.format("node index must be a non-negative int", invalidIndex);

    assertTrue(capturedLogEvent.getThrowable().get() instanceof BtreeException.NodeIndexException);
    assertTrue(capturedLogEvent.getThrowable().get().getMessage().contains(thrownMessage));
  }
}