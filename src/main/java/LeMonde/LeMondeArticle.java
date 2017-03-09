package LeMonde;

import IO.CSVConvertible;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    final static SimpleDateFormat dateFormat = new SimpleDateFormat("Y-m-d'T'H:M:S");

    public LeMondeArticle(String id, String title, Date date, String link, String topic) {

        this.id= id;
        this.title = title;
        this.date = date;
        this.link = link;
        this.topic = topic;
    }

    @Override
    public String toString() {
        return "-----------------------------------------------" +
                "\ndate=" + date +
                "\ntitle='" + title + '\'' +
                "\nlink='" + link + '\'' +
                "\ntopic='" + topic + '\'' +
                '}';
    }



    @Override
    public String[] getCSVHeaders(){
        return new String[]{"id","topic","date","title","link"};
    }

    @Override
    public Object getObjectFromField(String[] fields) {
        try {
            return new LeMondeArticle(
                    fields[0],
                    fields[1],
                    dateFormat.parse(fields[2]),
                    fields[3],
                    fields[4]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public String[] getFields(){
        return new String[]{
                id,
                topic,
                dateFormat.format(date),
                title,
                link};
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
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
}
