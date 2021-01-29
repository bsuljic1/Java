package ba.unsa.etf.rpr.tutorijal_3;

public class MedunarodniBroj extends TelefonskiBroj {
    private String drzava;
    private String broj;

    MedunarodniBroj(String drzava, String broj) {
        this.drzava = drzava;
        this.broj = broj;
    }

    public String ispisi() {
        return drzava + broj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MedunarodniBroj that = (MedunarodniBroj) o;

        if (drzava != null ? !drzava.equals(that.drzava) : that.drzava != null) return false;
        return broj != null ? broj.equals(that.broj) : that.broj == null;
    }

    @Override
    public int hashCode() {
        int result = drzava != null ? drzava.hashCode() : 0;
        result = 31 * result + (broj != null ? broj.hashCode() : 0);
        return result;
    }
}