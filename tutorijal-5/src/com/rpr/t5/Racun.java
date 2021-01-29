package com.rpr.t5;

public class Racun {
    private final Long brojRacuna;
    private final Osoba korisnikRacuna;
    private Double stanjeRacuna;
    private boolean odobrenjePrekoracenja;

    public Racun(Long brojRacuna, Osoba korisnikRacuna) {
        this.brojRacuna = brojRacuna;
        this.korisnikRacuna = korisnikRacuna;
        this.stanjeRacuna = 0d;
        this.odobrenjePrekoracenja = false;
    }

    boolean izvrsiUplatu(Double iznos) {
        stanjeRacuna += iznos;
        return true;
    }

    boolean izvrsiIsplatu(Double iznos) {
        if(stanjeRacuna.doubleValue() <= 0)
                provjeriOdobrenjePrekoracenja(iznos);
        else{
            double temp = stanjeRacuna.doubleValue();
            if((temp - iznos) < 0) provjeriOdobrenjePrekoracenja(iznos);
            else stanjeRacuna -= iznos;
        }
        return true;
    }

    public Double getStanjeRacuna() {
        return stanjeRacuna;
    }

    boolean provjeriOdobrenjePrekoracenja(Double iznos) {
        if (odobrenjePrekoracenja)
            stanjeRacuna -= iznos;
        else
            throw new IllegalArgumentException("Racun nema odobrenje prekoracenja!");
        return true;
    }

    public void odobriPrekoracenje() {
        odobrenjePrekoracenja = true;
    }

    public Long getBrojRacuna() {
        return brojRacuna;
    }

    public Osoba getKorisnikRacuna() {
        return korisnikRacuna;
    }

    public boolean isOdobrenjePrekoracenja() {
        return odobrenjePrekoracenja;
    }

}
