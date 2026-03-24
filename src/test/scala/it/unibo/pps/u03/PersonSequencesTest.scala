package it.unibo.pps.u03

import org.junit.*
import org.junit.Assert.*

import it.unibo.pps.u03.PersonSequences.Person.{Student, Teacher}
import it.unibo.pps.u03.Sequences.*
import it.unibo.pps.u03.Sequences.Sequence.*

import PersonSequences.*

class PersonSequencesTest:

    val persons: Sequence[Person] = Cons(Teacher("Viroli", "PPS"),
            Cons(Student("Marchionni", 2026),
            Cons(Teacher("Aguzzi", "PPS"),
            Cons(Teacher("Farabegoli", "PPS"),
            Cons(Student("Ronchi", 2026),
            Cons(Teacher("Ricci", "PCD"), Nil()))))))

    @Test def testCoursesOfTeachers(): Unit =
        assertEquals(Cons("PPS", Cons("PPS", Cons("PPS", Cons("PCD", Nil())))), coursesOfTeachers1(persons))
        assertEquals(Cons("PPS", Cons("PPS", Cons("PPS", Cons("PCD", Nil())))), coursesOfTeachers2(persons))
