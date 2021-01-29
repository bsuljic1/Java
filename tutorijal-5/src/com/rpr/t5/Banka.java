package com.rpr.t5;

import java.util.ArrayList;
import java.util.List;

public class Banka {
    private static Long brojRacuna = 1000000000000000L;
    private List<Uposlenik> uposleni;
    private List<Korisnik> korisnici;

    public Banka() {
        uposleni = new ArrayList<>();
        korisnici = new ArrayList<>();
    }

    public List<Uposlenik> getUposleni() {
        return uposleni;
    }

    public List<Korisnik> getKorisnici() {
        return korisnici;
    }

    public Korisnik kreirajNovogKorisnika(String ime, String prezime) {
        Korisnik user = new Korisnik(ime, prezime);
        korisnici.add(user);
        return user;
    }

    public Uposlenik dodajNovogUposlenog(String ime, String prezime) {
        Uposlenik employee = new Uposlenik(ime, prezime);
        uposleni.add(employee);
        return employee;
    }

    public Racun kreirajRacun(Korisnik user) {
        Racun r = new Racun(brojRacuna++, user);
        user.dodajRacun(r);
        return r;
    }

}
