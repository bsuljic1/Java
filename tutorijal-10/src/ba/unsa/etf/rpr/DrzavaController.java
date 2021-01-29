package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class DrzavaController {
    public Button btnOk;
    public Button btnCancel;
    public TextField fieldNaziv;
    public ChoiceBox<Grad> choiceGrad = new ChoiceBox<>();
    private Drzava drzava = null;
    private GeografijaDAO dao = GeografijaDAO.getInstance();

    public DrzavaController() {
    }

    public DrzavaController(Object o, ArrayList<Grad> gradovi) {

    }

    public void actionOk(ActionEvent actionEvent) {
        boolean validno = false;
        if (fieldNaziv.getText().isEmpty()) {
            fieldNaziv.getStyleClass().removeAll("poljeIspravno");
            fieldNaziv.getStyleClass().add("poljeNijeIspravno");
            validno = false;
        } else {
            fieldNaziv.getStyleClass().removeAll("poljeNijeIspravno");
            fieldNaziv.getStyleClass().add("poljeIspravno");
            validno = true;
        }
        if (validno) {
            Stage stage = (Stage) fieldNaziv.getScene().getWindow();
            Grad glavni;
            if (choiceGrad.getSelectionModel().getSelectedItem() == null) glavni = choiceGrad.getItems().get(0);
            else glavni = choiceGrad.getSelectionModel().getSelectedItem();
            Random r = new Random();
            int id = r.nextInt();
            drzava = new Drzava(id, fieldNaziv.getText(), glavni);
            dao.dodajDrzavu(drzava);
            stage.close();
        }
    }

    public void actionCancel(ActionEvent actionEvent) {
        drzava = null;
        Stage stage = (Stage) fieldNaziv.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void initialize() {
        choiceGrad.setItems(FXCollections.observableArrayList(GeografijaDAO.gradovi()));
    }

    public Drzava getDrzava() {
        return drzava;
    }
}
