package com.github.woodsjm.jbtree;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class HeapBuilder extends BtreeBuilder<HeapBuilder> {
  private boolean isMax;

  public HeapBuilder() {
    super();
    this.reset();
  }

  public Node create() {
    Node result = null;

    int[] numbers = this.generateHeapNums();
    for (int num : numbers) {
      this.values.add(this.letters ? Btree.numberToLetters(num) : num);
    }

    if (!this.isPerfect) {
      if (this.height == 0) {
        result = Btree.build(this.values);
        this.reset();
        return result;
      }

      if (this.height == 1) {
        this.values.remove(this.values.size() - 1);
      } else {
        int randomCut = ThreadLocalRandom.current().nextInt((1 << this.height), this.values.size());
        this.values.subList(randomCut, this.values.size()).clear();
      }
    }

    result = Btree.build(this.values);
    this.reset();
    return result;
  }

  // FIX: Hack to be replaced by an actual heapify method
  private int[] generateHeapNums() {
    int maxNodeCount = (1 << (this.height + 1)) - 1;
    int[] numValues =
        IntStream.iterate(0, n -> Integer.valueOf(n + 1)).limit(maxNodeCount).toArray();

    if (this.isMax) {
      int mid = Math.floorDiv(numValues.length, 2);
      for (int i = 0; i <= mid; i++) {
        int tmp = numValues[i];
        numValues[i] = numValues[numValues.length - (i + 1)];
        numValues[numValues.length - (i + 1)] = tmp;
      }
    }

    return numValues;
  }

  public HeapBuilder makeMin() {
    this.isMax = false;
    return this;
  }

  public void reset() {
    this.height = 3;
    this.isPerfect = false;
    this.letters = false;
    this.isMax = true;
    this.values = new ArrayList<>((1 << (height + 1)) - 1);
  }
}
