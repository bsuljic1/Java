package ba.unsa.etf.rpr;

import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalTime;

public class Child extends Person {
    private Behavior behavior = Behavior.UNRATED;
    private Parent parent;
    private Teacher teacher;
    private Activity currentActivity;
    private LocalTime start;
    private LocalTime end;
    private String notesByTeacher = "";

    public Child(){
    }

    public Child(String firstName, String lastName, String address, Behavior behavior, Parent parent, Teacher teacher, Activity activity, LocalTime start, LocalTime end) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setAddress(address);
        this.behavior = behavior;
        this.parent = parent;
        this.teacher = teacher;
        this.currentActivity = activity;
        this.start = start;
        this.end = end;
        this.behavior = Behavior.UNRATED;
        this.notesByTeacher = "No notes yet.";
    }

    public Behavior getBehavior() {
        return behavior;
    }

    public void setBehavior(Behavior behavior) {
        this.behavior = behavior;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public Parent getParent() {
        return parent;
    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }

    public String getNotesByTeacher() {
        return notesByTeacher;
    }

    public void setNotesByTeacher(String notesByTeacher) {
        this.notesByTeacher = notesByTeacher;
    }

    public Activity getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(Activity currentActivity) {
        this.currentActivity = currentActivity;
    }
}
