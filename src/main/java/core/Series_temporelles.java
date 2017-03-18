package core;
import java.text.SimpleDateFormat;
import IO.CSVManager;
import LeMonde.LeMondeArticle;
import LeMonde.Search;
import Twitter.MiTweet_Wrapper;
import Twitter.TwitterSearch;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Series_temporelles {
	public static void main(String[] args) throws ParseException {
			
		Timestamp ts_t = new Timestamp(1479335886*1000L);
		System.out.println(ts_t.toString());
		Scanner z = new Scanner(System.in);
		z.nextLine();
		//lecture articles
		LeMondeArticle article_tmp = new LeMondeArticle();
		CSVManager<LeMondeArticle> Url_articles = new CSVManager<LeMondeArticle>(";",true);
		ArrayList<LeMondeArticle> articles = Url_articles.readFromCSV("articles_i.csv",article_tmp);

		//    lecture tweets
		MiTweet_Wrapper tweet_tmp = new MiTweet_Wrapper();
		CSVManager<MiTweet_Wrapper> Url_tweets = new CSVManager<MiTweet_Wrapper>(";",true);
		ArrayList<MiTweet_Wrapper> tweets = Url_tweets.readFromCSV("tweets_i.csv",tweet_tmp);
		long[] l_ta = articles.get(0).courbe_temporelle_jour(
				(ArrayList<MiTweet_Wrapper>) 
				tweets.stream()
				.filter(
						a -> a.getId_Article().equals(articles.get(0).getId())
						)
				.collect(Collectors.toList())
				);
		for (long l : l_ta){
			System.out.println(Long.toString(l));
		}
		//    for (MiTweet_Wrapper t : tweets){
		//    	System.out.println(t.getUsername());
		//    }
	}
}
