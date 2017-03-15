package LeMonde;

import IO.CSVConvertible;
import Twitter.MiTweet_Wrapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Erwan on 06/03/2017.
 */
public class LeMondeArticle implements CSVConvertible {
    private String id;
    private Date date;
    private String title;
    private String link;
    private String topic;

    final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-dd'T'hh:mm:ss");


    public LeMondeArticle(String id, String title, Date date, String link, String topic) {

        this.id= id;
        this.title = title;
        this.date = date;	
        this.link = link;
        this.topic = topic;

    }   
    public LeMondeArticle() {
    }

    @Override
    public String toString() {
        return "-----------------------------------------------" +
                "\ndate=" + date +
                "\ntitle='" + title + '\'' +
                "\nlink='" + link + '\'' +
                "\nid='" + id + '\'' +
                "\ntopic='"+topic+'\''+
                '}';
    }

    @Override
    public String[] getCSVHeaders(){
        return new String[]{"id","title","date","link","topic"};
    }

    @Override
    public Object getObjectFromField(String[] fields) {
        try {
            return new LeMondeArticle(
                    fields[0],
                    fields[1],
                    dateFormat.parse(fields[2]),
                    fields[3],
                    fields[4]
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String[] getFields(){
        return new String[]{
                id,
                title,
                dateFormat.format(date),
                link,
                topic
        };
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    
    public long ecart_jour_tweet(MiTweet_Wrapper mi) throws ParseException {
    	SimpleDateFormat or = new SimpleDateFormat("yyyy-M-dd'T'hh:mm:ss");  
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-dd");
    	Date jour_art = dateFormat.parse(or.format(this.getDate()));
    	System.out.println(dateFormat.format(jour_art));
    	Date jour_twee = dateFormat.parse(or.format(mi.getTime_stamp()));
    	System.out.println(dateFormat.format(jour_twee));
		return (mi.getTime_stamp().getTime()-this.date.getTime())/24;
	}
    public long ecart_long_tweet(MiTweet_Wrapper mi) {
		return mi.getTime_stamp().getTime()-this.date.getTime();
	}
    public long[] courbe_temporelle_jour(ArrayList<MiTweet_Wrapper> a_mi) throws ParseException {
    	long[] t_l = new long[a_mi.size()];
    	int i=0;
    	for (MiTweet_Wrapper m : a_mi){
    		t_l[i]=ecart_jour_tweet(m);
    		i++;
    	}
		return t_l;
	}
}
