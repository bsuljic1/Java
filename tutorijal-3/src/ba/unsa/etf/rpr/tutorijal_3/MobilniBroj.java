package ba.unsa.etf.rpr.tutorijal_3;

public class MobilniBroj extends TelefonskiBroj {
    private int mobilnaMreza;
    private String broj;

    public MobilniBroj(int mobilnaMreza, String broj) {
        this.mobilnaMreza = mobilnaMreza;
        this.broj = broj;
    }

    public String ispisi() {
        return "0" + mobilnaMreza + "/" + broj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MobilniBroj that = (MobilniBroj) o;

        if (mobilnaMreza != that.mobilnaMreza) return false;
        return broj != null ? broj.equals(that.broj) : that.broj == null;
    }

    @Override
    public int hashCode() {
        int result = mobilnaMreza;
        result = 31 * result + (broj != null ? broj.hashCode() : 0);
        return result;
    }
}