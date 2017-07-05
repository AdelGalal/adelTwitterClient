package adel.twitterclient.businessModel.gson;

import com.google.gson.JsonElement;
import com.twitter.sdk.android.core.models.Tweet;

import adel.twitterclient.businessModel.DTO.FollowerResponse;

/**
 * Created by adelhegazy on 7/5/17.
 */

public class Gson {
    public static FollowerResponse parseFollowers(JsonElement json) {
        FollowerResponse response;
        com.google.gson.Gson gson = new com.google.gson.Gson();
        try {
            response = gson.fromJson(json, FollowerResponse.class);
        } catch (Exception ex) {
            return null;
        }
        return response;
    }

    public static Tweet[] parseUserTimeLineResponse(String json) {
        try {
            com.google.gson.Gson gson
                    = new com.google.gson.Gson();
            Tweet[] tweets = gson.fromJson(json, Tweet[].class);
            if (tweets != null)
                return tweets;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new Tweet[0];
    }
}
