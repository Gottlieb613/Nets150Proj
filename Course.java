import java.util.ArrayList;

class Course {
    private final String id;
    private final String title;
    private String description;
    private ArrayList<String> prereqs;

    public Course(String id, String title) {
        this.id = id;
        this.title = title;
        this.prereqs = new ArrayList<>();
    }

    public void addDescription(String desc) {
        this.description = desc;
    }

    public void addPrereq(String pre) {
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

    //this will return a string
    public String getDescriptionNewlines() {
        int wordsPerLine = 15;
        String descriptionLines = "";
        String[] newlineArray = this.description.split(" ");
        for (int i = 0; i < newlineArray.length; i++) {
            if (i == newlineArray.length - 1) {
                descriptionLines += newlineArray[i];
            }
            else if (i % wordsPerLine != wordsPerLine - 1) {
                descriptionLines += newlineArray[i] + " ";
            } else {
                descriptionLines += newlineArray[i] + "\n";
            }
        }
        return descriptionLines;
    }

    public ArrayList<String> getPrereqs() {
        return this.prereqs;
    }

    public String toString() {
        return this.id + ": " + this.title;
    }
}
