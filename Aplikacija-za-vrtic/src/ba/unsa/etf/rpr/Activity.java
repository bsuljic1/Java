package ba.unsa.etf.rpr;

public class Activity {
    private int id;
    private String name;
    private String description;

    public Activity() {
    }

    public Activity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Activity(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return name;
    }
}
