package com.github.woodsjm.jbtree.iterators;

import com.github.woodsjm.jbtree.Node;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedDeque;

public class PreOrderIterator<T extends Comparable<T>> implements Iterator<Node<T>> {
  private ConcurrentLinkedDeque<Optional<Node<T>>> nextNodes = new ConcurrentLinkedDeque();

  public PreOrderIterator(Node<T> root) {
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

    if (nextNode.get().getRight() != null) {
      this.nextNodes.addFirst(Optional.of(nextNode.get().getRight()));
    }

    if (nextNode.get().getLeft() != null) {
      this.nextNodes.addFirst(Optional.of(nextNode.get().getLeft()));
    }

    return nextNode.get();
  }
}
