package LeMonde;

import IO.CSVConvertible;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class LeMondeArticle implements CSVConvertible {
    private String id;
    private Date date;
    private String title;
    private String link;
    private String topic;

    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.FRENCH);


    public LeMondeArticle(String id, String title, Date date, String link,String topic) {

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
                "\nid='" + id + '\'' +
                "\ntopic='" + topic + "\'"+
                '}';
    }

    @Override
    public String[] getCSVHeaders(){
        return new String[]{"id","title","date","link","topic"};
    }

    @Override
    public Object getObjectFromField(String[] fields) {
        LeMondeArticle result = null;
        try {
            result =  new LeMondeArticle(
                    fields[0],
                    fields[1],
                    dateFormat.parse(fields[2]),
                    fields[3],
                    fields[4]
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;

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
}
