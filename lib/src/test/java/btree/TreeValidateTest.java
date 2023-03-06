package btree.java;

import static com.github.stefanbirkner.systemlambda.SystemLambda.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nl.altindag.log.LogCaptor;
import nl.altindag.log.model.LogEvent;
import org.junit.jupiter.api.Test;


public class TreeValidateTest {

  @Test
  void testTreeValidateWithIntegers() {
    Btree.Node root = new Btree.Node(1);
    root.validate();

    root = new Btree.Node(1);
    root.setLeft(new Btree.Node(2));
    root.validate();

    root = new Btree.Node(1);
    root.setLeft(new Btree.Node(2));
    root.setRight(new Btree.Node(3));
    root.validate();

    root = new Btree.Node(1);
    root.setLeft(new Btree.Node(2));
    root.setRight(new Btree.Node(3));
    root.getLeft().setLeft(new Btree.Node(4));
    root.getLeft().setRight(new Btree.Node(5));
    root.getLeft().getRight().setLeft(new Btree.Node(6));
    root.validate(); 
  }

  @Test
  void testTreeValidateWithLetters() {
    Btree.Node root = new Btree.Node("A");
    root.validate();

    root = new Btree.Node("A");
    root.setLeft(new Btree.Node("B"));
    root.validate();

    root = new Btree.Node("A");
    root.setLeft(new Btree.Node("B"));
    root.setRight(new Btree.Node(3));
    root.validate();

    root = new Btree.Node("A");
    root.setLeft(new Btree.Node("B"));
    root.setRight(new Btree.Node(3));
    root.getLeft().setLeft(new Btree.Node(4));
    root.getLeft().setRight(new Btree.Node(5));
    root.getLeft().getRight().setLeft(new Btree.Node(6));
    root.validate(); 
  }

  @Test
  void testTreeValidateWithIntegersWithException() throws Exception {
    LogCaptor logCaptor = LogCaptor.forClass(Btree.class);

    Btree.Node root = new Btree.Node(1);
    root.setLeft(new Btree.Node(2));
    root.getLeft().setRight(root);

    int statusCode = catchSystemExit(() -> {
      root.validate();
    });
    assertEquals(0, statusCode);

    LogEvent capturedLogEvent = logCaptor.getLogEvents().get(0);

    assertTrue(capturedLogEvent.getThrowable().get().getClass() == BtreeException.NodeReferenceException.class);
    assertTrue(capturedLogEvent.getThrowable().get().getMessage().contains("cyclic reference at Node(1) (level-order index 4)"));
  }

  @Test
  void testTreeValidateWithLettersWithException() throws Exception {
    LogCaptor logCaptor = LogCaptor.forClass(Btree.class);

    Btree.Node root = new Btree.Node("A");
    root.setLeft(new Btree.Node("B"));
    root.getLeft().setRight(root);

    int statusCode = catchSystemExit(() -> {
      root.validate();
    });
    assertEquals(0, statusCode);

    LogEvent capturedLogEvent = logCaptor.getLogEvents().get(0);

    assertTrue(capturedLogEvent.getThrowable().get().getClass() == BtreeException.NodeReferenceException.class);
    assertTrue(capturedLogEvent.getThrowable().get().getMessage().contains("cyclic reference at Node(A) (level-order index 4)"));
  }
}