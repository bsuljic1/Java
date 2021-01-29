package ba.unsa.etf.rpr.tutorijal_3;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

public class Imenik {
    private HashMap<String, TelefonskiBroj> mapa = new HashMap();

    void dodaj(String ime, TelefonskiBroj broj) {
        mapa.put(ime, broj);
    }

    String dajBroj(String ime) {
        return mapa.get(ime).ispisi();
    }

    String dajIme(TelefonskiBroj broj) {
        for (String ime : mapa.keySet())
            if (mapa.get(ime).hashCode() == broj.hashCode()) return ime;
        return null;
    }

    String naSlovo(char s) {
        int br = 0;
        String str = "";
        for (String ime : mapa.keySet()) {
            if (ime.charAt(0) == s) {
                br++;
                if (br != 1) str += "\n";
                str = str + br + ". " + ime + " - " + mapa.get(ime).ispisi();
            }
        }
        return str;
    }

    Set<String> izGrada(Grad g) {
        Set<String> s = new TreeSet<>();
        for (String ime : mapa.keySet())
            if (mapa.get(ime) instanceof FiksniBroj && ((FiksniBroj) mapa.get(ime)).getGrad() == g)
                s.add(ime);
        return s;
    }

    Set<TelefonskiBroj> izGradaBrojevi(Grad g) {
        Set<TelefonskiBroj> s = new TreeSet<>();
        for (String ime : mapa.keySet())
            if (mapa.get(ime) instanceof FiksniBroj && ((FiksniBroj) mapa.get(ime)).getGrad() == g)
                s.add(mapa.get(ime));
        return s;
    }

}