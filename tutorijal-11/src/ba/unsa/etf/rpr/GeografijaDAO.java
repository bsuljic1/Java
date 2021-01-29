package ba.unsa.etf.rpr;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class GeografijaDAO {
    private static GeografijaDAO instance = null;
    private static PreparedStatement GradStatement = null, GlavniGradStatement = null, ObrisiGradoveDrzaveStatement = null, ObrisiDrzavuStatement = null;
    private static PreparedStatement dodajGradStatement = null, dodajDrzavuStatement = null, izmijeniGradStatement = null, nadjiDrzavuStatement = null;
    private static PreparedStatement gradIdStatement = null, gradNazivStatement = null, drzaveStatement = null, nadjiGradStatement = null, obrisiGradStatement = null;
    private Connection connection = null;

    private GeografijaDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:baza.db");
            drzaveStatement = connection.prepareStatement("SELECT * from drzava");
            GradStatement = connection.prepareStatement("SELECT * FROM grad LEFT JOIN drzava ON drzava.id = grad.drzava ORDER BY broj_stanovnika DESC");
            gradIdStatement = connection.prepareStatement("SELECT * FROM grad WHERE id = ?");
            gradNazivStatement = connection.prepareStatement("SELECT * FROM grad WHERE naziv = ?");
            GlavniGradStatement = connection.prepareStatement("SELECT * from grad g INNER JOIN drzava d ON g.id = d.glavni_grad WHERE d.naziv=?");
            ObrisiGradoveDrzaveStatement = connection.prepareStatement("DELETE FROM grad WHERE drzava = (SELECT id FROM drzava WHERE drzava.naziv = ?) ");
            ObrisiDrzavuStatement = connection.prepareStatement("DELETE FROM drzava WHERE naziv = ?");
            dodajGradStatement = connection.prepareStatement("INSERT INTO grad VALUES (?,?,?,?)");
            dodajDrzavuStatement = connection.prepareStatement("INSERT INTO drzava VALUES (?,?,?)");
            izmijeniGradStatement = connection.prepareStatement("UPDATE grad SET naziv = ?, broj_stanovnika = ? WHERE id = ? ");
            nadjiDrzavuStatement = connection.prepareStatement("SELECT * FROM drzava WHERE naziv = ?");
            nadjiGradStatement = connection.prepareStatement("SELECT * FROM grad WHERE naziv = ?");
            obrisiGradStatement = connection.prepareStatement("DELETE FROM grad WHERE naziv = ?");
        } catch (SQLException e) {
            try {
                regenerisiBazu();
                drzaveStatement = connection.prepareStatement("SELECT * from drzava");
                GradStatement = connection.prepareStatement("SELECT * FROM grad LEFT JOIN drzava ON drzava.id = grad.drzava ORDER BY broj_stanovnika DESC");
                gradIdStatement = connection.prepareStatement("SELECT * FROM grad WHERE id = ?");
                gradNazivStatement = connection.prepareStatement("SELECT * FROM grad WHERE naziv = ?");
                GlavniGradStatement = connection.prepareStatement("SELECT * from grad g INNER JOIN drzava d ON g.id = d.glavni_grad WHERE d.naziv=?");
                ObrisiGradoveDrzaveStatement = connection.prepareStatement("DELETE FROM grad WHERE drzava = (SELECT id FROM drzava WHERE drzava.naziv = ?) ");
                ObrisiDrzavuStatement = connection.prepareStatement("DELETE FROM drzava WHERE naziv = ?");
                dodajGradStatement = connection.prepareStatement("INSERT INTO grad VALUES (?,?,?,?)");
                dodajDrzavuStatement = connection.prepareStatement("INSERT INTO drzava VALUES (?,?,?)");
                izmijeniGradStatement = connection.prepareStatement("UPDATE grad SET naziv = ?, broj_stanovnika = ? WHERE id = ? ");
                nadjiDrzavuStatement = connection.prepareStatement("SELECT * FROM drzava WHERE naziv = ?");
                nadjiGradStatement = connection.prepareStatement("SELECT * FROM grad WHERE naziv = ?");
                obrisiGradStatement = connection.prepareStatement("DELETE FROM grad WHERE naziv = ?");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static GeografijaDAO getInstance() {
        if (instance == null) instance = new GeografijaDAO();
        return instance;
    }

    public Connection getConnection(){
        return connection;
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
            ulaz = new Scanner(new FileInputStream("./src/ba/unsa/etf/rpr/baza.db.sql"));
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

    public static ArrayList<Grad> gradovi() {
        ArrayList<Grad> ret = new ArrayList<>();
        try {
            ResultSet resultSet = GradStatement.executeQuery();
            while (resultSet.next()) {
                Drzava drzava = new Drzava();
                Grad grad = new Grad();
                grad.setId(resultSet.getInt(1));
                grad.setNaziv(resultSet.getString(2));
                grad.setBrojStanovnika(resultSet.getInt(3));
                drzava.setNaziv(resultSet.getString(7));
                drzava.setId(resultSet.getInt(6));
                grad.setDrzava(drzava);
                ret.add(grad);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static ArrayList<Drzava> drzave() {
        ArrayList<Drzava> ret = new ArrayList<>();
        try {
            ResultSet resultSet = drzaveStatement.executeQuery();
            while (resultSet.next()) {
                Drzava drzava = new Drzava();
                drzava.setNaziv(resultSet.getString(2));
                drzava.setId(resultSet.getInt(1));
                Grad grad = new Grad();
                gradIdStatement.clearParameters();
                gradIdStatement.setInt(1, resultSet.getInt(3));
                ResultSet rs = gradIdStatement.executeQuery();
                grad.setId(resultSet.getInt(3));
                grad.setNaziv(rs.getString(2));
                grad.setBrojStanovnika(rs.getInt(3));
                grad.setDrzava(drzava);
                drzava.setGlavniGrad(grad);
                ret.add(drzava);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public Grad glavniGrad(String naziv) {
        Grad glavni = null;
        Drzava drzava = null;
        try {
            GlavniGradStatement.clearParameters();
            GlavniGradStatement.setString(1, naziv);
            ResultSet resultSet = GlavniGradStatement.executeQuery();
            while (resultSet.next()) {
                glavni = new Grad();
                drzava = new Drzava();
                glavni.setId(resultSet.getInt(1));
                glavni.setNaziv(resultSet.getString(2));
                glavni.setBrojStanovnika(resultSet.getInt(3));
                drzava.setNaziv(resultSet.getString(7));
                drzava.setId(resultSet.getInt(6));
                glavni.setDrzava(drzava);
                drzava.setGlavniGrad(glavni);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return glavni;
    }

    public void obrisiDrzavu(String naziv) {
        try {
            ObrisiGradoveDrzaveStatement.setString(1, naziv);
            ObrisiGradoveDrzaveStatement.executeUpdate();
            ObrisiDrzavuStatement.setString(1, naziv);
            ObrisiDrzavuStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void obrisiGrad(String naziv) {
        try {
            obrisiGradStatement.setString(1, naziv);
            obrisiGradStatement.executeUpdate();
            obrisiGradStatement.setString(1, naziv);
            obrisiGradStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Drzava nadjiDrzavu(String naziv) {
        Drzava drzava = new Drzava();
        try {
            nadjiDrzavuStatement.setString(1, naziv);
            ResultSet res = nadjiDrzavuStatement.executeQuery();
            drzava.setId(res.getInt(1));
            drzava.setNaziv(res.getString(2));
            return drzava;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void dodajGrad(Grad grad) {
        try {
            dodajGradStatement.setInt(1, grad.getId());
            dodajGradStatement.setString(2, grad.getNaziv());
            dodajGradStatement.setInt(3, grad.getBrojStanovnika());
            if (grad.getDrzava() != null) dodajGradStatement.setInt(4, grad.getDrzava().getId());
            else dodajGradStatement.setInt(4, -1);
            dodajGradStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dodajDrzavu(Drzava drzava) {
        try {
            dodajDrzavuStatement.setInt(1, drzava.getId());
            dodajDrzavuStatement.setString(2, drzava.getNaziv());
            if (drzava.getGlavniGrad() == null) dodajDrzavuStatement.setInt(3, -1);
            else dodajDrzavuStatement.setInt(3, drzava.getGlavniGrad().getId());
            dodajDrzavuStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void izmijeniGrad(Grad grad) {
        try {
            gradIdStatement.clearParameters();
            gradIdStatement.setInt(1, grad.getId());
            ResultSet resultSet = gradIdStatement.executeQuery();
            while (resultSet.next()) {
                izmijeniGradStatement.clearParameters();
                izmijeniGradStatement.clearParameters();
                izmijeniGradStatement.setString(1, grad.getNaziv());
                izmijeniGradStatement.setInt(2, grad.getBrojStanovnika());
                izmijeniGradStatement.setInt(3, grad.getId());
                izmijeniGradStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PreparedStatement getGlavniGradStatement() {
        return GlavniGradStatement;
    }

    public void setGlavniGradStatement(PreparedStatement glavniGradStatement) {
        GlavniGradStatement = glavniGradStatement;
    }

    public PreparedStatement getGradStatement() {
        return GradStatement;
    }

    public void setGradStatement(PreparedStatement gradStatement) {
        GradStatement = gradStatement;
    }

    public Grad nadjiGrad(String naziv) {
        Grad grad = new Grad();
        try {
            nadjiGradStatement.setString(1, naziv);
            ResultSet res = nadjiGradStatement.executeQuery();
            grad.setId(res.getInt(1));
            grad.setNaziv(res.getString(2));
            grad.setBrojStanovnika(res.getInt(3));
            return grad;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
