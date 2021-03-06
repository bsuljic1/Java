package ba.unsa.etf.rpr.zadaca2;

public class RandomString {
    static String getString() {
        String velika = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String brojevi = "0123456789";
        String mala = "abcdefghijklmnopqrstuvxyz";
        String svi = velika + brojevi + mala;

        StringBuilder sifra = new StringBuilder(8);
        sifra.append(velika.charAt((int) (velika.length() * Math.random())));
        sifra.append(brojevi.charAt((int) (brojevi.length() * Math.random())));
        sifra.append(mala.charAt((int) (mala.length() * Math.random())));
        for (int i = 0; i < 5; i++) {
            sifra.append(svi.charAt((int) (svi.length() * Math.random())));
        }
        return sifra.toString();
    }
}
