package btree.java;

import java.util.ArrayDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;


// only bfs for now
public class BtreeIterator<T extends Comparable<T>> implements Iterator<Node<T>> {
  private ConcurrentLinkedQueue<Optional<Node<T>>> nextNodes = new ConcurrentLinkedQueue<>();

  public BtreeIterator(Node<T> root) {
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