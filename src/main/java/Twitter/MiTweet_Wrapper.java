package Twitter;
import java.sql.Timestamp;

import IO.CSVConvertible;

public class MiTweet_Wrapper implements CSVConvertible {
	private Tweet tweet;
	private String username;
	private Timestamp time_stamp;
	private String id_Parent;
	private String id_Article;

	/*================================================================================================*/
	//            CONSTRUCTORS
	/*================================================================================================*/

	public MiTweet_Wrapper() {
	}

	public MiTweet_Wrapper(String t,Timestamp ts,String id,String ida) {
		username = t;
		time_stamp = ts;
		id_Parent = id;
		id_Article = ida;
	}
	public MiTweet_Wrapper(Tweet twee,String ida) {
		tweet = twee;
		id_Article = ida;
	}


	/*================================================================================================*/
	//            FUNCTIONS & METHODS
	/*================================================================================================*/

	@Override
	public String toString() {
		return "Twitter.Tweet{" +
				"username='" + tweet.getUsername() + '\'' +
				", timeStamp=" + Long.toString(tweet.getTimeStamptoS())+
				", from=" + tweet.getOriginal_Tweet().getUsername() +
				", Id_Article=" + id_Article +             
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
		long ts = Long.parseLong(fields[1]);
		Timestamp ts_b = new Timestamp(ts*1000);

		return new MiTweet_Wrapper(fields[0],
				ts_b,
				fields[2],
				fields[3]);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Timestamp getTime_stamp() {
		return time_stamp;
	}

	public void setTime_stamp(Timestamp time_stamp) {
		this.time_stamp = time_stamp;
	}

	public String getId_Parent() {
		return id_Parent;
	}

	public void setId_Parent(String id_Parent) {
		this.id_Parent = id_Parent;
	}

	public String getId_Article() {
		return id_Article;
	}

	public void setId_Article(String id_Article) {
		this.id_Article = id_Article;
	}

	@Override
	public String[] getFields(){
		return new String[]{
				tweet.getUsername(),
				Long.toString(tweet.getTimeStamptoS()),
				tweet.getOriginal_Tweet().getUsername(),
				id_Article
		};
	}
}
