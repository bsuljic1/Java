package ba.unsa.etf.rpr;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class KindergartenDAO {
    private static KindergartenDAO instance;
    private Connection conn = null;

    private static PreparedStatement getChildrenStatement = null, getTeachersStatement = null, getActivitiesStatement = null,
            findParentByIdStatement = null, findTeacherByIdStatement, removeChildStatement = null, removeTeacherStatement = null,
            removeActivityStatement = null, findParentByUsernameStatement = null, findDirectorByUsernameStatement = null,
            findTeacherByUsernameStatement = null, getParentsStatement = null, addChildStatement = null, addParentStatement = null,
            addTeacherStatement = null, addActivityStatement = null, getChildrenOfTeacherStatement = null, getChildrenOfParentStatement = null,
            editChildStatement = null, getActivityById = null;

    public Connection getConn() {
        return conn;
    }

    private KindergartenDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:base.db");
            getChildrenStatement = conn.prepareStatement("SELECT * FROM child");
            getTeachersStatement = conn.prepareStatement("SELECT * FROM teacher");
            getActivitiesStatement = conn.prepareStatement("SELECT * FROM activity");
            findParentByIdStatement = conn.prepareStatement("SELECT * FROM parent WHERE parent.id = ?");
            findTeacherByIdStatement = conn.prepareStatement("SELECT * FROM teacher WHERE teacher.id = ?");
            removeChildStatement = conn.prepareStatement("DELETE FROM child WHERE id = ?");
            removeTeacherStatement = conn.prepareStatement("DELETE FROM teacher WHERE id = ?");
            removeActivityStatement = conn.prepareStatement("DELETE FROM activity WHERE id = ?");
            findParentByUsernameStatement = conn.prepareStatement("SELECT * FROM parent WHERE username = ?");
            findDirectorByUsernameStatement = conn.prepareStatement("SELECT * FROM director WHERE username = ?");
            findTeacherByUsernameStatement = conn.prepareStatement("SELECT * FROM teacher WHERE username = ?");
            getParentsStatement = conn.prepareStatement("SELECT * FROM parent");
            addChildStatement = conn.prepareStatement("INSERT INTO child (first_name, last_name, address, parent,teacher,start_time, end_time, behavior, notes, activity) VALUES (?,?,?,?,?,?,?,?,?,?)");
            addParentStatement = conn.prepareStatement("INSERT INTO parent (first_name, last_name, address, username, password, phone_number) VALUES (?,?,?,?,?,?)");
            addTeacherStatement = conn.prepareStatement("INSERT INTO teacher (first_name, last_name, address, username, password, phone_number, start_of_work, end_of_work) VALUES (?,?,?,?,?,?,?,?)");
            addActivityStatement = conn.prepareStatement("INSERT INTO activity (name, description) VALUES (?,?)");
            getChildrenOfTeacherStatement = conn.prepareStatement("SELECT * FROM child WHERE teacher = ?");
            getChildrenOfParentStatement = conn.prepareStatement("SELECT * FROM child WHERE parent = ?");
            editChildStatement = conn.prepareStatement("UPDATE child SET behavior=?, notes=?, activity=? WHERE id =?");
            getActivityById = conn.prepareStatement("SELECT * FROM activity WHERE id =?");
        } catch (SQLException e) {
            try {
                regenerisiBazu();
                getChildrenStatement = conn.prepareStatement("SELECT * FROM child");
                getTeachersStatement = conn.prepareStatement("SELECT * FROM teacher");
                getActivitiesStatement = conn.prepareStatement("SELECT * FROM activity");
                findParentByIdStatement = conn.prepareStatement("SELECT * FROM parent WHERE parent.id = ?");
                findTeacherByIdStatement = conn.prepareStatement("SELECT * FROM teacher WHERE teacher.id = ?");
                removeChildStatement = conn.prepareStatement("DELETE FROM child WHERE id = ?");
                removeTeacherStatement = conn.prepareStatement("DELETE FROM teacher WHERE id = ?");
                removeActivityStatement = conn.prepareStatement("DELETE FROM activity WHERE id = ?");
                findParentByUsernameStatement = conn.prepareStatement("SELECT * FROM parent WHERE username = ?");
                findDirectorByUsernameStatement = conn.prepareStatement("SELECT * FROM director WHERE username = ?");
                findTeacherByUsernameStatement = conn.prepareStatement("SELECT * FROM teacher WHERE username = ?");
                getParentsStatement = conn.prepareStatement("SELECT * FROM parent");
                addChildStatement = conn.prepareStatement("INSERT INTO child (first_name, last_name, address, parent,teacher,start_time, end_time, behavior, notes, activity) VALUES (?,?,?,?,?,?,?,?,?,?)");
                addParentStatement = conn.prepareStatement("INSERT INTO parent (first_name, last_name, address, username, password, phone_number) VALUES (?,?,?,?,?,?)");
                addTeacherStatement = conn.prepareStatement("INSERT INTO teacher (first_name, last_name, address, username, password, phone_number, start_of_work, end_of_work) VALUES (?,?,?,?,?,?,?,?)");
                addActivityStatement = conn.prepareStatement("INSERT INTO activity (name, description) VALUES (?,?)");
                getChildrenOfTeacherStatement = conn.prepareStatement("SELECT * FROM child WHERE teacher = ?");
                getChildrenOfParentStatement = conn.prepareStatement("SELECT * FROM child WHERE parent = ?");
                editChildStatement = conn.prepareStatement("UPDATE child SET behavior=?, notes=?, activity=? WHERE id =?");
                getActivityById = conn.prepareStatement("SELECT * FROM activity WHERE id =?");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

        }

    }

    public static KindergartenDAO getInstance() {
        if (instance == null) instance = new KindergartenDAO();
        return instance;
    }

    public static void removeInstance() {
        try {
            instance.conn.close();
        } catch (NullPointerException | SQLException e) {
        }
        instance = null;
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void regenerisiBazu() {
        Scanner ulaz = null;
        try {
            ulaz = new Scanner(new FileInputStream("base.sql"));
            String sqlUpit = "";
            while (ulaz.hasNext()) {
                sqlUpit = sqlUpit + ulaz.nextLine();
                if (sqlUpit.length() > 1 && sqlUpit.charAt(sqlUpit.length() - 1) == ';') {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Child> getChildren() {
        ArrayList<Child> ret = new ArrayList<>();
        try {
            ResultSet resultSet = getChildrenStatement.executeQuery();
            while (resultSet.next()) {
                Child child = new Child();
                child.setId(resultSet.getInt(1));
                child.setFirstName(resultSet.getString(2));
                child.setLastName(resultSet.getString(3));
                child.setAddress(resultSet.getString(4));
                child.setStart(LocalTime.parse(resultSet.getString(7)));
                child.setEnd(LocalTime.parse(resultSet.getString(8)));
                switch (resultSet.getInt(9)) {
                    case 1: {
                        child.setBehavior(Behavior.POOR);
                        break;
                    }
                    case 2: {
                        child.setBehavior(Behavior.AVERAGE);
                        break;
                    }
                    case 3: {
                        child.setBehavior(Behavior.GOOD);
                        break;
                    }
                    case 4: {
                        child.setBehavior(Behavior.VERY_GOOD);
                        break;
                    }
                    case 5: {
                        child.setBehavior(Behavior.EXCELLENT);
                        break;
                    }
                    default:
                        child.setBehavior(Behavior.UNRATED);
                }

                child.setNotesByTeacher(resultSet.getString(10));

                Parent parent = new Parent();
                findParentByIdStatement.setInt(1, resultSet.getInt(5));
                ResultSet rs = findParentByIdStatement.executeQuery();
                parent.setId(rs.getInt(1));
                parent.setFirstName(rs.getString(2));
                parent.setLastName(rs.getString(3));
                parent.setAddress(rs.getString(4));
                parent.setUsername(rs.getString(5));
                parent.setPassword(rs.getString(6));
                parent.setPhoneNumber(rs.getInt(7));

                child.setParent(parent);

                Teacher teacher = new Teacher();
                findTeacherByIdStatement.setInt(1, resultSet.getInt(6));
                ResultSet rs2 = findTeacherByIdStatement.executeQuery();
                teacher.setId(rs.getInt(1));
                teacher.setFirstName(rs2.getString(2));
                teacher.setLastName(rs2.getString(3));
                teacher.setAddress(rs2.getString(4));
                teacher.setUsername(rs2.getString(5));
                teacher.setPassword(rs2.getString(6));
                teacher.setPhoneNumber(rs2.getInt(7));
                teacher.setStartOfWorkTime(LocalTime.parse(rs2.getString(8)));
                teacher.setEndOfWorkTime(LocalTime.parse(rs2.getString(9)));

                child.setTeacher(teacher);

                Activity activity = new Activity();
                getActivityById.setInt(1, resultSet.getInt(11));
                ResultSet rs3 = getActivityById.executeQuery();
                activity.setId(rs3.getInt(1));
                activity.setName(rs3.getString(2));
                activity.setDescription(rs3.getString(3));

                child.setCurrentActivity(activity);

                ret.add(child);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public ArrayList<Teacher> getTeachers() {
        ArrayList<Teacher> ret = new ArrayList<>();
        try {
            ResultSet resultSet = getTeachersStatement.executeQuery();
            while (resultSet.next()) {
                Teacher teacher = new Teacher();
                teacher.setId(resultSet.getInt(1));
                teacher.setFirstName(resultSet.getString(2));
                teacher.setLastName(resultSet.getString(3));
                teacher.setAddress(resultSet.getString(4));
                teacher.setUsername(resultSet.getString(5));
                teacher.setPassword(resultSet.getString(6));
                teacher.setPhoneNumber(resultSet.getInt(7));
                teacher.setStartOfWorkTime(LocalTime.parse(resultSet.getString(8)));
                teacher.setEndOfWorkTime(LocalTime.parse(resultSet.getString(9)));
                ret.add(teacher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public ArrayList<Activity> getActivities() {
        ArrayList<Activity> ret = new ArrayList<>();
        try {
            ResultSet resultSet = getActivitiesStatement.executeQuery();
            while (resultSet.next()) {
                Activity activity = new Activity();
                activity.setId(resultSet.getInt(1));
                activity.setName(resultSet.getString(2));
                activity.setDescription(resultSet.getString(3));
                ret.add(activity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public void removeChild(Child child) {
        try {
            removeChildStatement.setInt(1, child.getId());
            removeChildStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeTeacher(Teacher teacher) {
        try {
            removeTeacherStatement.setInt(1, teacher.getId());
            removeTeacherStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeActivity(Activity activity) {
        try {
            removeActivityStatement.setInt(1, activity.getId());
            removeActivityStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // this metode returns Person which is Director, Teacher or Parent, this is needed for login
    // it returns null if username is not found
    public Person findByUsername(String username) {
        try {
            findDirectorByUsernameStatement.setString(1, username);
            ResultSet resultSet = findDirectorByUsernameStatement.executeQuery();
            if (resultSet.next()) {
                Director director = new Director();
                director.setId(resultSet.getInt(1));
                director.setFirstName(resultSet.getString(2));
                director.setLastName(resultSet.getString(3));
                director.setAddress(resultSet.getString(4));
                director.setUsername(resultSet.getString(5));
                director.setPassword(resultSet.getString(6));
                director.setPhoneNumber(resultSet.getInt(7));
                return director;
            } else {
                findTeacherByUsernameStatement.setString(1, username);
                resultSet = findTeacherByUsernameStatement.executeQuery();
                if (resultSet.next()) {
                    Teacher teacher = new Teacher();
                    teacher.setId(resultSet.getInt(1));
                    teacher.setFirstName(resultSet.getString(2));
                    teacher.setLastName(resultSet.getString(3));
                    teacher.setAddress(resultSet.getString(4));
                    teacher.setUsername(resultSet.getString(5));
                    teacher.setPassword(resultSet.getString(6));
                    teacher.setPhoneNumber(resultSet.getInt(7));
                    teacher.setStartOfWorkTime(LocalTime.parse(resultSet.getString(8)));
                    teacher.setEndOfWorkTime(LocalTime.parse(resultSet.getString(9)));
                    return teacher;
                } else {
                    findParentByUsernameStatement.setString(1, username);
                    resultSet = findParentByUsernameStatement.executeQuery();
                    if (resultSet.next()) {
                        Parent parent = new Parent();
                        parent.setId(resultSet.getInt(1));
                        parent.setFirstName(resultSet.getString(2));
                        parent.setLastName(resultSet.getString(3));
                        parent.setAddress(resultSet.getString(4));
                        parent.setUsername(resultSet.getString(5));
                        parent.setPassword(resultSet.getString(6));
                        parent.setPhoneNumber(resultSet.getInt(7));
                        return parent;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Parent> getParents() {
        ArrayList<Parent> ret = new ArrayList<>();
        try {
            ResultSet resultSet = getParentsStatement.executeQuery();
            while (resultSet.next()) {
                Parent parent = new Parent();
                parent.setId(resultSet.getInt(1));
                parent.setFirstName(resultSet.getString(2));
                parent.setLastName(resultSet.getString(3));
                parent.setAddress(resultSet.getString(4));
                parent.setUsername(resultSet.getString(5));
                parent.setPassword(resultSet.getString(6));
                parent.setPhoneNumber(resultSet.getInt(7));
                ret.add(parent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public void addChild(Child child) {
        try {
            addChildStatement.setString(1, child.getFirstName());
            addChildStatement.setString(2, child.getLastName());
            addChildStatement.setString(3, child.getAddress());
            addChildStatement.setInt(4, child.getParent().getId());
            addChildStatement.setInt(5, child.getTeacher().getId());
            addChildStatement.setString(6, child.getStart().toString());
            addChildStatement.setString(7, child.getEnd().toString());
            int behavior = 0;
            switch (child.getBehavior()) {
                case EXCELLENT: {
                    behavior = 5;
                    break;
                }
                case VERY_GOOD: {
                    behavior = 4;
                    break;
                }
                case GOOD: {
                    behavior = 3;
                    break;
                }
                case AVERAGE: {
                    behavior = 2;
                    break;
                }
                case POOR: {
                    behavior = 1;
                    break;
                }
                default:
                    behavior = 0;
            }
            addChildStatement.setInt(8, behavior);
            addChildStatement.setString(9, child.getNotesByTeacher());
            addChildStatement.setInt(10, child.getCurrentActivity().getId());
            addChildStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void addParent(Parent parent) {
        try {
            addParentStatement.setString(1, parent.getFirstName());
            addParentStatement.setString(2, parent.getLastName());
            addParentStatement.setString(3, parent.getAddress());
            addParentStatement.setString(4, parent.getUsername());
            addParentStatement.setString(5, parent.getPassword());
            addParentStatement.setInt(6, parent.getPhoneNumber());
            addParentStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addTeacher(Teacher teacher) {
        try {
            addTeacherStatement.setString(1, teacher.getFirstName());
            addTeacherStatement.setString(2, teacher.getLastName());
            addTeacherStatement.setString(3, teacher.getAddress());
            addTeacherStatement.setString(4, teacher.getUsername());
            addTeacherStatement.setString(5, teacher.getPassword());
            addTeacherStatement.setInt(6, teacher.getPhoneNumber());
            addTeacherStatement.setString(7, (teacher.getStartOfWorkTime()).toString());
            addTeacherStatement.setString(8, (teacher.getEndOfWorkTime()).toString());
            addTeacherStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addActivity(Activity activity) {
        try {
            addActivityStatement.setString(1, activity.getName());
            addActivityStatement.setString(2, activity.getDescription());
            addActivityStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Child> getChildrenOfTeacher(int teacherId) {
        ArrayList<Child> children = new ArrayList<>();
        try {
            getChildrenOfTeacherStatement.setInt(1, teacherId);
            ResultSet resultSet = getChildrenOfTeacherStatement.executeQuery();
            while (resultSet.next()) {
                Child child = new Child();
                child.setId(resultSet.getInt(1));
                child.setFirstName(resultSet.getString(2));
                child.setLastName(resultSet.getString(3));
                child.setAddress(resultSet.getString(4));
                child.setStart(LocalTime.parse(resultSet.getString(7)));
                child.setEnd(LocalTime.parse(resultSet.getString(8)));
                switch (resultSet.getInt(9)) {
                    case 1: {
                        child.setBehavior(Behavior.POOR);
                        break;
                    }
                    case 2: {
                        child.setBehavior(Behavior.AVERAGE);
                        break;
                    }
                    case 3: {
                        child.setBehavior(Behavior.GOOD);
                        break;
                    }
                    case 4: {
                        child.setBehavior(Behavior.VERY_GOOD);
                        break;
                    }
                    case 5: {
                        child.setBehavior(Behavior.EXCELLENT);
                        break;
                    }
                    default:
                        child.setBehavior(Behavior.UNRATED);
                }

                child.setNotesByTeacher(resultSet.getString(10));

                Parent parent = new Parent();
                findParentByIdStatement.setInt(1, resultSet.getInt(5));
                ResultSet rs = findParentByIdStatement.executeQuery();
                parent.setId(rs.getInt(1));
                parent.setFirstName(rs.getString(2));
                parent.setLastName(rs.getString(3));
                parent.setAddress(rs.getString(4));
                parent.setUsername(rs.getString(5));
                parent.setPassword(rs.getString(6));
                parent.setPhoneNumber(rs.getInt(7));

                child.setParent(parent);

                Teacher teacher = new Teacher();
                findTeacherByIdStatement.setInt(1, teacherId);
                ResultSet rs2 = findTeacherByIdStatement.executeQuery();
                teacher.setId(rs.getInt(1));
                teacher.setFirstName(rs2.getString(2));
                teacher.setLastName(rs2.getString(3));
                teacher.setAddress(rs2.getString(4));
                teacher.setUsername(rs2.getString(5));
                teacher.setPassword(rs2.getString(6));
                teacher.setPhoneNumber(rs2.getInt(7));
                teacher.setStartOfWorkTime(LocalTime.parse(rs2.getString(8)));
                teacher.setEndOfWorkTime(LocalTime.parse(rs2.getString(9)));

                child.setTeacher(teacher);

                Activity activity = new Activity();
                getActivityById.setInt(1, resultSet.getInt(11));
                ResultSet rs3 = getActivityById.executeQuery();
                activity.setId(rs3.getInt(1));
                activity.setName(rs3.getString(2));
                activity.setDescription(rs3.getString(3));

                child.setCurrentActivity(activity);

                children.add(child);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return children;
    }

    public ArrayList<Child> getChildrenOfParent(int parentId) {
        ArrayList<Child> children = new ArrayList<>();
        try {
            getChildrenOfParentStatement.setInt(1, parentId);
            ResultSet resultSet = getChildrenOfParentStatement.executeQuery();
            while (resultSet.next()) {
                Child child = new Child();
                child.setId(resultSet.getInt(1));
                child.setFirstName(resultSet.getString(2));
                child.setLastName(resultSet.getString(3));
                child.setAddress(resultSet.getString(4));
                child.setStart(LocalTime.parse(resultSet.getString(7)));
                child.setEnd(LocalTime.parse(resultSet.getString(8)));
                switch (resultSet.getInt(9)) {
                    case 1: {
                        child.setBehavior(Behavior.POOR);
                        break;
                    }
                    case 2: {
                        child.setBehavior(Behavior.AVERAGE);
                        break;
                    }
                    case 3: {
                        child.setBehavior(Behavior.GOOD);
                        break;
                    }
                    case 4: {
                        child.setBehavior(Behavior.VERY_GOOD);
                        break;
                    }
                    case 5: {
                        child.setBehavior(Behavior.EXCELLENT);
                        break;
                    }
                    default:
                        child.setBehavior(Behavior.UNRATED);
                }

                child.setNotesByTeacher(resultSet.getString(10));

                Parent parent = new Parent();
                findParentByIdStatement.setInt(1, parentId);
                ResultSet rs = findParentByIdStatement.executeQuery();
                parent.setId(rs.getInt(1));
                parent.setFirstName(rs.getString(2));
                parent.setLastName(rs.getString(3));
                parent.setAddress(rs.getString(4));
                parent.setUsername(rs.getString(5));
                parent.setPassword(rs.getString(6));
                parent.setPhoneNumber(rs.getInt(7));

                child.setParent(parent);

                Teacher teacher = new Teacher();
                findTeacherByIdStatement.setInt(1, resultSet.getInt(6));
                ResultSet rs2 = findTeacherByIdStatement.executeQuery();
                teacher.setId(rs.getInt(1));
                teacher.setFirstName(rs2.getString(2));
                teacher.setLastName(rs2.getString(3));
                teacher.setAddress(rs2.getString(4));
                teacher.setUsername(rs2.getString(5));
                teacher.setPassword(rs2.getString(6));
                teacher.setPhoneNumber(rs2.getInt(7));
                teacher.setStartOfWorkTime(LocalTime.parse(rs2.getString(8)));
                teacher.setEndOfWorkTime(LocalTime.parse(rs2.getString(9)));

                child.setTeacher(teacher);

                Activity activity = new Activity();
                getActivityById.setInt(1, resultSet.getInt(11));
                ResultSet rs3 = getActivityById.executeQuery();
                activity.setId(rs3.getInt(1));
                activity.setName(rs3.getString(2));
                activity.setDescription(rs3.getString(3));

                child.setCurrentActivity(activity);

                children.add(child);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return children;
    }

    public void editChild(Child child, int behavior, String notes) {
        try {
            editChildStatement.setInt(1, behavior);
            editChildStatement.setString(2, notes);
            editChildStatement.setInt(3, child.getCurrentActivity().getId());
            editChildStatement.setInt(4, child.getId());
            editChildStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
