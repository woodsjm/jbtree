package btree.java;

import java.lang.Comparable;
import java.lang.Math;
import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;
import java.util.IdentityHashMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.stream.IntStream;


public class Node<T extends Comparable<T>> {
		private T val;
		private Node<T> left;
		private Node<T> right;

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

		public Node(List list) {
				try {
						if (true) {
								throw new BtreeException.NodeValueException("node value must be an Integer, a Float, or a String");
						}
				} catch (Exception e) {
						Logger.getLogger(Btree.class.getName()).log(Level.SEVERE, "", e);
						System.exit(0);
				}
		}

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

		public void setVal(List<T> list) {
				try {
						if (true) {
								throw new BtreeException.NodeValueException("node value must be an Integer, a Float, or a String");
						}
				} catch (Exception e) {
						Logger.getLogger(Btree.class.getName()).log(Level.SEVERE, "", e);
						System.exit(0);
				}
		}

		public Node<T> get(int index) {
				Node<T> searchResult = null;

				try {
						if (index < 0) {
								throw new BtreeException.NodeIndexException("node index must be a non-negative int");
						}

						searchResult = this.bfs(index);

						if (searchResult == null) {
								throw new BtreeException.NodeNotFoundException("node missing at index " + index);
						}
				} catch (Exception e) {
						Logger.getLogger(Btree.class.getName()).log(Level.SEVERE, "", e);
						System.exit(0);
				}

				return searchResult;
		}

		public void set(int index, Node<T> node) {
				try {
						if (index == 0) {
								throw new BtreeException.NodeModifyException("cannot modify the root node");
						}

						if (index < 0) {
								throw new BtreeException.NodeIndexException("node index must be a non-negative int");
						}

						int parentIdx = Math.floorDiv(index - 1, 2);
						Node<T> parent = this.bfs(parentIdx);

						if (parent == null) {
								throw new BtreeException.NodeNotFoundException("parent node missing at index " + parentIdx);
						}

						if (index % 2 == 0) {
								parent.setRight(node);
						} else {
								parent.setLeft(node);
						}

				} catch (Exception e) {
						Logger.getLogger(Btree.class.getName()).log(Level.SEVERE, "", e);
						System.exit(0);
				}
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

		public void remove(int index) {
				try {
						if (index == 0) {
								throw new BtreeException.NodeModifyException("cannot remove the root node");
						}

						if (index < 0) {
								throw new BtreeException.NodeIndexException("node index must be a non-negative int");
						}

						int parentIdx = Math.floorDiv(index - 1, 2);
						Node<T> parent = this.bfs(parentIdx);

						Node<T> nodeToRemove = null;
						if (parent != null) {
								nodeToRemove = index % 2 == 0 ? parent.getRight() : parent.getLeft();
						}

						if (nodeToRemove == null) {
								throw new BtreeException.NodeNotFoundException("no node to remove at index " + index);
						}

						if (index % 2 == 0) {
								parent.setRight(null);
						} else {
								parent.setLeft(null);
						}

				} catch (Exception e) {
						Logger.getLogger(Btree.class.getName()).log(Level.SEVERE, "", e);
						System.exit(0);
				}
		}

		private Node<T> bfs(int index) {
				Iterator<Node<T>> nodes = this.iterator();
				for (int iterationCount = 0; nodes.hasNext(); iterationCount++) {
						if (iterationCount == index) {
								return nodes.next();
						}
						nodes.next();
				}

				return null;
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

		//FIX: computed value should include computed values of left and right Nodes
		@Override
		public int hashCode() {
				return this.values().toString().hashCode();
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
						} else if (node1.getVal().compareTo(node2.getVal()) != 0) {
								return false;
						}
								
						stack1.push(node1.getRight());
						stack1.push(node1.getLeft());
						stack2.push(node2.getRight());
						stack2.push(node2.getLeft());
				}

				return true;
		}

		public Iterator<Node<T>> iterator() {
				return new BtreeIterator(this);
		}

		protected HashMap<String, Object> properties() {
				HashMap<String, Object> result = new HashMap<>();

				NodeProperties<T> props = this.getTreeProperties();
				HashMap<String, Object> propsMap = props.toHashMap(); 
				propsMap.forEach((k, v) -> result.put(k, v));

				result.put("isBalanced", props.determineIfBalanced(this) >= 0);
				result.put("isBST", props.determineIfBST(this));
				result.put("isSymmetric", props.determineIfSymmetric(this));

				return result;
		}

		public int height() {
				return this.getTreeProperties().height;
		}

		public int leafCount() {
				return this.getTreeProperties().leafCount;
		}

		public boolean isBalanced() {
				return new NodeProperties().determineIfBalanced(this) >= 0;
		}

		public boolean isBST() {
				return new NodeProperties().determineIfBST(this);
		}

		public boolean isSymmetric() {
				return new NodeProperties().determineIfSymmetric(this);
		}

		public boolean isMaxHeap() {
				return this.getTreeProperties().isMaxHeap;
		}

		public boolean isMinHeap() {
				return this.getTreeProperties().isMinHeap;
		}

		public boolean isPerfect() {
				return this.getTreeProperties().isPerfect;
		}

		public boolean isStrict() {
				return this.getTreeProperties().isStrict;
		}

		public boolean isComplete() {
				return this.getTreeProperties().isComplete;
		}

		public T minNodeValue() {
				return (T) this.getTreeProperties().minNodeValue;
		}

		public T maxNodeValue() {
				return (T) this.getTreeProperties().maxNodeValue;
		}

		public int maxLeafDepth() {
				return this.getTreeProperties().maxLeafDepth;
		}

		public int minLeafDepth() {
				return this.getTreeProperties().minLeafDepth;
		}

		protected NodeProperties getTreeProperties() {
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
				List<Node> currentNodes = new ArrayList<>();
				currentNodes.add(this);
				boolean nonFullNodeSeen = false;

				while (currentNodes.size() > 0) {
						maxLeafDepth++;
						List<Node> nextNodes = new ArrayList<>();

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
								if (node.getLeft() == null  && node.getRight() != null) {
										isStrict = false;
								} else if (node.getLeft() != null  && node.getRight() == null) {
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
						leafCount == (1 << maxLeafDepth), //isPerfect
						isStrict,
						isComplete, 
						leafCount, 
						minNodeValue,
						maxNodeValue,
						minLeafDepth,
						maxLeafDepth);
		}

		public int size() {
				Iterator<Node<T>> nodes = this.iterator();

				int nodeCount = 0;
				while(nodes.hasNext()) {
						if (nodes.next() != null) {
								nodeCount++;
						}
				}

				return nodeCount;
		}

		public String getNodeRepresentation() {
				if (this.val instanceof Float) {
						return String.format("Node(%.2f)", this.val);
				}

				//FIX: possible issue with localization using %s on number
				return String.format("Node(%s)", this.val);
		}

		//NOTE: refactor line stripping into BtreeCharacterBox and
		//add add new lines above and below returned box of lines
		@Override
		public String toString() {
				BtreeCharacterBox lines = this.buildTreeString(this, 0, false, "-");

				for (int i = 0; i < lines.box.size(); i++) {
						lines.box.set(i, lines.box.get(i).replaceAll("\\s+$", ""));
				}
				lines.box.remove(lines.box.size() - 1);

				return String.join("\n", lines.box);
		}

		public void prettyPrint() {
				this.prettyPrint(false, "-");
		}

		public void prettyPrint(boolean index) {
				this.prettyPrint(index, "-");
		}

		public void prettyPrint(String delimiter) {
				this.prettyPrint(false, delimiter);
		}

		//NOTE: refactor line stripping into BtreeCharacterBox and
		//add add new lines above and below returned box of lines
		public void prettyPrint(boolean index, String delimiter) {
				BtreeCharacterBox lines = this.buildTreeString(this, 0, index, delimiter);

				for (int i = 0; i < lines.box.size(); i++) {
						lines.box.set(i, lines.box.get(i).replaceAll("\\s+$", ""));
				}
				lines.box.remove(lines.box.size() - 1);

				System.out.print(String.join("\n", lines.box));
		}

		private BtreeCharacterBox buildTreeString(Node<T> root, int currentIdx, boolean includeIdx, String delimiter) {
				if (root == null) {
						return new BtreeCharacterBox();
				}

				List<String> line1 = new ArrayList<String>();
				List<String> line2 = new ArrayList<String>();
				String nodeRepr = "";
				if (root.getVal() instanceof Float) {
						nodeRepr = includeIdx ? String.format("%s%s%.2f", currentIdx, delimiter, root.getVal())
										: String.format("%.2f", root.getVal());
				} else {
						nodeRepr = includeIdx ? String.format("%s%s%s", currentIdx, delimiter, root.getVal())
										: String.valueOf(root.getVal());
				}

				int newRootWidth = nodeRepr.length();
				int gapSize = nodeRepr.length();
				int newRootStart;

				BtreeCharacterBox l = this.buildTreeString(root.getLeft(), 2 * currentIdx + 1, includeIdx, delimiter);
				BtreeCharacterBox r = this.buildTreeString(root.getRight(), 2 * currentIdx + 2, includeIdx, delimiter);

				if (l.width > 0) {
						int lRoot = Math.floorDiv((l.start + l.end), 2) + 1;
						line1.add(String.valueOf(new char[lRoot + 1]).replace("\0", " "));
						line1.add(String.valueOf(new char[l.width - lRoot]).replace("\0", "_"));
						line2.add(String.valueOf(new char[lRoot]).replace("\0", " ") + "/");
						line2.add(String.valueOf(new char[l.width - lRoot]).replace("\0", " "));
						newRootStart = l.width + 1;
						gapSize++;
				} else {
						newRootStart = 0;
				}
						
				line1.add(nodeRepr);
				line2.add(String.valueOf(new char[newRootWidth]).replace("\0", " "));
				

				if (r.width > 0) {
						int rRoot = Math.floorDiv((r.start + r.end), 2);
						line1.add(String.valueOf(new char[rRoot]).replace("\0", "_"));
						line1.add(String.valueOf(new char[r.width - rRoot + 1]).replace("\0", " "));
						line2.add(String.valueOf(new char[rRoot]).replace("\0", " ") + "\\");
						line2.add(String.valueOf(new char[r.width - rRoot]).replace("\0", " "));
						gapSize++;
				}

				int newRootEnd = newRootStart + newRootWidth - 1;

				String gap = String.valueOf(new char[gapSize]).replace("\0", " ");
				List<String> newBox = new ArrayList<>();
				newBox.add(String.join("", line1));
				newBox.add(String.join("", line2));

				for (int i = 0; i < Math.max(l.box == null ? 0 : l.box.size(), r.box == null ? 0 : r.box.size()); i++) {			
						String lLine = "";																							String rLine = "";																				
						if (l.box != null) {
								lLine = i < l.box.size() ? l.box.get(i) : String.valueOf(new char[l.width]).replace("\0", " ");
						}
						if (r.box != null) {
								rLine = i < r.box.size() ? r.box.get(i) : String.valueOf(new char[r.width]).replace("\0", " ");
						}
						
						newBox.add(lLine + gap + rLine);
				} 

				return new BtreeCharacterBox(newBox, newBox.get(0).length(), newRootStart, newRootEnd);
		}

		public void validate() {
				boolean hasMoreNodes = true;
				Set<Node<T>> nodesSeen = Collections.newSetFromMap(new IdentityHashMap<>());
				List<Node<T>> currentNodes = new ArrayList<>();
				currentNodes.add(this);
				int nodeIdx = 0;

				while (hasMoreNodes) {
						hasMoreNodes = false;
						List<Node<T>> nextNodes = new ArrayList<>();

						for (Node<T> node : currentNodes) {
								if (node == null) {
										nextNodes.add(null);
										nextNodes.add(null);
										nodeIdx++;
										continue;
								}

								try {
										if (nodesSeen.contains(node)) {
												String msg;
												if (node.val instanceof Float) {
														msg = String.format("cyclic reference at Node(%.2f) (level-order index %s)", node.val, nodeIdx);
												} else {
														msg = String.format("cyclic reference at Node(%s) (level-order index %s)", node.val, nodeIdx);
												}
												
												throw new BtreeException.NodeReferenceException(msg);
										}

										if (!(node instanceof Node)) {
												throw new BtreeException.NodeTypeException("invalid node instance at index " + nodeIdx);
										}

										//NOTE: probably not needed...
										if (!(node.val instanceof Integer || node.val instanceof String || node.val instanceof Float)) {
												throw new BtreeException.NodeValueException("invalid node value at index " + nodeIdx);
										}
								} catch (Exception e) {
										Logger.getLogger(Btree.class.getName()).log(Level.SEVERE, "", e);
										System.exit(0);
								}

								if (node.getLeft() != null || node.getRight() != null) {
										hasMoreNodes = true;
								}

								nodesSeen.add(node); 
								nextNodes.add(node.getLeft());
								nextNodes.add(node.getRight());

								nodeIdx++;
						}

						currentNodes = nextNodes;
				}
				
		}

		protected List<T> values() {
				List<Node<T>> currentNodes = new ArrayList<>();
				currentNodes.add(this);
				List<T> nodeValues = new ArrayList<>();

				boolean areNodesLeft = true;
				while (areNodesLeft) {
						areNodesLeft = false;
						List<Node<T>> nextNodes = new ArrayList<>();

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

		protected List<T> values2() {
				List<T> nodeValues = new ArrayList<>();

				Iterator<Node<T>> nodes = this.iterator();
				while (nodes.hasNext()) {
						Node<T> node = nodes.next();
						nodeValues.add(node == null ? null : node.getVal());
				}

				while (!nodeValues.isEmpty() && nodeValues.get(nodeValues.size() - 1) == null) {
						nodeValues.remove(nodeValues.size() - 1);
				}

				return nodeValues;
		}
}