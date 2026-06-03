package it.unibo.pps.u03

import Sequences.*
import Sequences.Sequence.*
import it.unibo.pps.u03.PersonSequences.Person.{Student, Teacher}

object PersonSequences:

  enum Person:
    case Student(name: String, year: Int)
    case Teacher(name: String, course: String)

  def name(p: Person): String = p match
    case Person.Student(n, _) => n
    case Person.Teacher(n, _) => n

  private def isTeacher(p: Person): Boolean = p match
    case Teacher(_, _) => true
    case _ => false

  private def isStudent(p: Person): Boolean = p match
    case Student(_, _) => true
    case _ => false

  def coursesOfTeachers1(s: Sequence[Person]): Sequence[String] =
      map(filter(s)(isTeacher))(_.asInstanceOf[Teacher].course)
//    map(filter(s)(_.isInstanceOf[Teacher]))(_.asInstanceOf[Teacher].course)
//    map(filter(s)({ case Teacher(_, _) => true; case _ => false }))({ case Teacher(_, course) => course })

  def coursesOfTeachers2(s: Sequence[Person]): Sequence[String] =
    flatMap(s) {
      case Teacher(_, course) => Cons(course, Nil())
      case _ => Nil()
    }

  def distinctCoursesOfTeachers(s: Sequence[Person]): Sequence[String] =
    distinct(map(filter(s)(isTeacher))(_.asInstanceOf[Teacher].course))

  def distinctCoursesOfTeachersAsString(s: Sequence[Person]): String = distinctCoursesOfTeachers(s) match
    case Cons(h, t) => foldLeft(t)(h)(_ + ", " + _)
    case _ => ""
