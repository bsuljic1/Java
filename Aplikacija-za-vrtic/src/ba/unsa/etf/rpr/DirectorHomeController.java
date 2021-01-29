package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import net.sf.jasperreports.engine.JRException;

import javax.tools.Diagnostic;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Optional;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class DirectorHomeController {
    private KindergartenDAO dao = KindergartenDAO.getInstance();
    public ListView childrenList;
    public ListView activitiesList;
    public ListView teachersList;
    private Child selectedChild;
    private Teacher selectedTeacher;
    private Activity selectedActivity;

    public Teacher getSelectedTeacher() {
        return selectedTeacher;
    }

    public Activity getSelectedActivity() {
        return selectedActivity;
    }

    public Child getSelectedChild() {
        return selectedChild;
    }

    @FXML
    public void initialize() {
        childrenList.setItems(FXCollections.observableArrayList(dao.getChildren()));
        teachersList.setItems(FXCollections.observableArrayList(dao.getTeachers()));
        activitiesList.setItems(FXCollections.observableArrayList(dao.getActivities()));
    }

    public void actionChildSelected(MouseEvent mouseEvent) {
        selectedChild = (Child) childrenList.getSelectionModel().getSelectedItem();
    }

    public void actionTeacherSelected(MouseEvent mouseEvent) {
        selectedTeacher = (Teacher) teachersList.getSelectionModel().getSelectedItem();
    }

    public void actionActivitySelected(MouseEvent mouseEvent) {
        selectedActivity = (Activity) activitiesList.getSelectionModel().getSelectedItem();
    }

    public void actionAddChild(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addChild.fxml"));
            root = loader.load();
            stage.setTitle("Add a new child");
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

    public void actionRemoveChild(ActionEvent actionEvent) {
        if (selectedChild == null) return;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CONFIRMATION");
        alert.setHeaderText("Are you sure you want to remove selected child?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            dao.removeChild(selectedChild);
            childrenList.getItems().remove(selectedChild);
        }
    }

    public void actionRemoveTeacher(ActionEvent actionEvent) {
        if (selectedTeacher == null) return;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CONFIRMATION");
        alert.setHeaderText("Are you sure you want to remove selected teacher?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            dao.removeTeacher(selectedTeacher);
            teachersList.getItems().remove(selectedTeacher);
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

    public void actionAddTeacher(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addTeacher.fxml"));
            root = loader.load();
            stage.setTitle("Add a new teacher");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.setOnHiding(windowEvent -> {
                teachersList.setItems(FXCollections.observableList(dao.getTeachers()));
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
            });
            stage.show();
            stage.toFront();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionLogout(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CONFIRMATION");
        alert.setHeaderText("Are you sure you want to logout?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) childrenList.getScene().getWindow();
            stage.close();
        }
    }

    public void saveAction(ActionEvent actionEvent){
        Window stage = childrenList.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a file");
        fileChooser.setInitialFileName("children");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("children", "*.txt"));
        writeInFile(fileChooser.showOpenDialog(stage));
    }
    //method to get a file in which is written list of all children in kindergarten
    public void writeInFile(File file) {
        if (file != null) {
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(new FileWriter(file));
                ArrayList<Child> childList = new ArrayList<>(dao.getChildren());
                for (Child child : childList) {
                    String firstName = child.getFirstName();
                    String lastName = child.getLastName();
                    String write = child.getId() + ":" + firstName + " " + lastName + " behavior  " + child.getBehavior() + " " +  "\n";
                    writer.write(write);
                }
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void printAction(ActionEvent actionEvent){
        DirectorReport report = new DirectorReport();
        try {
            report.showReport(dao.getConn());
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
