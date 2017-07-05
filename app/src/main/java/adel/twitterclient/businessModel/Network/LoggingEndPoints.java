package adel.twitterclient.businessModel.Network;


import adel.twitterclient.businessModel.DTO.FollowerResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LoggingEndPoints {


    @GET("/1.1/followers/list.json")
    Call<FollowerResponse> show(@Query("user_id") Long userId, @Query("screen_name") String
            var, @Query("skip_status") Boolean var1, @Query("include_user_entities") Boolean
                                var2, @Query("count") Integer var3);


}
