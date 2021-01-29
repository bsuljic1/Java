package ba.unsa.etf.rpr;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class ChildTest {
    @Test
    void konstruktor() {
        Child child = new Child("ime", "prezime", "adresa",  Behavior.EXCELLENT,
                null, null, null, LocalTime.of(8, 0), LocalTime.of(15, 0));
        assertEquals("ime", child.getFirstName());
        assertEquals("prezime", child.getLastName());
        assertEquals("adresa", child.getAddress());
    }

    @Test
    void toStringTest() {
        Child child = new Child();
        child.setFirstName("Berina");
        child.setLastName("Suljic");
        String s = child.toString();
        assertEquals("Berina Suljic", s);
    }

    @Test
    void setteri() {
        Child child = new Child("ime", "prezime", "adresa",  Behavior.EXCELLENT,
                null, null, null, LocalTime.of(8, 0), LocalTime.of(15, 0));
        child.setFirstName("berina");
        child.setLastName("suljic");
        assertEquals("berina", child.getFirstName());
        assertEquals("suljic", child.getLastName());
    }
}