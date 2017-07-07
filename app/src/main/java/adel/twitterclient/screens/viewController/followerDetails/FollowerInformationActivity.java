package adel.twitterclient.screens.viewController.followerDetails;

import android.support.v4.widget.NestedScrollView;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TweetView;

import adel.twitterclient.R;
import adel.twitterclient.businessModel.DTO.FollowerInfo;
import adel.twitterclient.businessModel.Network.NetwrokAvailability;
import adel.twitterclient.screens.viewController.ParentActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FollowerInformationActivity extends ParentActivity implements FollowerInfoListener{
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

    private FollowerInfo followerInfo;
    private FollowerInformationDataController followerInformationDataController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower_information);
        ButterKnife.bind(this);
        followerInfo = getIntent().getParcelableExtra("follower");
        setDataController();
        setFollowerData();
    }

    private void setDataController()
    {
        followerInformationDataController=new FollowerInformationDataController(this,followerInfo);
        followerInformationDataController.setFollowersInfoListener(this);
    }

    private void setFollowerData() {
        if (followerInfo == null) {
            Toast.makeText(this, R.string.error_msg, Toast.LENGTH_LONG).show();
            finish();
        } else {

            mToolbar.setTitleTextColor(getResources().getColor(R.color.tw__solid_white));
            if (followerInfo.getName() != null)
                mToolbar.setTitle(followerInfo.getName());

            if (followerInfo.getScreenName() != null)
                mToolbar.setSubtitle("@"+followerInfo.getScreenName());

            if (followerInfo.getBannerBackgroundUrl() != null && followerInfo.getBannerBackgroundUrl().isEmpty() == false) {
                Glide.with(this)
                        .load(followerInfo.getBannerBackgroundUrl())
                        .placeholder(R.color.colorPrimaryDark)
                        .centerCrop()
                        .into(backgroundImageView);
            }

            startConnectionToGetTweets();
        }
    }

    private void startConnectionToGetTweets() {
        if (NetwrokAvailability.isConnectedToInternet(this)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    followerInformationDataController.startConnectionToGetLastTenTweets();
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

    @Override
    public void notifyFollowersDetails(final Tweet[] tweets) {
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
    }

    @Override
    public void notifyErrorOfConnectionOrData() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(FollowerInformationActivity.this, getString(R.string.error_msg), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}



