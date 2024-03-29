package com.github.woodsjm.jbtree;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import nl.altindag.console.ConsoleCaptor;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class TreePrintWithIntegersTest {

  @ParameterizedTest
  @MethodSource("integerListWithIndexAndExpectedConsoleOutputProvider")
  void testTreePrettyPrintWithIntegersWithIndex(
      List<Integer> ints, List<String> expectedConsoleOutput) {
    ConsoleCaptor consoleCaptor =
        ConsoleCaptor.builder().allowTrimmingWhiteSpace(false).allowEmptyLines(true).build();

    Node<Integer> root = Btree.build(ints);
    assertNotNull(root);

    root.prettyPrint(true, ":"); // index, delimiter
    List<String> standardOutput = consoleCaptor.getStandardOutput();

    assertArrayEquals(expectedConsoleOutput.toArray(), standardOutput.toArray());
  }

  static Stream<Arguments> integerListWithIndexAndExpectedConsoleOutputProvider() {
    return Stream.of(
        Arguments.arguments(Arrays.asList(1), Arrays.asList("0:1")),
        Arguments.arguments(Arrays.asList(1, 2), Arrays.asList("   _0:1", "  /", "1:2")),
        Arguments.arguments(Arrays.asList(1, null, 3), Arrays.asList("0:1_", "    \\", "    2:3")),
        Arguments.arguments(
            Arrays.asList(1, 2, 3), Arrays.asList("   _0:1_", "  /     \\", "1:2     2:3")),
        Arguments.arguments(
            Arrays.asList(1, 2, 3, null, 5),
            Arrays.asList(
                "   _____0:1_", "  /         \\", "1:2_        2:3", "    \\", "    4:5")),
        Arguments.arguments(
            Arrays.asList(1, 2, 3, null, 5, 6),
            Arrays.asList(
                "   _____0:1_____",
                "  /             \\",
                "1:2_           _2:3",
                "    \\         /",
                "    4:5     5:6")),
        Arguments.arguments(
            Arrays.asList(1, 2, 3, null, 5, 6, 7),
            Arrays.asList(
                "   _____0:1_____",
                "  /             \\",
                "1:2_           _2:3_",
                "    \\         /     \\",
                "    4:5     5:6     6:7")),
        Arguments.arguments(
            Arrays.asList(1, 2, 3, 8, 5, 6, 7),
            Arrays.asList(
                "       _____0:1_____",
                "      /             \\",
                "   _1:2_           _2:3_",
                "  /     \\         /     \\",
                "3:8     4:5     5:6     6:7")));
  }

  @ParameterizedTest
  @MethodSource("integerListAndExpectedConsoleOutputProvider")
  void testTreePrettyPrintWithIntegersNoIndex(
      List<Integer> ints, List<String> expectedConsoleOutput) {
    ConsoleCaptor consoleCaptor =
        ConsoleCaptor.builder().allowTrimmingWhiteSpace(false).allowEmptyLines(true).build();

    Node<Integer> root = Btree.build(ints);
    assertNotNull(root);

    root.prettyPrint();
    List<String> standardOutput = consoleCaptor.getStandardOutput();

    assertArrayEquals(expectedConsoleOutput.toArray(), standardOutput.toArray());
  }

  @ParameterizedTest
  @MethodSource("integerListAndExpectedConsoleOutputProvider")
  void testTreeDefaultPrinttWithIntegersNoIndex(
      List<Integer> ints, List<String> expectedConsoleOutput) {
    ConsoleCaptor consoleCaptor =
        ConsoleCaptor.builder().allowTrimmingWhiteSpace(false).allowEmptyLines(true).build();

    Node<Integer> root = Btree.build(ints);
    assertNotNull(root);

    System.out.print(root);
    List<String> standardOutput = consoleCaptor.getStandardOutput();

    assertArrayEquals(expectedConsoleOutput.toArray(), standardOutput.toArray());
  }

  static Stream<Arguments> integerListAndExpectedConsoleOutputProvider() {
    return Stream.of(
        Arguments.arguments(Arrays.asList(1), Arrays.asList("1")),
        Arguments.arguments(Arrays.asList(1, 2), Arrays.asList("  1", " /", "2")),
        Arguments.arguments(Arrays.asList(1, null, 3), Arrays.asList("1", " \\", "  3")),
        Arguments.arguments(Arrays.asList(1, 2, 3), Arrays.asList("  1", " / \\", "2   3")),
        Arguments.arguments(
            Arrays.asList(1, 2, 3, null, 5),
            Arrays.asList("  __1", " /   \\", "2     3", " \\", "  5")),
        Arguments.arguments(
            Arrays.asList(1, 2, 3, null, 5, 6),
            Arrays.asList("  __1__", " /     \\", "2       3", " \\     /", "  5   6")),
        Arguments.arguments(
            Arrays.asList(1, 2, 3, null, 5, 6, 7),
            Arrays.asList("  __1__", " /     \\", "2       3", " \\     / \\", "  5   6   7")),
        Arguments.arguments(
            Arrays.asList(1, 2, 3, 8, 5, 6, 7),
            Arrays.asList(
                "    __1__", "   /     \\", "  2       3", " / \\     / \\", "8   5   6   7")));
  }
}
