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

public class BSTGenerationTest {

  final int REPETITIONS = 20;

  BtreeBuilder<BSTBuilder> bb = new BSTBuilder();

  @Test
  void testBSTGeneration() {
    Node<Integer> root = this.bb.setHeight(0).create();
    assertNotNull(root);

    root.validate();
    assertEquals(0, root.height());
    assertNull(root.getLeft());
    assertNull(root.getRight());
    assertTrue(root.getVal() instanceof Integer);

    for (int dummy = 0; dummy < REPETITIONS; dummy++) {
      int height = ThreadLocalRandom.current().nextInt(10);

      this.bb.reset();
      root = this.bb.setHeight(height).create();
      assertNotNull(root);

      root.validate();
      assertEquals(height, root.height());
    }

    for (int dummy = 0; dummy < REPETITIONS; dummy++) {
      int height = ThreadLocalRandom.current().nextInt(10);

      this.bb.reset();
      Node<String> lettersBST = this.bb.setHeight(height).withLetters().create();
      assertNotNull(lettersBST);

      lettersBST.validate();
      assertTrue(lettersBST.isBST());
      assertEquals(height, lettersBST.height());
    }

    for (int dummy = 0; dummy < REPETITIONS; dummy++) {
      int height = ThreadLocalRandom.current().nextInt(10);

      this.bb.reset();
      Node<Integer> perfectIntBST = this.bb.setHeight(height).makePerfect().create();
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

      this.bb.reset();
      Node<Integer> perfectLettersBST =
          this.bb.setHeight(height).makePerfect().withLetters().create();
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

    int statusCode =
        catchSystemExit(
            () -> {
              this.bb.reset();
              this.bb.setHeight(invalidHeight).create();
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
