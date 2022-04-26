import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
public class CisElectives {

    private String url;
    private Document currentDoc;
    private List<String>electives;

    /**
     * Constructor for CisElectives
     */
    public CisElectives(){
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
            //System.out.println(contentForElectives);
            System.out.println("2- " + contentForElectives.get(2).text());
            System.out.println("3- " + contentForElectives.get(3).text().equals("NO"));
            System.out.println("4- " + contentForElectives.get(4).select("td > span.tooltip").text());

        } catch (IOException e){
            System.out.println("Something went wrong extracting electives from url");
        }
    }



}
