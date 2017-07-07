package adel.twitterclient.twitter;

import android.content.Context;

import com.google.gson.JsonElement;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterSession;

import adel.twitterclient.businessModel.constants.ApplicationConstants;
import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Response;


/**
 * Created by Adel on 4/07/2017.
 */

public class TwitterClientHelper {
    private static boolean isTwitterInitialized = false;

    public static void initializeTwitter(Context context) {
        TwitterAuthConfig authConfig = new TwitterAuthConfig(ApplicationConstants.TWITTER_CONSUMER_KEY, ApplicationConstants.TWITTER_SECRET);
        Fabric.with(context, new Twitter(authConfig));
        isTwitterInitialized = true;
    }

    public static boolean checkIfUserLoggedIn() {
        if (isTwitterInitialized) {
            TwitterSession session = Twitter.getSessionManager().getActiveSession();
            if (session != null)
                return true;
            return false;
        }
        return false;

    }


    public static Response<JsonElement> GetFollowers(long userId, int pageSize, String cursor) {
        TwitterSession currentSession = Twitter.getSessionManager().getActiveSession();
        TwitterClient apiClient = new TwitterClient(currentSession);
        TwitterUserFollowersData followersService = apiClient.getUserFollowersData();
        Call<JsonElement> call = followersService.list(userId, pageSize, cursor, false, false);
        try {
            return call.execute();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static Response<JsonElement> GetUserTimeline(long userId, int pageSize) {
        TwitterSession currentSession = Twitter.getSessionManager().getActiveSession();
        TwitterClient apiClient = new TwitterClient(currentSession);
        TwitterUserTimeData service = apiClient.getUserTimelineData();
        Call<JsonElement> call = service.list(userId, pageSize);
        try {
            return call.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static long GetCurrentUserId() {
        if (isTwitterInitialized) {
            TwitterSession currentSession = Twitter.getSessionManager().getActiveSession();
            if (currentSession != null)
                return currentSession.getUserId();
        }
        return -1;
    }
    
}
