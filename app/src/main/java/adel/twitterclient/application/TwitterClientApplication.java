package adel.twitterclient.application;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;


import java.util.Locale;
import adel.twitterclient.twitter.TwitterClientHelper;
import adel.twitterclient.screens.viewController.LoginActivity;
import adel.twitterclient.util.SharedPreference;

/**
 * Created by adelhegazy on 7/3/17.
 */

public class TwitterClientApplication extends Application{
    public  static String LOCAL_LANG;
    @Override
    public void onCreate() {
        super.onCreate();
        TwitterClientHelper.initializeTwitter(this);


    }
    @SuppressWarnings("deprecation")
    public  static void forceChangeLanguage(Activity activity)
    {
        String localeLang = (String) SharedPreference.load_SP_Data(activity, LOCAL_LANG,String.class);
        if(localeLang==null)
        {
            localeLang = "en";
        }
        else
        {
            if(localeLang.equalsIgnoreCase("en"))
            {
                localeLang = "ar";

            }
            else
            {
                localeLang = "en";
            }
        }
        SharedPreference.save_SP_Data(activity,LOCAL_LANG,localeLang);

        Resources resources = activity.getResources();
        Configuration overrideConfiguration = resources.getConfiguration();
        Locale overrideLocale = new Locale(localeLang);
        Locale.setDefault(overrideLocale);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            overrideConfiguration.setLocale(overrideLocale);
        } else {
            overrideConfiguration.locale = overrideLocale;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            activity.createConfigurationContext(overrideConfiguration);
        } else {
            resources.updateConfiguration(overrideConfiguration, null);
        }
        Intent startAgain = new Intent(activity, LoginActivity.class);
        startAgain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(startAgain);
    }

}
