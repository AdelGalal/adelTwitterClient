package adel.twitterclient.ui.viewController;

import android.app.Activity;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.JsonElement;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TweetView;

import adel.twitterclient.R;
import adel.twitterclient.application.TwitterClientApplication;
import adel.twitterclient.businessModel.DTO.FollowerInfo;
import adel.twitterclient.businessModel.Network.NetwrokConfig;
import adel.twitterclient.businessModel.gson.Gson;
import adel.twitterclient.twitter.TwitterClientHelper;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FollowerInformationActivity extends Activity {
    @BindView(R.id.NestedScrollView)
    NestedScrollView mScrollView;

    @BindView(R.id.tweetsLinearLayout)
    LinearLayout tweetsLinearLayout;

    @BindView(R.id.followerBannerImageView)
    ImageView backgroundImageView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.changeLanguage)
    Button changeLanguageButton;

    FollowerInfo mFollower;
    Tweet[] tweets;

    final int PAGESIZE = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower_information);
        ButterKnife.bind(this);
        tweets = new Tweet[0];

        mFollower = getIntent().getParcelableExtra("follower");

        loadFollowerData();
        prepareViewsActions();
    }
    private void prepareViewsActions()
    {
        changeLanguageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TwitterClientApplication.forceChangeLanguage(FollowerInformationActivity.this);
            }
        });
    }
    private void loadFollowerData() {
        if (mFollower == null) {
            Toast.makeText(this, R.string.error_msg, Toast.LENGTH_LONG).show();
            finish();
        } else {

            mToolbar.setTitleTextColor(getResources().getColor(R.color.tw__solid_white));
            if (mFollower.getName() != null)
                mToolbar.setTitle(mFollower.getName());

            if (mFollower.getScreenName() != null)
                mToolbar.setSubtitle("@"+mFollower.getScreenName());

            if (mFollower.getBannerBackgroundUrl() != null && mFollower.getBannerBackgroundUrl().isEmpty() == false) {
                Glide.with(this)
                        .load(mFollower.getBannerBackgroundUrl())
                        .placeholder(R.color.colorPrimaryDark)
                        .centerCrop()
                        .into(backgroundImageView);
            }


            getLastTenTweets();
        }
    }
    private void getLastTenTweets() {
        if (NetwrokConfig.isConnectedToInternet(this)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    retrofit2.Response<JsonElement> res = TwitterClientHelper.GetUserTimeline(Long.parseLong(mFollower.getId()), PAGESIZE);
                    if (res.isSuccessful()) {
                        tweets = Gson.parseUserTimeLineResponse(res.body().toString());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < tweets.length; i++) {

                                    tweetsLinearLayout.addView(new TweetView(FollowerInformationActivity.this, tweets[i]));
                                }

                                if (tweets.length == 0) {
                                    Toast.makeText(FollowerInformationActivity.this, R.string.no_tweets, Toast.LENGTH_SHORT).show();
                                }

                                progressBar.setVisibility(View.GONE);
                            }
                        });


                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(FollowerInformationActivity.this, getString(R.string.error_msg), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        });


                    }
                }
            }).start();


        } else {
            showNoConnectionToast();
        }
    }
        private void showNoConnectionToast() {

            Toast.makeText(FollowerInformationActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();

            progressBar.setVisibility(View.GONE);

        }
    }



