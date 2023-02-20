/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package btree.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.contrib.java.lang.system.ExpectedSystemExit.*;

import nl.altindag.log.LogCaptor;
import nl.altindag.log.model.LogEvent;
import org.junit.contrib.java.lang.system.Assertion;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.rules.ExpectedException;
import org.junit.Rule;
import org.junit.Test;

public class BtreeTest {
    final int REPETITIONS = 20;

    @Rule public final ExpectedSystemExit exit = ExpectedSystemExit.none();
    @Rule public final ExpectedException exceptionRule = ExpectedException.none();

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

    @Test public void throwsNodeValueExceptionOnEmptyNode() throws Exception {
        exceptionRule.expect(BtreeException.NodeValueException.class);
        exceptionRule.expectMessage("node value must be an Integer, a Float, or a String");
        Btree.Node root = new Btree.Node(Collections.emptyList());
    }

    @Test public void throwsNodeValueExceptionOnEmptyVal() throws Exception {
        exceptionRule.expect(BtreeException.NodeValueException.class);
        exceptionRule.expectMessage("node value must be an Integer, a Float, or a String");
        Btree.Node<Integer> root = new Btree.Node<>(1);
        root.setVal(new ArrayList<Integer>());
    }
    
    @Test public void testTreeEqualsWithIntegers() {
        Btree.Node<Integer> root1 = new Btree.Node<>(1);
        Btree.Node<Integer> root2 = new Btree.Node<>(1);
        assertFalse(root1.equals(null));
        assertFalse(root1.equals(Integer.valueOf(1)));
        assertFalse(root1.equals(new Btree.Node<>(2)));
        assertTrue(root1.equals(root2));
        assertTrue(root2.equals(root1));

        root1.setLeft(new Btree.Node<>(2));
        assertFalse(root1.equals(root2));
        assertFalse(root2.equals(root1));

        root2.setLeft(new Btree.Node<>(2));
        assertTrue(root1.equals(root2));
        assertTrue(root2.equals(root1));

        root1.setRight(new Btree.Node<>(3));
        assertFalse(root1.equals(root2));
        assertFalse(root2.equals(root1));

        root2.setRight(new Btree.Node<>(3));
        assertTrue(root1.equals(root2));
        assertTrue(root2.equals(root1));

        root1.getRight().setLeft(new Btree.Node<>(4));
        assertFalse(root1.equals(root2));
        assertFalse(root2.equals(root1));

        root2.getRight().setLeft(new Btree.Node<>(4));
        assertTrue(root1.equals(root2));
        assertTrue(root2.equals(root1));
    }

    @Test public void testTreeEqualsWithFloats() {
        Btree.Node<Float> root1 = new Btree.Node<>(1.50f);
        Btree.Node<Float> root2 = new Btree.Node<>(1.50f);
        assertFalse(root1.equals(null));
        assertFalse(root1.equals(Float.valueOf(1.50f)));
        assertFalse(root1.equals(new Btree.Node<>(2.50f)));
        assertTrue(root1.equals(root2));
        assertTrue(root2.equals(root1));

        root1.setLeft(new Btree.Node<>(2.50f));
        assertFalse(root1.equals(root2));
        assertFalse(root2.equals(root1));

        root2.setLeft(new Btree.Node<>(2.50f));
        assertTrue(root1.equals(root2));
        assertTrue(root2.equals(root1));

        root1.setRight(new Btree.Node<>(3.50f));
        assertFalse(root1.equals(root2));
        assertFalse(root2.equals(root1));

        root2.setRight(new Btree.Node<>(3.50f));
        assertTrue(root1.equals(root2));
        assertTrue(root2.equals(root1));

        root1.getRight().setLeft(new Btree.Node<>(4.50f));
        assertFalse(root1.equals(root2));
        assertFalse(root2.equals(root1));

        root2.getRight().setLeft(new Btree.Node<>(4.50f));
        assertTrue(root1.equals(root2));
        assertTrue(root2.equals(root1));
    }

    @Test public void testTreeEqualsWithLetters() {
        Btree.Node<String> root1 = new Btree.Node<>("A");
        Btree.Node<String> root2 = new Btree.Node<>("A");
        assertFalse(root1.equals(null));
        assertFalse(root1.equals("A"));
        assertFalse(root1.equals(new Btree.Node<>("B")));
        assertTrue(root1.equals(root2));
        assertTrue(root2.equals(root1));

        root1.setLeft(new Btree.Node<>("B"));
        assertFalse(root1.equals(root2));
        assertFalse(root2.equals(root1));

        root2.setLeft(new Btree.Node<>("B"));
        assertTrue(root1.equals(root2));
        assertTrue(root2.equals(root1));

        root1.setRight(new Btree.Node<>("C"));
        assertFalse(root1.equals(root2));
        assertFalse(root2.equals(root1));

        root2.setRight(new Btree.Node<>("C"));
        assertTrue(root1.equals(root2));
        assertTrue(root2.equals(root1));

        root1.getRight().setLeft(new Btree.Node<>("D"));
        assertFalse(root1.equals(root2));
        assertFalse(root2.equals(root1));

        root2.getRight().setLeft(new Btree.Node<>("D"));
        assertTrue(root1.equals(root2));
        assertTrue(root2.equals(root1));
    }

    @Test public void testTreeCloneWithNumbers() {
        for (int dummy = 0; dummy < REPETITIONS; dummy++) {
            int height = ThreadLocalRandom.current().nextInt(10);
            boolean isPerfect = ThreadLocalRandom.current().nextBoolean();
            boolean letters = false;

            Btree.Node<Integer> root = Btree.tree(height, isPerfect, letters);
            assertNotNull(root);
            Btree.Node<Integer> clone = root.deepCopy();
            assertEquals(clone.values(), root.values());
            assertTrue(root.equals(clone));
            assertTrue(clone.equals(root));
            assertEquals(clone.properties(), root.properties());
        }
    }

    @Test public void testTreeCloneWithLetters() {
        for (int dummy = 0; dummy < REPETITIONS; dummy++) {
            int height = ThreadLocalRandom.current().nextInt(10);
            boolean isPerfect = ThreadLocalRandom.current().nextBoolean();
            boolean letters = true;

            Btree.Node<String> root = Btree.tree(height, isPerfect, letters);
            assertNotNull(root);
            Btree.Node<String> clone = root.deepCopy();
            assertEquals(clone.values(), root.values());
            assertTrue(root.equals(clone));
            assertTrue(clone.equals(root));
            assertEquals(clone.properties(), root.properties());
        }
    }

    @Test public void testListRepresentation1() {
        LogCaptor logCaptor = LogCaptor.forClass(Btree.class);

        ArrayList<Integer> treeAsList = new ArrayList<Integer>();

        Btree.Node root = Btree.build(new ArrayList()); // []
        assertNull(root);

        treeAsList.addAll(Arrays.asList(1));
        root = Btree.build(treeAsList); // [1]
        assertNotNull(root);
        assertEquals(Integer.valueOf(1), root.getVal());
        assertNull(root.getLeft());
        assertNull(root.getRight());

        treeAsList.addAll(Arrays.asList(2));
        root = Btree.build(treeAsList); // [1, 2]
        assertNotNull(root);
        assertEquals(Integer.valueOf(1), root.getVal());
        assertNotNull(root.getLeft());
        assertEquals(Integer.valueOf(2), root.getLeft().getVal());
        assertNull(root.getRight());

        treeAsList.addAll(Arrays.asList(3));
        root = Btree.build(treeAsList); // [1, 2, 3]
        assertNotNull(root);
        assertEquals(Integer.valueOf(1), root.getVal());
        assertNotNull(root.getLeft());
        assertEquals(Integer.valueOf(2), root.getLeft().getVal());
        assertNotNull(root.getRight());
        assertEquals(Integer.valueOf(3), root.getRight().getVal());
        assertNull(root.getLeft().getLeft());
        assertNull(root.getLeft().getRight());
        assertNull(root.getRight().getLeft());
        assertNull(root.getRight().getRight());

        treeAsList.addAll(Arrays.asList(null, 4));
        root = Btree.build(treeAsList); // [1, 2, 3, null, 4]
        assertNotNull(root);
        assertEquals(Integer.valueOf(1), root.getVal());
        assertNotNull(root.getLeft());
        assertEquals(Integer.valueOf(2), root.getLeft().getVal());
        assertNotNull(root.getRight());
        assertEquals(Integer.valueOf(3), root.getRight().getVal());
        assertNull(root.getLeft().getLeft());
        assertNotNull(root.getLeft().getRight());
        assertEquals(Integer.valueOf(4), root.getLeft().getRight().getVal());
        assertNull(root.getRight().getLeft());
        assertNull(root.getRight().getRight());
        assertNull(root.getLeft().getRight().getLeft());
        assertNull(root.getLeft().getRight().getRight());

        treeAsList.clear();
        treeAsList.addAll(Arrays.asList(null, 2, 3));
        exit.expectSystemExitWithStatus(0);
        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() {
                String capturedLogs = String.valueOf(logCaptor.getLogs());
                LogEvent capturedLogEvent = logCaptor.getLogEvents().get(0);

                assertTrue(capturedLogs.contains("Likely a problem with the ArrayList argument"));
                assertTrue(capturedLogs.contains("Here's your stack trace..."));
                
                assertTrue(capturedLogEvent.getThrowable().get() instanceof BtreeException.NodeNotFoundException);
                assertTrue(capturedLogEvent.getThrowable().get().getMessage().contains("parent node missing at index 0"));
            }
        });
        root = Btree.build(treeAsList); // [null, 2, 3]

        logCaptor.clearLogs();

        treeAsList.clear();
        treeAsList.addAll(Arrays.asList(1, null, 2, 3, 4));
        exit.expectSystemExitWithStatus(0);
        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() {
                String capturedLogs = String.valueOf(logCaptor.getLogs());
                LogEvent capturedLogEvent = logCaptor.getLogEvents().get(0);

                assertTrue(capturedLogs.contains("Likely a problem with the ArrayList argument"));
                assertTrue(capturedLogs.contains("Here's your stack trace..."));

                assertTrue(capturedLogEvent.getThrowable().get() instanceof BtreeException.NodeNotFoundException);
                assertTrue(capturedLogEvent.getThrowable().get().getMessage().contains("parent node missing at index 1"));
            }
        });
        root = Btree.build(treeAsList); // [1, null, 2, 3, 4]

        treeAsList.clear();
        treeAsList.addAll(Arrays.asList(1));
        root = new Btree.Node(1); // [1]
        assertArrayEquals(treeAsList.toArray(), root.values().toArray());

        treeAsList.addAll(Arrays.asList(null, 3));
        root.setRight(new Btree.Node(3)); // [1, null, 3]
        assertArrayEquals(treeAsList.toArray(), root.values().toArray());

        treeAsList.set(1, 2);
        root.setLeft(new Btree.Node(2)); // [1, 2, 3]
        assertArrayEquals(treeAsList.toArray(), root.values().toArray());

        treeAsList.addAll(Arrays.asList(null, null, 4));
        root.getRight().setLeft(new Btree.Node(4)); // [1, 2, 3, null, null, 4]
        assertArrayEquals(treeAsList.toArray(), root.values().toArray());

        treeAsList.add(5);
        root.getRight().setRight(new Btree.Node(5)); // [1, 2, 3, null, null, 4, 5]
        assertArrayEquals(treeAsList.toArray(), root.values().toArray()); 

        treeAsList.set(3, 6);
        root.getLeft().setLeft(new Btree.Node(6)); // [1, 2, 3, 6, null, 4, 5]
        assertArrayEquals(treeAsList.toArray(), root.values().toArray());

        treeAsList.set(4, 7);
        root.getLeft().setRight(new Btree.Node(7)); // [1, 2, 3, 6, 7, 4, 5]
        assertArrayEquals(treeAsList.toArray(), root.values().toArray());

        for (int dummy = 0; dummy < REPETITIONS; dummy++) {
            Btree.Node<Integer> tree1 = Btree.tree();
            assertNotNull(tree1);

            Btree.Node<Integer> tree2 = Btree.build(tree1.values());
            assertNotNull(tree2);
            
            assertArrayEquals(tree2.values().toArray(), tree1.values().toArray());
        }
    }

    @Test public void testNodeToString() {
        assertEquals("Node(1)", new Btree.Node<>(1).toString());
        assertEquals("Node(1.50)", new Btree.Node<>(1.50f).toString());
        assertEquals("Node(A)", new Btree.Node<>("A").toString());
    }

    @Test 
    public void main() {
        Btree.main(new String[] {});
    }
}
