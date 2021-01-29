package ba.unsa.etf.rpr;

import java.time.LocalTime;

public class Teacher extends Person {
    private String username;
    private String password;
    private int phoneNumber;
    private LocalTime startOfWorkTime;
    private LocalTime endOfWorkTime;

    public Teacher(){}

    public Teacher(String firstName, String lastName, String address, String username, String password, int phoneNumber, LocalTime startOfWorkTime, LocalTime endOfWorkTime) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setAddress(address);
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.startOfWorkTime = startOfWorkTime;
        this.endOfWorkTime = endOfWorkTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalTime getStartOfWorkTime() {
        return startOfWorkTime;
    }

    public void setStartOfWorkTime(LocalTime startOfWorkTime) {
        this.startOfWorkTime = startOfWorkTime;
    }

    public LocalTime getEndOfWorkTime() {
        return endOfWorkTime;
    }

    public void setEndOfWorkTime(LocalTime endOfWorkTime) {
        this.endOfWorkTime = endOfWorkTime;
    }
}
