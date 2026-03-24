package it.unibo.pps.u03

import org.junit.*
import org.junit.Assert.*

class BooleanLambdaTest extends LambdaTest:
  import Lambda.*

  @Test
  def testNot(): Unit =
    assertEquals(Not(False), True)
    assertEquals(Not(True), False)

  @Test
  def testOr(): Unit =
    assertFalse(toBoolean(Or(False)(False)))
    assertTrue(toBoolean(Or(False)(True)))
    assertTrue(toBoolean(Or(True)(False)))
    assertTrue(toBoolean(Or(True)(True)))

  @Test
  def testAnd(): Unit =
    assertFalse(toBoolean(And(False)(False)))
    assertFalse(toBoolean(And(False)(True)))
    assertFalse(toBoolean(And(True)(False)))
    assertTrue(toBoolean(And(True)(True)))
