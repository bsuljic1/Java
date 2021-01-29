package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Optional;

public class EditChildController {
    private KindergartenDAO dao = KindergartenDAO.getInstance();
    public DatePicker dateOfBirth;
    public Label childFirstName, childLastName, timeInKindergarten, parentFirstName, parentLastName, phoneNumber, address;
    public RadioButton unratedRadio, poorRadio, averageRadio, goodRadio, verygoodRadio, excellentRadio;
    public TextArea notesArea;
    private Child child;
    public ChoiceBox<Activity> activityChoice;

    public void setChild(Child child) {
        this.child = child;
    }

    @FXML
    public void initialize() {
        childFirstName.setText(child.getFirstName());
        childLastName.setText(child.getLastName());
        String start = child.getStart().toString();
        String end = child.getEnd().toString();
        String time = start + " - " + end;
        timeInKindergarten.setText(time);
        parentFirstName.setText(child.getParent().getFirstName());
        parentLastName.setText(child.getParent().getLastName());
        phoneNumber.setText(String.valueOf(child.getParent().getPhoneNumber()));
        address.setText(child.getAddress());
        notesArea.setText(child.getNotesByTeacher());
        ArrayList<Activity> acts = dao.getActivities();
        activityChoice.setItems(FXCollections.observableList(acts));
        activityChoice.getSelectionModel().select(acts.stream().filter((a) -> {
            return a.getId() == child.getCurrentActivity().getId();
        }).findFirst().get());

        switch (child.getBehavior()) {
            case EXCELLENT: {
                excellentRadio.setSelected(true);
                unratedRadio.setSelected(false);
                averageRadio.setSelected(false);
                goodRadio.setSelected(false);
                verygoodRadio.setSelected(false);
                poorRadio.setSelected(false);
                break;
            }
            case POOR: {
                poorRadio.setSelected(true);
                unratedRadio.setSelected(false);
                averageRadio.setSelected(false);
                goodRadio.setSelected(false);
                verygoodRadio.setSelected(false);
                excellentRadio.setSelected(false);
                break;
            }
            case AVERAGE: {
                averageRadio.setSelected(true);
                unratedRadio.setSelected(false);
                poorRadio.setSelected(false);
                goodRadio.setSelected(false);
                verygoodRadio.setSelected(false);
                excellentRadio.setSelected(false);
                break;
            }
            case GOOD: {
                goodRadio.setSelected(true);
                unratedRadio.setSelected(false);
                averageRadio.setSelected(false);
                poorRadio.setSelected(false);
                verygoodRadio.setSelected(false);
                excellentRadio.setSelected(false);
                break;
            }
            case VERY_GOOD: {
                verygoodRadio.setSelected(true);
                unratedRadio.setSelected(false);
                averageRadio.setSelected(false);
                goodRadio.setSelected(false);
                poorRadio.setSelected(false);
                excellentRadio.setSelected(false);
                break;
            }
            default: {
                unratedRadio.setSelected(true);
                poorRadio.setSelected(false);
                averageRadio.setSelected(false);
                goodRadio.setSelected(false);
                verygoodRadio.setSelected(false);
                excellentRadio.setSelected(false);
                break;
            }

        }
    }

    public void actionSave(ActionEvent actionEvent) {
        int behavior;
        if (unratedRadio.isSelected()) behavior = 0;
        else if (poorRadio.isSelected()) behavior = 1;
        else if (averageRadio.isSelected()) behavior = 2;
        else if (goodRadio.isSelected()) behavior = 3;
        else if (verygoodRadio.isSelected()) behavior = 4;
        else behavior = 5;

        Activity activity = activityChoice.getSelectionModel().getSelectedItem();
        child.setCurrentActivity(activity);

        dao.editChild(child, behavior, notesArea.getText());
        Stage stage = (Stage) childFirstName.getScene().getWindow();
        stage.close();
    }

    public void actionCancel(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CONFIRMATION");
        alert.setHeaderText("Are you sure you want to cancel?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) childFirstName.getScene().getWindow();
            stage.close();
        }
    }

    public void actionUnrated(ActionEvent actionEvent) {
        poorRadio.setSelected(false);
        averageRadio.setSelected(false);
        goodRadio.setSelected(false);
        verygoodRadio.setSelected(false);
        excellentRadio.setSelected(false);
    }

    public void actionPoor(ActionEvent actionEvent) {
        unratedRadio.setSelected(false);
        averageRadio.setSelected(false);
        goodRadio.setSelected(false);
        verygoodRadio.setSelected(false);
        excellentRadio.setSelected(false);
    }

    public void actionAverage(ActionEvent actionEvent) {
        unratedRadio.setSelected(false);
        poorRadio.setSelected(false);
        goodRadio.setSelected(false);
        verygoodRadio.setSelected(false);
        excellentRadio.setSelected(false);
    }

    public void actionGood(ActionEvent actionEvent) {
        unratedRadio.setSelected(false);
        averageRadio.setSelected(false);
        poorRadio.setSelected(false);
        verygoodRadio.setSelected(false);
        excellentRadio.setSelected(false);
    }

    public void actionVerygood(ActionEvent actionEvent) {
        unratedRadio.setSelected(false);
        averageRadio.setSelected(false);
        goodRadio.setSelected(false);
        poorRadio.setSelected(false);
        excellentRadio.setSelected(false);
    }

    public void actionExcellent(ActionEvent actionEvent) {
        unratedRadio.setSelected(false);
        averageRadio.setSelected(false);
        goodRadio.setSelected(false);
        verygoodRadio.setSelected(false);
        poorRadio.setSelected(false);
    }
}
