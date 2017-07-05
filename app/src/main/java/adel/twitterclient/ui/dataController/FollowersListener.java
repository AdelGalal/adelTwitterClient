package adel.twitterclient.ui.dataController;

import adel.twitterclient.businessModel.DTO.FollowerResponse;

/**
 * Created by adelhegazy on 7/5/17.
 */

public interface FollowersListener {
    public void notifyFollowersData(FollowerResponse followerResponse);
}
