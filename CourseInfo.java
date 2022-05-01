import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


//This works fine (at least it did in my minimal testing lol)
public class CourseInfo {
    public static ArrayList<Course> getAllCoursesInSubject(String subjCode) {
        Document subjDoc;
        String url = "https://catalog.upenn.edu/courses/" + subjCode.toLowerCase();
        ArrayList<Course> coursesMap = new ArrayList<>();
        try {
            subjDoc = Jsoup.connect(url).get();
        } catch (IOException e) {
            System.out.println("URL connection failed, given url = " + url);
            return null;
        }

        Pattern getCourseInfo = Pattern.compile("<div class=\"courseblock\">.*");

        Elements divElems = subjDoc.select("div");
        for (Element div : divElems) {
            //the boolean below is because for some reason other div tags
            // were matching, even though they DIDNT start with <div class="courseblock"
            // I have no idea why the regex would allow that but whatever
            boolean classOnly = div.toString().charAt(12) == 'c';
            Matcher isInfo = getCourseInfo.matcher(div.toString());
            if (isInfo.find() && classOnly) {
                Pattern getIdAndTitle = Pattern.compile(".*<strong>(.*)&nbsp;(\\d+) (.*)</strong>.*");
                //selectInfo's group1 should be the courseID and group2 is the title
                Matcher selectIdAndTitle = getIdAndTitle.matcher(div.toString());

                if (selectIdAndTitle.find()) {
                    Elements pElems = div.select("p");
                    String id = selectIdAndTitle.group(1) + " " + selectIdAndTitle.group(2);
                        //the above is to work around the stupid "&nbsp;" in course names in the HTML
                        // i.e. CIS&nbsp;110
                    String title = selectIdAndTitle.group(3);
                    Course c = new Course(id, title);

                    //adding DESCRIPTION to c Course object
                    Pattern getDescr = Pattern.compile(".*courseblockextra noindent\">(.*)</p>.*");
                    Matcher matchDescr = getDescr.matcher(pElems.get(1).toString());
                    if (matchDescr.find()) {
                        String description = matchDescr.group(1);
                        c.addDescription(description);
                    }

                    //adding PREREQS to c Course object
                    Pattern prereqElement = Pattern.compile(
                            "<p class=\"courseblockextra noindent\">Prerequisite:.*");
                    Pattern getPrereqPElem = Pattern.compile(".*this, '(.+ \\d+)'.*");
                    for (Element p : pElems) {
                        Matcher matchPrereqElem = prereqElement.matcher(p.toString());
                        if (matchPrereqElem.find()) {
                            for (Element a : p.select("a")) {
                                Matcher matchPrereqs = getPrereqPElem.matcher(a.toString());
                                if (matchPrereqs.find()) {
                                    String pre = matchPrereqs.group(1);
                                    c.addPrereq(pre);
                                }
                            }

                        }
                    }

                    //now putting c Course object in the overall Array
                    coursesMap.add(c);
                }
            }
        }

        return coursesMap;
    }

    public static Course getCourseObj(String subjCode, String courseID) {
        Document subjDoc;
        String url = "https://catalog.upenn.edu/courses/" + subjCode.toLowerCase();
        Course c;
        try {
            subjDoc = Jsoup.connect(url).get();
        } catch (IOException e) {
            System.out.println("URL connection failed, given url = " + url);
            return null;
        }

        Pattern getCourseInfo = Pattern.compile("<div class=\"courseblock\">.*");

        Elements divElems = subjDoc.select("div");
        for (Element div : divElems) {
            //the boolean below is because for some reason other div tags
            // were matching, even though they DIDNT start with <div class="courseblock"
            // I have no idea why the regex would allow that but whatever
            boolean classOnly = div.toString().charAt(12) == 'c';
            Matcher isInfo = getCourseInfo.matcher(div.toString());
            if (isInfo.find() && classOnly) {
                Pattern getIdAndTitle = Pattern.compile(".*<strong>(.*)&nbsp;(\\d+) (.*)</strong>.*");
                //selectInfo's group1 should be the courseID and group2 is the title
                Matcher selectIdAndTitle = getIdAndTitle.matcher(div.toString());


                if (selectIdAndTitle.find()) {
                    Elements pElems = div.select("p");
                    String id = selectIdAndTitle.group(1) + " " + selectIdAndTitle.group(2);
                    if (id.equals(courseID)) {
                        String title = selectIdAndTitle.group(3);
                        c = new Course(id, title);

                        //adding DESCRIPTION to c Course object
                        Pattern getDescr = Pattern.compile(".*courseblockextra noindent\">(.*)</p>.*");
                        Matcher matchDescr = getDescr.matcher(pElems.get(1).toString());
                        if (matchDescr.find()) {
                            String description = matchDescr.group(1);
                            c.addDescription(description);
                        }

                        //adding PREREQS to c Course object
                        Pattern prereqElement = Pattern.compile(
                                "<p class=\"courseblockextra noindent\">Prerequisite.*");
                        Pattern getPrereqPElem = Pattern.compile(".*this, '(.+ \\d+)'.*");
                        for (Element p : pElems) {
                            Matcher matchPrereqElem = prereqElement.matcher(p.toString());
                            if (matchPrereqElem.find()) {
                                for (Element a : p.select("a")) {
                                    Matcher matchPrereqs = getPrereqPElem.matcher(a.toString());
                                    if (matchPrereqs.find()) {

                                        String pre = matchPrereqs.group(1);
                                        c.addPrereq(pre);
                                    }
                                }

                            }
                        }

                        //now returning c Course
                        return c;
                    }
                }
            }
        }

        //only will exit loop without returning
        // if did not find the course
        System.out.println("Course not found");
        return null;
    }

    //Instead of getting the course title manually from every page which is pain, as we already spent too much time
    //doing that before. This method can easily just return the subject Title for any valid subject code
    public static String getSubjectTitle(String courseCode){
        Document doc ;
        String url = "https://catalog.upenn.edu/courses/" + courseCode.toLowerCase();
        String courseTitle = "";
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            System.out.println("URL connection failed, given url = " + url);
            return null;
        }
        Pattern yesCourseTitle = Pattern.compile("^(.*?)\\s\\(");
        String temp = doc.getElementsByClass("page-title").text();
        Matcher check = yesCourseTitle.matcher(temp);
        if (check.find()){
            courseTitle = check.group(1);
        }
        //System.out.println(courseTitle);
        return courseTitle;
    }
}