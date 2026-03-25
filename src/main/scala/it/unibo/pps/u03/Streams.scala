package it.unibo.pps.u03

object Streams extends App :

  import Sequences.*

  enum Stream[A]:
    private case Empty()
    private case Cons(head: () => A, tail: () => Stream[A])

  object Stream:

    def empty[A](): Stream[A] = Empty()

    def cons[A](hd: => A, tl: => Stream[A]): Stream[A] =
      lazy val head = hd
      lazy val tail = tl
      Cons(() => head, () => tail)

    def toList[A](stream: Stream[A]): Sequence[A] = stream match
      case Cons(h, t) => Sequence.Cons(h(), toList(t()))
      case _ => Sequence.Nil()

    def map[A, B](stream: Stream[A])(f: A => B): Stream[B] = stream match
      case Cons(head, tail) => cons(f(head()), map(tail())(f))
      case _ => Empty()

    def filter[A](stream: Stream[A])(predicate: A => Boolean): Stream[A] = stream match
      case Cons(head, tail) if predicate(head()) => cons(head(), filter(tail())(predicate))
      case Cons(head, tail) => filter(tail())(predicate)
      case _ => Empty()

    def take[A](stream: Stream[A])(n: Int): Stream[A] = (stream, n) match
      case (Cons(head, tail), n) if n > 0 => cons(head(), take(tail())(n - 1))
      case _ => Empty()

    def iterate[A](init: => A)(next: A => A): Stream[A] =
      cons(init, iterate(next(init))(next))

    def takeWhile[A](stream: Stream[A])(predicate: A => Boolean): Stream[A] = stream match
      case Cons(head, tail) if predicate(head()) => cons(head(), takeWhile(tail())(predicate))
      case _ => Empty()

    def fill[A](num: Int)(e: A): Stream[A] =  if num > 0
      then cons(e, fill(num - 1)(e))
      else Empty()

    def fibonacci(): Stream[Int] =
      def _fibonacci(a: => Int = 0, b: => Int = 1): Stream[Int] =
        cons(a + b, _fibonacci(b, a + b))
      cons(0, cons(1, _fibonacci()))

  end Stream

@main def tryStreams =
  import Streams.*
  import Sequences.Sequence.*

  val str1 = Stream.iterate(0)(_ + 1) // {0,1,2,3,..}
  val str2 = Stream.map(str1)(_ + 1) // {1,2,3,4,..}
  val str3 = Stream.filter(str2)(x => (x < 3 || x > 20)) // {1,2,21,22,..}
  val str4 = Stream.take(str3)(10) // {1,2,21,22,..,28}
  println(show(Stream.toList(str4))) // [1, 2, 21, 22, .., 28]

  lazy val corec: Stream[Int] = Stream.cons(1, corec) // {1,1,1,..}
  println(show(Stream.toList(Stream.take(corec)(10)))) // [1, 1, .., 1]

  val stream = Stream.iterate(0)(_ + 1)
  println(show(Stream.toList(Stream.takeWhile(stream)(_ < 5)))) // [0, 1, 2, 3, 4]
//  Cons(0, Cons(1, Cons(2, Cons(3, Cons(4, Nil())))))

  println(show(Stream.toList(Stream.fill(3)("a")))) // [a, a, a]
//  Cons(a, Cons(a, Cons(a, Nil())))

  val fibonacci: Stream[Int] = Stream.fibonacci()
  println(show(Stream.toList(Stream.take(fibonacci)(0)))) // []
  println(show(Stream.toList(Stream.take(fibonacci)(2)))) // [0, 1]
  println(show(Stream.toList(Stream.take(fibonacci)(8)))) // [0, 1, 1, 2, 3, 5, 8, 13]
//  Cons(0, Cons(1, Cons(1, Cons(2, Cons(3, Nil()))))
