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


public class TreeGenerationTest {

    final int REPETITIONS = 20;

    @Test
    void testTreeGeneration() {
        Node<Integer> root = Btree.tree(0); // height
        assertNotNull(root);

        root.validate();
        assertEquals(0, root.height());
        assertNull(root.getLeft());
        assertNull(root.getRight());
        assertTrue(root.getVal() instanceof Integer);

        for (int dummy = 0; dummy < REPETITIONS; dummy++) {
            int height = ThreadLocalRandom.current().nextInt(10);

            root = Btree.tree(height);
            assertNotNull(root);

            root.validate();
            assertEquals(height, root.height());
        }

        for (int dummy = 0; dummy < REPETITIONS; dummy++) {
        int height = ThreadLocalRandom.current().nextInt(10);

        root = Btree.tree(height, true); // height, isPerfect
        assertNotNull(root);

        root.validate();
        assertEquals(height, root.height());
        assertTrue(root.isPerfect());
        assertTrue(root.isBalanced());
        assertTrue(root.isStrict());
        }
    }

    @ParameterizedTest
    @MethodSource("invalidHeightProvider")
    void testTreeGenerationInvalidHeightWithException(int invalidHeight) throws Exception {
        LogCaptor logCaptor = LogCaptor.forClass(Btree.class);

        int statusCode = catchSystemExit(() -> {
            Btree.tree(invalidHeight);
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