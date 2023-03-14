/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.github.woodsjm.jbtree;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import nl.altindag.console.ConsoleCaptor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class TreePrintWithLettersTest {

    @ParameterizedTest
    @MethodSource("letterListWithIndexAndExpectedConsoleOutputProvider")
    void testTreePrettyPrintWithLettersWithIndex(List<String> letters, List<String> expectedConsoleOutput) {
        ConsoleCaptor consoleCaptor = ConsoleCaptor.builder().allowTrimmingWhiteSpace(false).allowEmptyLines(true).build();

        Node<String> root = Btree.build(letters);
        assertNotNull(root);

        root.prettyPrint(true, ":"); // index, delimiter
        List<String> standardOutput = consoleCaptor.getStandardOutput();
        
        assertArrayEquals(expectedConsoleOutput.toArray(), standardOutput.toArray());
    }

    static Stream<Arguments> letterListWithIndexAndExpectedConsoleOutputProvider() {
      return Stream.of(
        Arguments.arguments(Arrays.asList("A"), Arrays.asList("0:A")),
        Arguments.arguments(Arrays.asList("A", "B"), Arrays.asList("   _0:A", "  /", "1:B")),
        Arguments.arguments(Arrays.asList("A", null, "C"), Arrays.asList("0:A_", "    \\", "    2:C")),
        Arguments.arguments(Arrays.asList("A", "B", "C"), Arrays.asList("   _0:A_", "  /     \\", "1:B     2:C")),
        Arguments.arguments(Arrays.asList("A", "B", "C", null, "E"), Arrays.asList(
            "   _____0:A_",
            "  /         \\",
            "1:B_        2:C",
            "    \\",
            "    4:E"
        )),
        Arguments.arguments(Arrays.asList("A", "B", "C", null, "E", "F"), Arrays.asList(
            "   _____0:A_____",
            "  /             \\",
            "1:B_           _2:C",
            "    \\         /",
            "    4:E     5:F"
        )),
        Arguments.arguments(Arrays.asList("A", "B", "C", null, "E", "F", "G"), Arrays.asList(
            "   _____0:A_____",
            "  /             \\",
            "1:B_           _2:C_",
            "    \\         /     \\",
            "    4:E     5:F     6:G"
        )),
        Arguments.arguments(Arrays.asList("A", "B", "C", "D", "E", "F", "G"), Arrays.asList(
            "       _____0:A_____",
            "      /             \\",
            "   _1:B_           _2:C_",
            "  /     \\         /     \\",
            "3:D     4:E     5:F     6:G"
        ))
      );
    }

    @ParameterizedTest
    @MethodSource("letterListAndExpectedConsoleOutputProvider")
    void testTreePrettyPrintWithLettersNoIndex(List<String> letters, List<String> expectedConsoleOutput) {
        ConsoleCaptor consoleCaptor = ConsoleCaptor.builder().allowTrimmingWhiteSpace(false).allowEmptyLines(true).build();

        Node<String> root = Btree.build(letters);
        assertNotNull(root);

        root.prettyPrint();
        List<String> standardOutput = consoleCaptor.getStandardOutput();
        
        assertArrayEquals(expectedConsoleOutput.toArray(), standardOutput.toArray());
    }

    @ParameterizedTest
    @MethodSource("letterListAndExpectedConsoleOutputProvider")
    void testTreeDefaultPrintWithLettersNoIndex(List<String> letters, List<String> expectedConsoleOutput) {
        ConsoleCaptor consoleCaptor = ConsoleCaptor.builder().allowTrimmingWhiteSpace(false).allowEmptyLines(true).build();

        Node<String> root = Btree.build(letters);
        assertNotNull(root);

        System.out.print(root);
        List<String> standardOutput = consoleCaptor.getStandardOutput();
        
        assertArrayEquals(expectedConsoleOutput.toArray(), standardOutput.toArray());
    }

    static Stream<Arguments> letterListAndExpectedConsoleOutputProvider() {
        return Stream.of(
            Arguments.arguments(Arrays.asList("A"), Arrays.asList("A")),
            Arguments.arguments(Arrays.asList("A", "B"), Arrays.asList("  A", " /", "B")),
            Arguments.arguments(Arrays.asList("A", null, "C"), Arrays.asList("A", " \\", "  C")),
            Arguments.arguments(Arrays.asList("A", "B", "C"), Arrays.asList("  A", " / \\", "B   C")),
            Arguments.arguments(Arrays.asList("A", "B", "C", null, "E"), Arrays.asList("  __A", " /   \\", "B     C", " \\", "  E")),
            Arguments.arguments(Arrays.asList("A", "B", "C", null, "E", "F"), Arrays.asList("  __A__", " /     \\", "B       C", " \\     /", "  E   F")),
            Arguments.arguments(Arrays.asList("A", "B", "C", null, "E", "F", "G"), Arrays.asList(
                "  __A__",
                " /     \\",
                "B       C",
                " \\     / \\",
                "  E   F   G"
            )),
            Arguments.arguments(Arrays.asList("A", "B", "C", "D", "E", "F", "G"), Arrays.asList(
                "    __A__",
                "   /     \\",
                "  B       C",
                " / \\     / \\",
                "D   E   F   G"
            ))
        );
    }
}
