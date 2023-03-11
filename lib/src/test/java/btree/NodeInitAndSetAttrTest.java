package btree.java;

import static com.github.stefanbirkner.systemlambda.SystemLambda.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nl.altindag.log.LogCaptor;
import nl.altindag.log.model.LogEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;


public class NodeInitAndSetAttrTest {
  
    @Test
    public void testNodeInitAndSetAttrWithIntegers() {
        Node<Integer> root = new Node<>(1);
        assertNull(root.getLeft());
        assertNull(root.getRight());
        assertEquals(Integer.valueOf(1), root.getVal());
        assertEquals("Node(1)", root.getNodeRepresentation());

        //NOTE: Missing matching tests for multinamed node value
        //attribute node.val + node.value, because current attribute 
        //implementation lacks multiple names

        Node<Integer> leftChild = new Node<>(2);
        root.setLeft(leftChild);
        assertEquals(leftChild, root.getLeft());
        assertNull(root.getRight());
        assertEquals(Integer.valueOf(1), root.getVal());
        assertNull(root.getLeft().getLeft());
        assertNull(root.getLeft().getRight());
        assertEquals(Integer.valueOf(2), root.getLeft().getVal());
        assertEquals("Node(2)", leftChild.getNodeRepresentation());

        Node<Integer> rightChild = new Node<>(3);
        root.setRight(rightChild);
        assertEquals(leftChild, root.getLeft());
        assertEquals(rightChild, root.getRight());
        assertEquals(Integer.valueOf(1), root.getVal());
        assertNull(root.getRight().getLeft());
        assertNull(root.getRight().getRight());
        assertEquals(Integer.valueOf(3), root.getRight().getVal());
        assertEquals("Node(3)", rightChild.getNodeRepresentation());

        Node<Integer> lastNode = new Node<>(4);
        leftChild.setRight(lastNode);
        assertEquals(lastNode, root.getLeft().getRight());
        assertEquals("Node(4)", root.getLeft().getRight().getNodeRepresentation());
    }

    @Test
    public void testNodeInitAndSetAttrWithFloats() {
        Node<Float> root = new Node<>(1.50f);
        assertNull(root.getLeft());
        assertNull(root.getRight());
        assertEquals(Float.valueOf(1.50f), root.getVal());
        assertEquals("Node(1.50)", root.getNodeRepresentation());

        //NOTE: Missing matching tests for multinamed node value
        //attribute node.val + node.value, because current attribute 
        //implementation lacks multiple names

        Node<Float> leftChild = new Node<>(2.50f);
        root.setLeft(leftChild);
        assertEquals(leftChild, root.getLeft());
        assertNull(root.getRight());
        assertEquals(Float.valueOf(1.50f), root.getVal());
        assertNull(root.getLeft().getLeft());
        assertNull(root.getLeft().getRight());
        assertEquals(Float.valueOf(2.50f), root.getLeft().getVal());
        assertEquals("Node(2.50)", leftChild.getNodeRepresentation());

        Node<Float> rightChild = new Node<>(3.50f);
        root.setRight(rightChild);
        assertEquals(leftChild, root.getLeft());
        assertEquals(rightChild, root.getRight());
        assertEquals(Float.valueOf(1.50f), root.getVal());
        assertNull(root.getRight().getLeft());
        assertNull(root.getRight().getRight());
        assertEquals(Float.valueOf(3.50f), root.getRight().getVal());
        assertEquals("Node(3.50)", rightChild.getNodeRepresentation());

        Node<Float> lastNode = new Node<>(4.50f);
        leftChild.setRight(lastNode);
        assertEquals(lastNode, root.getLeft().getRight());
        assertEquals("Node(4.50)", root.getLeft().getRight().getNodeRepresentation());
    }

    @Test
    public void testNodeInitAndSetAttrWithLetters() {
        Node<String> root = new Node<>("A");
        assertNull(root.getLeft());
        assertNull(root.getRight());
        assertEquals("A", root.getVal());
        assertEquals("Node(A)", root.getNodeRepresentation());

        //NOTE: Missing matching tests for multinamed node value
        //attribute node.val + node.value, because current attribute 
        //implementation lacks multiple names

        Node<String> leftChild = new Node<>("B");
        root.setLeft(leftChild);
        assertEquals(leftChild, root.getLeft()); 
        assertNull(root.getRight());
        assertEquals("A", root.getVal());
        assertNull(root.getLeft().getLeft());
        assertNull(root.getLeft().getRight());
        assertEquals("B", root.getLeft().getVal());
        assertEquals("Node(B)", leftChild.getNodeRepresentation());

        Node<String> rightChild = new Node<>("C");
        root.setRight(rightChild);
        assertEquals(leftChild, root.getLeft());
        assertEquals(rightChild, root.getRight());
        assertEquals("A", root.getVal());
        assertNull(root.getRight().getLeft());
        assertNull(root.getRight().getRight());
        assertEquals("C", root.getRight().getVal());
        assertEquals("Node(C)", rightChild.getNodeRepresentation());

        Node<String> lastNode = new Node<>("D");
        leftChild.setRight(lastNode);
        assertEquals(lastNode, root.getLeft().getRight());
        assertEquals("Node(D)", root.getLeft().getRight().getNodeRepresentation());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void testNodeValueExceptionOnEmptyNode(List list) throws Exception {
        LogCaptor logCaptor = LogCaptor.forClass(Btree.class);

        int statusCode = catchSystemExit(() -> {
            Node<Integer> root = new Node<>(list);
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

        Node<Integer> root = new Node<>(1);
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