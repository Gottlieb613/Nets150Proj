import java.util.ArrayList;

class Course {
    private final String id;
    private final String title;
    private String description;
    private ArrayList<Course> prereqs;

    public Course(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public void addDescription(String desc) {
        this.description = desc;
    }

    public void addPrereq(Course pre) {
        this.prereqs.add(pre);
    }

    public String getID() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public ArrayList<Course> getPrereqs() {
        return this.prereqs;
    }

    public String toString() {
        return this.id + ": " + this.title;
    }
}
