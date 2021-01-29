package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.Optional;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;


public class LoginController {
    private KindergartenDAO dao = KindergartenDAO.getInstance();
    public TextField usernameField;
    public PasswordField passwordField;
    public Button buttonLogin;
    public ImageView imageKindergartenWelcome;

    @FXML
    public void initialize() {
        imageKindergartenWelcome.setImage(new Image(getClass().getClassLoader().getResource("images/kindergarten_welcome.png").toString(),128, 128, true, true));
    }

    public void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(message);
        Optional<ButtonType> result = alert.showAndWait();
    }

    public void actionLogin(ActionEvent actionEvent) {
        Person whoWantsToLogin = dao.findByUsername(usernameField.getText());
        if (whoWantsToLogin instanceof Director) {
            if (((Director) whoWantsToLogin).getPassword().equals(passwordField.getText())) { //checking if password is valid for this username
                Stage stage = new Stage();
                javafx.scene.Parent root = null;
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/directorHome.fxml"));
                    DirectorHomeController directorHomeController = new DirectorHomeController();
                    loader.setController(directorHomeController);
                    root = loader.load();
                    stage.setTitle("Director's home");
                    stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                    stage.setResizable(false);
                    stage.show();
                    stage.toFront();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                showAlert("Password is invalid!");
            }
        } else if (whoWantsToLogin instanceof Teacher) {
            if (((Teacher) whoWantsToLogin).getPassword().equals(passwordField.getText())) {
                Stage stage = new Stage();
                javafx.scene.Parent root = null;
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/teacherHome.fxml"));
                    TeacherHomeController teacherHomeController = new TeacherHomeController();
                    teacherHomeController.setTeacherId(whoWantsToLogin.getId());
                    loader.setController(teacherHomeController);
                    root = loader.load();
                    stage.setTitle("Teacher's home");
                    stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                    stage.setResizable(false);
                    stage.show();
                    stage.toFront();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                showAlert("Password is invalid!");
            }
        } else if (whoWantsToLogin instanceof Parent) {
            if (passwordField.getText().equals(((Parent) whoWantsToLogin).getPassword())) {

                Stage stage = new Stage();
                javafx.scene.Parent root = null;
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/parentHome.fxml"));
                    ParentHomeController parentHomeController = new ParentHomeController();
                    parentHomeController.setParentId(whoWantsToLogin.getId());
                    loader.setController(parentHomeController);
                    root = loader.load();
                    stage.setTitle("Parent's home");
                    stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                    stage.setResizable(false);
                    stage.show();
                    stage.toFront();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else { //password not correct
                showAlert("Password is invalid!");
            }
        } else {// username doesn't exist
            showAlert("Username or password is invalid!");
        }
    }

}
