/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package btree.java;

import java.lang.Comparable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import java.util.Stack;


public class Btree {

    private static class NodeProperties {
        int height;
        int size;
        boolean isMaxHeap;
        boolean isMinHeap;
        boolean isPerfect;
        boolean isStrict;
        boolean isComplete;
        int leafCount;
        Object minNodeValue;
        Object maxNodeValue;
        int minLeafDepth;
        int maxLeafDepth;
    
        public NodeProperties() { }
    
        public NodeProperties(
            int height, 
            int size, 
            boolean isMaxHeap, 
            boolean isMinHeap, 
            boolean isPerfect, 
            boolean isStrict, 
            boolean isComplete,
            int leafCount,
            Object minNodeValue,
            Object maxNodeValue,
            int minLeafDepth,
            int maxLeafDepth) {
                this.height = height;
                this.size = size;
                this.isMaxHeap = isMaxHeap;
                this.isMinHeap = isMinHeap;
                this.isPerfect = isPerfect;
                this.isStrict = isStrict;
                this.isComplete = isComplete;
                this.leafCount = leafCount;
                
                if (minNodeValue instanceof Integer || minNodeValue instanceof Float || minNodeValue instanceof String) {
                    this.minNodeValue = minNodeValue;
                }
                
                if (maxNodeValue instanceof Integer || maxNodeValue instanceof Float || maxNodeValue instanceof String) {
                    this.maxNodeValue = maxNodeValue;
                }
                
                this.minLeafDepth = minLeafDepth;
                this.maxLeafDepth = maxLeafDepth;
        }

        private HashMap<String, Object> toHashMap() {
            HashMap<String, Object> propsHashMap = new HashMap<>();

            propsHashMap.put("height", this.height);
            propsHashMap.put("size", this.size);
            propsHashMap.put("isMaxHeap", this.isMaxHeap);
            propsHashMap.put("isMinHeap", this.isMinHeap);
            propsHashMap.put("isPerfect", this.isPerfect);
            propsHashMap.put("isStrict", this.isStrict);
            propsHashMap.put("isComplete", this.isComplete);
            propsHashMap.put("leafCount", this.leafCount);
            propsHashMap.put("minNodeValue", this.minNodeValue);
            propsHashMap.put("maxNodeValue", this.maxNodeValue);
            propsHashMap.put("minLeafDepth", this.minLeafDepth);
            propsHashMap.put("maxLeafDepth", this.maxLeafDepth);

            return propsHashMap;
        }
    }

    public static class Node<T extends Comparable<T>> {
        private T val;
        private Node left;
        private Node right;

        // Init
        public Node(T value) {
            try {
                if (value instanceof Integer || value instanceof Float || value instanceof String) {
                    this.val = value;
                } else {
                    throw new BtreeException.NodeValueException("node value must be an Integer, a Float, or a String");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } 
        }

        public Node(List list) throws BtreeException.NodeValueException {
            if (list.size() == 0) {
                throw new BtreeException.NodeValueException("node value must be an Integer, a Float, or a String");
            }
        }

        // Getters + Setters
        public T getVal() {
            return this.val;
        }

        public void setVal(T value) throws BtreeException.NodeValueException {
            if (value instanceof Integer || value instanceof Float || value instanceof String) {
                this.val = value;
            } else {
                throw new BtreeException.NodeValueException("node value must be an Integer, a Float, or a String");
            }
        }

        public void setVal(List<T> list) throws BtreeException.NodeValueException {
            throw new BtreeException.NodeValueException("node value must be an Integer, a Float, or a String");
        }

        public Node getLeft() {
            return this.left;
        }

        public Node getRight() {
            return this.right;
        }

        public void setLeft(Node node) {
            this.left = node;
        }

        public void setRight(Node node) {
            this.right = node;
        }

        public Node deepCopy() {
            Node clone = new Node(this.getVal());

            Stack<Node> stack1 = new Stack<>();
            stack1.push(this);
            Stack<Node> stack2 = new Stack<>();
            stack2.push(clone);
            
            while (stack1.size() > 0 || stack2.size() > 0) {
                Node node1 = stack1.pop();
                Node node2 = stack2.pop();

                if (node1.getLeft() != null) {
                    node2.setLeft(new Node(node1.getLeft().getVal()));
                    stack1.push(node1.getLeft());
                    stack2.push(node2.getLeft());
                }

                if (node1.getRight() != null) {
                    node2.setRight(new Node(node1.getRight().getVal()));
                    stack1.push(node1.getRight());
                    stack2.push(node2.getRight());
                }
            }

            return clone;
        }

        @Override
        public int hashCode() {
            return Integer.valueOf(this.getVal().toString()).intValue();
        }

        @Override
        public boolean equals(Object other) {
            if ( !(other instanceof Node) ) {
                return false;
            } else if (other == this) {
                return true;
            }
    
            Stack<Node<T>> stack1 = new Stack<>();
            stack1.push(this);
            Stack<Node<T>> stack2 = new Stack<>();
            stack2.push((Node<T>) other);

            while (stack1.size() > 0 || stack2.size() > 0) {
                Node<T> node1 = stack1.pop();
                Node<T> node2 = stack2.pop();

                if (node1 == null && node2 == null) {
                    continue;
                } else if (node1 == null || node2 == null) {
                    return false;
                } else if (!(other instanceof Node)) {
                    return false;
                } else {
                    if (node1.getVal() instanceof Float && node2.getVal() instanceof Float) {
                        String float1 = String.format("%.2f", node1.getVal());
                        String float2 = String.format("%.2f", node2.getVal());
                        if (Float.compare(Float.valueOf(float1), Float.valueOf(float2)) != 0) {
                            return false;
                        }
                    } else if (node1.getVal() != node2.getVal()) {
                        return false;
                    }
                }
                    
                stack1.push(node1.getRight());
                stack1.push(node1.getLeft());
                stack2.push(node2.getRight());
                stack2.push(node2.getLeft());
            }

            return true;
        }

        protected HashMap<String, Object> properties() {
            boolean isDescending = true;
            boolean isAscending = true;
            T minNodeValue = this.getVal();
            T maxNodeValue = this.getVal();
            int size = 0;
            int leafCount = 0;
            int minLeafDepth = 0;
            int maxLeafDepth = -1;
            boolean isStrict = true;
            boolean isComplete = true;
            ArrayList<Node> currentNodes = new ArrayList<>();
            currentNodes.add(this);
            boolean nonFullNodeSeen = false;

            while (currentNodes.size() > 0) {
                maxLeafDepth++;
                ArrayList<Node> nextNodes = new ArrayList<>();

                for (Node<T> node: currentNodes) {
                    size++;
                    T val = node.getVal();

                    if (val != null) {
                        minNodeValue = minNodeValue.compareTo(val) > 0 ? val : minNodeValue;
                        maxNodeValue = maxNodeValue.compareTo(val) < 0 ? val : maxNodeValue;
                    }
                    
                    // Node is a leaf
                    if (node.getLeft() == null && node.getRight() == null) {
                        if (minLeafDepth == 0) {
                            minLeafDepth = maxLeafDepth;
                        }

                        leafCount++;
                    }

                    if (node.getLeft() != null && node.getLeft().getVal() != null && val != null) {
                        // FIX: Check for mismatched NodeValue types (e.g. order comparision of letters and numbers)
                        if (node.getLeft().getVal().compareTo(val) > 0) {
                            isDescending = false;
                        } else if (node.getLeft().getVal().compareTo(val) < 0) {
                            isAscending = false;
                        }

                        nextNodes.add(node.getLeft());
                        isComplete = !nonFullNodeSeen;
                    } else {
                        nonFullNodeSeen = true;
                    }

                    if (node.getRight() != null && node.getRight().getVal() != null && val != null) {
                        if (node.getRight().getVal().compareTo(val) > 0) {
                            isDescending = false;
                        } else if (node.getRight().getVal().compareTo(val) < 0) {
                            isAscending = false;
                        }

                        nextNodes.add(node.getRight());
                        isComplete = !nonFullNodeSeen;
                    } else {
                        nonFullNodeSeen = true;
                    }

                    // If we see a node with only one child, it is not strict
                    if ((node.getLeft() == null) == (node.getRight() == null)) {
                        isStrict = false;
                    }
                }

                currentNodes = nextNodes;
            }

            // FIX: Add properties for checks of isBalanced, isBST, and isSymmetric
            return new NodeProperties(
                maxLeafDepth, // height
                size,                             
                isComplete && isDescending, // isMaxHeap
                isComplete && isAscending,  // isMinHeap
                leafCount == (1 << maxLeafDepth), //isPefect
                isStrict,
                isComplete, 
                leafCount, 
                minNodeValue,
                maxNodeValue,
                minLeafDepth,
                maxLeafDepth).toHashMap();
        }


        @Override
        public String toString() {
            if (this.val instanceof Float) {
                return String.format("Node(%.2f)", this.getVal());
            }

            return String.format("Node(%s)", this.getVal().toString());
        }

        protected ArrayList<T> values() {
            ArrayList<Node<T>> currentNodes = new ArrayList<>();
            currentNodes.add(this);
            ArrayList<T> nodeValues = new ArrayList<>();

            boolean areNodesLeft = true;
            while (areNodesLeft) {
                areNodesLeft = false;
                ArrayList<Node<T>> nextNodes = new ArrayList<>();

                for (Node<T> node: currentNodes) {
                    if (node == null) {
                        nodeValues.add(null);
                        nextNodes.add(null);
                        nextNodes.add(null);
                    } else {
                        if (node.getLeft() != null || node.getRight() != null) {
                            areNodesLeft = true;
                        }

                        nodeValues.add(node.getVal());
                        nextNodes.add(node.getLeft());
                        nextNodes.add(node.getRight());
                    }
                }

                currentNodes = nextNodes;
            }

            while (!nodeValues.isEmpty() && nodeValues.get(nodeValues.size() - 1) == null) {
                nodeValues.remove(nodeValues.size() - 1);
            }

            return nodeValues;
        }
    }

    public static Node tree() {
        return tree(3, false, false);
    }

    public static Node tree(int height, boolean isPerfect, boolean letters) {
        validateTreeHeight(height);
        int[] numbers = generateRandomNumbers(height);

        ArrayList<Comparable> values = new ArrayList<>();
        for (int num: numbers) {
            values.add(letters ? numberToLetters(num) : num);
        }

        if (isPerfect) {
            return build(values);
        }

        int leafCount = generateRandomLeafCount(height);
        Node root;
        if (values.get(0) != null) {
            root = new Node(values.get(0));
        } else {
            return null;
        }
        
        HashSet<Node> leaves = new HashSet<>();

        for (Comparable value: values) {
            Node node = root;
            int depth = 0;
            boolean inserted = false;

            while (depth < height && !inserted) {
                String direction = String.valueOf(ThreadLocalRandom.current().nextBoolean() ? "left" : "right");
                Node child = direction == "left" ? node.getLeft() : node.getRight();

                if (child == null) {
                    if (direction == "left") {
                        root.setLeft(new Node(value));
                    } else if (direction == "right") {
                        root.setRight(new Node(value));
                    }
                    inserted = true;
                }

                node = child;
                depth++;
            }

            if (inserted && depth == height) {
                leaves.add(node);
            }

            if (leaves.size() == leafCount) {
                break;
            }
        }

        return root;
    }

    protected static <T extends Comparable<T>> Node build(ArrayList<T> values) {
        ArrayList<Node> nodes = new ArrayList<>();
        values.forEach(val -> nodes.add(val == null ? null : new Node(val)));

        for (int idx = 1; idx < nodes.size(); idx++) {
            if (!( nodes.get(idx) == null) ) {
                int parentIdx = Math.floorDiv(idx - 1, 2);
                Node parent = nodes.get(parentIdx);

                try {
                    if (parent == null) {
                        throw new BtreeException.NodeNotFoundException("parent node missing at index " + parentIdx);
                    }
                } catch (Exception e) {
                    final String message = "Likely a problem with the ArrayList argument\n\n"
                        + "Example 1: [null, 2, 4]\n"
                        + "Fix: [1, 2, 4]\n\n"
                        + "Example 2: [1, null, 4, null, 8]\n"
                        + "Fix: [1, 2, 4, null, 8]\n\n"
                        + "Here's your stack trace...";
                    Logger.getLogger(Btree.class.getName()).log(Level.WARNING, message, e);
                    System.exit(0);
                } 
                
                if (idx % 2 == 0) {
                    parent.setRight(nodes.get(idx));
                } else {
                    parent.setLeft(nodes.get(idx));
                }
            }
        }

        return nodes.isEmpty() ? null : nodes.get(0);
    }

    private static int generateRandomLeafCount(int height) {
        int maxLeafCount = 1 << height;
        int halfLeafCount = Math.floorDiv(maxLeafCount, 2);

        int roll1 = ThreadLocalRandom.current().nextInt(halfLeafCount == 0 ? 1 : halfLeafCount);
        int roll2 = ThreadLocalRandom.current().nextInt(maxLeafCount - halfLeafCount);

        return roll1 + roll2 > 0 ? roll1 + roll2 : halfLeafCount;
    }

    private static int[] generateRandomNumbers(int height) {
		int maxNodeCount = (1 << (height + 1)) - 1;
		int[] nodeValues = IntStream.iterate(0, n -> n + 1).limit(maxNodeCount).toArray();

		for (int idx = 0; idx < nodeValues.length; idx++) {
			int idxToSwap = ThreadLocalRandom.current().nextInt(nodeValues.length);
			int temp = nodeValues[idxToSwap];
			nodeValues[idxToSwap] = nodeValues[idx];
			nodeValues[idx] = temp;
		}
		
	    return nodeValues;
	}

    private static String numberToLetters(int number) {
        BigDecimal bigNum = new BigDecimal(number);
        BigDecimal[] divMod = bigNum.divideAndRemainder(new BigDecimal(26));

        int quotient = divMod[0].intValue();
        int remainder = divMod[1].intValue();

        String prefix = new String(new char[quotient]).replace("\0", "Z");
        return prefix + Character.toString(65 + remainder);
    }

    private static void validateTreeHeight(int height)  {
        try {
            if (height < 0 || height > 9) {
                throw new BtreeException.TreeHeightException("height must be an int between 0 - 9");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Btree() {}

    public static void main(String... args) {
        final String message = "\n"
            + "Btree-java is a port of the (Python) Binarytree library.\n"
            + "It has most of Binarytree's functionality, but lacks support for Graphviz and Jupyter Notebooks.\n"
            + "Btree-java includes matching unit tests.\n";
        
        System.out.println(message);
    }
}


