package ba.unsa.etf.rpr.tutorijal_3;

public class FiksniBroj extends TelefonskiBroj {
    private Grad grad;
    private String broj;

    public FiksniBroj(Grad grad, String broj) {
        this.grad = grad;
        this.broj = broj;
    }

    public Grad getGrad() {
        return grad;
    }

    public String ispisi() {
        String s;
        switch (grad) {
            case TRAVNIK:
                s = "030";
                break;
            case ORASJE:
                s = "031";
                break;
            case ZENICA:
                s = "032";
                break;
            case SARAJEVO:
                s = "033";
                break;
            case LIVNO:
                s = "034";
                break;
            case TUZLA:
                s = "035";
                break;
            case MOSTAR:
                s = "036";
                break;
            case BIHAC:
                s = "037";
                break;
            case GORAZDE:
                s = "038";
                break;
            case SIROKIBRIJEG:
                s = "039";
                break;
            default:
                s = "049";
                break;
        }
        return s + "/" + broj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FiksniBroj that = (FiksniBroj) o;

        if (grad != that.grad) return false;
        return broj != null ? broj.equals(that.broj) : that.broj == null;
    }


    @Override
    public int hashCode() {
        int result = grad != null ? grad.hashCode() : 0;
        result = 31 * result + (broj != null ? broj.hashCode() : 0);
        return result;
    }
}
