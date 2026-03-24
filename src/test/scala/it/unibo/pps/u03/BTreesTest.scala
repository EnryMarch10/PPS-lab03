package it.unibo.pps.u03

import org.junit.*
import org.junit.Assert.*
import BTrees.Tree.*

class BTreesTest:

  val tree = Branch(Branch(Leaf(1), Leaf(2)), Leaf(1))

  @Test def testCount(): Unit =
    assertEquals(2, count(tree, 1))
    assertEquals(1, count(tree, 2))
    assertEquals(0, count(tree, 3))

  @Test def testSize(): Unit =
    assertEquals(3, size(tree))

  @Test def testFind(): Unit =
    assertTrue(find(tree, 2))
    assertFalse(find(tree, 4))