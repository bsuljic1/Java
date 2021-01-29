package ba.unsa.etf.rpr;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
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
class TeacherHomeControllerTest {
    Stage stage;
    TeacherHomeController teacherHomeController;

    @Start
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/teacherHome.fxml"));
        teacherHomeController = new TeacherHomeController();
        teacherHomeController.setTeacherId(3);
        loader.setController(teacherHomeController);
        Parent root = loader.load();
        stage.setTitle("Teacher's home");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
        stage.toFront();
        this.stage = stage;
    }



    @Test
    void actionAddActivity(FxRobot robot) {
        KindergartenDAO.removeInstance();
        File dbfile = new File("base.db");
        dbfile.delete();
        KindergartenDAO dao = KindergartenDAO.getInstance();

        robot.clickOn("#buttonAddActivity");
        robot.lookup("#nameField").tryQuery().isPresent();

        robot.clickOn("#nameField");
        robot.write("aktivnost");

        robot.clickOn("#descriptionArea");
        robot.write("nova aktivnost");

        robot.clickOn("#buttonOk");

        boolean added = false;
        ArrayList<Activity> activities = dao.getActivities();
        for (Activity activity : activities)
            if (activity.getName().equals("aktivnost") && activity.getDescription().equals("nova aktivnost"))
                added = true;

        assertTrue(added);
    }

    @Test
    void testEditChild(FxRobot robot){
        KindergartenDAO dao = KindergartenDAO.getInstance();
        robot.lookup("#childrenList").tryQuery().isPresent();

        robot.clickOn("Tomica Tomic");
        Child child = teacherHomeController.getSelectedChild();
        robot.clickOn("#buttonEditChild");

        robot.lookup("#notesArea").tryQuery().isPresent();

        robot.clickOn("#notesArea");
        robot.write(". Tomica is very clever kid.");

        robot.clickOn("#poorRadio");
        robot.clickOn("#excellentRadio");

        robot.clickOn("#buttonSave");

        Child daoChild = dao.getChildren().stream().filter((a) -> {return a.getId() == child.getId();}).findFirst().get();

        assertEquals("All the best. Tomica is very clever kid.", daoChild.getNotesByTeacher());
    }




}