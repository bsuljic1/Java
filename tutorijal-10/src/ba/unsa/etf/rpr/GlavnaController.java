package ba.unsa.etf.rpr;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class GlavnaController {
    public GeografijaDAO dao = GeografijaDAO.getInstance();
    public TableView<Grad> tableViewGradovi;// = new TableView<>(FXCollections.observableArrayList(GeografijaDAO.gradovi()));
    public TableColumn<Grad, Integer> colGradId;
    public TableColumn<Grad, String> colGradNaziv;
    public TableColumn<Grad, Integer> colGradStanovnika;
    public TableColumn<Grad, String> colGradDrzava;


    // Metoda za potrebe testova, vraća bazu u polazno stanje
    public void resetujBazu() {
        GeografijaDAO.removeInstance();
        File dbfile = new File("baza.db");
        dbfile.delete();
        GeografijaDAO dao = GeografijaDAO.getInstance();
    }

    public void actionDrzava(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/drzava.fxml"));
        DrzavaController controller = new DrzavaController();
        loader.setController(controller);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle("Drzava");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
        stage.toFront();
    }

    public void actionGrad(ActionEvent actionEvent) throws IOException {
        dao = GeografijaDAO.getInstance();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/grad.fxml"));
        GradController controller = new GradController(null, GeografijaDAO.drzave());
        loader.setController(controller);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle("Grad");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setOnHiding(windowEvent -> {
            dao.dodajGrad(controller.getGrad());
            tableViewGradovi.getItems().add(controller.getGrad());
        });
        stage.show();
        stage.toFront();
    }

    public void actionIzmijeni(ActionEvent actionEvent) throws IOException {
        dao = GeografijaDAO.getInstance();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/grad.fxml"));
        GradController controller = new GradController(tableViewGradovi.selectionModelProperty().getValue().getSelectedItem(), GeografijaDAO.drzave());
        loader.setController(controller);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle("Grad");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setOnHiding(windowEvent -> {
            if (controller.getGrad() != null) dao.izmijeniGrad(controller.getGrad());
        });
        stage.show();
        stage.toFront();
    }

    public void actionObrisi(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CONFIRMATION");
        alert.setHeaderText("Da li ste sigurni da zelite izbrisati ovaj grad?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            dao.obrisiGrad(tableViewGradovi.getSelectionModel().getSelectedItem().getNaziv());
            tableViewGradovi.getItems().remove(tableViewGradovi.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    public void initialize() {
        ObservableList<Grad> obs = FXCollections.observableArrayList();
        ArrayList<Grad> g = GeografijaDAO.gradovi();
        obs.addAll(g);
        colGradId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colGradNaziv.setCellValueFactory(new PropertyValueFactory<>("naziv"));
        colGradStanovnika.setCellValueFactory(new PropertyValueFactory<>("brojStanovnika"));
        colGradDrzava.setCellValueFactory(new PropertyValueFactory<>("drzava"));
        tableViewGradovi.setItems(obs);
    }
}
