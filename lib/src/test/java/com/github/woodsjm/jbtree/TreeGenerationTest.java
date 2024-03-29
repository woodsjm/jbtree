package com.github.woodsjm.jbtree;

import static com.github.stefanbirkner.systemlambda.SystemLambda.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;
import nl.altindag.log.LogCaptor;
import nl.altindag.log.model.LogEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class TreeGenerationTest {

  final int REPETITIONS = 20;

  BtreeBuilder<TreeBuilder> tb = new TreeBuilder();

  @Test
  void testTreeGeneration() {
    Node<Integer> root = this.tb.setHeight(0).create();
    assertNotNull(root);

    root.validate();
    assertEquals(0, root.height());
    assertNull(root.getLeft());
    assertNull(root.getRight());
    assertTrue(root.getVal() instanceof Integer);

    for (int dummy = 0; dummy < REPETITIONS; dummy++) {
      int height = ThreadLocalRandom.current().nextInt(10);

      this.tb.reset();
      root = this.tb.setHeight(height).create();
      assertNotNull(root);

      root.validate();
      assertEquals(height, root.height());
    }

    for (int dummy = 0; dummy < REPETITIONS; dummy++) {
      int height = ThreadLocalRandom.current().nextInt(10);

      this.tb.reset();
      root = this.tb.setHeight(height).makePerfect().create();
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

    int statusCode =
        catchSystemExit(
            () -> {
              this.tb.reset();
              this.tb.setHeight(invalidHeight).create();
            });
    assertEquals(0, statusCode);

    LogEvent capturedLogEvent = logCaptor.getLogEvents().get(0);

    assertTrue(
        capturedLogEvent.getThrowable().get().getClass()
            == BtreeException.TreeHeightException.class);
    assertTrue(
        capturedLogEvent
            .getThrowable()
            .get()
            .getMessage()
            .contains("height must be an int between 0 - 9"));
  }

  static Stream<Arguments> invalidHeightProvider() {
    return Stream.of(Arguments.arguments(-1), Arguments.arguments(10));
  }
}
