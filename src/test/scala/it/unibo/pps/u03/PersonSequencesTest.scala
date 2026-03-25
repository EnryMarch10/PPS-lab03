package it.unibo.pps.u03

import org.junit.*
import org.junit.Assert.*

import it.unibo.pps.u03.PersonSequences.Person.{Student, Teacher}
import it.unibo.pps.u03.Sequences.*
import it.unibo.pps.u03.Sequences.Sequence.*

import PersonSequences.*

class PersonSequencesTest:

  val persons: Sequence[Person] = Cons(Teacher("Viroli", "PPS"),
      Cons(Teacher("Bravetti", "LCMC"),
      Cons(Student("Marchionni", 2026),
      Cons(Teacher("Maltoni", "ML"),
      Cons(Teacher("Aguzzi", "PPS"),
      Cons(Teacher("Farabegoli", "PPS"),
      Cons(Teacher("Omicini", "DS"),
      Cons(Student("Ronchi", 2026),
      Cons(Teacher("Ciatto", "DS"),
      Cons(Teacher("Ricci", "PCD"),
      Cons(Teacher("Golfarelli", "DT"),
        Nil())))))))))))

  @Test def testCoursesOfTeachers(): Unit =
    assertEquals(Cons("PPS", Cons("LCMC", Cons("ML", Cons("PPS", Cons("PPS", Cons("DS", Cons("DS", Cons("PCD", Cons("DT", Nil()))))))))),
      coursesOfTeachers1(persons))
    assertEquals(Cons("PPS", Cons("LCMC", Cons("ML", Cons("PPS", Cons("PPS", Cons("DS", Cons("DS", Cons("PCD", Cons("DT", Nil()))))))))),
      coursesOfTeachers2(persons))

  @Test def testDistinctCoursesOfTeachers(): Unit =
    assertEquals(Cons("PPS", Cons("LCMC", Cons("ML", Cons("DS", Cons("PCD", Cons("DT", Nil())))))),
      distinctCoursesOfTeachers(persons))
    assertEquals("PPS, LCMC, ML, DS, PCD, DT", distinctCoursesOfTeachersAsString(persons))
