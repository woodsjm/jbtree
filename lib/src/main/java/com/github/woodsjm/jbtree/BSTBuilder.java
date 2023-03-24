package com.github.woodsjm.jbtree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;

public class BSTBuilder extends BtreeBuilder<BSTBuilder> {

  public BSTBuilder() {
    super();
    this.reset();
  }

  public Node create() {

    if (this.isPerfect) {
      return this.generatePerfectBST();
    }

    List<Comparable> values = new ArrayList<>((1 << (this.height + 1)) - 1);

    int[] numbers = this.generateRandomNumbers();
    for (int num : numbers) {
      values.add(letters ? Btree.numberToLetters(num) : num);
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

      while (depth < this.height && !inserted) {
        String direction = String.valueOf(node.getVal().compareTo(value) > 0 ? "left" : "right");
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

  private Node generatePerfectBST() {
    int maxNodeCount = (1 << (this.height + 1)) - 1;
    int[] numValues =
        IntStream.iterate(0, n -> Integer.valueOf(n + 1)).limit(maxNodeCount).toArray();

    String[] letterValues = new String[maxNodeCount];
    if (this.letters) {
      for (int num : numValues) {
        letterValues[num] = Btree.numberToLetters(num);
      }
      return buildBSTFromSortedValues(letterValues);
    }

    return buildBSTFromSortedValues(numValues);
  }

  // NOTE: Refactor into individual buildBST...
  private Node buildBSTFromSortedValues(int[] sortedValues) {
    if (sortedValues.length == 0) {
      return null;
    }

    int mid = Math.floorDiv(sortedValues.length, 2);
    Node root = new Node(sortedValues[mid]);
    root.setLeft(buildBSTFromSortedValues(Arrays.copyOfRange(sortedValues, 0, mid)));
    root.setRight(
        buildBSTFromSortedValues(Arrays.copyOfRange(sortedValues, mid + 1, sortedValues.length)));
    return root;
  }

  // NOTE: Refactor into individual buildBST...
  private Node buildBSTFromSortedValues(String[] sortedValues) {
    if (sortedValues.length == 0) {
      return null;
    }

    int mid = Math.floorDiv(sortedValues.length, 2);
    Node root = new Node(sortedValues[mid]);
    root.setLeft(buildBSTFromSortedValues(Arrays.copyOfRange(sortedValues, 0, mid)));
    root.setRight(
        buildBSTFromSortedValues(Arrays.copyOfRange(sortedValues, mid + 1, sortedValues.length)));
    return root;
  }

  public void reset() {
    this.height = 3;
    this.isPerfect = false;
    this.letters = false;
  }
}
