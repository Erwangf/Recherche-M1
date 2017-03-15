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
                "username='" + Tweet.getUsername() + '\'' +
                ", timeStamp=" + Long.toString(Tweet.getTimeStamptoS())+
                ", from=" + Tweet.getOriginal_Tweet().getUsername() +
                ", Id_Article=" + Id_Article +             
                '}';
    }


/*================================================================================================*/
    //            GETTERS & SETTERS
    /*================================================================================================*/



    @Override
    public String[] getCSVHeaders() {

        return new String[]{
        	    "username",
        	    "timeStamp",
        	   "idParent",
        	   "Id_Article"};
    }

    @Override
    public Object getObjectFromField(String[] fields) {
        return null;
    }

    @Override
    public String[] getFields(){
        return new String[]{
               Tweet.getUsername(),
                Long.toString(Tweet.getTimeStamptoS()),
                Tweet.getOriginal_Tweet().getUsername(),
                Id_Article
        };
    }
}
