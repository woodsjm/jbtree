# JBtree: A Java port of the Python Binarytree library

JBtree is a port of the Python [Binarytree](https://github.com/joowani/binarytree) library. Most of the functionality, except for planned graph vizualization support, has been ported in the latest alpha [release](https://github.com/woodsjm/jbtree/releases). 

Documentation will be wrapped into the **v0.5.0-beta** release that is added to Maven Central.

JBtree includes matching tests.

----

<br>

## Try it out

A replit using the latest release can be found [here](https://replit.com/@JBtree/JBtree-Playground?v=1).

Or drop into a new project where `Main.java` is and:
```shell
wget https://github.com/woodsjm/jbtree/releases/download/v0.1.2-alpha/jbtree-0.1.2.jar && mkdir jars/ && mv jbtree-0.1.2.jar jars/ 
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

Create and print a binary tree:

```java
// A random binary tree with a 
// height of 3, that is not perfect, and has letters
Node<String> root = tree(3, false, true);

// Standard print
System.out.println(root);

// Gets you:
  __A__
 /     \
B       C
 \     / \
  E   F   G

// Then change it up
root.getRight().getLeft().setVal("Z");

// And print with indices
root.prettyPrint(true);

// Gets you:
   _____0:A_____
  /             \
1:B           _2:C_
  \         /      \
  4:E     5:Z       6:G

```





