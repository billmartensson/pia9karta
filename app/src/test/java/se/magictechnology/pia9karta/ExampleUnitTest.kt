package se.magictechnology.pia9karta

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun checkPerson() {

        var someone = Person()
        someone.firstname = "Torsten"
        someone.lastname = "Banansson"

        assertEquals("Torsten Banansson", someone.getFullName())


        someone.firstname = "Torsten"
        someone.lastname = ""

        assertEquals("Torsten", someone.getFullName())

        someone.firstname = ""
        someone.lastname = ""

        assertEquals("Inget namn", someone.getFullName())


    }

}