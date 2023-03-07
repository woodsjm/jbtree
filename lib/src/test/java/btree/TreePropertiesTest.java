package btree.java;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nl.altindag.log.LogCaptor;
import nl.altindag.log.model.LogEvent;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class TreePropertiesTest {

  @Test
  void testTreeProperties() {
    Btree.Node root = new Btree.Node(1);
    HashMap<String, Object> actualProperties = root.properties();
    HashMap<String, Object> expectedProperties = new HashMap<>();
    expectedProperties.put("height", 0);
    expectedProperties.put("isBalanced", true);
    expectedProperties.put("isBST", true);
    expectedProperties.put("isComplete", true);
    expectedProperties.put("isMaxHeap", true);
    expectedProperties.put("isMinHeap", true);
    expectedProperties.put("isPerfect", true);
    expectedProperties.put("isStrict", true);
    expectedProperties.put("isSymmetric", true);
    expectedProperties.put("leafCount", 1);
    expectedProperties.put("maxLeafDepth", 0);
    expectedProperties.put("maxNodeValue", 1);
    expectedProperties.put("minLeafDepth", 0);
    expectedProperties.put("minNodeValue", 1);
    expectedProperties.put("size", 1);

    
    assertEquals(expectedProperties.size(), actualProperties.size());
    assertTrue(actualProperties.keySet().equals(expectedProperties.keySet()));
    expectedProperties.forEach((k, v) -> {
      if (v instanceof Integer) {
        assertEquals((Integer) v, (Integer) actualProperties.get(k));
      } else if (v instanceof Boolean) {
        assertEquals((Boolean) v, (Boolean) actualProperties.get(k));
      }
    });
    
    assertEquals(Integer.valueOf(0), root.height());
    assertTrue((boolean) root.isBalanced());
    assertTrue((boolean) root.isBST());
    assertTrue((boolean) root.isComplete());
    assertTrue((boolean) root.isMaxHeap());
    assertTrue((boolean) root.isMinHeap());
    assertTrue((boolean) root.isPerfect());
    assertTrue((boolean) root.isStrict());
    assertTrue((boolean) root.isSymmetric());
    assertEquals(Integer.valueOf(1), root.leafCount());
    assertEquals(Integer.valueOf(0), root.maxLeafDepth());
    assertEquals(Integer.valueOf(1), root.maxNodeValue());
    assertEquals(Integer.valueOf(0), root.minLeafDepth());
    assertEquals(Integer.valueOf(1), root.minNodeValue());
    assertEquals(Integer.valueOf(1), root.size());
  }
}