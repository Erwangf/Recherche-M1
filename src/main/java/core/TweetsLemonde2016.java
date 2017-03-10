package core;


import IO.CSVManager;
import LeMonde.LeMondeArticle;
import LeMonde.Search;
import Twitter.MiTweet_Wrapper;
import Twitter.TwitterSearch;
import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class TweetsLemonde2016 {
    public static void main(String[] args) throws IOException, ParseException {
        ArrayList<LeMondeArticle> articles = Search.getUrlFromTopic("international", 200);

        ArrayList<MiTweet_Wrapper> miTweet_wrappers = new ArrayList<>();
        articles.forEach(article -> {
            try {
                System.out.println(article.getId());
                TwitterSearch
                        .getTweetsFromTwitter(10000,article.getLink(),true)
                        .forEach(t-> miTweet_wrappers.add(new MiTweet_Wrapper(t,article.getId())));

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        });

        CSVManager<MiTweet_Wrapper> csvManager = new CSVManager<MiTweet_Wrapper>(";",true);
        csvManager.writeToCSV(miTweet_wrappers,"testLeMonde2016_International_pages10_to_10.csv");

    }



}
