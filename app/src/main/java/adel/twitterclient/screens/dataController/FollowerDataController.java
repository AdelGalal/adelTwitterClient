package adel.twitterclient.screens.dataController;

import android.content.Context;
import com.google.gson.JsonElement;
import adel.twitterclient.businessModel.DTO.FollowerResponse;
import adel.twitterclient.businessModel.gson.Gson;
import adel.twitterclient.twitter.TwitterClientHelper;
import adel.twitterclient.screens.viewController.FollowersActivity;
import retrofit2.Response;

/**
 * Created by adelhegazy on 7/5/17.
 */

public class FollowerDataController {
    private Context context;
    private FollowerResponse followerResponse;
    private FollowersListener followersListener;
    public FollowerDataController(Context context,String cursor)
    {
        this.context=context;
    }

    public void setFollowersListener(FollowersListener followersListener) {
        this.followersListener = followersListener;
    }
    public void startConnectionToGetFollowers(String cursor) {
        final Response<JsonElement> response = TwitterClientHelper.GetFollowers(TwitterClientHelper.GetCurrentUserId(), FollowersActivity.NUM_OF_ITEMS, cursor);
        if (response!=null) {
            if (response.isSuccessful()) {
                followerResponse = Gson.parseFollowers(response.body());
                if (response.isSuccessful()) {
                    followersListener.notifyFollowersData(followerResponse);
                } else {
                    followersListener.notifyConnectionOrDataError();
                }

            }
        }
    }

}
