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

    Btree.Node root = new Btree.Node(1);
    HashMap<String, Object> props = root.properties();
    assertTrue(props.keySet().equals(expectedProperties.keySet()));
    
    assertEquals(Integer.valueOf(0), props.get("height"));
    assertTrue((boolean) props.get("isBalanced"));
    assertTrue((boolean) props.get("isBST"));
    assertTrue((boolean) props.get("isComplete"));
    assertTrue((boolean) props.get("isMaxHeap"));
    assertTrue((boolean) props.get("isMinHeap"));
    assertTrue((boolean) props.get("isPerfect"));
    assertTrue((boolean) props.get("isStrict"));
    assertTrue((boolean) props.get("isSymmetric"));
    assertEquals(Integer.valueOf(1), props.get("leafCount"));
    assertEquals(Integer.valueOf(0), props.get("maxLeafDepth"));
    assertEquals(Integer.valueOf(1), props.get("maxNodeValue"));
    assertEquals(Integer.valueOf(0), props.get("minLeafDepth"));
    assertEquals(Integer.valueOf(1), props.get("minNodeValue"));
    assertEquals(Integer.valueOf(1), props.get("size"));
  }
}