package ba.unsa.etf.rpr;


public class Parent extends Person {
    private String username;
    private String password;
    private int phoneNumber;

    public Parent(){

    }

    public Parent(String firstName, String lastName, String address, String username, String password, int phoneNumber) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setAddress(address);
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
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


}
