package Twitter;
import IO.CSVConvertible;

public class MiTweet_Wrapper implements CSVConvertible {
    private Tweet Tweet;
    private String Id_Article;


    /*================================================================================================*/
    //            CONSTRUCTORS
    /*================================================================================================*/

    public MiTweet_Wrapper() {
    }

    public MiTweet_Wrapper(Tweet Tweet,String Id_Article) {
        this.Tweet = Tweet;
        this.Id_Article=Id_Article;  
    }

    /*================================================================================================*/
    //            FUNCTIONS & METHODS
    /*================================================================================================*/

    @Override
    public String toString() {
        return "Twitter.Tweet{" +
                "user='" + Tweet.getUser() + '\'' +
                ", timeStamp=" + Tweet.getTimeStamptoS() +
                ", from=" + Tweet.getOriginal_Tweet().getUser() +
                ", Id_Article=" + Id_Article +             
                '}';
    }


/*================================================================================================*/
    //            GETTERS & SETTERS
    /*================================================================================================*/



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
               Tweet.getUser(),
                Tweet.getTimeStamptoS(),
                Tweet.getOriginal_Tweet().getUser(),
                Id_Article
        };
    }
}
