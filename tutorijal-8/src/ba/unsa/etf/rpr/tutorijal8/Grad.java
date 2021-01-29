package ba.unsa.etf.rpr.tutorijal8;

public class Grad {
    private String naziv = "";
    private int brojStanovnika = 0;
    private double[] temperature = new double[1000];

    public Grad(){}

    public Grad(String naziv, double[] temperature){
        this.naziv = naziv;
        this.temperature = temperature;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getBrojStanovnika() {
        return brojStanovnika;
    }

    public void setBrojStanovnika(int brojStanovnika) {
        this.brojStanovnika = brojStanovnika;
    }

    public double[] getTemperature() {
        return temperature;
    }

    public void setTemperature(double[] temperature) {
        this.temperature = temperature;
    }
}
