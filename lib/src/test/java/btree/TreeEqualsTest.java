package btree.java;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


public class TreeEqualsTest {

    @Test
    public void testTreeEqualsWithIntegers() {
        Node<Integer> root1 = new Node<>(1);
        Node<Integer> root2 = new Node<>(1);
        assertFalse(root1.equals(null));
        assertFalse(root1.equals(Integer.valueOf(1)));
        assertFalse(root1.equals(new Node<>(2)));
        assertTrue(root1.equals(root2));
        assertTrue(root2.equals(root1));

        root1.setLeft(new Node<>(2));
        assertFalse(root1.equals(root2));
        assertFalse(root2.equals(root1));

        root2.setLeft(new Node<>(2));
        assertTrue(root1.equals(root2));
        assertTrue(root2.equals(root1));

        root1.setRight(new Node<>(3));
        assertFalse(root1.equals(root2));
        assertFalse(root2.equals(root1));

        root2.setRight(new Node<>(3));
        assertTrue(root1.equals(root2));
        assertTrue(root2.equals(root1));

        root1.getRight().setLeft(new Node<>(4));
        assertFalse(root1.equals(root2));
        assertFalse(root2.equals(root1));

        root2.getRight().setLeft(new Node<>(4));
        assertTrue(root1.equals(root2));
        assertTrue(root2.equals(root1));
    }

    @Test
    public void testTreeEqualsWithFloats() {
        Node<Float> root1 = new Node<>(1.50f);
        Node<Float> root2 = new Node<>(1.50f);
        assertFalse(root1.equals(null));
        assertFalse(root1.equals(Float.valueOf(1.50f)));
        assertFalse(root1.equals(new Node<>(2.50f)));
        assertTrue(root1.equals(root2));
        assertTrue(root2.equals(root1));

        root1.setLeft(new Node<>(2.50f));
        assertFalse(root1.equals(root2));
        assertFalse(root2.equals(root1));

        root2.setLeft(new Node<>(2.50f));
        assertTrue(root1.equals(root2));
        assertTrue(root2.equals(root1));

        root1.setRight(new Node<>(3.50f));
        assertFalse(root1.equals(root2));
        assertFalse(root2.equals(root1));

        root2.setRight(new Node<>(3.50f));
        assertTrue(root1.equals(root2));
        assertTrue(root2.equals(root1));

        root1.getRight().setLeft(new Node<>(4.50f));
        assertFalse(root1.equals(root2));
        assertFalse(root2.equals(root1));

        root2.getRight().setLeft(new Node<>(4.50f));
        assertTrue(root1.equals(root2));
        assertTrue(root2.equals(root1));
    }

    @Test
    public void testTreeEqualsWithLetters() {
        Node<String> root1 = new Node<>("A");
        Node<String> root2 = new Node<>("A");
        assertFalse(root1.equals(null));
        assertFalse(root1.equals("A"));
        assertFalse(root1.equals(new Node<>("B")));
        assertTrue(root1.equals(root2));
        assertTrue(root2.equals(root1));

        root1.setLeft(new Node<>("B"));
        assertFalse(root1.equals(root2));
        assertFalse(root2.equals(root1));

        root2.setLeft(new Node<>("B"));
        assertTrue(root1.equals(root2));
        assertTrue(root2.equals(root1));

        root1.setRight(new Node<>("C"));
        assertFalse(root1.equals(root2));
        assertFalse(root2.equals(root1));

        root2.setRight(new Node<>("C"));
        assertTrue(root1.equals(root2));
        assertTrue(root2.equals(root1));

        root1.getRight().setLeft(new Node<>("D"));
        assertFalse(root1.equals(root2));
        assertFalse(root2.equals(root1));

        root2.getRight().setLeft(new Node<>("D"));
        assertTrue(root1.equals(root2));
        assertTrue(root2.equals(root1));
    }
}