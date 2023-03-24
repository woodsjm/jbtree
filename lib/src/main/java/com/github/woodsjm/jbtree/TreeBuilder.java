package com.github.woodsjm.jbtree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class TreeBuilder extends BtreeBuilder<TreeBuilder> {

  public TreeBuilder() {
    super();
    this.reset();
  }

  public Node create() {

    List<Comparable> values = new ArrayList<>((1 << (this.height + 1)) - 1);

    int[] numbers = this.generateRandomNumbers();
    for (int num : numbers) {
      values.add(letters ? Btree.numberToLetters(num) : num);
    }

    if (this.isPerfect) {
      return Btree.build(values);
    }

    if (values.get(0) == null) {
      return null;
    }

    Node root = new Node(values.remove(0));
    HashSet<Node> leaves = new HashSet<>();
    int leafCount = this.generateRandomLeafCount();

    for (Comparable value : values) {
      Node node = root;
      int depth = 0;
      boolean inserted = false;

      while (depth < height && !inserted) {
        String direction =
            String.valueOf(ThreadLocalRandom.current().nextBoolean() ? "left" : "right");
        Node child = direction == "left" ? node.getLeft() : node.getRight();

        if (child == null) {
          if (direction == "left") {
            node.setLeft(new Node(value));
          } else if (direction == "right") {
            node.setRight(new Node(value));
          }
          inserted = true;
        }

        node = child;
        depth++;
      }

      if (inserted && depth == this.height) {
        leaves.add(node);
      }

      if (leaves.size() == leafCount) {
        break;
      }
    }

    return root;
  }

  public void reset() {
    this.height = 3;
    this.isPerfect = false;
    this.letters = false;
  }
}
