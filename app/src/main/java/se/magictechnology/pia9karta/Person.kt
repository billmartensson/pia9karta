package se.magictechnology.pia9karta

class Person {

    var firstname = ""
    var lastname = ""

    fun getFullName() : String
    {
        if(firstname == "" && lastname == "")
        {
            return "Inget namn"
        }

        if(lastname == "")
        {
            return firstname
        }

        return firstname + " " + lastname
    }

}