/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package btree.java;

import java.util.*;

import org.junit.rules.ExpectedException;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;

public class BtreeTest {
    @Test public void testNodeInitAndSetAttrWithIntegers() {
        Btree.Node root = new Btree.Node(Integer.valueOf(1));
        assertEquals(null, root.getLeft());
        assertEquals(null, root.getRight());
        assertEquals(Integer.valueOf(1), root.getVal());
        assertEquals(String.valueOf("Node(1)"), root.toString());

        //NOTE: Missing matching tests for multinamed node value
        //attribute node.val + node.value, because current attribute 
        //implementation lacks multiple names

        Btree.Node leftChild = new Btree.Node(Integer.valueOf(2));
        root.setLeft(leftChild);
        assertEquals(leftChild, root.getLeft());
        assertEquals(null, root.getRight());
        assertEquals(Integer.valueOf(1), root.getVal());
        assertEquals(null, root.getLeft().getLeft());
        assertEquals(null, root.getLeft().getRight());
        assertEquals(Integer.valueOf(2), root.getLeft().getVal());
        assertEquals(String.valueOf("Node(2)"), leftChild.toString());

        Btree.Node rightChild = new Btree.Node(Integer.valueOf(3));
        root.setRight(rightChild);
        assertEquals(leftChild, root.getLeft());
        assertEquals(rightChild, root.getRight());
        assertEquals(Integer.valueOf(1), root.getVal());
        assertEquals(null, root.getRight().getLeft());
        assertEquals(null, root.getRight().getRight());
        assertEquals(Integer.valueOf(3), root.getRight().getVal());
        assertEquals(String.valueOf("Node(3)"), rightChild.toString());

        Btree.Node lastNode = new Btree.Node(Integer.valueOf(4));
        leftChild.setRight(lastNode);
        assertEquals(lastNode, root.getLeft().getRight());
        assertEquals(String.valueOf("Node(4)"), root.getLeft().getRight().toString());
    }

    @Test public void testNodeInitAndSetAttrWithFloats() {
        Btree.Node root = new Btree.Node(Float.valueOf(1.50f));
        assertEquals(null, root.getLeft());
        assertEquals(null, root.getRight());
        assertEquals(Float.valueOf(1.50f), root.getVal());
        assertEquals(String.valueOf("Node(1.50)"), root.toString());

        //NOTE: Missing matching tests for multinamed node value
        //attribute node.val + node.value, because current attribute 
        //implementation lacks multiple names

        Btree.Node leftChild = new Btree.Node(Float.valueOf(2.50f));
        root.setLeft(leftChild);
        assertEquals(leftChild, root.getLeft());
        assertEquals(null, root.getRight());
        assertEquals(Float.valueOf(1.50f), root.getVal());
        assertEquals(null, root.getLeft().getLeft());
        assertEquals(null, root.getLeft().getRight());
        assertEquals(Float.valueOf(2.50f), root.getLeft().getVal());
        assertEquals(String.valueOf("Node(2.50)"), leftChild.toString());

        Btree.Node rightChild = new Btree.Node(Float.valueOf(3.50f));
        root.setRight(rightChild);
        assertEquals(leftChild, root.getLeft());
        assertEquals(rightChild, root.getRight());
        assertEquals(Float.valueOf(1.50f), root.getVal());
        assertEquals(null, root.getRight().getLeft());
        assertEquals(null, root.getRight().getRight());
        assertEquals(Float.valueOf(3.50f), root.getRight().getVal());
        assertEquals(String.valueOf("Node(3.50)"), rightChild.toString());

        Btree.Node lastNode = new Btree.Node(Float.valueOf(4.50f));
        leftChild.setRight(lastNode);
        assertEquals(lastNode, root.getLeft().getRight());
        assertEquals(String.valueOf("Node(4.50)"), root.getLeft().getRight().toString());
    }

    @Test public void testNodeInitAndSetAttrWithLetters() {
        Btree.Node root = new Btree.Node(String.valueOf("A"));
        assertEquals(null, root.getLeft());
        assertEquals(null, root.getRight());
        assertEquals(String.valueOf("A"), root.getVal());
        assertEquals(String.valueOf("Node(A)"), root.toString());

        //NOTE: Missing matching tests for multinamed node value
        //attribute node.val + node.value, because current attribute 
        //implementation lacks multiple names

        Btree.Node leftChild = new Btree.Node(String.valueOf("B"));
        root.setLeft(leftChild);
        assertEquals(leftChild, root.getLeft()); 
        assertEquals(null, root.getRight());
        assertEquals(String.valueOf("A"), root.getVal());
        assertEquals(null, root.getLeft().getLeft());
        assertEquals(null, root.getLeft().getRight());
        assertEquals(String.valueOf("B"), root.getLeft().getVal());
        assertEquals(String.valueOf("Node(B)"), leftChild.toString());

        Btree.Node rightChild = new Btree.Node(String.valueOf("C"));
        root.setRight(rightChild);
        assertEquals(leftChild, root.getLeft());
        assertEquals(rightChild, root.getRight());
        assertEquals(String.valueOf("A"), root.getVal());
        assertEquals(null, root.getRight().getLeft());
        assertEquals(null, root.getRight().getRight());
        assertEquals(String.valueOf("C"), root.getRight().getVal());
        assertEquals(String.valueOf("Node(C)"), rightChild.toString());

        Btree.Node lastNode = new Btree.Node(String.valueOf("D"));
        leftChild.setRight(lastNode);
        assertEquals(lastNode, root.getLeft().getRight());
        assertEquals(String.valueOf("Node(D)"), root.getLeft().getRight().toString());
    }

    // Test Node init and set attribtue error cases
    // NOTE: these matching exception tests are split out
    // instead of grouped into a single test as in the 
    // Python lib
    @Rule public ExpectedException exceptionRule = ExpectedException.none();
    @Test public void throwsNodeValueExceptionOnEmptyNode() throws Exception {
        exceptionRule.expect(BtreeException.NodeValueException.class);
        exceptionRule.expectMessage("node value must be an Integer, a Float, or a String");
        Btree.Node root = new Btree.Node(Collections.emptyList());
    }

    @Test public void throwsNodeValueExceptionOnEmptyVal() throws Exception {
        exceptionRule.expect(BtreeException.NodeValueException.class);
        exceptionRule.expectMessage("node value must be an Integer, a Float, or a String");
        Btree.Node root = new Btree.Node(Integer.valueOf(1));
        root.setVal(Collections.emptyList());
    }
    
    @Test public void testTreeEqualsWithIntegers() {
        Btree.Node root1 = new Btree.Node(Integer.valueOf(1));
        Btree.Node root2 = new Btree.Node(Integer.valueOf(1));
        assertFalse(root1.equals(null));
        assertFalse(root1.equals(Integer.valueOf(1)));
        assertFalse(root1.equals(new Btree.Node(Integer.valueOf(2))));
        assertTrue(root1.equals(root2));
        assertTrue(root2.equals(root1));

        root1.setLeft(new Btree.Node(Integer.valueOf(2)));
        assertFalse(root1.equals(root2));
        assertFalse(root2.equals(root1));

        root2.setLeft(new Btree.Node(Integer.valueOf(2)));
        assertTrue(root1.equals(root2));
        assertTrue(root2.equals(root1));

        root1.setRight(new Btree.Node(Integer.valueOf(3)));
        assertFalse(root1.equals(root2));
        assertFalse(root2.equals(root1));

        root2.setRight(new Btree.Node(Integer.valueOf(3)));
        assertTrue(root1.equals(root2));
        assertTrue(root2.equals(root1));

        root1.getRight().setLeft(new Btree.Node(Integer.valueOf(4)));
        assertFalse(root1.equals(root2));
        assertFalse(root2.equals(root1));

        root2.getRight().setLeft(new Btree.Node(Integer.valueOf(4)));
        assertTrue(root1.equals(root2));
        assertTrue(root2.equals(root1));
    }

    @Test public void testTreeEqualsWithFloats() {
        Btree.Node root1 = new Btree.Node(Float.valueOf(1.50f));
        Btree.Node root2 = new Btree.Node(Float.valueOf(1.50f));
        assertFalse(root1.equals(null));
        assertFalse(root1.equals(Float.valueOf(1.50f)));
        assertFalse(root1.equals(new Btree.Node(Float.valueOf(2.50f))));
        assertTrue(root1.equals(root2));
        assertTrue(root2.equals(root1));

        root1.setLeft(new Btree.Node(Float.valueOf(2.50f)));
        assertFalse(root1.equals(root2));
        assertFalse(root2.equals(root1));

        root2.setLeft(new Btree.Node(Float.valueOf(2.50f)));
        assertTrue(root1.equals(root2));
        assertTrue(root2.equals(root1));

        root1.setRight(new Btree.Node(Float.valueOf(3.50f)));
        assertFalse(root1.equals(root2));
        assertFalse(root2.equals(root1));

        root2.setRight(new Btree.Node(Float.valueOf(3.50f)));
        assertTrue(root1.equals(root2));
        assertTrue(root2.equals(root1));

        root1.getRight().setLeft(new Btree.Node(Float.valueOf(4.50f)));
        assertFalse(root1.equals(root2));
        assertFalse(root2.equals(root1));

        root2.getRight().setLeft(new Btree.Node(Float.valueOf(4.50f)));
        assertTrue(root1.equals(root2));
        assertTrue(root2.equals(root1));
    }

    @Test public void testTreeEqualsWithLetters() {
        Btree.Node root1 = new Btree.Node(String.valueOf("A"));
        Btree.Node root2 = new Btree.Node(String.valueOf("A"));
        assertFalse(root1.equals(null));
        assertFalse(root1.equals(String.valueOf("A")));
        assertFalse(root1.equals(new Btree.Node(String.valueOf("B"))));
        assertTrue(root1.equals(root2));
        assertTrue(root2.equals(root1));

        root1.setLeft(new Btree.Node(String.valueOf("B")));
        assertFalse(root1.equals(root2));
        assertFalse(root2.equals(root1));

        root2.setLeft(new Btree.Node(String.valueOf("B")));
        assertTrue(root1.equals(root2));
        assertTrue(root2.equals(root1));

        root1.setRight(new Btree.Node(String.valueOf("C")));
        assertFalse(root1.equals(root2));
        assertFalse(root2.equals(root1));

        root2.setRight(new Btree.Node(String.valueOf("C")));
        assertTrue(root1.equals(root2));
        assertTrue(root2.equals(root1));

        root1.getRight().setLeft(new Btree.Node(String.valueOf("D")));
        assertFalse(root1.equals(root2));
        assertFalse(root2.equals(root1));

        root2.getRight().setLeft(new Btree.Node(String.valueOf("D")));
        assertTrue(root1.equals(root2));
        assertTrue(root2.equals(root1));
    }

    @Test public void testTreeCloneWithNumbers() {
        for (int dummy = 0; dummy < 20; dummy++) {
            int height = 3;
            boolean isPerfect = true;
            boolean letters = false;

            Btree btree = new Btree();
            Btree.Node root = Btree.tree(height, isPerfect, letters);
            assertNotNull(root);
            Btree.Node clone = root.deepCopy();
            assertEquals(clone.values, root.values);
            assertTrue(root.equals(clone));
            assertTrue(clone.equals(root));
            assertEquals(clone.properties, root.properties);
        }
    }

    @Test public void testNodeToString() {
        Integer num = Integer.valueOf(76);
        Btree.Node intNode = new Btree.Node(num);
        String intNodeAsString = "Node(76)";
        assertEquals(intNodeAsString, intNode.toString());

        String str = String.valueOf("ABC");
        Btree.Node strNode = new Btree.Node(str);
        String strNodeAsString = "Node(ABC)";
        assertEquals(strNodeAsString, strNode.toString());
        
        Float flt = Float.valueOf(25.53f);
        Btree.Node fltNode = new Btree.Node(flt);
        String fltNodeAsString = "Node(25.53)";
        assertEquals(fltNodeAsString, fltNode.toString());
    }

    @Test 
    public void main() {
        Btree.main(new String[] {});
    }
}
