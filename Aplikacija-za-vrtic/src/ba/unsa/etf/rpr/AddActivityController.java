package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class AddActivityController {
    private KindergartenDAO dao = KindergartenDAO.getInstance();
    public TextField nameField;
    public TextArea descriptionArea;

    // adding new activity
    public void actionOk(ActionEvent actionEvent) {
        boolean valid = true;
        if (nameField.getText().isEmpty() || descriptionArea.getText().isEmpty()){
            valid = false;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("You didn't enter all the information!");
            alert.showAndWait();
        }

        Activity newActivity = new Activity(nameField.getText(), descriptionArea.getText());

        if (valid) {
            dao.addActivity(newActivity);
            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.close();
        }

    }

    public void actionCancel(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CONFIRMATION");
        alert.setHeaderText("Are you sure you want to cancel?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.close();
        }
    }

}
