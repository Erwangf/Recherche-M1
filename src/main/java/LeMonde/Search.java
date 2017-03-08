package LeMonde;

import IO.CSVManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static Twitter.TwitterSearch.readAll;

/**
 * Created by Erwan on 06/03/2017.
 */
public class Search {

    /**
     *
     * @param topic the topic ( ex : international )
     * @param nbPages the maximum amount of search page  to fetch
     * @return a list of articles URL ( String )
     * @throws IOException in case of network problem
     */
    public static ArrayList<LeMondeArticle> getUrlFromTopic(String topic, int nbPages) throws IOException {
        //Constants

        final int errorDelay = 6; //5 sec error delay
        final SimpleDateFormat dateFormat = new SimpleDateFormat("Y-m-d'T'H:M:S");
        //initializing
        int articleCount = 0;
        ArrayList<LeMondeArticle> result = new ArrayList<>();

        for(int pageIndex = 1; pageIndex<=nbPages;pageIndex++){
            String pageUrl = "http://www.lemonde.fr/"+topic+"/"+pageIndex+".html";

            //opening connexion
            HttpURLConnection connection = (HttpURLConnection) new URL(pageUrl).openConnection();
            //setting requests property
            connection.setRequestProperty("Accept-Charset", "utf-8");
            connection.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("x-overlay-request", "true");
            connection.setRequestMethod("GET");

            InputStreamReader ISR = new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8"));
            BufferedReader rd = new BufferedReader(ISR);

            String text = readAll(rd);
            Document doc = Jsoup.parse(text);
            Elements articles = doc.select("article");
            for(Element article : articles){
                // date
                Elements date_html = article.select("time");
                String date_article_brut = date_html.get(0).attr("datetime");
                Date date_article;
                try {
                    date_article = dateFormat.parse(date_article_brut);
                } catch (ParseException e) {
                    System.out.println("Error, incorrect date format :\n"+date_article_brut);
                    date_article = null;
                }


                // link

                String link = "http://www.lemonde.fr"+article.select("a").get(0).attr("href");


                // title of the article

                Element titleElement = article.select("a").get(0);
                titleElement.select("span").forEach(Element::remove); //we remove all the anoying span elements...
                String title = titleElement.text();

                // finally, we add this article to the result
                result.add(new LeMondeArticle(title,date_article,link,topic));

            }

        }

        return result;
    }

    public static void main(String[] args) throws IOException {
        ArrayList<LeMondeArticle> articles = getUrlFromTopic("international",1);
        articles.forEach(System.out::println);
        CSVManager<LeMondeArticle> csvManager = new CSVManager<>();
        csvManager.writeToCSV(articles,"articles_lemonde.csv");
    }
}
