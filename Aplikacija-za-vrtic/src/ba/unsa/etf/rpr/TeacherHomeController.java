package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;


public class TeacherHomeController {
    private KindergartenDAO dao = KindergartenDAO.getInstance();
    public ListView<Child> childrenList;
    public ListView<Activity> activitiesList;
    private Activity selectedActivity;
    private Child selectedChild;
    private int teacherId;

    public Child getSelectedChild() {
        return selectedChild;
    }

    @FXML
    public void initialize() {
        childrenList.setItems(FXCollections.observableArrayList(dao.getChildrenOfTeacher(teacherId)));
        activitiesList.setItems(FXCollections.observableArrayList(dao.getActivities()));
    }

    public void setTeacherId(int teacherId){
        this.teacherId = teacherId;
    }

    public void actionActivitySelected(MouseEvent mouseEvent) {
        selectedActivity = (Activity) activitiesList.getSelectionModel().getSelectedItem();
    }

    public void actionChildSelected(MouseEvent mouseEvent) {
        selectedChild = (Child) childrenList.getSelectionModel().getSelectedItem();
    }

    public void actionEditChild(ActionEvent actionEvent){
        if(selectedChild == null) return;
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/editChild.fxml"));
            EditChildController editChildController = new EditChildController();
            editChildController.setChild(selectedChild);
            loader.setController(editChildController);
            root = loader.load();
            stage.setTitle("Edit child");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.setOnHiding(windowEvent -> {
                childrenList.setItems(FXCollections.observableList(dao.getChildren()));
            });
            stage.show();
            stage.toFront();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionAddActivity(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addActivity.fxml"));
            root = loader.load();
            stage.setTitle("Add a new activity");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.setOnHiding(windowEvent -> {
                activitiesList.setItems(FXCollections.observableList(dao.getActivities()));
                childrenList.setItems(FXCollections.observableList(dao.getChildren()));
            });
            stage.show();
            stage.toFront();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionRemoveActivity(ActionEvent actionEvent) {
        if (selectedActivity == null) return;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CONFIRMATION");
        alert.setHeaderText("Are you sure you want to remove selected activity?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            dao.removeActivity(selectedActivity);
            activitiesList.getItems().remove(selectedActivity);
        }
    }



}
