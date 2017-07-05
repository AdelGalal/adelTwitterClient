package adel.twitterclient.businessModel.constants;

public class ApplicationConstants {

    public  static final String TWITTER_CONSUMER_KEY="M1aitok6m1WuYhrGZUfENnpEX";
    public  static final String TWITTER_SECRET="TcItIehLo8YcCd6xgXFDag9qIq11zxyGiGYKERqaYGwOj6d2qo";
    public  static final String TWITTER_MAIN_URL = "https://api.twitter.com/";
    public static  final String TWITTER_FOLLOWERS="1.1/followers/list.json/";

    public static String getUserFollowers ()
    {
        return  TWITTER_MAIN_URL + TWITTER_FOLLOWERS ;
    }


}

