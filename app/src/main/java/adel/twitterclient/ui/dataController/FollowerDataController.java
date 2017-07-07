package adel.twitterclient.ui.dataController;

import android.content.Context;

import com.google.gson.JsonElement;
import com.j256.ormlite.dao.Dao;

import java.util.concurrent.Callable;

import adel.twitterclient.businessModel.DTO.FollowerInfo;
import adel.twitterclient.businessModel.DTO.FollowerResponse;
import adel.twitterclient.businessModel.gson.Gson;
import adel.twitterclient.database.DatabaseHelper;
import adel.twitterclient.twitter.TwitterClientHelper;
import adel.twitterclient.ui.viewController.FollowersActivity;
import retrofit2.Response;

/**
 * Created by adelhegazy on 7/5/17.
 */

public class FollowerDataController {
    private Context context;
    private String cursor;
    private FollowerResponse followerResponse;
    private FollowersListener followersListener;
    private DatabaseHelper databaseConfig;
    public FollowerDataController(Context context,String cursor)
    {
        this.context=context;
        this.cursor=cursor;
        databaseConfig = new DatabaseHelper(context);

    }

    public void setFollowersListener(FollowersListener followersListener) {
        this.followersListener = followersListener;
    }
    public void startConnectionToGetFollowers() {
        final Response<JsonElement> response = TwitterClientHelper.GetFollowers(TwitterClientHelper.GetCurrentUserId(), FollowersActivity.NUM_OF_ITEMS, cursor);
        if (response.isSuccessful()) {
            followerResponse = Gson.parseFollowers(response.body());
            followersListener.notifyFollowersData(followerResponse);
        }
    }

    public void accessAndUpdateDatabase(final FollowerResponse followerResponse) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final Dao<FollowerInfo, Integer> followersDao = databaseConfig.getFollowerInfoDao();
                    followersDao.callBatchTasks(new Callable<Void>() {
                        @Override
                        public Void call() throws Exception {
                            if (followerResponse != null && followerResponse.getFollowerOfUsers() != null) {
                                for (FollowerInfo f : followerResponse.getFollowerOfUsers())
                                    followersDao.createOrUpdate(f);
                            }
                            return null;
                        }
                    });


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();

    }
}
