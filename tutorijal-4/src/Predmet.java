package ba.unsa.etf.rpr.tut4;

public class Predmet {
    private int ects_kredit;
    private Student[] studenti_na_predmetu = new Student[];
    int broj_studenata = 0;

    public Predmet(int ects_kredit){
        this.ects_kredit = ects_kredit;
    }

    public int dajBrojKreditaPredmeta(){return ects_kredit;}

    public void DodajStudenta(Student s){
        studenti_na_predmetu[broj_studenata] = s;
        broj_studenata++;
    }

    public void SpisakTrenutnoUpisanih(){
        for(Student s : studenti_na_predmetu)
            System.out.println(s.dajIme());
    }
}
