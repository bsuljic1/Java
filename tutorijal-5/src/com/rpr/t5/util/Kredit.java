package com.rpr.t5.util;

import com.rpr.t5.Korisnik;

import java.util.List;

public class Kredit {

    public static Double proracunKreditneSposobnosti(KreditnaSposobnost f, Korisnik user) {
        return f.provjeri(user.getRacun());
    }

    public static void ispisiSveKorisnikeBezPrekoracenja(List<Korisnik> lista) {
        lista.stream().filter(user -> user.getRacun().getStanjeRacuna() > 0).forEach(System.out::println);
    }

    public static void odobriPrekoracenje(List<Korisnik> lista) {
        lista.stream().filter(user -> user.getRacun().getStanjeRacuna() > 10000).forEach(user -> user.getRacun().odobriPrekoracenje());
    }
}
