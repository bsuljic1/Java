package ba.unsa.etf.rpr.zadaca2;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class KorisniciModel {
    private ObservableList<Korisnik> korisnici = FXCollections.observableArrayList();
    private SimpleObjectProperty<Korisnik> trenutniKorisnik = new SimpleObjectProperty<>();

    public KorisniciModel() {
    }

    public void napuni() {
        korisnici.add(new Korisnik("Vedran", "Ljubović", "vljubovic@etf.unsa.ba", "vedranlj", "test"));
        korisnici.add(new Korisnik("Amra", "Delić", "adelic@etf.unsa.ba", "amrad", "test"));
        korisnici.add(new Korisnik("Tarik", "Sijerčić", "tsijercic1@etf.unsa.ba", "tariks", "test"));
        korisnici.add(new Korisnik("Rijad", "Fejzić", "rfejzic1@etf.unsa.ba", "rijadf", "test"));
        trenutniKorisnik.set(null);
    }

    public ObservableList<Korisnik> getKorisnici() {
        return korisnici;
    }

    public void setKorisnici(ObservableList<Korisnik> korisnici) {
        this.korisnici = korisnici;
    }

    public Korisnik getTrenutniKorisnik() {
        return trenutniKorisnik.get();
    }


    public SimpleObjectProperty<Korisnik> trenutniKorisnikProperty() {
        return trenutniKorisnik;
    }

    public void setTrenutniKorisnik(Korisnik trenutniKorisnik) {
        this.trenutniKorisnik.set(trenutniKorisnik);
    }

    public void setTrenutniKorisnik(int i) {
        this.trenutniKorisnik.set(korisnici.get(i));
    }

    public void setGodina(Korisnik korisnik, Number godina) {
        korisnik.setGodinaRodjenja(godina);
    }


    public void obrisiKorisnika() {
        korisnici.remove(trenutniKorisnik.get());
        if (korisnici.size() != 0) this.trenutniKorisnik.set(korisnici.get(korisnici.size() - 1));
        else trenutniKorisnik.setValue(null);
    }

    public void generisiKorisnika() {
        if (trenutniKorisnik.get().getIme().equals("") || trenutniKorisnik.get().getPrezime().equals("")) return;
        String ime = trenutniKorisnik.get().getIme().toLowerCase();
        String prezime = trenutniKorisnik.get().getPrezime().toLowerCase();
        for (int i = 0; i < prezime.length(); i++) {
            if (prezime.charAt(i) == 'č') prezime = prezime.replace('č', 'c');
            if (prezime.charAt(i) == 'ć') prezime = prezime.replace('ć', 'c');
            if (prezime.charAt(i) == 'š') prezime = prezime.replace('š', 's');
            if (prezime.charAt(i) == 'ž') prezime = prezime.replace('ž', 'z');
            if (prezime.charAt(i) == 'đ') prezime = prezime.replace('đ', 'd');
        }
        if (ime.charAt(0) == 'č') ime = ime.replace('č', 'c');
        if (ime.charAt(0) == 'ć') ime = ime.replace('ć', 'c');
        if (ime.charAt(0) == 'š') ime = ime.replace('š', 's');
        if (ime.charAt(0) == 'ž') ime = ime.replace('ž', 'z');
        if (ime.charAt(0) == 'đ') ime = ime.replace('đ', 'd');
        String korIme = "";
        korIme += ime.charAt(0);
        korIme += prezime;
        trenutniKorisnik.get().setUsername(korIme);

    }

    public String generisiSifru() {
        String sifra;
        if (getTrenutniKorisnik() instanceof Administrator) sifra = RandomStringAdmin.getString();
        else sifra = RandomString.getString();
        getTrenutniKorisnik().setPassword(sifra);
        return sifra;
    }
}
