# Lab 03 - More Functional Programming

Fourth laboratory of [Programming and Development Paradigms - a.y. 2025-2026](https://www.unibo.it/en/teaching/course-unit-catalogue/course-unit/2025/526526) ([Computer Science and Engineering](https://corsi.unibo.it/2cycle/ComputerScienceEngineering)).

## Author

[@EnryMarch10](https://github.com/EnryMarch10)

## Details

Second laboratory about functional programming using **Scala**.

### Exercises

#### Part 1 - Lists

- Look at the file `u03/Sequences.scala`.
- Provide explicit implementations for the missing methods in the `Sequence` module, ensuring each behavior aligns with
  the specifications in `SequenceTest.scala`.
- Follow the suggested order for the mandatory methods:
  - *Mandatory*: `skip`, `zip`, `concat`, `reverse`, `flatMap`.
- If you want to exercise more, you can also implement the following methods:
  - *Optional*: `min`, `evenIndices`, `contains`, `distinct`, `group`, `partition`.
- Look at the corresponding tests in `u03/SequenceTest.scala` to observe the expected behavior.

> It is recommended to first implement the mandatory methods.
> Once completed, feel free to continue with the optional methods as time permits.

#### Part 2 - More on Lists

- Consider `Person` and `Sequence` as implemented in class slides. Create a function that takes a sequence of `Persons`
  and returns a sequence containing only the `courses` of `Teacher` in that list:
  - *Hint 1*: you essentially need to combine `filter` and `map`.
  - *Hint 2*: there is a very concise solution that reuses `flatMap`.
- (**Hard**) Implement `foldLeft` function that, starting from a default value, "fold over" sequences by "accumulating"
  elements via a binary operator.
  - Idea: given a list `[3, 7, 1, 5]` and a default value `0`, a left-fold (resp., right-fold) through e.g. operator `+`
    is given by `(((0 + 3) + 7) + 1) + 5`.
  > The accumulator type may differ from the element type.
  ```Scala
  val lst = Cons(3, Cons(7, Cons(1, Cons(5, Nil()))))
  foldLeft(lst)(0)(_ - _) // -16
  ```
- Consider again `Person` and `Sequence`. Create a function that takes a sequence of `Persons` and returns the total
  number of distinct courses taught by all `Teacher` in that list (the same course may be taught by multiple teachers).
  Use a combination of `filter`, `map`, `distinct`, and `foldLeft`.
  - Example: `Teacher("Viroli", "PPS")`, `Teacher("Aguzzi", "PPS")`, `Teacher("Ricci", "PCD")` => 2 distinct courses
    (PPS and PCD).
  > In this case no test are given, so you have to write your own tests!

#### Part 3 - Streams

- Consider the Stream type discussed in class. Define a function, called `takeWhile(s)(pred)`, that returns the first `n`
  elements of the stream `s` that satisfy a given predicate `pred`.
  ```Scala
  val stream = Stream.iterate(0)(_ + 1)
  Stream.toList(Stream.takeWhile(stream)(_ < 5))
  // Cons(0, Cons(1, Cons(2, Cons(3, Cons(4, Nil())))))
  ```
- Implement a generic function `fill(n)(k)` that creates a stream of `n` elements, each of which is `k`.
  ```Scala
  Stream.toList(Stream.fill(3)("a")) // Cons(a, Cons(a, Cons(a, Nil())))
  ```
- Implement an infinite stream for the Fibonacci Numbers: [https://en.wikipedia.org/wiki/Fibonacci_number](https://en.wikipedia.org/wiki/Fibonacci_number).
  ```Scala
  val fibonacci: Stream[Int] = ???
  Stream.toList(Stream.take(fibonacci)(5)) // Cons(0, Cons(1, Cons(1, Cons(2, Cons(3, Nil()))))
  ```
- (*Optional*) Implement a generic function `interleave(s1, s2)` that merges two streams by alternating elements from
  each.
  ```Scala
  val s1 = Stream.fromList(List(1, 3, 5))
  val s2 = Stream.fromList(List(2, 4, 6, 8, 10))
  Stream.toList(Stream.interleave(s1, s2))
  // Expected output: Cons(1, Cons(2, Cons(3, Cons(4, Cons(5, Cons(6, Cons(8, Cons(10, Nil()))))))))
  ```
- (*Optional*) Implement a function `cycle(list)` that creates an infinite stream by cycling through the elements of a
  given finite list in an innovative way.
  ```Scala
  def cycle[A](lst: Sequence[A]): Stream[A] = ???
  val repeat = cycle(Cons('a', Cons('b', Cons('c', Nil()))))
  Stream.toList(Stream.take(innovativeStream)(5))
  // Expected output: Cons(a, Cons(b, Cons(c, Cons(a, Cons(b, Nil())))))
  ```

## License

[MIT](https://choosealicense.com/licenses/mit/)
