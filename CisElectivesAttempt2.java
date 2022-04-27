import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CisElectivesAttempt2 {

    private String url;
    private Document currentDoc;
    private List<String>electivesDescriptions;
    private List<String> electives;

    /**
     * Constructor for CisElectives
     * Automatically calls fillElectiveList() method
     * Assumes the cis url
     */
    public CisElectivesAttempt2(){
        url = "https://advising.cis.upenn.edu/tech-electives/";
        electivesDescriptions = new LinkedList<>();
        electives = new LinkedList<>();
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
            Pattern coursePattern = Pattern.compile("<span class=\"tooltip\">(.*)<span.*</span></span>");
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
                            String courseCode = getCourseCode.group(1);
                            electives.add(courseCode+"-"+electivesDescriptions.get(index));
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

    public void printElectiveList(){
        int i=1;
        for (String e: electives){
            System.out.println(i + ": " + e);
            i++;
        }
    }

}
