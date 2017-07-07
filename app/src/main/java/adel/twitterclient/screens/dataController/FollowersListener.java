package adel.twitterclient.screens.dataController;

import adel.twitterclient.businessModel.DTO.FollowerResponse;

/**
 * Created by adelhegazy on 7/5/17.
 */

public interface FollowersListener {
    public void notifyFollowersData(FollowerResponse followerResponse);
    public void notifyConnectionOrDataError();
}
