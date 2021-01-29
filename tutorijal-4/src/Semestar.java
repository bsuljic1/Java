package ba.unsa.etf.rpr.tut4;

public class Semestar {
    private Predmet[50] obavezni = new Predmet[50];
    private Predmet[50] izborni = new Predmet[50];

    public int DajBrojKreditaSemestra(){
        int broj = 0;
        for(Predmet p : obavezni)
            broj += p.dajBrojKreditaPredmeta();
        for(Predmet p : izborni)
            broj += p.dajBrojKreditaPredmeta();
        return broj;
    }


}
