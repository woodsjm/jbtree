package com.github.woodsjm.jbtree;

import static com.github.stefanbirkner.systemlambda.SystemLambda.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nl.altindag.log.LogCaptor;
import nl.altindag.log.model.LogEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;


public class HeapGenerationTest {

    final int REPETITIONS = 20;

    @Test
    void testHeapGeneration() {
        Node<Integer> root = Btree.heap(0); // height
        assertNotNull(root);

        root.validate();
        assertEquals(0, root.height());
        assertNull(root.getLeft());
        assertNull(root.getRight());
        assertTrue(root.getVal() instanceof Integer);

        for (int dummy = 0; dummy < REPETITIONS; dummy++) {
            int height = ThreadLocalRandom.current().nextInt(1, 10);

            Node<Integer> imperfectIntMaxHeap = Btree.heap(height, false, false, true); // height, isPerfect, letters, isMax
            assertNotNull(imperfectIntMaxHeap);
            

            imperfectIntMaxHeap.validate();
            assertTrue(imperfectIntMaxHeap.isMaxHeap());
            imperfectIntMaxHeap.prettyPrint();
            assertFalse(imperfectIntMaxHeap.isMinHeap());
            assertEquals(height, imperfectIntMaxHeap.height());
        }

        for (int dummy = 0; dummy < REPETITIONS; dummy++) {
            int height = ThreadLocalRandom.current().nextInt(1, 10);

            Node<String> imperfectLettersMaxHeap = Btree.heap(height, false, true, true); // height, isPerfect, letters, isMax
            assertNotNull(imperfectLettersMaxHeap);

            imperfectLettersMaxHeap.validate();
            assertTrue(imperfectLettersMaxHeap.isMaxHeap());
            assertFalse(imperfectLettersMaxHeap.isMinHeap());
            assertEquals(height, imperfectLettersMaxHeap.height());
        }

        for (int dummy = 0; dummy < REPETITIONS; dummy++) {
            int height = ThreadLocalRandom.current().nextInt(1, 10);

            Node<Integer> imperfectIntMinHeap = Btree.heap(height, false, false, false); // height, isPerfect, letters, isMax
            assertNotNull(imperfectIntMinHeap);

            imperfectIntMinHeap.validate();
            assertFalse(imperfectIntMinHeap.isMaxHeap());
            assertTrue(imperfectIntMinHeap.isMinHeap());
            assertEquals(height, imperfectIntMinHeap.height());
        }

        for (int dummy = 0; dummy < REPETITIONS; dummy++) {
            int height = ThreadLocalRandom.current().nextInt(1, 10);

            Node<String> imperfectLettersMinHeap = Btree.heap(height, false, true, false); // height, isPerfect, letters, isMax
            assertNotNull(imperfectLettersMinHeap);

            imperfectLettersMinHeap.validate();
            assertFalse(imperfectLettersMinHeap.isMaxHeap());
            assertTrue(imperfectLettersMinHeap.isMinHeap());
            assertEquals(height, imperfectLettersMinHeap.height());
        }

        for (int dummy = 0; dummy < REPETITIONS; dummy++) {
            int height = ThreadLocalRandom.current().nextInt(1, 10);

            Node<Integer> perfectIntMinHeap = Btree.heap(height, true); // height, isPerfect
            assertNotNull(perfectIntMinHeap);

            perfectIntMinHeap.validate();
            assertTrue(perfectIntMinHeap.isMaxHeap());
            assertFalse(perfectIntMinHeap.isMinHeap());
            assertTrue(perfectIntMinHeap.isPerfect());
            assertTrue(perfectIntMinHeap.isBalanced());
            assertTrue(perfectIntMinHeap.isStrict());
            assertEquals(height, perfectIntMinHeap.height());
        }

        for (int dummy = 0; dummy < REPETITIONS; dummy++) {
            int height = ThreadLocalRandom.current().nextInt(1, 10);

            Node<String> perfectLettersMaxHeap = Btree.heap(height, true, true); // height, isPerfect, letters
            assertNotNull(perfectLettersMaxHeap);

            perfectLettersMaxHeap.validate();
            assertTrue(perfectLettersMaxHeap.isMaxHeap());
            assertFalse(perfectLettersMaxHeap.isMinHeap());
            assertTrue(perfectLettersMaxHeap.isPerfect());
            assertTrue(perfectLettersMaxHeap.isBalanced());
            assertTrue(perfectLettersMaxHeap.isStrict());
            assertEquals(height, perfectLettersMaxHeap.height());
        }
    }

    @ParameterizedTest
    @MethodSource("invalidHeightProvider")
        void testHeapGenerationInvalidHeightWithException(int invalidHeight) throws Exception {
        LogCaptor logCaptor = LogCaptor.forClass(Btree.class);

        int statusCode = catchSystemExit(() -> {
            Btree.heap(invalidHeight);
        });
        assertEquals(0, statusCode);

        LogEvent capturedLogEvent = logCaptor.getLogEvents().get(0);

        assertTrue(capturedLogEvent.getThrowable().get().getClass() == BtreeException.TreeHeightException.class);
        assertTrue(capturedLogEvent.getThrowable().get().getMessage().contains("height must be an int between 0 - 9"));
    }

    static Stream<Arguments> invalidHeightProvider() {
        return Stream.of(
            Arguments.arguments(-1),
            Arguments.arguments(10)
        );
    }
}