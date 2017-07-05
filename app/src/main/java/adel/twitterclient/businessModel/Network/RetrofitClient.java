package adel.twitterclient.businessModel.Network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import java.io.IOException;
import java.net.URLEncoder;

import adel.twitterclient.businessModel.constants.ApplicationConstants;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class RetrofitClient {


    private static Retrofit retrofit = null;

    private static OkHttpClient defaultHttpClient ;


    public static Retrofit getClient(final Context context) {

        if (retrofit == null) {
            Long tsLong = System.currentTimeMillis()/1000;
            final String ts = tsLong.toString();
            defaultHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(
                            new Interceptor() {
                                @Override
                                public Response intercept(Chain chain) throws IOException {
                                    Request request = chain.request().newBuilder()
                                           //.addHeader("Content-Type", "application/x-www-form-urlencoded")

                                            .build();
                                    return chain.proceed(request);
                                }
                            }).build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(ApplicationConstants.TWITTER_MAIN_URL)
                    .addConverterFactory(GsonConverterFactory.create()).client(defaultHttpClient)
                    .build();
        }
        return retrofit;
    }


}