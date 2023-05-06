package com.github.woodsjm.jbtree.iterators;

import com.github.woodsjm.jbtree.Node;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedDeque;

public class PostOrderIterator<T extends Comparable<T>> implements Iterator<Node<T>> {
  private ConcurrentLinkedDeque<Optional<Node<T>>> nextNodes = new ConcurrentLinkedDeque();

  public PostOrderIterator(Node<T> root) {
    this.traverse(Optional.ofNullable(root));
  }

  private void traverse(Optional<Node<T>> current) {
    while (current.isPresent()) {
      this.nextNodes.addFirst(current);
      if (current.get().getLeft() != null) {
        current = Optional.of(current.get().getLeft());
      } else {
        current = Optional.ofNullable(current.get().getRight());
      }
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

    if (!this.nextNodes.isEmpty()) {
      if (nextNode.get() == this.nextNodes.peek().get().getLeft()) {
        this.traverse(Optional.ofNullable(this.nextNodes.peek().get().getRight()));
      }
    }

    return nextNode.get();
  }
}
