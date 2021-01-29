package ba.unsa.etf.rpr;

import org.assertj.core.internal.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class TeacherTest {

    @Test
    void konstruktor() {
        Teacher teacher = new Teacher("ime", "prezime", "adresa", "username", "password", 123456789, LocalTime.of(8, 0), LocalTime.of(15, 0));
        assertEquals("ime", teacher.getFirstName());
        assertEquals("prezime", teacher.getLastName());
        assertEquals("adresa", teacher.getAddress());
        assertEquals("username", teacher.getUsername());
        assertEquals("password", teacher.getPassword());
        assertEquals(123456789, teacher.getPhoneNumber());
        assertEquals(LocalTime.of(8, 0), teacher.getStartOfWorkTime());
        assertEquals(LocalTime.of(15, 0), teacher.getEndOfWorkTime());
    }

    @Test
    void toStringTest() {
        Teacher teacher = new Teacher("ime", "prezime", "adresa", "username", "password", 123456789, LocalTime.of(8, 0), LocalTime.of(15, 0));
        String s = teacher.toString();
        assertEquals("ime prezime", s);
    }

    @Test
    void setteri() {
        Teacher teacher = new Teacher("ime", "prezime", "adresa", "username", "password", 123456789, LocalTime.of(8, 0), LocalTime.of(15, 0));
        teacher.setFirstName("novoime");
        teacher.setLastName("novoprezime");
        teacher.setAddress("novaadresa");
        teacher.setUsername("noviusername");
        teacher.setPassword("novasifra");
        teacher.setPhoneNumber(987654321);
        teacher.setStartOfWorkTime(LocalTime.of(9,0));
        teacher.setEndOfWorkTime(LocalTime.of(16,0));

        assertEquals("novoime", teacher.getFirstName());
        assertEquals("novoprezime", teacher.getLastName());
        assertEquals("novaadresa", teacher.getAddress());
        assertEquals("noviusername", teacher.getUsername());
        assertEquals("novasifra", teacher.getPassword());
        assertEquals(987654321, teacher.getPhoneNumber());
        assertEquals(LocalTime.of(9, 0), teacher.getStartOfWorkTime());
        assertEquals(LocalTime.of(16, 0), teacher.getEndOfWorkTime());
    }
}