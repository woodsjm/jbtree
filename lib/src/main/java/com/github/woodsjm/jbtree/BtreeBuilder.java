package com.github.woodsjm.jbtree;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public abstract class BtreeBuilder<T extends BtreeBuilder> {
  protected int height;
  protected boolean isPerfect;
  protected boolean letters;

  public abstract Node create();

  public int generateRandomLeafCount() {
    int maxLeafCount = 1 << this.height;
    int halfLeafCount = Math.floorDiv(maxLeafCount, 2);

    int roll1 = ThreadLocalRandom.current().nextInt(halfLeafCount == 0 ? 1 : halfLeafCount);
    int roll2 = ThreadLocalRandom.current().nextInt(maxLeafCount - halfLeafCount);

    return roll1 + roll2 > 0 ? roll1 + roll2 : halfLeafCount;
  }

  public int[] generateRandomNumbers() {
    int maxNodeCount = (1 << (this.height + 1)) - 1;
    int[] nodeValues = IntStream.iterate(0, n -> n + 1).limit(maxNodeCount).toArray();

    // shuffle
    for (int idx = 0; idx < nodeValues.length; idx++) {
      int idxToSwap = ThreadLocalRandom.current().nextInt(nodeValues.length);
      int temp = nodeValues[idxToSwap];
      nodeValues[idxToSwap] = nodeValues[idx];
      nodeValues[idx] = temp;
    }

    return nodeValues;
  }

  public T makePerfect() {
    this.isPerfect = true;
    return (T) this;
  }

  public abstract void reset();

  public T setHeight(int height) {
    Btree.validateTreeHeight(height);
    this.height = height;
    return (T) this;
  }

  public T withLetters() {
    this.letters = true;
    return (T) this;
  }
}
