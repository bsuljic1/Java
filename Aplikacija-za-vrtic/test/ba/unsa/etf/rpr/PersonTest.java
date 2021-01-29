package ba.unsa.etf.rpr;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    @Test
    void toStringTest(){
        Person person = new Person(123, "ime", "prezime", "adresa");
        assertEquals("ime prezime", person.toString());
    }

}