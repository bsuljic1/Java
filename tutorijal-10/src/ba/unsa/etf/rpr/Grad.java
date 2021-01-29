package ba.unsa.etf.rpr;

public class Grad {
    private int id = -1;
    private int brojStanovnika;
    private String naziv;
    private Drzava drzava;

    public Grad() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Grad(int id, int brojStanovnika, String naziv, Drzava drzava) {
        this.id = id;
        this.brojStanovnika = brojStanovnika;
        this.naziv = naziv;
        this.drzava = drzava;
    }


    public int getBrojStanovnika() {
        return brojStanovnika;
    }

    public void setBrojStanovnika(int brojStanovnika) {
        this.brojStanovnika = brojStanovnika;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Drzava getDrzava() {
        return drzava;
    }

    public void setDrzava(Drzava drzava) {
        this.drzava = drzava;
    }

    @Override
    public String toString() {
        return naziv;
    }
}
