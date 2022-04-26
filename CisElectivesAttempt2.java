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
    private List<String>electives;

    /**
     * Constructor for CisElectives
     */
    public CisElectivesAttempt2(){
        url = "https://advising.cis.upenn.edu/tech-electives/";
        electives = new LinkedList<>();
        try {
            this.currentDoc = Jsoup.connect(this.url).get();
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

            for (int i = 0; i < contentForElectives.size(); i++) {
                Element tdCurrent = contentForElectives.get(i);
                Matcher checkYes = yesPattern.matcher(tdCurrent.toString());
                if (checkYes.find()) { //the i'th <td> elem is a green YES
                    Element tdCourses = contentForElectives.get(i + 1);
                    Elements spans = tdCourses.select("span");

                    //this loop gets only the <span> elements with a course code
                    // and extracts the code itself.
                    // the problem is, the course Description is always outside of the <span>
                    // and doesn't even seem to be in any type of element!
                    // so at this point idk how to retrieve it
                    for (Element span : spans) {
                        Matcher getCourseCode = coursePattern.matcher(span.toString());
                        if (getCourseCode.find()) {
                            String courseCode = getCourseCode.group(1);
                            System.out.println(courseCode);

                        }
                    }

                }
            }

            //System.out.println(contentForElectives);
            System.out.println("2- " + contentForElectives.get(2).text());
            System.out.println("3- " + contentForElectives.get(3).text().equals("NO"));
            System.out.println("4- " + contentForElectives.get(4).select("td > span.tooltip").text());

        } catch (IOException e){
            System.out.println("Something went wrong extracting electives from url");
        }
    }



}
