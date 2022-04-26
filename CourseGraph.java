import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CourseGraph {
    private ArrayList<Course> courses;
    private final String title;
    private Document cisDoc;

    public CourseGraph(String title) {
        this.title = title;
        this.courses = new ArrayList<>();
    }

    public void makeGraph() {
        try {
            String cisURL = "https://catalog.upenn.edu/courses/cis/";
            this.cisDoc = Jsoup.connect(cisURL).get();
        } catch (IOException e) {
            System.out.println("Could not load CIS catalog");
        }



        Pattern getCourseInfo = Pattern.compile("<div class=\"courseblock\">.*");

        Elements divElems = cisDoc.select("div");
        for (Element div : divElems) {
            Matcher isInfo = getCourseInfo.matcher(div.toString());
            if (isInfo.find()) {
                Pattern getIdAndTitle = Pattern.compile(".*<strong>(.*\\d+) (.*)</strong>.*");
                //selectInfo's group1 should be the courseID and group2 is the title
                Matcher selectIdAndTitle = getIdAndTitle.matcher(div.toString());
                if (selectIdAndTitle.find()) {
                    String id = selectIdAndTitle.group(1);
                    String title = selectIdAndTitle.group(2);
                    Course c = new Course(id, title);
                    courses.add(c);
                }
            }
        }

        //TODO: DELETE
        // testing code
        for (Course c : courses) {
            System.out.println(c);
        }
    }




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
}
