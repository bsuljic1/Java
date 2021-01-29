package ba.unsa.etf.rpr.t7;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;


public class KorisniciModel {
    private ObservableList<Korisnik> korisnici = FXCollections.observableArrayList();
    private SimpleObjectProperty<Korisnik> trenutniKorisnik = new SimpleObjectProperty<>();

    private static KorisniciModel instance = null;
    private Connection connection = null;
    private PreparedStatement dajKorisnike = null, azurirajKorisnika = null, obrisiKorisnika = null;

    public KorisniciModel() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:korisnici.db");
            dajKorisnike = connection.prepareStatement("SELECT * FROM korisnik");
            azurirajKorisnika = connection.prepareStatement("UPDATE korisnik SET ime=?, prezime=?, email=?, username=?, password=?, slika=? WHERE id =?");
            obrisiKorisnika = connection.prepareStatement("DELETE FROM korisnik WHERE id=?");
        } catch (SQLException e) {
            try {
                regenerisiBazu();
                dajKorisnike = connection.prepareStatement("SELECT * FROM korisnik");
                azurirajKorisnika = connection.prepareStatement("UPDATE korisnik SET ime=?, prezime=?, email=?, username=?,  password=?, slika=? WHERE id =?");
                obrisiKorisnika = connection.prepareStatement("DELETE FROM korisnik WHERE id=?");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static KorisniciModel getInstance() {
        if (instance == null) instance = new KorisniciModel();
        return instance;
    }


    public static void removeInstance() {
        try {
            instance.connection.close();
        } catch (NullPointerException | SQLException e) {
        }
        instance = null;
    }


    private void regenerisiBazu() {
        Scanner ulaz = null;
        try {
            ulaz = new Scanner(new FileInputStream("./src/ba/unsa/etf/rpr/t7/korisnici.sql"));
            String sqlUpit = "";
            while (ulaz.hasNext()) {
                sqlUpit = sqlUpit + ulaz.nextLine();
                if (sqlUpit.length() > 1 && sqlUpit.charAt(sqlUpit.length() - 1) == ';') {
                    try {
                        Statement stmt = connection.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Korisnik> korisnici() {
        ArrayList<Korisnik> listaKorisnika = new ArrayList<>();
        try {
            ResultSet resultSet = dajKorisnike.executeQuery();
            while (resultSet.next()) {
                Korisnik korisnik = new Korisnik(resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5),
                        resultSet.getString(6), resultSet.getString(7));
                korisnik.setId(resultSet.getInt(1));
                listaKorisnika.add(korisnik);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaKorisnika;
    }

    public void azuriranjeKorisnika(Korisnik korisnik) {
        try {
            azurirajKorisnika.clearParameters();
            azurirajKorisnika.setString(1, korisnik.getIme());
            azurirajKorisnika.setString(2, korisnik.getPrezime());
            azurirajKorisnika.setString(3, korisnik.getEmail());
            azurirajKorisnika.setString(4, korisnik.getUsername());
            azurirajKorisnika.setString(5, korisnik.getPassword());
            azurirajKorisnika.setString(6, korisnik.getSlika());
            azurirajKorisnika.setInt(7, korisnik.getId());
            azurirajKorisnika.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void brisanjeKorisnika(Korisnik korisnik) {
        try {
            obrisiKorisnika.clearParameters();
            obrisiKorisnika.setInt(1, korisnik.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void resetujBazu() {
        KorisniciModel.removeInstance();
        File dbfile = new File("baza.db");
        dbfile.delete();
        KorisniciModel dao = KorisniciModel.getInstance();
    }

    public void napuni() {
        korisnici = FXCollections.observableArrayList(korisnici());
        trenutniKorisnik.set(null);
    }

    public ObservableList<Korisnik> getKorisnici() {
        return korisnici;
    }

    public void setKorisnici(ObservableList<Korisnik> korisnici) {
        this.korisnici = korisnici;
    }

    public Korisnik getTrenutniKorisnik() {
        return trenutniKorisnik.get();
    }

    public SimpleObjectProperty<Korisnik> trenutniKorisnikProperty() {
        return trenutniKorisnik;
    }

    public void setTrenutniKorisnik(Korisnik trenutniKorisnik) {
        if (this.trenutniKorisnik.get() != null) azuriranjeKorisnika(this.trenutniKorisnik.get());
        this.trenutniKorisnik.set(trenutniKorisnik);
    }

    public void setTrenutniKorisnik(int i) {
        this.trenutniKorisnik.set(korisnici.get(i));
    }

    public Connection getConnection() {
        return connection;
    }

    public void obrisiKorisnika() {
        brisanjeKorisnika(this.trenutniKorisnik.get());
    }

    public void diskonektuj() {
        if (trenutniKorisnik.get() != null) azuriranjeKorisnika(trenutniKorisnik.get());
        KorisniciModel.removeInstance();
    }

    public void zapisiDatoteku(File datoteka) {
        if (datoteka != null) {
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(new FileWriter(datoteka));
                ArrayList<Korisnik> listaKorisnika = new ArrayList<>(korisnici);
                for (Korisnik korisnik : listaKorisnika) {
                    String username = korisnik.getUsername();
                    String password = korisnik.getPassword();
                    int id = korisnik.getId();
                    String imeIPrezime = korisnik.getIme() + " " + korisnik.getPrezime();
                    String zapisi = username + ":" + password + ":" + id + ":" + id + ":" + imeIPrezime + "::\n";
                    writer.write(zapisi);
                }
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
