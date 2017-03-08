package LeMonde;

import IO.CSVConvertible;

import java.util.Date;

/**
 * Created by Erwan on 06/03/2017.
 */
public class LeMondeArticle implements CSVConvertible {
    private Date date;
    private String title;
    private String link;
    private String topic;

    public LeMondeArticle(){

    }

    public LeMondeArticle(String title, Date date, String link, String topic) {
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
        return new String[]{"topic","title","date","link"};
    }

    @Override
    public String[] getFields(){
        return new String[]{topic,title,date.toString(),link};
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
