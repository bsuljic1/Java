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

import static java.lang.Integer.parseInt;

public class GradController {
    public Button btnOk;
    public Button btnCancel;
    public TextField fieldNaziv;
    public TextField fieldBrojStanovnika;
    public ChoiceBox<Drzava> choiceDrzava = new ChoiceBox<>();
    private Grad grad = null;
    private Grad edit = null;
    private ArrayList<Drzava> drzave;

    public GradController(Grad edit) {
        this.edit = edit;
    }

    public GradController(Grad edit, ArrayList<Drzava> drzave) {
        this.edit = edit;
        this.drzave = drzave;
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
        try {
            if (parseInt(fieldBrojStanovnika.getText()) < 0) {
                fieldBrojStanovnika.getStyleClass().removeAll("poljeIspravno");
                fieldBrojStanovnika.getStyleClass().add("poljeNijeIspravno");
                validno = false;
            } else {
                fieldBrojStanovnika.getStyleClass().removeAll("poljeNijeIspravno");
                fieldBrojStanovnika.getStyleClass().add("poljeIspravno");
                validno = true;
            }
        } catch (Exception e) {
            fieldBrojStanovnika.getStyleClass().removeAll("poljeIspravno");
            fieldBrojStanovnika.getStyleClass().add("poljeNijeIspravno");
            validno = false;
        }
        if (validno) {
            Stage stage = (Stage) fieldNaziv.getScene().getWindow();
            Drzava drzava;
            if (choiceDrzava.getSelectionModel().getSelectedItem() == null) drzava = null;
            else drzava = choiceDrzava.getSelectionModel().getSelectedItem();
            if (edit == null) {
                Random r = new Random();
                int id = r.nextInt();
                grad = new Grad(id, parseInt(fieldBrojStanovnika.getText()), fieldNaziv.getText(), drzava);
            }
            else
            {
                grad = new Grad(edit.getId(), parseInt(fieldBrojStanovnika.getText()), fieldNaziv.getText(), drzava);
            }
            stage.close();
        }
    }

    public void actionCancel(ActionEvent actionEvent) {
        grad = null;
        Stage stage = (Stage) fieldNaziv.getScene().getWindow();
        stage.close();
    }

    public Grad getGrad() {
        return grad;
    }

    public Grad getEdit() {
        return edit;
    }

    @FXML
    public void initialize() {
        choiceDrzava.setItems(FXCollections.observableArrayList(drzave));
        if (edit != null) {
            fieldNaziv.setText(edit.getNaziv());
            fieldBrojStanovnika.setText(Integer.toString(edit.getBrojStanovnika()));
            choiceDrzava.getSelectionModel().select(edit.getDrzava());
        }
    }


}
