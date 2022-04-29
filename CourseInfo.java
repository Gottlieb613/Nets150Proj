import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


//TODO: FINISH
// this is a work in progress
public class CourseInfo {
    public static HashMap<String, Course> getAllCoursesInSubject(String subjCode) {
        Document subjDoc;
        String url = "https://catalog.upenn.edu/courses/" + subjCode.toLowerCase();
        HashMap<String, Course> coursesMap = new HashMap<>();
        try {
            subjDoc = Jsoup.connect(url).get();
        } catch (IOException e) {
            System.out.println("URL connection failed, given url = " + url);
            return null;
        }

        Pattern getCourseInfo = Pattern.compile("<div class=\"courseblock\">.*");

        Elements divElems = subjDoc.select("div");
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

                    //adding DESCRIPTION to c Course object
                    Pattern getDescr = Pattern.compile(".*courseblockextra noindent\">(.*)</p>.*");
                    Matcher matchDescr = getDescr.matcher(div.toString());
                    if (matchDescr.find()) {
                        String description = matchDescr.group(1);
                        c.addDescription(description);
                    }

                    //adding PREREQS to c Course object
                    // yeah this regex is wacko, i dont know how to explain it
                    Elements pElems = div.select("p");
                    Pattern prereqElement = Pattern.compile(
                            "<p class=\"courseblockextra noindent\">Prerequisite:.*");
                    Pattern getPrereqs = Pattern.compile(".*this, '(.+ \\d+)'.*");
                    for (Element p : pElems) {
                        Matcher matchPrereqElem = prereqElement.matcher(p.toString());
                        if (matchPrereqElem.find()) {
                            Matcher matchPrereqs = getPrereqs.matcher(p.toString());
                            while (matchPrereqs.find()) {
                                String pre = matchPrereqs.group(1);
                                c.addPrereq(pre);
                            }
                        }
                    }

                    //now putting c Course object in the overall HashMap
                    coursesMap.put(id, c);
                }
            }
        }

        return coursesMap;
    }

    public static Course getCourseObj(String subjCode, String courseID) {
        Document subjDoc;
        String url = "https://advising.cis.upenn.edu/tech-electives/" + subjCode.toLowerCase();
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
            Matcher isInfo = getCourseInfo.matcher(div.toString());
            if (isInfo.find()) {
                Pattern getIdAndTitle = Pattern.compile(".*<strong>(.*)</strong>.*");
                //selectInfo's group1 should be the courseID and group2 is the title
                Matcher selectIdAndTitle = getIdAndTitle.matcher(div.toString());


                //check that we are in a course element
                // AND that it's the same as the course we're searching for
                if (selectIdAndTitle.find() && selectIdAndTitle.group(1).equals(courseID)) {
                    String id = selectIdAndTitle.group(1);
                    String title = selectIdAndTitle.group(2);
                    c = new Course(courseID, title);

                    //adding DESCRIPTION to c Course object
                    Pattern getDescr = Pattern.compile(".*courseblockextra noindent\">(.*)</p>.*");
                    Matcher matchDescr = getDescr.matcher(div.toString());
                    if (matchDescr.find()) {
                        String description = matchDescr.group(1);
                        c.addDescription(description);
                    }

                    //adding PREREQS to c Course object
                    // yeah this regex is wacko, i dont know how to explain it
                    Elements pElems = div.select("p");
                    Pattern prereqElement = Pattern.compile(
                            "<p class=\"courseblockextra noindent\">Prerequisite:.*");
                    Pattern getPrereqs = Pattern.compile(".*this, '(.+ \\d+)'.*");
                    for (Element p : pElems) {
                        Matcher matchPrereqElem = prereqElement.matcher(p.toString());
                        if (matchPrereqElem.find()) {
                            Matcher matchPrereqs = getPrereqs.matcher(p.toString());
                            while (matchPrereqs.find()) {
                                c.addPrereq(matchPrereqs.group(1));
                            }
                        }
                    }

                    return c;
                }
            }
        }

        System.out.println("Course not found");
        return null;
    }
}
