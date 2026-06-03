package it.unibo.pps.u03

import Optionals.Optional

import scala.annotation.tailrec

object Sequences: // Essentially, generic linked lists

  enum Sequence[E]:
    case Cons(head: E, tail: Sequence[E])
    case Nil()

  object Sequence:

    extension [E](s1: Sequence[E])
      def headAdd(s2: Sequence[E]): Sequence[E] = (s1, s2) match
        case (_, Cons(h2, t2)) => Cons(h2, s1.headAdd(t2))
        case _                 => s1
//        case (Cons(h1, t1), _) => Cons(h1, t1.headAdd(s2))
//        case _                 => Nil()

// TODO: tried to tail but the following also reverses ORDER, maybe can't be tailed.
//        @tailrec
//        def _headAdd(s1: Sequence[E], s2: Sequence[E], acc: Sequence[E] = Nil()): Sequence[E] = (s1, s2) match
//            case (_, Cons(h2, t2)) => _headAdd(s1, t2, Cons(h2, acc))
//            case _ => acc
//        _headAdd(s1, s2)

      def headAddElement(e: E): Sequence[E] = headAdd(Cons(e, Nil()))

      def tailAdd(s2: Sequence[E]): Sequence[E] = s1 match
        case Cons(h, t) => Cons(h, t.tailAdd(s2))
        case _          => s2

      def tailAddElement(e: E): Sequence[E] = tailAdd(Cons(e, Nil()))

    def sum(s: Sequence[Int]): Int =
      @tailrec
      def _sum(s: Sequence[Int], acc: Int = 0): Int = s match
        case Cons(h, t) => _sum(t, h + acc)
        case _ => acc
      _sum(s)
// RECURSIVE
//        l match
//          case Cons(h, t) => h + sum(t)
//          case _          => 0

    def map[A, B](s: Sequence[A])(mapper: A => B): Sequence[B] = s match
      case Cons(h, t) => Cons(mapper(h), map(t)(mapper))
      case _          => Nil()
//      case Nil()      => Nil()

    def filter[A](s: Sequence[A])(predicate: A => Boolean): Sequence[A] = s match
      case Cons(h, t) if predicate(h) => Cons(h, filter(t)(predicate))
      case Cons(_, t)                 => filter(t)(predicate)
      case _                          => Nil()

    // Lab 03

    /*
     * Skip the first n elements of the sequence
     * E.g., [10, 20, 30], 2 => [30]
     * E.g., [10, 20, 30], 3 => []
     * E.g., [10, 20, 30], 0 => [10, 20, 30]
     * E.g., [], 2 => []
     */
    @tailrec
    def skip[A](s: Sequence[A])(n: Int): Sequence[A] = s match
      case Cons(h, t) if n > 0 => skip(t)(n - 1)
      case _ => s

    /*
     * Zip two sequences
     * E.g., [10, 20, 30], [40, 50] => [(10, 40), (20, 50)]
     * E.g., [10], [] => []
     * E.g., [], [] => []
     */
    def zip[A, B](s1: Sequence[A], s2: Sequence[B]): Sequence[(A, B)] = (s1, s2) match
      case (Cons(h1, t1), Cons(h2, t2)) => Cons((h1, h2), zip(t1, t2))
      case _ => Nil()
//      case (Cons(h2, t2), Nil()) => Nil()
//      case (Nil(), Cons(h2, t2)) => Nil()

    /*
     * Concatenate two sequences
     * E.g., [10, 20, 30], [40, 50] => [10, 20, 30, 40, 50]
     * E.g., [10], [] => [10]
     * E.g., [], [] => []
     */
    def concat[A](s1: Sequence[A], s2: Sequence[A]): Sequence[A] = s1 match
      case Cons(h, t) => Cons(h, concat(t, s2))
      case _ => s2
//      (s1, s2) match
//        case (Cons(h1, t1), _) => Cons(h1, concat(t1, s2))
//        case (_, Cons(h2, t2)) => Cons(h2, concat(s1, t2))
//        case _ => Nil()

    /*
     * Reverse the sequence
     * E.g., [10, 20, 30] => [30, 20, 10]
     * E.g., [10] => [10]
     * E.g., [] => []
     */
    def reverse[A](s: Sequence[A]): Sequence[A] =
      @tailrec
      def _reverse(s: Sequence[A], acc: Sequence[A] = Nil()): Sequence[A] = s match
        case Cons(h, t) => _reverse(t, Cons(h, acc));
        case _ => acc
      _reverse(s)
//        s match
//          case Cons(h, t) => reverse(t).tailAddElement(h); // Potevi farlo con la concat
//          case _ => Nil()

    /*
     * Map the elements of the sequence to a new sequence and flatten the result
     * E.g., [10, 20, 30], calling with mapper(v => [v, v + 1]) returns [10, 11, 20, 21, 30, 31]
     * E.g., [10, 20, 30], calling with mapper(v => [v]) returns [10, 20, 30]
     * E.g., [10, 20, 30], calling with mapper(v => Nil()) returns []
     */
    def flatMap[A, B](s: Sequence[A])(mapper: A => Sequence[B]): Sequence[B] = s match
      case Cons(h, t) => concat(mapper(h), flatMap(t)(mapper))
      case _ => Nil()
//      case Cons(h, t) => flatMap(t)(mapper).headAdd(mapper(h))
//      case _ => Nil()

    /*
     * Get the minimum element in the sequence
     * E.g., [30, 20, 10] => 10
     * E.g., [10, 1, 30] => 1
     */
    def min(s: Sequence[Int]): Optional[Int] =
      @tailrec
      def _min(s: Sequence[Int], min: Optional[Int] = Optional.Empty()): Optional[Int] = s match
        case Cons(h, t) => _min(t, if h < Optional.orElse(min, Int.MaxValue) then Optional.Just(h) else min)
        case _ => min
      _min(s)

    /*
     * Get the elements at even indices
     * E.g., [10, 20, 30] => [10, 30]
     * E.g., [10, 20, 30, 40] => [10, 30]
     */
    def evenIndices[A](s: Sequence[A]): Sequence[A] =
      def _evenIndices(s: Sequence[A], i: Int = 0): Sequence[A] = s match
        case Cons(h, t) if i % 2 == 0 => Cons(h, _evenIndices(t, i + 1))
        case Cons(_, t)               => _evenIndices(t, i + 1)
        case _                        => Nil()
      _evenIndices(s)

    /*
     * Check if the sequence contains the element
     * E.g., [10, 20, 30] => true if elem is 20
     * E.g., [10, 20, 30] => false if elem is 40
     */
    @tailrec
    def contains[A](s: Sequence[A])(elem: A): Boolean = s match
      case Cons(h, t) if h == elem => true
      case Cons(_, t)              => contains(t)(elem)
      case _                       => false

    /*
     * Remove duplicates from the sequence
     * E.g., [10, 20, 10, 30] => [10, 20, 30]
     * E.g., [10, 20, 30] => [10, 20, 30]
     */
    def distinct[A](s: Sequence[A]): Sequence[A] = s match
      case Cons(h, t) if contains(t)(h) => Cons(h, distinct(filter(t)(_ != h)))
      case Cons(h, t) => Cons(h, distinct(t))
      case _ => Nil()
//      @tailrec
//      def _distinct(r: Sequence[A], acc: Sequence[A] = Nil()): Sequence[A] = r match
//          case Cons(h, t) if !contains(t)(h) => _distinct(t, Cons(h, acc))
//          case Cons(_, t) => _distinct(t, acc)
//          case _ => acc
//      _distinct(reverse(s))

    /*
     * Group contiguous elements in the sequence
     * E.g., [10, 10, 20, 30] => [[10, 10], [20], [30]]
     * E.g., [10, 20, 30] => [[10], [20], [30]]
     * E.g., [10, 20, 20, 30] => [[10], [20, 20], [30]]
     */
    def group[A](s: Sequence[A]): Sequence[Sequence[A]] =
      def _group(s: Sequence[A], contiguous: Sequence[A], last: A): Sequence[Sequence[A]] = (s, contiguous) match
        case (Cons(h, t), Cons(hc, tc)) if h == last => _group(t, concat(contiguous, Cons(h, Nil())), h)
        case (Cons(h, t), _) if h == last => _group(t, Cons(last, Cons(h, Nil())), h)
        case (Cons(h, t), Cons(hc, tc)) => Cons(contiguous, _group(t, Nil(), h))
        case (Cons(h, t), _) => Cons(Cons(last, Nil()), _group(t, Nil(), h))
        case (_, Cons(h, t))  => Cons(contiguous, Nil())
        case _ => Cons(Cons(last, Nil()), Nil())
      s match
        case Cons(h, t) => _group(t, Nil(), h)
        case _ => Nil()

    /*
     * Partition the sequence into two sequences based on the predicate
     * E.g., [10, 20, 30] => ([10], [20, 30]) if pred is (_ < 20)
     * E.g., [11, 20, 31] => ([20], [11, 31]) if pred is (_ % 2 == 0)
     */
    def partition[A](s: Sequence[A])(predicate: A => Boolean): (Sequence[A], Sequence[A]) =
      (filter(s)(predicate), filter(s)(!predicate(_)))

    def show[E](s: Sequence[E]): String =
      lazy val start = "["
      lazy val end = "]"
      lazy val separator = ", "
      def _show(s: Sequence[E]): String = s match
        case Cons(h, t) if t != Nil() => h match
          case Cons(hn, tn) => start + _show(h.asInstanceOf[Sequence[E]]) + end + separator + _show(t)
          case _ => "" + h + separator + _show(t)
        case Cons(h, t) => h match
          case Cons(hn, tn) => start + _show(h.asInstanceOf[Sequence[E]]) + end
          case _ => "" + h
        case _ => throw IllegalStateException()
      s match
        case Cons(h, t) => start + _show(s) + end
        case _ => start + end

    @tailrec
    def foldLeft[E](s: Sequence[E])(n: E)(f: (E, E) => E): E = s match
      case Cons(h, t) => foldLeft(t)(f(n, h))(f)
      case _ => n

@main def trySequences(): Unit =
  import Sequences.*
  val l = Sequence.Cons(10, Sequence.Cons(20, Sequence.Cons(30, Sequence.Nil())))
  println(Sequence.sum(l)) // 30

  import Sequence.*

  println(sum(map(filter(l)(_ >= 20))(_ + 1))) // 21+31 = 52

  import Sequences.Sequence.*
  val sequence1 = Cons(10, Cons(10, Cons(20, Cons(30, Cons(20, Nil())))))
  val sequence2 = Cons(10, Cons(13, Cons(20, Cons(20, Cons(20, Nil())))))
  val sequence3 = Cons(20, Cons(20, Cons(10, Cons(20, Cons(20, Nil())))))
  println("GROUPINGS:")
  print("Sequence 1: ")
  println(show(sequence1))
  print("Grouping 1: ")
  println(show(group(sequence1)))
  print("Sequence 2: ")
  println(show(sequence2))
  print("Grouping 2: ")
  println(show(group(sequence2)))
  print("Sequence 3: ")
  println(show(sequence3))
  print("Grouping 3: ")
  println(show(group(sequence3)))
