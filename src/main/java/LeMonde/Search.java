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
import java.util.Locale;

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
	 * @throws ParseException 
	 */
	public static ArrayList<LeMondeArticle> getUrlFromTopic(String topic) throws IOException, ParseException {
		//Constants
		final int errorDelay = 6; //5 sec error delay
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-dd'T'hh:mm:ss");
		final Date date_debut = dateFormat.parse("2017-03-01T00:00:00");
		final Date date_fin = dateFormat.parse("2017-03-03T00:00:00");
		//initializing
		int articleCount = 0;
		ArrayList<LeMondeArticle> result = new ArrayList<>();
		int pageIndex= 1;
		boolean ilenreste = true;
		boolean a_parcourir = true;
		while(ilenreste){
			System.out.println(pageIndex);
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
			a_parcourir = true;
			int index_articles = 0;
			while(a_parcourir){
				if (index_articles!=articles.size()-1){
					Element article	= articles.get(index_articles);
					// date
					//test
					Elements date_html = article.select("time");
					String date_article_brut = date_html.get(0).attr("datetime");
					Date date_article;
					try {
						date_article = dateFormat.parse(date_article_brut);
					} catch (ParseException e) {
						System.out.println("Error, incorrect date format :\n"+date_article_brut);
						date_article = null;
					}
					System.out.println(date_article.toString());
					if (date_article.before(date_fin)){
						if(date_article.after(date_debut)){
							//Starting processings for object instanciation
							// link
							String link = "http://www.lemonde.fr"+article.select("a").get(0).attr("href");


							// title of the article

							Element titleElement = article.select("a").get(0);
							titleElement.select("span").forEach(Element::remove); //we remove all the anoying span elements...
							String title = titleElement.text();

							String id = topic+"_"+articleCount; // example of ID : international_1233

							// finally, we add this article to the result
							result.add(new LeMondeArticle(id,title,date_article,link));
							System.out.println(result.get(result.size()-1).toString());
							articleCount++;
						}
						else{
							ilenreste = false;
							a_parcourir = false;
						}
					}
					//fintestdate
					index_articles++;
				}
				else{
					a_parcourir = false;
				}
			}			
			pageIndex++;
		}
		return result;
	}

	private static void topicsearch(String topic) throws IOException, ParseException {
		ArrayList<LeMondeArticle> articles = getUrlFromTopic(topic);
		CSVManager<LeMondeArticle> csvManager = new CSVManager<>();
		csvManager.writeToCSV(articles,topic+".csv");
		System.out.println(topic+" :fini");

	}

	public static void main(String[] args) throws IOException, ParseException {
		topicsearch("international");
		topicsearch("sport");
	}
}
