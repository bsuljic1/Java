package ba.unsa.etf.rpr.tut4;

public class Student {
    private String ime;
    private int broj_indeksa;
    private Semestar semestar = new Semestar();

    public String dajIme(){return ime;}
    public int DajBrojIndeksa(){return broj_indeksa;}

    public void UpisiNaSemestar(){}
}
