package adel.twitterclient.screens.viewController;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import adel.twitterclient.R;
import adel.twitterclient.twitter.TwitterClientHelper;
import adel.twitterclient.util.ActivitySwiping;
import adel.twitterclient.util.SharedPreference;
import butterknife.BindView;
import butterknife.ButterKnife;

import static adel.twitterclient.application.TwitterClientApplication.LOCAL_LANG;

public class LoginActivity extends ParentActivity {

    @BindView(R.id.twitterLoginButton)
    TwitterLoginButton twitterLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkUserSession();

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        twitterLoginButton.setText(R.string.login_with_twitter);
        twitterLoginButton.setCallback(twitterLoginCallback);
        String localeLang = (String) SharedPreference.load_SP_Data(this, LOCAL_LANG,String.class);
        if(localeLang==null)
        {
            localeLang = "en";
            SharedPreference.save_SP_Data(this,LOCAL_LANG,localeLang);

        }
    }

    private void checkUserSession() {
        if (TwitterClientHelper.checkIfUserLoggedIn())
            navigateToMainPage();
    }

    private void navigateToMainPage() {

        ActivitySwiping.goTOAnotherActivityAndFinish(this,FollowersActivity.class);
    }

    Callback<TwitterSession> twitterLoginCallback = new Callback<TwitterSession>() {
        @Override
        public void success(Result<TwitterSession> result) {
            navigateToMainPage();
        }

        @Override
        public void failure(TwitterException exception) {
            Toast.makeText(LoginActivity.this, getString(R.string.login_failed_msg), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        twitterLoginButton.onActivityResult(requestCode, resultCode, data);
    }

    }

