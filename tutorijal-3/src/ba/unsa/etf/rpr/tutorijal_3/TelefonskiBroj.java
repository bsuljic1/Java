package ba.unsa.etf.rpr.tutorijal_3;

public abstract class TelefonskiBroj implements Comparable {
    public abstract String ispisi();

    public abstract int hashCode();

    @Override
    public int compareTo(Object o) {
        return ispisi().compareTo(((TelefonskiBroj)o).ispisi());
    }
}





