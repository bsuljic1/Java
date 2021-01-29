package ba.unsa.etf.rpr;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class ActivityTest {

    @Test
    void konstruktor() {
        Activity activity = new Activity("aktivnost", "nova aktivnost");
        assertEquals("aktivnost", activity.getName());
        assertEquals("nova aktivnost", activity.getDescription());
    }

    @Test
    void toStringTest() {
        Activity activity = new Activity("aktivnost", "nova aktivnost");
        String s = activity.toString();
        assertEquals("aktivnost", s);
    }

    @Test
    void setteri() {
        Activity activity = new Activity("aktivnost", "nova aktivnost");
        activity.setName("aktivnostaktivnost");
        activity.setDescription("novanova");
        assertEquals("aktivnostaktivnost", activity.getName());
        assertEquals("novanova", activity.getDescription());
    }
}