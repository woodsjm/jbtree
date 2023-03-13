package btree.java;

import static com.github.stefanbirkner.systemlambda.SystemLambda.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nl.altindag.log.LogCaptor;
import nl.altindag.log.model.LogEvent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;


class UtilityFunctionTest {

    private static Node<Integer> root;

    @BeforeAll
    public static void init() {
        root = new Node<>(0);
        root.setLeft(new Node<>(1));
        root.setRight(new Node<>(2));
        root.getLeft().setLeft(new Node<>(3));
        root.getRight().setRight(new Node<>(4));
    }

    @Test
    public void testGetIndexUtilityFunction() {
        assertEquals(0, Btree.getIndex(root, root));
        assertEquals(1, Btree.getIndex(root, root.getLeft()));
        assertEquals(2, Btree.getIndex(root, root.getRight()));
        assertEquals(3, Btree.getIndex(root, root.getLeft().getLeft()));
        assertEquals(6, Btree.getIndex(root, root.getRight().getRight())); 

        assertEquals(root.getLeft(), Btree.getParent(root, root.getLeft().getLeft()));
        assertEquals(root, Btree.getParent(root, root.getLeft()));
        assertEquals(null, Btree.getParent(root, root));
        assertEquals(root.getRight(), Btree.getParent(root, root.getRight().getRight()));
        assertEquals(root, Btree.getParent(root, root.getRight()));
        assertEquals(null, Btree.getParent(root, new Node<>(5)));
        assertEquals(null, Btree.getParent(null, root.getLeft()));
        assertEquals(null, Btree.getParent(root, null));   
    }

    @ParameterizedTest
    @MethodSource("rootAndDescendantAndExceptionProvider")
    public void testGetIndexUtilityFunctionWithException(Node root, Node descendant, Class exception, String message) throws Exception {
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
}