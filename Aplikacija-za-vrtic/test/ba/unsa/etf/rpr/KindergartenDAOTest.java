package ba.unsa.etf.rpr;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class KindergartenDAOTest {


    @Test
    void testGetChildren() {
        KindergartenDAO.removeInstance();
        File dbfile = new File("base.db");
        dbfile.delete();
        KindergartenDAO dao = KindergartenDAO.getInstance();

        ArrayList<Child> children = dao.getChildren();
        assertEquals(1, children.size());
    }

    @Test
    void testRemoveChild() {
        KindergartenDAO.removeInstance();
        File dbfile = new File("base.db");
        dbfile.delete();
        KindergartenDAO dao = KindergartenDAO.getInstance();

        Child child = new Child();
        child.setId(2);
        dao.removeChild(child);
        assertEquals(0, dao.getChildren().size());
    }

    @Test
    void testRemoveTeacher() {
        KindergartenDAO.removeInstance();
        File dbfile = new File("base.db");
        dbfile.delete();
        KindergartenDAO dao = KindergartenDAO.getInstance();

        Teacher teacher = new Teacher();
        teacher.setId(3);
        dao.removeTeacher(teacher);
        assertEquals(0, dao.getTeachers().size());
    }

    @Test
    void testRemoveActivity() {
        KindergartenDAO.removeInstance();
        File dbfile = new File("base.db");
        dbfile.delete();
        KindergartenDAO dao = KindergartenDAO.getInstance();

        Activity activity = new Activity();
        activity.setId(5);
        dao.removeActivity(activity);
        assertEquals(1, dao.getActivities().size());
    }

    @Test
    void testFindByUsername() {
        KindergartenDAO dao = KindergartenDAO.getInstance();
        Person director = dao.findByUsername("director");
        assertTrue(director instanceof Director);
    }

    @Test
    void testAddActivity() {
        Activity activity = new Activity("aktivnost", "nova aktivnost");
        KindergartenDAO dao = KindergartenDAO.getInstance();
        dao.addActivity(activity);
        boolean added = false;
        ArrayList<Activity> activities = dao.getActivities();
        for(Activity a : activities)
            if(a.getName().equals("aktivnost")) added = true;

        assertTrue(added);
    }

}