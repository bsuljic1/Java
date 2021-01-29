package ba.unsa.etf.rpr;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Gradovi su:\n" + ispisiGradove());
        glavniGrad();
    }

    public static String ispisiGradove() {
        String ret = "";
        ArrayList<Grad> gradovi = GeografijaDAO.gradovi();
        if (!gradovi.isEmpty())
            for (Grad grad : gradovi)
                ret += grad.getNaziv() + " (" + grad.getDrzava().getNaziv() + ") - " + grad.getBrojStanovnika() + "\n";
        return ret;
    }

    public static void glavniGrad() {
        String ispisi = "";
        System.out.println("Unesite naziv drzave:\n ");
        Scanner unesi = new Scanner("");
        String naziv = unesi.nextLine();
        Drzava drzava = GeografijaDAO.nadjiDrzavu(naziv);
        if (drzava != null) ispisi += "Glavni grad države " + naziv + " je " + drzava.getGlavniGrad();
        else ispisi += "Nepostojeća država";
        System.out.println(ispisi);
    }

}
