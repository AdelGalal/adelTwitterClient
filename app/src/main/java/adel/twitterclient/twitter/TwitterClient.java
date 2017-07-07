package adel.twitterclient.twitter;

import com.google.gson.JsonElement;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterSession;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Adel on 4/07/2017.
 */


public class TwitterClient extends TwitterApiClient {

    public TwitterClient(TwitterSession session) {
        super(session);
    }


    public TwitterUserFollowersData getUserFollowersData() {
        return getService(TwitterUserFollowersData.class);
    }

    public TwitterUserTimeData getUserTimelineData() {
        return getService(TwitterUserTimeData.class);
    }
}


interface TwitterUserFollowersData {
    @GET("/1.1/followers/list.json")
    Call<JsonElement> list(@Query("user_id") long id, @Query("count") int pageSize, @Query("cursor") String cursor, @Query("include_user_entities") boolean includeUserEntities, @Query("skip_status") boolean skipStatuses);

}

interface TwitterUserTimeData {
    @GET("/1.1/statuses/user_timeline.json")
    Call<JsonElement> list(@Query("user_id") long id, @Query("count") int pageSize);
}

