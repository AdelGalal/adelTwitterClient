package adel.twitterclient.screens.viewController.followerDetails;

import android.content.Context;

import com.google.gson.JsonElement;
import com.twitter.sdk.android.core.models.Tweet;

import adel.twitterclient.businessModel.DTO.FollowerInfo;
import adel.twitterclient.businessModel.gson.Gson;
import adel.twitterclient.twitter.TwitterClientHelper;

/**
 * Created by adelhegazy on 7/7/17.
 */

public class FollowerInformationDataController {
    private Context context;
    private FollowerInfoListener followerInfoListener;
    Tweet[] tweets;
    private final int NumberOfData = 10;
    private FollowerInfo followerInfo;
    public FollowerInformationDataController(Context context,FollowerInfo followerInfo) {
        this.context=context;
        this.followerInfo=followerInfo;
        tweets = new Tweet[0];
    }

    public void setFollowersInfoListener(FollowerInfoListener followerInformationDataController) {
        this.followerInfoListener = followerInformationDataController;
    }

    public void startConnectionToGetLastTenTweets()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                retrofit2.Response<JsonElement> res = TwitterClientHelper.GetUserTimeline(Long.parseLong(followerInfo.getId()), NumberOfData);
                if (res.isSuccessful())
                {
                    tweets = Gson.parseUserTimeLineResponse(res.body().toString());
                    followerInfoListener.notifyFollowersDetails(tweets);
                }
                else {
                    followerInfoListener.notifyErrorOfConnectionOrData();
                }
            }
        }).start();

    }
}
