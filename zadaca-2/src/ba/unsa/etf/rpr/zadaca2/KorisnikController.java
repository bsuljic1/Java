package ba.unsa.etf.rpr.zadaca2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class KorisnikController {
    public TextField fldIme;
    public TextField fldPrezime;
    public TextField fldEmail;
    public TextField fldUsername;
    public ListView<Korisnik> listKorisnici;
    public PasswordField fldPassword;
    public PasswordField fldPasswordRepeat;
    public Slider sliderGodinaRodjenja;
    public CheckBox cbAdmin;

    private KorisniciModel model;

    public KorisnikController(KorisniciModel model) {
        this.model = model;
    }

    @FXML
    public void initialize() {
        listKorisnici.setItems(model.getKorisnici());

        listKorisnici.getSelectionModel().selectedItemProperty().addListener((obs, oldKorisnik, newKorisnik) -> {
            model.setTrenutniKorisnik(newKorisnik);
            listKorisnici.refresh();
        });

        model.trenutniKorisnikProperty().addListener((obs, oldKorisnik, newKorisnik) -> {
            if (oldKorisnik != null) {
                fldIme.textProperty().unbindBidirectional(oldKorisnik.imeProperty());
                fldPrezime.textProperty().unbindBidirectional(oldKorisnik.prezimeProperty());
                fldEmail.textProperty().unbindBidirectional(oldKorisnik.emailProperty());
                fldUsername.textProperty().unbindBidirectional(oldKorisnik.usernameProperty());
                fldPassword.textProperty().unbindBidirectional(oldKorisnik.passwordProperty());
                sliderGodinaRodjenja.valueProperty().unbindBidirectional(oldKorisnik.godinaRodjenjaProperty());
            }
            if (newKorisnik == null) {
                fldIme.setText("");
                fldPrezime.setText("");
                fldEmail.setText("");
                fldUsername.setText("");
                fldPassword.setText("");
                fldPasswordRepeat.setText("");
                sliderGodinaRodjenja.setValue(2000);
            } else {
                fldIme.textProperty().bindBidirectional(newKorisnik.imeProperty());
                fldPrezime.textProperty().bindBidirectional(newKorisnik.prezimeProperty());
                fldEmail.textProperty().bindBidirectional(newKorisnik.emailProperty());
                fldUsername.textProperty().bindBidirectional(newKorisnik.usernameProperty());
                fldPassword.textProperty().bindBidirectional(newKorisnik.passwordProperty());
                fldPasswordRepeat.textProperty().setValue(newKorisnik.getPassword());
                sliderGodinaRodjenja.valueProperty().bindBidirectional(newKorisnik.godinaRodjenjaProperty());
                sliderGodinaRodjenja.adjustValue(newKorisnik.getGodinaRodjenja());
                cbAdmin.selectedProperty().setValue(newKorisnik instanceof Administrator);
            }
        });

        fldIme.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty() && newIme.matches("([a-zA-Z -]){3,}")) {
                fldIme.getStyleClass().removeAll("poljeNijeIspravno");
                fldIme.getStyleClass().add("poljeIspravno");
            } else {
                fldIme.getStyleClass().removeAll("poljeIspravno");
                fldIme.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldPrezime.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty() && newIme.matches("([a-zA-Z -]){3,}")) {
                fldPrezime.getStyleClass().removeAll("poljeNijeIspravno");
                fldPrezime.getStyleClass().add("poljeIspravno");
            } else {
                fldPrezime.getStyleClass().removeAll("poljeIspravno");
                fldPrezime.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldEmail.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty() && newIme.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")) {
                fldEmail.getStyleClass().removeAll("poljeNijeIspravno");
                fldEmail.getStyleClass().add("poljeIspravno");
            } else {
                fldEmail.getStyleClass().removeAll("poljeIspravno");
                fldEmail.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldUsername.textProperty().addListener((obs, oldIme, newIme) -> {
            boolean validno = true;
            if (!newIme.isEmpty() && !(newIme.charAt(0) >= 'A' && newIme.charAt(0) <= 'z') && !(newIme.charAt(0) == '$') && !(newIme.charAt(0) == '_'))
                validno = false;
            for (int i = 1; i < newIme.length(); i++) {
                if (!(newIme.charAt(i) >= 'A' && newIme.charAt(i) <= 'z') && !(newIme.charAt(i) >= '0' && newIme.charAt(i) <= '9') && !(newIme.charAt(i) == '$') && !(newIme.charAt(i) == '_'))
                    validno = false;
            }
            if (!newIme.isEmpty() && newIme.length() <= 16 && validno) {
                fldUsername.getStyleClass().removeAll("poljeNijeIspravno");
                fldUsername.getStyleClass().add("poljeIspravno");
            } else {
                fldUsername.getStyleClass().removeAll("poljeIspravno");
                fldUsername.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldPassword.textProperty().addListener((obs, oldIme, newIme) -> {
            boolean validno = true;
            if (model.getTrenutniKorisnik() != null) {
                model.getTrenutniKorisnik().setPassword(newIme);
                validno = model.getTrenutniKorisnik().checkPassword();
            }
            if (!newIme.isEmpty() && fldPassword.getText().equals(fldPasswordRepeat.getText()) && validno) {
                fldPassword.getStyleClass().removeAll("poljeNijeIspravno");
                fldPassword.getStyleClass().add("poljeIspravno");
                fldPasswordRepeat.getStyleClass().removeAll("poljeNijeIspravno");
                fldPasswordRepeat.getStyleClass().add("poljeIspravno");
            } else {
                fldPassword.getStyleClass().removeAll("poljeIspravno");
                fldPassword.getStyleClass().add("poljeNijeIspravno");
                fldPasswordRepeat.getStyleClass().removeAll("poljeIspravno");
                fldPasswordRepeat.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldPasswordRepeat.textProperty().addListener((obs, oldIme, newIme) -> {
            boolean validno = true;
            if (model.getTrenutniKorisnik() != null) {
                validno = model.getTrenutniKorisnik().checkPassword();
            }
            if (!newIme.isEmpty() && fldPassword.getText().equals(fldPasswordRepeat.getText()) && validno) {
                fldPassword.getStyleClass().removeAll("poljeNijeIspravno");
                fldPassword.getStyleClass().add("poljeIspravno");
                fldPasswordRepeat.getStyleClass().removeAll("poljeNijeIspravno");
                fldPasswordRepeat.getStyleClass().add("poljeIspravno");
            } else {
                fldPassword.getStyleClass().removeAll("poljeIspravno");
                fldPassword.getStyleClass().add("poljeNijeIspravno");
                fldPasswordRepeat.getStyleClass().removeAll("poljeIspravno");
                fldPasswordRepeat.getStyleClass().add("poljeNijeIspravno");
            }
        });


        sliderGodinaRodjenja.valueProperty().addListener((obs, oldValue, newValue) -> {
            model.setGodina(model.getTrenutniKorisnik(), newValue);
        });

        cbAdmin.selectedProperty().addListener((obs, oldValue, newValue) -> {
            Korisnik trenutni = model.getTrenutniKorisnik();
            if (trenutni == null) return;
            if (newValue) {
                Administrator admin = new Administrator(trenutni.getIme(), trenutni.getPrezime(), trenutni.getEmail(), trenutni.getUsername(), trenutni.getPassword());
                model.getKorisnici().set(model.getKorisnici().indexOf(model.getTrenutniKorisnik()), admin);
            } else {
                Korisnik korisnik = new Korisnik(trenutni.getIme(), trenutni.getPrezime(), trenutni.getEmail(), trenutni.getUsername(), trenutni.getPassword());
                model.getKorisnici().set(model.getKorisnici().indexOf(model.getTrenutniKorisnik()), korisnik);
            }
        });

    }

    public void dodajAction(ActionEvent actionEvent) {
        model.getKorisnici().add(new Korisnik("", "", "", "", ""));
        listKorisnici.getSelectionModel().selectLast();
    }

    public void krajAction(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void obrisiAction(ActionEvent actionEvent) {
        model.obrisiKorisnika();
    }

    public void generisiAction(ActionEvent actionEvent) {
        model.generisiKorisnika();
        String sifra = model.generisiSifru();
        fldPassword.setText(sifra);
        fldPasswordRepeat.setText(sifra);
        listKorisnici.refresh();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Vaša lozinka glasi: " + sifra);
        alert.showAndWait();
    }

}
