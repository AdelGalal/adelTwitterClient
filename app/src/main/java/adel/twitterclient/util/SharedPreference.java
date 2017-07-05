package adel.twitterclient.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class SharedPreference {
    private static final String SP_NAME = "TwitterClient";

    public static final String SP_USER_INFO = "userInfo";

    public static void save_SP_Data(Context mContext, String key,
                                    Object object) {

        SharedPreferences sharedPref = mContext
                .getSharedPreferences(SP_NAME,
                        Context.MODE_PRIVATE);

        SharedPreferences.Editor prefsEditor = sharedPref
                .edit();

        Gson gson = new Gson();
        String json = gson.toJson(object);

        prefsEditor.putString(key, json);
        prefsEditor.commit();
    }

    public static Object load_SP_Data(Context mContext, String key,
                                      Class<?> returnType) {

        SharedPreferences sharedPref = mContext.getSharedPreferences(SP_NAME,
                Context.MODE_PRIVATE);

        Gson gson = new Gson();

        String json = sharedPref.getString(key, null);

        if (json != null) {

            return gson.fromJson(json, returnType);
        }

        return null;
    }


    public static void removeAllKeySP(Context mContext)
    {
        SharedPreferences sharedPref = mContext.getSharedPreferences(SP_NAME,
                Context.MODE_PRIVATE);

        sharedPref.edit().clear().commit();
    }
}
