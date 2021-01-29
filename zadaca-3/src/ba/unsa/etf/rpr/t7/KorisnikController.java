package ba.unsa.etf.rpr.t7;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import static javafx.application.Platform.exit;

public class KorisnikController {
    public TextField fldIme;
    public TextField fldPrezime;
    public TextField fldEmail;
    public TextField fldUsername;
    public ListView<Korisnik> listKorisnici;
    public PasswordField fldPassword;
    public MenuBar menuBar;
    public ImageView imageView;

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
                fldIme.textProperty().unbindBidirectional(oldKorisnik.imeProperty() );
                fldPrezime.textProperty().unbindBidirectional(oldKorisnik.prezimeProperty() );
                fldEmail.textProperty().unbindBidirectional(oldKorisnik.emailProperty() );
                fldUsername.textProperty().unbindBidirectional(oldKorisnik.usernameProperty() );
                fldPassword.textProperty().unbindBidirectional(oldKorisnik.passwordProperty() );
            }
            if (newKorisnik == null) {
                fldIme.setText("");
                fldPrezime.setText("");
                fldEmail.setText("");
                fldUsername.setText("");
                fldPassword.setText("");
                imageView.setImage(new Image(getClass().getClassLoader().getResource("slike/face-smile.png").toString(), 128, 128, false, false));
            }
            else {
                fldIme.textProperty().bindBidirectional( newKorisnik.imeProperty() );
                fldPrezime.textProperty().bindBidirectional( newKorisnik.prezimeProperty() );
                fldEmail.textProperty().bindBidirectional( newKorisnik.emailProperty() );
                fldUsername.textProperty().bindBidirectional( newKorisnik.usernameProperty() );
                fldPassword.textProperty().bindBidirectional( newKorisnik.passwordProperty() );
                if (!newKorisnik.getSlika().isEmpty())
                    imageView.setImage(new Image(newKorisnik.getSlika(), 128, 128, false, false));
                else
                    imageView.setImage(new Image(getClass().getClassLoader().getResource("slike/face-smile.png").toString(), 128, 128, false, false));
            }
        });

        fldIme.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                fldIme.getStyleClass().removeAll("poljeNijeIspravno");
                fldIme.getStyleClass().add("poljeIspravno");
            } else {
                fldIme.getStyleClass().removeAll("poljeIspravno");
                fldIme.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldPrezime.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                fldPrezime.getStyleClass().removeAll("poljeNijeIspravno");
                fldPrezime.getStyleClass().add("poljeIspravno");
            } else {
                fldPrezime.getStyleClass().removeAll("poljeIspravno");
                fldPrezime.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldEmail.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                fldEmail.getStyleClass().removeAll("poljeNijeIspravno");
                fldEmail.getStyleClass().add("poljeIspravno");
            } else {
                fldEmail.getStyleClass().removeAll("poljeIspravno");
                fldEmail.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldUsername.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                fldUsername.getStyleClass().removeAll("poljeNijeIspravno");
                fldUsername.getStyleClass().add("poljeIspravno");
            } else {
                fldUsername.getStyleClass().removeAll("poljeIspravno");
                fldUsername.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldPassword.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                fldPassword.getStyleClass().removeAll("poljeNijeIspravno");
                fldPassword.getStyleClass().add("poljeIspravno");
            } else {
                fldPassword.getStyleClass().removeAll("poljeIspravno");
                fldPassword.getStyleClass().add("poljeNijeIspravno");
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

    public void obrisiAction(ActionEvent actionEvent){
        Korisnik korisnik = listKorisnici.getSelectionModel().getSelectedItem();
        listKorisnici.getItems().remove(korisnik);
        model.obrisiKorisnika();
    }

    public void exitAction(ActionEvent actionEvent){
        exit();
    }

    public void saveAction(ActionEvent actionEvent){
        Window stage = listKorisnici.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Odabir datoteke");
        fileChooser.setInitialFileName("passwd");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("passwd", "*.passwd"));
        model.zapisiDatoteku(fileChooser.showOpenDialog(stage));
    }

    public void printAction(ActionEvent actionEvent){
        try {
            new KorisniciReport().showReport(model.getConnection());
        } catch (JRException e1) {
            e1.printStackTrace();
        }
    }

    public void bosanskiAction(ActionEvent actionEvent) throws IOException {
        Locale.setDefault(new Locale("bs", "BA"));

        ResourceBundle bundle = ResourceBundle.getBundle("Language");
        KorisniciModel model = new KorisniciModel();
        model.napuni();
        KorisnikController ctrl = new KorisnikController(model);
        Scene scene = listKorisnici.getScene();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/korisnici.fxml"), bundle);
        loader.setController(ctrl);
        Parent root = loader.load();
        scene.setRoot(root);
    }

    public void englishAction(ActionEvent actionEvent) throws IOException {
        Locale.setDefault(new Locale("en", "US"));

        ResourceBundle bundle = ResourceBundle.getBundle("Language");
        KorisniciModel model = new KorisniciModel();
        model.napuni();
        KorisnikController ctrl = new KorisnikController(model);
        Scene scene = listKorisnici.getScene();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/korisnici.fxml"), bundle);
        loader.setController(ctrl);
        Parent root = loader.load();
        scene.setRoot(root);
    }

    public void aboutAction(ActionEvent actionEvent) throws IOException {
        AboutController ctrl = new AboutController();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/about.fxml"));
        loader.setController(ctrl);
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("About");
        stage.setScene(new Scene(root, 550, 300));
        stage.setResizable(false);
        stage.show();
        stage.toFront();
    }

    public void imgKorisnikAction(ActionEvent actionEvent) throws IOException {
        GiphySearchController ctrl = new GiphySearchController();

        ResourceBundle bundle = ResourceBundle.getBundle("Language");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/giphySearch.fxml"), bundle);
        loader.setController(ctrl);
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Giphy");
        stage.setScene(new Scene(root, 1000, 500));
        stage.setResizable(true);
        stage.setOnHiding(event -> {
            if (model.getTrenutniKorisnik() == null) return;
            if (!ctrl.getSelectedURL().isEmpty()) {
                model.getTrenutniKorisnik().setSlika(ctrl.getSelectedURL());
                imageView.setImage(new Image(ctrl.getSelectedURL(), 128, 128, false, false));
            }
        });
        stage.show();
        stage.toFront();
    }

}
