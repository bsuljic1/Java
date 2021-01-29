package ba.unsa.etf.rpr.zadaca2;

public class Administrator extends Korisnik {

    public Administrator(String ime, String prezime, String email, String username, String password) {
        super(ime, prezime, email, username, password);
    }

    @Override
    boolean checkPassword() {
        boolean sadrziZnak = false;
        for (int i = 0; i < getPassword().length(); i++)
            if (!(getPassword().charAt(i) >= 'A' && getPassword().charAt(i) <= 'Z') && !(getPassword().charAt(i) >= 'a' && getPassword().charAt(i) <= 'z') && !(getPassword().charAt(i) >= '0' && getPassword().charAt(i) <= '9'))
                sadrziZnak = true;

        return super.checkPassword() && sadrziZnak;
    }
}
