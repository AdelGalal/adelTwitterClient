package adel.twitterclient.screens.viewController.followerDetails;

import com.twitter.sdk.android.core.models.Tweet;

/**
 * Created by adelhegazy on 7/7/17.
 */

public interface FollowerInfoListener {
    public void notifyFollowersDetails(Tweet[] tweets);
    public void notifyErrorOfConnectionOrData();
}
