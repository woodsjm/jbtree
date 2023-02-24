package btree.java;

import nl.altindag.log.LogCaptor;
import nl.altindag.log.model.LogEvent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static com.github.stefanbirkner.systemlambda.SystemLambda.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TreeGetNodeByLevelOrderIndex {

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

  @Test
  public void testTreeGetNodeByLevelOrderIndexWithExit() throws Exception {
    LogCaptor logCaptor = LogCaptor.forClass(Btree.class);

    int statusCode = catchSystemExit(() -> {
      root.get(5);
    });
    assertEquals(0, statusCode);

    LogEvent capturedLogEvent = logCaptor.getLogEvents().get(0);

    assertTrue(capturedLogEvent.getThrowable().get() instanceof BtreeException.NodeNotFoundException);
    assertTrue(capturedLogEvent.getThrowable().get().getMessage().contains("node missing at index 5"));

  }
}