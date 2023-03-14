package com.github.woodsjm.jbtree;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import nl.altindag.log.LogCaptor;
import nl.altindag.log.model.LogEvent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.github.stefanbirkner.systemlambda.SystemLambda.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class UtilityFunctionTest {

    private static Node<Integer> root;

    final int REPETITIONS = 20;

    @BeforeAll
    public static void init() {
        root = new Node<>(0);
        root.setLeft(new Node<>(1));
        root.setRight(new Node<>(2));
        root.getLeft().setLeft(new Node<>(3));
        root.getRight().setRight(new Node<>(4));
    }

    @Test
    void testGetIndexUtilityFunction() {
        assertEquals(0, Btree.getIndex(root, root));
        assertEquals(1, Btree.getIndex(root, root.getLeft()));
        assertEquals(2, Btree.getIndex(root, root.getRight()));
        assertEquals(3, Btree.getIndex(root, root.getLeft().getLeft()));
        assertEquals(6, Btree.getIndex(root, root.getRight().getRight()));
    }

    @Test
    void testGetParentUtilityFunction() {
        assertEquals(root.getLeft(), Btree.getParent(root, root.getLeft().getLeft()));
        assertEquals(root, Btree.getParent(root, root.getLeft()));
        assertEquals(null, Btree.getParent(root, root));
        assertEquals(root.getRight(), Btree.getParent(root, root.getRight().getRight()));
        assertEquals(root, Btree.getParent(root, root.getRight()));
        assertEquals(null, Btree.getParent(root, new Node<>(5)));
        assertEquals(null, Btree.getParent(null, root.getLeft()));
        assertEquals(null, Btree.getParent(root, null));   
    }

    @Test
    void testNumberToLettersUtilityFunction() {
        assertEquals("A", Btree.numberToLetters(0));
        assertEquals("B", Btree.numberToLetters(1));
        assertEquals("Z", Btree.numberToLetters(25));
        assertEquals("ZA", Btree.numberToLetters(26));
        assertEquals("ZZ", Btree.numberToLetters(51));
        assertEquals("ZZA", Btree.numberToLetters(52));

        for (int dummy = 0; dummy < REPETITIONS; dummy++) {
            int num1 = ThreadLocalRandom.current().nextInt(1000);
            int num2 = ThreadLocalRandom.current().nextInt(1000);
            String str1 = Btree.numberToLetters(num1);
            String str2 = Btree.numberToLetters(num2);

            assertEquals(num1 < num2, str1.compareTo(str2) < 0);
            assertEquals(num1 > num2, str1.compareTo(str2) > 0);
            assertEquals(num1 == num2, str1.compareTo(str2) == 0);
        }
    } 

    @ParameterizedTest
    @MethodSource("rootAndDescendantAndExceptionProvider")
    void testGetIndexUtilityFunctionWithException(Node root, Node descendant, Class exception, String message) throws Exception {
        LogCaptor logCaptor = LogCaptor.forClass(Btree.class);

        int statusCode = catchSystemExit(() -> {
            Btree.getIndex(root, descendant);
        });
        assertEquals(0, statusCode);

        LogEvent capturedLogEvent = logCaptor.getLogEvents().get(0);

        assertTrue(capturedLogEvent.getThrowable().get().getClass() == exception);
        assertTrue(capturedLogEvent.getThrowable().get().getMessage().contains(message));
    }

    static Stream<Arguments> rootAndDescendantAndExceptionProvider() {
        return Stream.of(
            Arguments.arguments(root.getRight(), root.getLeft(), BtreeException.NodeReferenceException.class, "given nodes are not in the same tree"),
            Arguments.arguments(root, null, BtreeException.NodeTypeException.class, "descendent must be a Node instance"),
            Arguments.arguments(null, root.getLeft(), BtreeException.NodeTypeException.class, "root must be a Node instance")
        );
    }

    @ParameterizedTest
    @ValueSource(ints = { -1 })
    void testNumberToLettersUtilityFunctionWithInvalidNum(int invalidNum) throws Exception {
        LogCaptor logCaptor = LogCaptor.forClass(Btree.class);

        int statusCode = catchSystemExit(() -> {
            Btree.numberToLetters(invalidNum);
        });
        assertEquals(0, statusCode);

        LogEvent capturedLogEvent = logCaptor.getLogEvents().get(0);

        assertTrue(capturedLogEvent.getThrowable().get() instanceof AssertionError);
    }
}