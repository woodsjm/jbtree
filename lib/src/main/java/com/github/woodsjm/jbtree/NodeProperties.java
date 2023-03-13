package com.github.woodsjm.jbtree;

import java.lang.Comparable;
import java.lang.Math;
import java.util.HashMap;
import java.util.Stack;

public class NodeProperties<T extends Comparable<T>> {
    int height;
    int size;
    boolean isMaxHeap;
    boolean isMinHeap;
    boolean isPerfect;
    boolean isStrict;
    boolean isComplete;
    int leafCount;
    T minNodeValue;
    T maxNodeValue;
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
        T minNodeValue,
        T maxNodeValue,
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

    protected HashMap<String, Object> toHashMap() {
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

    protected final int determineIfBalanced(Node root) {
        if (root == null) {
            return 0;
        }

        int left = determineIfBalanced(root.getLeft());
        
        if (left < 0) {
            return -1;
        }

        int right = determineIfBalanced(root.getRight());

        if (right < 0) {
            return -1;
        }

        return Math.abs(left - right) > 1 ? -1 : Math.max(left, right) + 1;
    }

    protected final boolean determineIfBST(Node<T> root) {
        Stack<Node<T>> stack = new Stack<>();
        Node<T> current = root;
        Node<T> previous = null;

        while (!stack.isEmpty() || current != null) {
            if (current != null) {
                stack.add(current);
                current = current.getLeft() == null ? null : current.getLeft();
            } else {
                Node<T> node = stack.pop();

                if (previous != null && node.getVal().compareTo(previous.getVal()) <= 0) {
                    return false;
                }

                previous = node;
                current = node.getRight() == null ? null : node.getRight();
            }
        }

        return true;
    }

    protected final boolean determineIfSymmetric(Node<T> root) {
        return symmetricHelper(root, root);
    }

    protected final boolean symmetricHelper(Node<T> left, Node<T> right) {
        if (left == null && right == null) {
            return true;
        }

        if (left == null || right == null) {
            return false;
        }

        boolean areValsEqual = left.getVal().compareTo(right.getVal()) == 0;
        Node<T> lLeftChild = left.getLeft() == null ? null : left.getLeft();
        Node<T> lRightChild = left.getRight() == null ? null : left.getRight();
        Node<T> rLeftChild = right.getLeft() == null ? null : right.getLeft();
        Node<T> rRightChild = right.getRight() == null ? null : right.getRight();

        return areValsEqual && symmetricHelper(lLeftChild, rRightChild) && symmetricHelper(lRightChild, rLeftChild);
    }
}