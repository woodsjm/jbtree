package com.github.woodsjm.jbtree;

import java.util.List;

public abstract class BtreeBuilder<T extends BtreeBuilder> {
  protected int height;
  protected boolean isPerfect;
  protected boolean letters;

  protected List<Comparable> values;

  public abstract Node create();

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
    this.letters = letters;
    return (T) this;
  }
}
