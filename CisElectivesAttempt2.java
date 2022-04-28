import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CisElectivesAttempt2 {

    private String url;
    private Document currentDoc;
    private List<String>electivesDescriptions;
    private List<String> electives;
    private HashMap<String, ArrayList<Course>> elecMap;
        //this is a map that connects a department string
        //with an array of all the acceptable courses in it

    /**
     * Constructor for CisElectives
     * Automatically calls fillElectiveList() method
     * Assumes the cis url
     */
    public CisElectivesAttempt2(){
        url = "https://advising.cis.upenn.edu/tech-electives/";
        electivesDescriptions = new LinkedList<>();
        electives = new LinkedList<>();
        elecMap = new HashMap<>();

        try {
            this.currentDoc = Jsoup.connect(this.url).get();
            fillElectiveList();
        } catch (IOException e) {
            System.out.println("URL connection failed, given url = " + url);
        }
    }

    public void fillElectiveList(){
        try{
            Document electiveDocument = Jsoup.connect(url).get();
            Elements contentForElectives = electiveDocument.getElementsByTag("td");
            Pattern yesPattern = Pattern.compile("<td bgcolor=\"green\">YES</td>");
            Pattern coursePattern = Pattern.compile("<span class=\"tooltip\">(.+) (\\d+)<span.*</span></span>");
            Pattern courseDescription = Pattern.compile("</span> (.*?)<br> ", Pattern.CASE_INSENSITIVE);

            int index = 0;
            for (int i = 0; i < contentForElectives.size(); i++) {
                Element tdCurrent = contentForElectives.get(i);
                Matcher checkYes = yesPattern.matcher(tdCurrent.toString());
                if (checkYes.find()) { //the i'th <td> elem is a green YES
                    Element tdCourses = contentForElectives.get(i + 1);
                    Elements spans = tdCourses.select("span");

                    //this matches for the course name inside the td tag.
                    //course name is not inside the span tag
                    Matcher check1 = courseDescription.matcher(tdCourses.toString());
                    while (check1.find()){
                        electivesDescriptions.add(check1.group(1));
                    }

                    //this loop gets only the <span> elements with a course code
                    // and extracts the code itself
                    //it also fills the electives list with course codes and names
                    for (Element span : spans) {
                        Matcher getCourseCode = coursePattern.matcher(span.toString());
                        if (getCourseCode.find()) {
                            /*
                                Charlie: I changed the Pattern very slightly
                                so that I could extract the department (i.e. CBE, MEAM, STAT, etc)
                                in addition to the id itself (i.e. 1210, 4100, etc).
                                I combined them in 'courseCode' below to make the standard (like MEAM 1001)
                                and then I also made a 'Course' object with the information of the courseCode
                                and the description (thanks Hussain) and put that in a HashMap
                                that has keys of the deparment and values of an array with all
                                elective courses in that department
                             */

                            String department = getCourseCode.group(1);
                            String courseCode = department + " " + getCourseCode.group(2);

                            electives.add(courseCode+"-"+electivesDescriptions.get(index));


                            Course elec = new Course(courseCode, electivesDescriptions.get(index));
                            if (elecMap.containsKey(department)) {
                                elecMap.get(department).add(elec);
                            } else {
                                elecMap.put(department, new ArrayList<>());
                                elecMap.get(department).add(elec);
                            }


                            index++;
                            //System.out.println(courseCode + " " + electivesDescriptions.get(index));
                        }
                    }

                }//end big if statement
            }

        } catch (IOException e){
            System.out.println("Something went wrong extracting electives from url");
        }
    }

    public ArrayList<Course> coursesInDept(String department) {
        return elecMap.get(department);
    }

    public void printElectiveList(){
        int i=1;
        for (String e: electives){
            System.out.println(i + ": " + e);
            i++;
        }
    }

}
