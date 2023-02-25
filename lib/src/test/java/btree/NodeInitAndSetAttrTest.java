package btree.java;

import java.util.ArrayList;
import java.util.List;

import nl.altindag.log.LogCaptor;
import nl.altindag.log.model.LogEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static com.github.stefanbirkner.systemlambda.SystemLambda.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NodeInitAndSetAttrTest {
  @Test public void testNodeInitAndSetAttrWithIntegers() {
    Btree.Node<Integer> root = new Btree.Node<>(1);
    assertNull(root.getLeft());
    assertNull(root.getRight());
    assertEquals(Integer.valueOf(1), root.getVal());
    assertEquals("Node(1)", root.toString());

    //NOTE: Missing matching tests for multinamed node value
    //attribute node.val + node.value, because current attribute 
    //implementation lacks multiple names

    Btree.Node<Integer> leftChild = new Btree.Node<>(2);
    root.setLeft(leftChild);
    assertEquals(leftChild, root.getLeft());
    assertNull(root.getRight());
    assertEquals(Integer.valueOf(1), root.getVal());
    assertNull(root.getLeft().getLeft());
    assertNull(root.getLeft().getRight());
    assertEquals(Integer.valueOf(2), root.getLeft().getVal());
    assertEquals("Node(2)", leftChild.toString());

    Btree.Node<Integer> rightChild = new Btree.Node<>(3);
    root.setRight(rightChild);
    assertEquals(leftChild, root.getLeft());
    assertEquals(rightChild, root.getRight());
    assertEquals(Integer.valueOf(1), root.getVal());
    assertNull(root.getRight().getLeft());
    assertNull(root.getRight().getRight());
    assertEquals(Integer.valueOf(3), root.getRight().getVal());
    assertEquals("Node(3)", rightChild.toString());

    Btree.Node<Integer> lastNode = new Btree.Node<>(4);
    leftChild.setRight(lastNode);
    assertEquals(lastNode, root.getLeft().getRight());
    assertEquals("Node(4)", root.getLeft().getRight().toString());
  }

  @Test public void testNodeInitAndSetAttrWithFloats() {
    Btree.Node<Float> root = new Btree.Node<>(1.50f);
    assertNull(root.getLeft());
    assertNull(root.getRight());
    assertEquals(Float.valueOf(1.50f), root.getVal());
    assertEquals("Node(1.50)", root.toString());

    //NOTE: Missing matching tests for multinamed node value
    //attribute node.val + node.value, because current attribute 
    //implementation lacks multiple names

    Btree.Node<Float> leftChild = new Btree.Node<>(2.50f);
    root.setLeft(leftChild);
    assertEquals(leftChild, root.getLeft());
    assertNull(root.getRight());
    assertEquals(Float.valueOf(1.50f), root.getVal());
    assertNull(root.getLeft().getLeft());
    assertNull(root.getLeft().getRight());
    assertEquals(Float.valueOf(2.50f), root.getLeft().getVal());
    assertEquals("Node(2.50)", leftChild.toString());

    Btree.Node<Float> rightChild = new Btree.Node<>(3.50f);
    root.setRight(rightChild);
    assertEquals(leftChild, root.getLeft());
    assertEquals(rightChild, root.getRight());
    assertEquals(Float.valueOf(1.50f), root.getVal());
    assertNull(root.getRight().getLeft());
    assertNull(root.getRight().getRight());
    assertEquals(Float.valueOf(3.50f), root.getRight().getVal());
    assertEquals("Node(3.50)", rightChild.toString());

    Btree.Node<Float> lastNode = new Btree.Node<>(4.50f);
    leftChild.setRight(lastNode);
    assertEquals(lastNode, root.getLeft().getRight());
    assertEquals("Node(4.50)", root.getLeft().getRight().toString());
  }

  @Test public void testNodeInitAndSetAttrWithLetters() {
    Btree.Node<String> root = new Btree.Node<>("A");
    assertNull(root.getLeft());
    assertNull(root.getRight());
    assertEquals("A", root.getVal());
    assertEquals("Node(A)", root.toString());

    //NOTE: Missing matching tests for multinamed node value
    //attribute node.val + node.value, because current attribute 
    //implementation lacks multiple names

    Btree.Node<String> leftChild = new Btree.Node<>("B");
    root.setLeft(leftChild);
    assertEquals(leftChild, root.getLeft()); 
    assertNull(root.getRight());
    assertEquals("A", root.getVal());
    assertNull(root.getLeft().getLeft());
    assertNull(root.getLeft().getRight());
    assertEquals("B", root.getLeft().getVal());
    assertEquals("Node(B)", leftChild.toString());

    Btree.Node<String> rightChild = new Btree.Node<>("C");
    root.setRight(rightChild);
    assertEquals(leftChild, root.getLeft());
    assertEquals(rightChild, root.getRight());
    assertEquals("A", root.getVal());
    assertNull(root.getRight().getLeft());
    assertNull(root.getRight().getRight());
    assertEquals("C", root.getRight().getVal());
    assertEquals("Node(C)", rightChild.toString());

    Btree.Node<String> lastNode = new Btree.Node<>("D");
    leftChild.setRight(lastNode);
    assertEquals(lastNode, root.getLeft().getRight());
    assertEquals("Node(D)", root.getLeft().getRight().toString());
  }

  @ParameterizedTest
  @NullAndEmptySource
  public void testNodeValueExceptionOnEmptyNode(List list) throws Exception {
    LogCaptor logCaptor = LogCaptor.forClass(Btree.class);

    int statusCode = catchSystemExit(() -> {
      Btree.Node<Integer> root = new Btree.Node<>(list);
    });
    assertEquals(0, statusCode);

    LogEvent capturedLogEvent = logCaptor.getLogEvents().get(0);

    assertTrue(capturedLogEvent.getThrowable().get() instanceof BtreeException.NodeValueException);
    assertTrue(capturedLogEvent.getThrowable().get().getMessage().contains("node value must be an Integer, a Float, or a String"));
  }

  @ParameterizedTest
  @NullAndEmptySource
  public void testNodeValueExceptionOnEmptyVal(List list) throws Exception {
    LogCaptor logCaptor = LogCaptor.forClass(Btree.class);

    Btree.Node<Integer> root = new Btree.Node<>(1);
   // List<Integer> emptyVal = new ArrayList<>();

    int statusCode = catchSystemExit(() -> {
      root.setVal(list);
    });
    assertEquals(0, statusCode);

    LogEvent capturedLogEvent = logCaptor.getLogEvents().get(0);

    assertTrue(capturedLogEvent.getThrowable().get() instanceof BtreeException.NodeValueException);
    assertTrue(capturedLogEvent.getThrowable().get().getMessage().contains("node value must be an Integer, a Float, or a String"));
  }
}