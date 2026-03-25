package it.unibo.pps.u03

import Sequences.*
import Sequences.Sequence.*
import it.unibo.pps.u03.PersonSequences.Person.Teacher

object PersonSequences:

  enum Person:
    case Student(name: String, year: Int)
    case Teacher(name: String, course: String)

  def name(p: Person): String = p match
    case Person.Student(n, _) => n
    case Person.Teacher(n, _) => n

  def coursesOfTeachers1(s: Sequence[Person]): Sequence[String] =
    map(filter(s)(_.isInstanceOf[Teacher]))(_.asInstanceOf[Teacher].course)
//        map(filter(s)({ case Teacher(_, _) => true; case _ => false }))({ case Teacher(_, course) => course })

  def coursesOfTeachers2(s: Sequence[Person]): Sequence[String] =
    flatMap(s) {
      case Teacher(_, course) => Cons(course, Nil());
      case _ => Nil()
    }
