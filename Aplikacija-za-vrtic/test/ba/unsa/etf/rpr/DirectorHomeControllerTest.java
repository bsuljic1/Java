package ba.unsa.etf.rpr;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javax.tools.Diagnostic;
import java.io.File;
import java.util.ArrayList;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class DirectorHomeControllerTest {
    Stage theStage;
    DirectorHomeController directorHomeController;


    @Start
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/directorHome.fxml"));
        directorHomeController = new DirectorHomeController();
        loader.setController(directorHomeController);
        Parent root = loader.load();
        stage.setTitle("Director's home");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
        stage.toFront();
        theStage = stage;
    }

    @Test
    void testInitialize(FxRobot robot) {
        //test koliko ima djece u listi
        KindergartenDAO.removeInstance();
        File dbfile = new File("base.db");
        dbfile.delete();
        KindergartenDAO dao = KindergartenDAO.getInstance();
//        directorHomeController.resetBase();
        ListView childrenList = robot.lookup("#childrenList").queryAs(ListView.class);
        assertEquals(1, childrenList.getItems().size());

        //test koliko ima aktivnosti u listi
        ListView activitiesList = robot.lookup("#activitiesList").queryAs(ListView.class);
        assertEquals(2, activitiesList.getItems().size());

        //test koliko ima uciteljica u listi
        ListView teachersList = robot.lookup("#teachersList").queryAs(ListView.class);
        assertEquals(1, childrenList.getItems().size());
    }


    @Test
    void actionChildSelectedTest(FxRobot robot) { //test da li se prepoznaje selektovanje djeteta
        KindergartenDAO.removeInstance();
        File dbfile = new File("base.db");
        dbfile.delete();
        KindergartenDAO dao = KindergartenDAO.getInstance();
        robot.clickOn("Tomica Tomic");
        Child selectedChild = directorHomeController.getSelectedChild();
        assertEquals("Tomica Tomic", selectedChild.toString());
    }

    @Test
        //test da li se prepoznaje selektovanje uciteljice
    void actionTeacherSelectedTest(FxRobot robot) {
        KindergartenDAO.removeInstance();
        File dbfile = new File("base.db");
        dbfile.delete();
        KindergartenDAO dao = KindergartenDAO.getInstance();
        robot.clickOn("Sara Saric");
        Teacher selectedTeacher = directorHomeController.getSelectedTeacher();
        assertEquals("Sara Saric", selectedTeacher.toString());
    }

    @Test
        //test da li se prepoznaje selektovanje aktivnosti
    void actionActivitySelectedTest(FxRobot robot) {
        KindergartenDAO.removeInstance();
        File dbfile = new File("base.db");
        dbfile.delete();
        KindergartenDAO dao = KindergartenDAO.getInstance();
        robot.clickOn("Sleeping");
        Activity selectedActivity = directorHomeController.getSelectedActivity();
        assertEquals("Sleeping", selectedActivity.toString());
    }

    @Test
    void actionAddChildTest(FxRobot robot) throws InterruptedException {
        KindergartenDAO.removeInstance();
        File dbfile = new File("base.db");
        dbfile.delete();
        KindergartenDAO dao = KindergartenDAO.getInstance();

        // Otvaranje forme za dodavanje
        robot.clickOn("#buttonAddChild");

        // Čekamo da dijalog postane vidljiv
        robot.lookup("#addressField").tryQuery().isPresent();


        robot.clickOn("#firstNameField");
        robot.write("Berina");

        robot.clickOn("#lastNameField");
        robot.write("Suljic");

        robot.clickOn("#addressField");
        robot.write("ulica");


        ComboBox<Teacher> combo = robot.lookup("#teacherCombo").query();
        robot.clickOn(combo);
        robot.press(KeyCode.DOWN);
        robot.press(KeyCode.ENTER);


        robot.clickOn("#startTimeCombo");
        robot.clickOn("08:00");

        robot.clickOn("#endTimeCombo");
        robot.clickOn("15:00");

        robot.clickOn("#parentFirstNameField");
        robot.write("Amir");

        robot.clickOn("#parentLastNameField");
        robot.write("Suljic");

        robot.clickOn("#phoneNumberField");
        robot.write("061232562");

        robot.clickOn("#usernameField");
        robot.write("asuljic");

        robot.clickOn("#passwordField");
        robot.write("suljicsuljic");


        // Klik na dugme Ok
        robot.clickOn("#buttonOk");

        // Da li je dijete dodano u bazu

        boolean found = false;
        for (Child child : dao.getChildren())
            if (child.getFirstName().equals("Berina") && child.getLastName().equals("Suljic"))
                found = true;
        assertTrue(found);
    }

    @Test
    void actionRemoveChildTest(FxRobot robot) {
        KindergartenDAO.removeInstance();
        File dbfile = new File("base.db");
        dbfile.delete();
        KindergartenDAO dao = KindergartenDAO.getInstance();
        robot.clickOn("Tomica Tomic");
        robot.clickOn("#buttonRemoveChild");
        robot.clickOn("OK");
        assertEquals(0, dao.getChildren().size());
    }

    @Test
    void actionAddTeacherTest(FxRobot robot) {
        KindergartenDAO.removeInstance();
        File dbfile = new File("base.db");
        dbfile.delete();
        KindergartenDAO dao = KindergartenDAO.getInstance();

        robot.clickOn("#buttonAddTeacher");
        //cekanje forme
        robot.lookup("#firstNameField").tryQuery().isPresent();

        robot.clickOn("#firstNameField");
        robot.write("berina");

        robot.clickOn("#lastNameField");
        robot.write("suljic");

        robot.clickOn("#addressField");
        robot.write("adresa");

        robot.clickOn("#usernameField");
        robot.write("bsuljic1");

        robot.clickOn("#passwordField");
        robot.write("sifra");

        robot.clickOn("#phoneNumberField");
        robot.write("123456789");

        robot.clickOn("#startTimeCombo");
        robot.clickOn("08:00");

        robot.clickOn("#endTimeCombo");
        robot.clickOn("16:00");

        robot.clickOn("#buttonOk");

        boolean added = false;
        ArrayList<Teacher> teachers = dao.getTeachers();
        for (Teacher t : teachers) if (t.getUsername().equals("bsuljic1")) added = true;
        assertTrue(added);
    }

}