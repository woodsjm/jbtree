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

## Example

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





