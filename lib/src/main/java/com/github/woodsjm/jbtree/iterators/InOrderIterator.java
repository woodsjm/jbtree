package com.github.woodsjm.jbtree.iterators;

import com.github.woodsjm.jbtree.Node;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedDeque;

public class InOrderIterator<T extends Comparable<T>> implements Iterator<Node<T>> {
  private ConcurrentLinkedDeque<Optional<Node<T>>> nextNodes = new ConcurrentLinkedDeque();

  public InOrderIterator(Node<T> root) {
    this.traverse(Optional.ofNullable(root));
  }

  private void traverse(Optional<Node<T>> current) {
    while (current.isPresent()) {
      this.nextNodes.addFirst(current);
      current = Optional.ofNullable(current.get().getLeft());
    }
  }

  public boolean hasNext() {
    return !this.nextNodes.isEmpty();
  }

  public Node<T> next() {
    if (!this.hasNext()) {
      throw new NoSuchElementException("no more nodes");
    }

    Optional<Node<T>> nextNode = this.nextNodes.poll();

    if (nextNode.isPresent()) {
      this.traverse(Optional.ofNullable(nextNode.get().getRight()));
    }

    return nextNode.get();
  }
}
