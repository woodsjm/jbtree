/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package btree.java;

import org.junit.Test;
import static org.junit.Assert.*;

public class BtreeTest {
    @Test public void testNodeConstructor() {
        Integer num = Integer.valueOf(76);
        Btree.Node intNode = new Btree.Node(num);
        assertEquals(num, intNode.val);
        assertNull(intNode.left);
        assertNull(intNode.right);

        String str = String.valueOf("ABC");
        Btree.Node strNode = new Btree.Node(str);
        assertEquals(str, strNode.val);
        assertNull(strNode.left);
        assertNull(strNode.right);

        Float flt = Float.valueOf(25.53f);
        Btree.Node fltNode = new Btree.Node(flt);
        assertEquals(flt, fltNode.val); 
        assertNull(fltNode.left);
        assertNull(fltNode.right);
    }

    @Test public void testEquals() {
        Integer num = Integer.valueOf(76);
        Btree.Node intNode = new Btree.Node(num);
        assertEquals(intNode, intNode);
        
        Btree.Node intNodeValCopy = new Btree.Node(num);
        System.out.println(intNode.equals(intNodeValCopy));
        // assertEquals(intNode, intNodeValCopy);

        // Btree.Node intNode2 = new Btree.Node(Integer.valueOf(43));
        // assertNotEquals(intNode, intNode2);

        // String str = String.valueOf("ABC");
        // Btree.Node strNode = new Btree.Node(str);
        // Float flt = Float.valueOf(25.53f);
        // Btree.Node fltNode = new Btree.Node(flt);
        // assertNotEquals(fltNode, strNode);

        // // with children
        // Btree.Node root = new Btree.Node(Integer.valueOf(24));
        // root.left = new Btree.Node(Integer.valueOf(35));
        // root.right = new Btree.Node(Integer.valueOf(56));
        // Btree.Node rootCopy = new Btree.Node(Integer.valueOf(24));
        // rootCopy.left = new Btree.Node(Integer.valueOf(35));
        // rootCopy.right = new Btree.Node(Integer.valueOf(56));
        // assertEquals(rootCopy, root);

        // Btree.Node root2 = new Btree.Node(String.valueOf("ABC"));
        // root2.left = new Btree.Node(String.valueOf("DEF"));
        // root2.right = new Btree.Node(Float.valueOf(56.72f));
        // Btree.Node root3 = new Btree.Node(String.valueOf("ABC"));
        // root3.left = new Btree.Node(String.valueOf("Not the same"));
        // root3.right = new Btree.Node(Float.valueOf(56.72f));
        // assertNotEquals(root3, root2);
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
