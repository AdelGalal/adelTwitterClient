package adel.twitterclient.application;

import android.app.Application;

import adel.twitterclient.twitter.TwitterClientHelper;

/**
 * Created by adelhegazy on 7/3/17.
 */

public class TwitterClientApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        TwitterClientHelper.initializeTwitter(this);

    }
}
