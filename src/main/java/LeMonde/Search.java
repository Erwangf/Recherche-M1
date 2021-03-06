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



	public static String getHTMLFromURL(String url) throws IOException {
		//opening connexion
		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		//setting requests property
		connection.setRequestProperty("Accept-Charset", "utf-8");
		connection.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		connection.setRequestProperty("x-overlay-request", "true");
		connection.setRequestMethod("GET");

		InputStreamReader ISR = new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8"));
		BufferedReader rd = new BufferedReader(ISR);
		return readAll(rd);
	}

	/**
	 *
	 * @param topic the topic ( ex : international )
	 * @return a list of articles URL ( String )
	 * @throws ParseException 
	 */
	public static ArrayList<LeMondeArticle> getUrlFromTopic(String topic,Date dd,Date df) throws ParseException {
		//Constants
		final int errorDelay = 5000; //5 sec error delay
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-dd'T'hh:mm:ss");
		//initializing
		int articleCount = 0;
		ArrayList<LeMondeArticle> result = new ArrayList<>();
		int pageIndex= 1;
		boolean ilenreste = true;
		boolean a_parcourir = true;
		while(ilenreste){
			System.out.println(pageIndex);
			String pageUrl = "http://www.lemonde.fr/"+topic+"/"+pageIndex+".html";
			boolean fini = false;
			String text  = "";
			while(!fini)
			try {
				text = getHTMLFromURL(pageUrl);
				fini = true;
			} catch (IOException e) {
				System.out.println("Erreur, on attend !");
				try {
					Thread.sleep(errorDelay);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}

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
					assert date_article != null;
					System.out.println(date_article.toString());
					if (date_article.before(df)){
						if(date_article.after(dd)){
							//Starting processings for object instanciation
							// link
							String link = "http://www.lemonde.fr"+article.select("a").get(0).attr("href");


							// title of the article

							Element titleElement = article.select("a").get(0);
							titleElement.select("span").forEach(Element::remove); //we remove all the anoying span elements...
							String title = titleElement.text();

							String id = topic+"_"+articleCount; // example of ID : international_1233

							// finally, we add this article to the result
							result.add(new LeMondeArticle(id,title,date_article,link,topic));
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

	private static void topicsearch(String topic,Date dd,Date df) throws IOException, ParseException {
		ArrayList<LeMondeArticle> articles = getUrlFromTopic(topic,dd,df);
		CSVManager<LeMondeArticle> csvManager = new CSVManager<>();
		csvManager.writeToCSV(articles,topic+".csv");
		System.out.println(topic+" :fini");

	}

	public static void main(String[] args) throws IOException, ParseException {
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-dd'T'hh:mm:ss");
		Date date_debut = dateFormat.parse("2016-01-01T00:00:00");
		Date date_fin = dateFormat.parse("2017-01-01T00:00:00");

		/*BufferedReader br = new BufferedReader(new FileReader("thememonde.txt"));
		String line = br.readLine();
		String[] themes = line.split(";");
		br.close();
		for (String t : themes){
			topicsearch(t,date_debut,date_fin);
		};*/
		if(args.length!=1) throw new Error("ERROR, MISSING ARG");

		topicsearch(args[0],date_debut,date_fin);

	}
}
