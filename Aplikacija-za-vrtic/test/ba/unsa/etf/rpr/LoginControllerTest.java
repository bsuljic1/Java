package ba.unsa.etf.rpr;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.File;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class LoginControllerTest {
    Stage theStage;
    LoginController loginController;

    @Start
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        loginController = new LoginController();
        loader.setController(loginController);
        Parent root = loader.load();
        stage.setTitle("Login");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
        stage.toFront();
        theStage = stage;
    }

    @Test
    void actionLoginTest(FxRobot robot){
        KindergartenDAO.removeInstance();
        File dbfile = new File("base.db");
        dbfile.delete();
        KindergartenDAO dao = KindergartenDAO.getInstance();
        robot.clickOn("#usernameField");
        robot.write("director");

        robot.clickOn("#passwordField");
        robot.write("director");

        robot.clickOn("#buttonLogin");
        assertTrue(robot.lookup("#buttonAddChild").tryQuery().isPresent());
    }

    @Test
    void actionLoginTest2(FxRobot robot){
        KindergartenDAO.removeInstance();
        File dbfile = new File("base.db");
        dbfile.delete();
        KindergartenDAO dao = KindergartenDAO.getInstance();
        robot.clickOn("#usernameField");
        robot.write("ttomic");

        robot.clickOn("#passwordField");
        robot.write("tomictomic1");

        robot.clickOn("#buttonLogin");
        assertTrue(robot.lookup("#getReportButton").tryQuery().isPresent());
    }

    @Test
    void actionLoginTest3(FxRobot robot){
        KindergartenDAO.removeInstance();
        File dbfile = new File("base.db");
        dbfile.delete();
        KindergartenDAO dao = KindergartenDAO.getInstance();
        robot.clickOn("#usernameField");
        robot.write("ssaric3");

        robot.clickOn("#passwordField");
        robot.write("saricsaric3");

        robot.clickOn("#buttonLogin");
        assertTrue(robot.lookup("#buttonEditChild").tryQuery().isPresent());
    }

}