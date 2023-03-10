package btree.java;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;


public class TreeTraversalTest {

  static Node<Integer> node1;
  static Node<Integer> node2;
  static Node<Integer> node3;
  static Node<Integer> node4;
  static Node<Integer> node5;

  @Test
  void testTreeTraversalLevels() {
    node1 = new Node<>(1);

    checkLevels(node1.levels(), Arrays.asList( // [[node1]]
      Arrays.asList(node1))
    );

    node2 = new Node<>(2);
    node1.setLeft(node2);

    checkLevels(node1.levels(), Arrays.asList( // [[node1], [node2]]
      Arrays.asList(node1), 
      Arrays.asList(node2))
    );

    node3 = new Node<>(3);
    node1.setRight(node3);

    checkLevels(node1.levels(), Arrays.asList( // [[node1], [node2, node3]]
      Arrays.asList(node1), 
      Arrays.asList(node2, node3))
    );

    node4 = new Node<>(4);
    node5 = new Node<>(5);
    node1.getLeft().setLeft(node4);
    node1.getLeft().setRight(node5);

    checkLevels(node1.levels(), Arrays.asList( // [[node1], [node2, node3], [node4, node5]]
      Arrays.asList(node1), 
      Arrays.asList(node2, node3), 
      Arrays.asList(node4, node5))
    );
  }

  void checkLevels(List<List<Node<Integer>>> actualLevels, List<List<Node<Integer>>> expectedLevels) {
    assertEquals(expectedLevels.size(), actualLevels.size());

    ListIterator<List<Node<Integer>>> actualLevelsIter = actualLevels.listIterator();
    for (int i = 0; actualLevelsIter.hasNext(); i = 0 + actualLevelsIter.nextIndex()) {

      ListIterator<Node<Integer>> actualLevelIter = actualLevelsIter.next().listIterator();
      for (int j = 0; actualLevelIter.hasNext(); j = 0 + actualLevelIter.nextIndex()) {
        assertTrue(actualLevelIter.next().equals(expectedLevels.get(i).get(j)));
      }
    }
  }
}