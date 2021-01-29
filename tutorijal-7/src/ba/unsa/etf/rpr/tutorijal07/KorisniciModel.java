package ba.unsa.etf.rpr.tutorijal07;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class KorisniciModel {
    private ObservableList<Korisnik> lista = FXCollections.observableArrayList();
    private SimpleObjectProperty<Korisnik> trenutni = new SimpleObjectProperty<>();

    public KorisniciModel(ObservableList<Korisnik> lista, SimpleObjectProperty<Korisnik> trenutni) {
        this.lista = lista;
        this.trenutni = trenutni;
    }

    public KorisniciModel() {
    }

    public ObservableList<Korisnik> getLista() {
        return lista;
    }

    public void setLista(ObservableList<Korisnik> lista) {
        this.lista = lista;
    }

    public Korisnik getTrenutni() {
        return trenutni.get();
    }


    public SimpleObjectProperty<Korisnik> trenutniProperty() {
        return trenutni;
    }

    public void setTrenutni(Korisnik trenutni) {
        this.trenutni.set(trenutni);
    }

    public void napuni() {
        Korisnik k1 = new Korisnik("Berina", "Suljic", "bsuljic1@etf.unsa.ba", "bsuljic1", "sklj");
        Korisnik k2 = new Korisnik("Meho", "Mehic", "mmehic1@etf.unsa.ba", "mmehic1", "mmm");
        Korisnik k3 = new Korisnik("Sara", "Saric", "ssaric1@etf.unsa.ba", "ssaric1", "sss");
        lista.add(k1);
        lista.add(k2);
        lista.add(k3);
        trenutni.set(null);
    }

    public void dodaj(){
        Korisnik korisnik = new Korisnik("", "", "", "", "");
        lista.add(korisnik);
        setTrenutni(korisnik);
    }
}
