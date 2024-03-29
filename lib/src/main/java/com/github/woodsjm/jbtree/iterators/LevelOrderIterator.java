package com.github.woodsjm.jbtree.iterators;

import com.github.woodsjm.jbtree.Node;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LevelOrderIterator<T extends Comparable<T>> implements Iterator<Node<T>> {
  private ConcurrentLinkedQueue<Optional<Node<T>>> nextNodes = new ConcurrentLinkedQueue<>();

  public LevelOrderIterator(Node<T> root) {
    this.nextNodes.add(Optional.of(root));
  }

  public boolean hasNext() {
    return !this.nextNodes.isEmpty();
  }

  public Node<T> next() {
    if (!this.hasNext()) {
      throw new NoSuchElementException("no more nodes");
    }

    Optional<Node<T>> nextNode = this.nextNodes.poll();
    if (!nextNode.isPresent()) {
      return null;
    }

    if (nextNode.get().getLeft() == null) {
      this.nextNodes.add(Optional.empty());
    } else {
      this.nextNodes.add(Optional.of(nextNode.get().getLeft()));
    }

    if (nextNode.get().getRight() == null) {
      this.nextNodes.add(Optional.empty());
    } else {
      this.nextNodes.add(Optional.of(nextNode.get().getRight()));
    }

    return nextNode.get();
  }
}
