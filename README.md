# JBtree: A Java port of the Python Binarytree library

JBtree is a port of the Python [Binarytree](https://github.com/joowani/binarytree) library. Most of the functionality, except for planned graph vizualization support, has been ported in the latest alpha [release](https://github.com/woodsjm/jbtree/releases). 

Documentation will be wrapped into the **v0.5.0-beta** release that is added to Maven Central.

JBtree includes matching tests.




## Try it out

A replit using the latest release can be found [here](https://replit.com/@JBtree/JBtree-Playground?v=1).

Or drop into a new project where `Main.java` is and:
```shell
wget https://github.com/woodsjm/jbtree/releases/download/v0.1.5-alpha/jbtree-0.1.5.jar && mkdir jars/ && mv jbtree-0.1.5.jar jars/ 
```

and then in `Main.java`:
```java
import com.github.woodsjm.jbtree.*;
```

and finally:
```shell
javac -classpath ./jars/* -d . Main.java && java -classpath ./jars/* Main
```

## Getting Started

JBtree uses this class to represent a node:
```java

public class Node<T extends Comparable<T>> {
  private T val; // Node value (float/integer/string)
  private Node<T> left; // Left child
  private Node<T> right; // Right child

```

Create and print different types of binary trees:
```java
// Create a random binary Tree and return its root node
BtreeBuilder<TreeBuilder> treeBuilder = new TreeBuilder();
Node<Integer> defaultTree = treeBuilder.create();

// Create a random BST that is perfect and return its root node
BtreeBuilder<BSTBuilder> bstBuilder = new BSTBuilder();
Node<Integer> perfectBST = bstBuilder.setHeight(3).makePerfect().create();

// Create a random min Heap and return its root node
BtreeBuilder<HeapBuilder> heapBuilder = new HeapBuilder();
Node<Integer> heap = heapBuilder.setHeight(3).makeMin().create();


defaultTree.prettyPrint();
//
//           _____10______
//          /             \
//      ___2__           __4___
//     /      \         /      \
//    9        14      0       _7
//   / \      /       / \     /
//  1   11   3       8   6   13


perfectBST.prettyPrint();
//
//          ______7_______
//         /              \
//      __3__           ___11___
//     /     \         /        \
//    1       5       9         _13
//   / \     / \     / \       /   \
//  0   2   4   6   8   10    12    14


minHeap.prettyPrint();
//
//          _______0__
//         /          \
//      __1__          2
//     /     \        / \
//    3       4      5   6
//   / \     / \
//  7   8   9   10
```

Build a tree yourself:
```java

Node<Integer> root = new Node<>(1);
root.setLeft(new Node<>(2));
root.setRight(new Node<>(3));
root.getLeft().setRight(new Node<>(4));

System.out.println(root);
//
//    _1
//   /  \
//  2    3
//   \
//    4
```

Inspect tree properties:
```java

Node<Integer> root = new Node<>(1);
root.setLeft(new Node<>(2));
root.setRight(new Node<>(3));
root.getLeft().setLeft(new Node<>(4));
root.getLeft().setRight(new Node<>(5));

System.out.println(root);
//
//      _1
//     /  \
//    2    3
//   / \
//  4   5

assertEquals((Integer) 2, root.height());
assertEquals((boolean) true, root.isBalanced());
assertEquals((boolean) false, root.isBST());
assertEquals((boolean) true, root.isComplete());
assertEquals((boolean) false, root.isMaxHeap());
assertEquals((boolean) true, root.isMinHeap());
assertEquals((boolean) false, root.isPerfect());
assertEquals((boolean) true, root.isStrict());
assertEquals((boolean) false, root.isSymmetric());
assertEquals((Integer) 3, root.leafCount());
assertEquals((Integer) 2, root.maxLeafDepth());
assertEquals((Integer) 5, root.maxNodeValue());
assertEquals((Integer) 1, root.minLeafDepth());
assertEquals((Integer) 1, root.minNodeValue());
assertEquals((Integer) 5, root.size());
```





