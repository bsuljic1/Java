package ba.unsa.etf.rpr.tutorijal07;


import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Controller {
    private KorisniciModel model = new KorisniciModel();

    public TextField imeField;
    public TextField prezimeField;
    public TextField emailField;
    public TextField korimeField;
    public PasswordField lozinkaField;
    public ListView<Korisnik> listKorisnici;

    public Controller(){
        model.napuni();
    }

    public Controller(KorisniciModel model) {
        this.model = model;
    }

    public void actionKraj(ActionEvent actionEvent) {
        Platform.exit();
    }

    @FXML
    public void initialize() {


        listKorisnici.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Korisnik>() {
            @Override
            public void changed(ObservableValue<? extends Korisnik> observableValue, Korisnik korisnik, Korisnik t1) {
                model.setTrenutni(t1);
            }
        });
//        imeField.textProperty().bindBidirectional(model.getTrenutni().imeProperty());
//        prezimeField.textProperty().bindBidirectional(model.getTrenutni().prezimeProperty());
//        emailField.textProperty().bindBidirectional(model.getTrenutni().emailProperty());
//        korimeField.textProperty().bindBidirectional(model.getTrenutni().korImeProperty());
//        lozinkaField.textProperty().bindBidirectional(model.getTrenutni().lozinkaProperty());
        listKorisnici.setItems(model.getLista());

        model.trenutniProperty().addListener(
                (obs, oldKorisnik, newKorisnik ) -> {
                    if(oldKorisnik != null) {
                        imeField.textProperty().unbindBidirectional(oldKorisnik.imeProperty());
                        prezimeField.textProperty().unbindBidirectional(oldKorisnik.prezimeProperty());
                        emailField.textProperty().unbindBidirectional(oldKorisnik.emailProperty());
                        lozinkaField.textProperty().unbindBidirectional(oldKorisnik.lozinkaProperty());
                    } //brise prijasnje bindove
                    if(newKorisnik == null) {
                        imeField.setText("");
                        prezimeField.setText("");
                        emailField.setText("");
                        lozinkaField.setText("");
                    } //ako je null kao na primjer na pocetku
                    else {
                        imeField.textProperty().bindBidirectional(model.getTrenutni().imeProperty());
                        prezimeField.textProperty().bindBidirectional(model.getTrenutni().prezimeProperty());
                        emailField.textProperty().bindBidirectional(model.getTrenutni().emailProperty());
                        korimeField.textProperty().bindBidirectional(model.getTrenutni().korImeProperty());
                        lozinkaField.textProperty().bindBidirectional(model.getTrenutni().lozinkaProperty());
                    } //ako nije null stavi date vrijednosti i ta daaaa done
                });


}

    public void actionDodaj(ActionEvent actionEvent) {
        Korisnik korisnik = new Korisnik("", "", "", "", "");
        model.getLista().add(korisnik);
        model.setTrenutni(korisnik);
    }

}
