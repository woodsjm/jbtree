package btree.java;

import static com.github.stefanbirkner.systemlambda.SystemLambda.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nl.altindag.log.LogCaptor;
import nl.altindag.log.model.LogEvent;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ListRepresentation1Test {

  final int REPETITIONS = 20;

  @Test
  public void testListRepresentation1() {
    LogCaptor logCaptor = LogCaptor.forClass(Btree.class);

    List<Integer> treeAsList = new ArrayList<Integer>();

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

  @Test
  public void testListRepresentation1EmptyRoot() throws Exception {
    LogCaptor logCaptor = LogCaptor.forClass(Btree.class);

    int statusCode = catchSystemExit(() -> {
      Btree.Node root = Btree.build(Arrays.asList(null, 2, 3)); // [null, 2, 3]
    });
    assertEquals(0, statusCode);

    String capturedLogs = String.valueOf(logCaptor.getLogs());
    LogEvent capturedLogEvent = logCaptor.getLogEvents().get(0);

    assertTrue(capturedLogs.contains("Likely a problem with the ArrayList argument"));
    assertTrue(capturedLogs.contains("Here's your stack trace..."));

    assertTrue(capturedLogEvent.getThrowable().get() instanceof BtreeException.NodeNotFoundException);
    assertTrue(capturedLogEvent.getThrowable().get().getMessage().contains("parent node missing at index 0"));
  }

  @Test
  public void testListRepresentation1EmptyParent() throws Exception {
    LogCaptor logCaptor = LogCaptor.forClass(Btree.class);

    int statusCode = catchSystemExit(() -> {
      Btree.Node root = Btree.build(Arrays.asList(1, null, 2, 3, 4)); // [1, null, 2, 3, 4]
    });
    assertEquals(0, statusCode);

    String capturedLogs = String.valueOf(logCaptor.getLogs());
    LogEvent capturedLogEvent = logCaptor.getLogEvents().get(0);

    assertTrue(capturedLogs.contains("Likely a problem with the ArrayList argument"));
    assertTrue(capturedLogs.contains("Here's your stack trace..."));

    assertTrue(capturedLogEvent.getThrowable().get() instanceof BtreeException.NodeNotFoundException);
    assertTrue(capturedLogEvent.getThrowable().get().getMessage().contains("parent node missing at index 1"));
  }
}