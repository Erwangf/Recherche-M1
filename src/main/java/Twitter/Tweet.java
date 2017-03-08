package Twitter;

import IO.CSVConvertible;
import twitter4j.Status;

import java.sql.Timestamp;

public class Tweet implements CSVConvertible {
    private String user;
    private String tweetId;
    private String username;
    private Timestamp timeStamp;
    private String content;
    private int nbResponses;
    private int nbRetweets;
    private int nbLikes;
    private Tweet original_Tweet;
    private String inReplyToTweetId;
    private Float note;


    /*================================================================================================*/
    //            CONSTRUCTORS
    /*================================================================================================*/

    public Tweet() {
    }

    public Tweet(Status status) {
        this.user = status.getUser().getScreenName();
        this.tweetId = Long.toString(status.getId());
        this.username = status.getUser().getName();
        this.timeStamp = new Timestamp(status.getCreatedAt().getTime());
        this.content = status.getText().replace("\"", "");
        this.nbResponses = 0;
        this.nbRetweets = status.getRetweetCount();
        this.nbLikes = status.getFavoriteCount();
        original_Tweet = new Tweet();
    }

    public Tweet(String user, String tweetId, String username, Timestamp timeStamp, String content, int nbResponses,
                 int nbRetweets, int nbLikes) {
        super();
        this.user = user;
        this.tweetId = tweetId;
        this.username = username;
        this.timeStamp = timeStamp;
        this.content = content.replace("\"", "");
        this.nbResponses = nbResponses;
        this.nbRetweets = nbRetweets;
        this.nbLikes = nbLikes;
        original_Tweet = new Tweet();

    }

    /*================================================================================================*/
    //            FUNCTIONS & METHODS
    /*================================================================================================*/

    @Override
    public String toString() {
        return "Twitter.Tweet{" +
                "user='" + user + '\'' +
                ", tweetId='" + tweetId + '\'' +
                ", username='" + username + '\'' +
                ", timeStamp=" + timeStamp +
                ", content='" + content + '\'' +
                ", nbResponses=" + nbResponses +
                ", nbRetweets=" + nbRetweets +
                ", nbLikes=" + nbLikes +
                ", originalTweetd='" + original_Tweet.getUser() + '\'' +
                ", inReplyToTweetId='" + inReplyToTweetId + '\'' +
                ", note=" + note +
                '}';
    }


/*================================================================================================*/
    //            GETTERS & SETTERS
    /*================================================================================================*/


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTweetId() {
        return tweetId;
    }

    public void setTweetId(String tweetId) {
        this.tweetId = tweetId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getNbResponses() {
        return nbResponses;
    }

    public void setNbResponses(int nbResponses) {
        this.nbResponses = nbResponses;
    }

    public int getNbRetweets() {
        return nbRetweets;
    }

    public void setNbRetweets(int nbRetweets) {
        this.nbRetweets = nbRetweets;
    }

    public int getNbLikes() {
        return nbLikes;
    }

    public void setNbLikes(int nbLikes) {
        this.nbLikes = nbLikes;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }
    public String getTimeStamptoS() {
        return timeStamp.toString();
    }
    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Tweet getOriginal_Tweet() {
        return original_Tweet;
    }

    public void setOriginalTweet(Tweet originalTweetd) {
        this.original_Tweet = originalTweetd;
    }

    public String getInReplyToTweetId() {
        return inReplyToTweetId;
    }

    public void setInReplyToTweetId(String inReplyToTweetId) {
        this.inReplyToTweetId = inReplyToTweetId;
    }

    public Float getNote() {
        return note;
    }

    public void setNote(Float note) {
        this.note = note;
    }

    @Override
    public String[] getCSVHeaders() {

        return new String[]{
                "user",
                "tweetId",
                "inReplyToTweetId",
                "username",
                "timestamp",
                "content",
                "nbResponses",
                "nbRetweets",
                "nbLikes"};
    }

    @Override
    public String[] getFields(){
        return new String[]{
                user,
                tweetId,
                inReplyToTweetId,
                username,
                timeStamp.toString(),
                content,
                Integer.toString(nbResponses),
                Integer.toString(nbRetweets),
                Integer.toString(nbLikes)
        };
    }


    public void defineFromCSVLine(String line, String separator) {
        // use comma as separator
        String[] fields = line.split("\"" + separator + "\"");
        for (int i = 0; i < fields.length; i++) {
            fields[i] = fields[i].replace("\"", "");
        }

        user = fields[0];
        inReplyToTweetId = fields[1]; //tweetID
        username = fields[2]; //username
        timeStamp = new Timestamp(Integer.parseInt(fields[3]));
        content = fields[4]; //content
        nbResponses = Integer.parseInt(fields[5]);
        nbRetweets = Integer.parseInt(fields[6]);
        nbLikes = Integer.parseInt(fields[7]);
        note = Float.parseFloat(fields[8]);
    }
}
