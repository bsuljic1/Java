package ba.unsa.etf.rpr;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParentTest {

    @Test
    void konstruktor() {
        Parent parent = new Parent("ime", "prezime", "adresa", "username", "password", 123456789);
        assertEquals("ime", parent.getFirstName());
        assertEquals("prezime", parent.getLastName());
        assertEquals("adresa", parent.getAddress());
        assertEquals("username", parent.getUsername());
        assertEquals("password", parent.getPassword());
        assertEquals(123456789, parent.getPhoneNumber());
    }

    @Test
    void toStringTest() {
        Parent parent = new Parent("ime", "prezime", "adresa", "username", "password", 123456789);
        String s = parent.toString();
        assertEquals("ime prezime", s);
    }

    @Test
    void setteri() {
        Parent parent = new Parent("ime", "prezime", "adresa", "username", "password", 123456789);
        parent.setFirstName("novoime");
        parent.setLastName("novoprezime");
        parent.setAddress("novaadresa");
        parent.setUsername("noviusername");
        parent.setPassword("novasifra");
        parent.setPhoneNumber(987654321);
        assertEquals("novoime", parent.getFirstName());
        assertEquals("novoprezime", parent.getLastName());
        assertEquals("novaadresa", parent.getAddress());
        assertEquals("noviusername", parent.getUsername());
        assertEquals("novasifra", parent.getPassword());
        assertEquals(987654321, parent.getPhoneNumber());
    }
}