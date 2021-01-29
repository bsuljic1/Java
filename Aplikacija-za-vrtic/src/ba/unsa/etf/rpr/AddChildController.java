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

public class AddChildController {
    private KindergartenDAO dao = KindergartenDAO.getInstance();
    public TextField firstNameField;
    public TextField lastNameField;
    public ComboBox<Teacher> teacherCombo;
    public ComboBox<LocalTime> startTimeCombo;
    public ComboBox<LocalTime> endTimeCombo;
    public TextField parentFirstNameField;
    public TextField parentLastNameField;
    public TextField phoneNumberField;
    public TextField usernameField;
    public TextField passwordField;
    public TextField addressField;
    public CheckBox parentCheck;
    public ComboBox<Parent> parentCombo;

    @FXML
    public void initialize() {
        teacherCombo.setItems(FXCollections.observableArrayList(dao.getTeachers()));
        parentCombo.setItems(FXCollections.observableArrayList(dao.getParents()));
        ArrayList<LocalTime> time = new ArrayList<LocalTime>();
        time.add(LocalTime.parse("07:00:00"));
        time.add(LocalTime.parse("08:00:00"));
        time.add(LocalTime.parse("09:00:00"));
        time.add(LocalTime.parse("10:00:00"));
        time.add(LocalTime.parse("11:00:00"));
        time.add(LocalTime.parse("12:00:00"));
        time.add(LocalTime.parse("13:00:00"));
        time.add(LocalTime.parse("14:00:00"));
        time.add(LocalTime.parse("15:00:00"));
        time.add(LocalTime.parse("16:00:00"));
        time.add(LocalTime.parse("17:00:00"));
        time.add(LocalTime.parse("18:00:00"));
        time.add(LocalTime.parse("19:00:00"));
        time.add(LocalTime.parse("20:00:00"));
        startTimeCombo.setItems(FXCollections.observableArrayList(time));
        endTimeCombo.setItems(FXCollections.observableArrayList(time));
    }

    // validating information and adding new child if information is valid
    public void actionOk(ActionEvent actionEvent) {
        boolean valid = true;
        if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() ||
                teacherCombo.getSelectionModel().getSelectedItem() == null || startTimeCombo.getSelectionModel().getSelectedItem() == null
                || endTimeCombo.getSelectionModel().getSelectedItem() == null) {
            valid = false;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("You didn't enter all the information!");
            alert.showAndWait();
        }
        Parent parent = new Parent();
        if (parentCheck.isSelected()) { // parent is existing
            if (parentCombo.getSelectionModel().getSelectedItem() != null)
                parent = parentCombo.getSelectionModel().getSelectedItem();
            else {
                //alert, parent isnt selected
                valid = false;
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("You didn't select any parent!");
                alert.showAndWait();
            }
        } else { //new parent
            if (parentFirstNameField.getText().isEmpty() || parentLastNameField.getText().isEmpty() || usernameField.getText().isEmpty() ||
                    passwordField.getText().isEmpty() || addressField.getText().isEmpty() || phoneNumberField.getText().isEmpty()) {
                valid = false;
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("You didn't enter all the information!");
                alert.showAndWait();
            }
            if (dao.findByUsername(usernameField.getText()) == null) {
                parent = new Parent(parentFirstNameField.getText(), parentLastNameField.getText(), addressField.getText(),
                        usernameField.getText(), passwordField.getText(), Integer.parseInt(phoneNumberField.getText()));
                dao.addParent(parent);
            } else {
                //username is existing
                valid = false;
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Username is already existing. Try with another one!");
                alert.showAndWait();
            }
        }
        parent.setId(dao.findByUsername(parent.getUsername()).getId());

        Teacher teacher = teacherCombo.getSelectionModel().getSelectedItem();
        if (teacherCombo.getSelectionModel().getSelectedItem() == null) {
            valid = false;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("You didn't select any teacher!");
            alert.showAndWait();
        }

        Activity activity = new Activity(6, "Undefined", "Activity is not entered yet");

        Child newChild = new Child(firstNameField.getText(), lastNameField.getText(), parent.getAddress(),
                Behavior.EXCELLENT, parent, teacher, activity,
                startTimeCombo.getSelectionModel().getSelectedItem(),
                endTimeCombo.getSelectionModel().getSelectedItem());

        if (valid) {
            dao.addChild(newChild);
            Stage stage = (Stage) firstNameField.getScene().getWindow();
            stage.close();
        }

    }

    public void actionCancel(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CONFIRMATION");
        alert.setHeaderText("Are you sure you want to cancel?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) firstNameField.getScene().getWindow();
            stage.close();
        }
    }

}
