/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package btree.java;

import org.junit.Test;
import static org.junit.Assert.*;

public class BtreeTest {
    @Test public void testConstructor() {
        Integer num = Integer.valueOf(76);
        Btree btree = new Btree(num);
        assertEquals(num, btree.nodeValue);
    }

    @Test 
    public void main() {
        Btree.main(new String[] {});
    }
}
