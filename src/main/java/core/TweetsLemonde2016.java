package core;


import IO.CSVManager;
import LeMonde.LeMondeArticle;
import LeMonde.Search;
import Twitter.MiTweet_Wrapper;
import Twitter.TwitterSearch;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class TweetsLemonde2016 {
    public static void main(String[] args) throws IOException, ParseException {
		BufferedReader br = new BufferedReader(new FileReader("theme.txt"));
		String line = br.readLine();
		String[] themes = line.split(";");
		br.close();  
		for (String theme : themes){
			
    	LeMondeArticle article_tmp = new LeMondeArticle();
    	CSVManager<LeMondeArticle> Url_articles = new CSVManager<LeMondeArticle>(";",true);
        ArrayList<LeMondeArticle> articles = Url_articles.readFromCSV(theme+".csv",article_tmp);
        ArrayList<MiTweet_Wrapper> miTweet_wrappers = new ArrayList<>();
        articles.forEach(article -> {
            try {
                System.out.println(article.getId());
                TwitterSearch
                        .getTweetsFromTwitter(1,article.getLink(),true)
                        .forEach(t-> miTweet_wrappers.add(new MiTweet_Wrapper(t,article.getId())));

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        });
        miTweet_wrappers.forEach(mw -> {
                System.out.println(mw.toString());
        });
        CSVManager<MiTweet_Wrapper> csvManager = new CSVManager<MiTweet_Wrapper>(";",true);
        csvManager.writeToCSV(miTweet_wrappers,"tweet_"+theme+"_partiel.csv");

    }

    }

}
