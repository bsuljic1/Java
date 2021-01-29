package ba.unsa.etf.rpr;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProgramTest {

    @Test
    void main() {
        String unos = "e3\n" +
                "c5\n" +
                "f4\n" +
                "d6\n" +
                "Na3\n" +
                "Nf6\n" +
                "Bc4\n" +
                "Qa5\n" +
                "c3\n" +
                "Qc3\n" +
                "c3\n" +
                "Bg4\n" +
                "Qg4\n" +
                "d5\n" +
                "Qc8\n" +
                "X\n";

        System.setIn(new ByteArrayInputStream(unos.getBytes()));
        OutputStream ispis = new ByteArrayOutputStream();
        System.setOut(new PrintStream(ispis));
        Program.main(null);
        assertEquals("White move: \r\nBlack move: \r\nWhite move: \r\nBlack move: \r\nWhite move: \r\nBlack move: \r\n" +
                "White move: \r\nBlack move: \r\nCHECK!!!\r\nWhite move: \r\nBlack move: \r\nWhite move: \r\nBlack move: \r\nWhite move: \r\nBlack move: \r\n" +
                "White move: \r\nCHECK!!!\r\nBlack move: \r\nBlack surrendered\r\n", ispis.toString());
    }
}