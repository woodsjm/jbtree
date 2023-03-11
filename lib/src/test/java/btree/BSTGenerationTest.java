package btree.java;

import static com.github.stefanbirkner.systemlambda.SystemLambda.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
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


public class BSTGenerationTest {

    final int REPETITIONS = 20;

    @Test
    void testBSTGeneration() {
        Node<Integer> root = Btree.bst(0); // height
        assertNotNull(root);

        root.validate();
        assertEquals(0, root.height());
        assertNull(root.getLeft());
        assertNull(root.getRight());
        assertTrue(root.getVal() instanceof Integer);

        for (int dummy = 0; dummy < REPETITIONS; dummy++) {
            int height = ThreadLocalRandom.current().nextInt(10);

            root = Btree.bst(height);
            assertNotNull(root);

            root.validate();
            assertEquals(height, root.height());
        }

        for (int dummy = 0; dummy < REPETITIONS; dummy++) {
            int height = ThreadLocalRandom.current().nextInt(10);

            Node<String> lettersBST = Btree.bst(height, false, true); // height, isPerfect, letters
            assertNotNull(lettersBST);

            lettersBST.validate();
            assertTrue(lettersBST.isBST());
            assertEquals(height, lettersBST.height());
        }

        for (int dummy = 0; dummy < REPETITIONS; dummy++) {
            int height = ThreadLocalRandom.current().nextInt(10);

            Node<Integer> perfectIntBST = Btree.bst(height, true); // height, isPerfect, letters
            assertNotNull(perfectIntBST);

            perfectIntBST.validate();
            assertEquals(height, perfectIntBST.height());

            assertTrue(perfectIntBST.isBST());
            assertTrue(perfectIntBST.isPerfect());
            assertTrue(perfectIntBST.isBalanced());
            assertTrue(perfectIntBST.isStrict());
        }

        for (int dummy = 0; dummy < REPETITIONS; dummy++) {
            int height = ThreadLocalRandom.current().nextInt(10);

            Node<Integer> perfectLettersBST = Btree.bst(height, true, true); // height, isPerfect, letters
            assertNotNull(perfectLettersBST);

            perfectLettersBST.validate();
            assertEquals(height, perfectLettersBST.height());

            assertTrue(perfectLettersBST.isBST());
            assertTrue(perfectLettersBST.isPerfect());
            assertTrue(perfectLettersBST.isBalanced());
            assertTrue(perfectLettersBST.isStrict());
        }
    }

    @ParameterizedTest
    @MethodSource("invalidHeightProvider")
        void testBSTGenerationInvalidHeightWithException(int invalidHeight) throws Exception {
        LogCaptor logCaptor = LogCaptor.forClass(Btree.class);

        int statusCode = catchSystemExit(() -> {
            Btree.bst(invalidHeight);
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