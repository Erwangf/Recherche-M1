package Twitter;

import IO.CSVConvertible;
import twitter4j.Status;

import java.sql.Timestamp;

public class MiTweet implements CSVConvertible {
    private String user;
    private Timestamp timeStamp;
    private int nbRetweets;
    private MiTweet original_Tweet;
    private String Id_Article;


    /*================================================================================================*/
    //            CONSTRUCTORS
    /*================================================================================================*/

    public MiTweet() {
    }

    public MiTweet(String user, Timestamp timeStamp, int nbRetweets, MiTweet original_Tweet,String Id_Article) {
    	
        this.user = user;
        this.timeStamp = timeStamp;
        this.nbRetweets = nbRetweets;
        this.original_Tweet = original_Tweet;
        this.Id_Article=Id_Article;
    }

    /*================================================================================================*/
    //            FUNCTIONS & METHODS
    /*================================================================================================*/

    @Override
    public String toString() {
        return "Twitter.Tweet{" +
                "user='" + user + '\'' +
                ", timeStamp=" + timeStamp +
                ", nbRetweets=" + nbRetweets +
                ", Id_Article=" + Id_Article +             
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

	public Timestamp getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}

	public int getNbRetweets() {
		return nbRetweets;
	}

	public void setNbRetweets(int nbRetweets) {
		this.nbRetweets = nbRetweets;
	}

	public MiTweet getOriginal_Tweet() {
		return original_Tweet;
	}

	public void setOriginal_Tweet(MiTweet original_Tweet) {
		this.original_Tweet = original_Tweet;
	}

	public String getId_Article() {
		return Id_Article;
	}

	public void setId_Article(String id_Article) {
		Id_Article = id_Article;
	}

    @Override
    public String[] getCSVHeaders() {

        return new String[]{
        	    "user",
        	    "timeStamp",
        	   "idParent",
        	   "Id_Article"};
    }

	@Override
    public String[] getFields(){
        return new String[]{
                user,
                timeStamp.toString(),
                original_Tweet.user,
                Id_Article
        };
    }
}
