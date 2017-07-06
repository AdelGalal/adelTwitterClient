package adel.twitterclient.ui.viewController;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.Callable;

import adel.twitterclient.R;
import adel.twitterclient.application.TwitterClientApplication;
import adel.twitterclient.database.DatabaseConfig;
import adel.twitterclient.twitter.TwitterClientHelper;
import adel.twitterclient.businessModel.DTO.FollowerInfo;
import adel.twitterclient.businessModel.DTO.FollowerResponse;
import adel.twitterclient.businessModel.Network.NetwrokConfig;
import adel.twitterclient.businessModel.gson.Gson;
import adel.twitterclient.ui.adapter.RecyclerViewAdapter;
import adel.twitterclient.ui.dataController.FollowerDataController;
import adel.twitterclient.ui.dataController.FollowersListener;
import adel.twitterclient.util.SharedPreference;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

import static adel.twitterclient.application.TwitterClientApplication.LOCAL_LANG;

public class FollowersActivity extends Activity implements FollowersListener {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout followerSwipeRefreshLayout;

    @BindView(R.id.noFollowersTextView)
    TextView noFollowersTextView;

    @BindView(R.id.changeLanguage)
    Button changeLanguageButton;
     @BindView(R.id.loader)
     ProgressBar progressBar;

    private DatabaseConfig databaseConfig;
    LinearLayoutManager linearLayoutManager;
    ArrayList<FollowerInfo> mFollowers;
    RecyclerViewAdapter mAdapter;
    public static int NUM_OF_ITEMS = 30;
    private String cursor = "-1";
    private int mFirstVisibleItem;
    private int mVisibleItemCount;
    private int mTotalItemCount;
    private int mLastFirstVisibleItem;
    public boolean isLoading = false;
    private static boolean isTheEnd = false;
    FollowerResponse followerResponse;
   // private FollowerDataController followerDataController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        databaseConfig = new DatabaseConfig(this);

        ButterKnife.bind(this);
        prepareViewsAndData();
        startConnectionToGetFollowers();
    }
    private void prepareViewsAndData()
    {
        databaseConfig = new DatabaseConfig(FollowersActivity.this);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        followerSwipeRefreshLayout.setOnRefreshListener(swipRefreshListener);
        changeLanguageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TwitterClientApplication.forceChangeLanguage(FollowersActivity.this);
            }
        });
 //       followerDataController=new FollowerDataController(this,cursor);
//        followerDataController.setFollowersListener(this);
    }
    private SwipeRefreshLayout.OnRefreshListener swipRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            cursor = "-1";
            if (NetwrokConfig.isConnectedToInternet(FollowersActivity.this)) {

                startConnectionToGetFollowers();

            } else {

                showErrorToast();

            }
        }
    };

    private void startConnectionToGetFollowers()
    {
        showProgressbar();
        if (NetwrokConfig.isConnectedToInternet(this)) {
            isLoading = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final Response<JsonElement> response = TwitterClientHelper.GetFollowers(TwitterClientHelper.GetCurrentUserId(), NUM_OF_ITEMS, cursor);
                    //followerDataController.startConnectionToGetFollowers();
                    if (response.isSuccessful())
                    {
                        followerResponse = Gson.parseFollowers(response.body());
                        accessAndUpdateDatabase(followerResponse);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                isLoading = false;
                                mFollowers = followerResponse.getFollowerOfUsers();
                                mAdapter = new RecyclerViewAdapter(FollowersActivity.this, mFollowers);
                                recyclerView.setAdapter(mAdapter);
                                recyclerView.setHasFixedSize(true);
                                hideProgressbar();

                                recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                                    @Override
                                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                        super.onScrolled(recyclerView, dx, dy);
                                        mVisibleItemCount = recyclerView.getChildCount();
                                        mTotalItemCount = linearLayoutManager.getItemCount();
                                        mFirstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();

                                        if (isTheEnd) {
                                            return;
                                        }

                                        int lastInScreen = mFirstVisibleItem + mVisibleItemCount;

                                        if ((lastInScreen == mTotalItemCount) && !(isLoading) && (mTotalItemCount >= 30) && !isTheEnd) {
                                            cursor = followerResponse.getNext_cursor();
                                            loadMoreFollowers();

                                        }

                                    }
                                });

                            }
                        });
                    } else {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showErrorToast();
                                hideProgressbar();

                            }
                        });
                    }
                }
            }).start();
        }
        else {
            loadOffline();
            showErrorToast();
            hideProgressbar();

        }
    }

    private void loadMoreFollowers() {

        if (NetwrokConfig.isConnectedToInternet(this)) {
            isLoading = true;
           showProgressbar();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    final retrofit2.Response<JsonElement> res = TwitterClientHelper.GetFollowers(TwitterClientHelper.GetCurrentUserId(), NUM_OF_ITEMS, cursor);
                    if (res !=null && res.isSuccessful()) {
                        followerResponse = Gson.parseFollowers(res.body());
                        accessAndUpdateDatabase(followerResponse);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                mFollowers.addAll(followerResponse.getFollowerOfUsers());
                                mAdapter.add(followerResponse.getFollowerOfUsers());
                               hideProgressbar();
                                isLoading = false;

                            }
                        });
                    } else {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showErrorToast();
                                hideProgressbar();
                                isLoading = false;

                            }
                        });
                    }
                }
            }).start();
        } else {
            showErrorToast();
            isLoading = false;
        }

    }
    private void hideProgressbar() {
      progressBar.setVisibility(View.GONE);
        if (followerSwipeRefreshLayout.isRefreshing())
            followerSwipeRefreshLayout.setRefreshing(false);
    }
    private void showProgressbar()
    {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void showErrorToast(){
        Toast.makeText(FollowersActivity.this, getString(R.string.error_msg), Toast.LENGTH_SHORT).show();
        hideProgressbar();
    }

    private void accessAndUpdateDatabase(final FollowerResponse followerResponse) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.e("accessAndUpdateDatabase--","accessAndUpdateDatabase--");
                    final Dao<FollowerInfo, Integer> followersDao = databaseConfig.getFollowerInfoDao();
                    followersDao.callBatchTasks(new Callable<Void>() {
                        @Override
                        public Void call() throws Exception {
                            if (followerResponse != null && followerResponse.getFollowerOfUsers() != null) {
                                for (FollowerInfo f : followerResponse.getFollowerOfUsers())
                                    followersDao.createOrUpdate(f);
                                Log.e("followerResponse--","followerResponse--");
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
    private void loadOffline() {
        Log.e("loadOffline","loadOffline");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final Dao<FollowerInfo, Integer> followersDao = databaseConfig.getFollowerInfoDao();
                    Log.e("loadOffline","loadOffline"+databaseConfig.getFollowerInfoDao());
                    int totalNumber = (int) followersDao.countOf();
                    QueryBuilder<FollowerInfo, Integer> builder = followersDao.queryBuilder();

                    if (followerResponse == null){
                        followerResponse = new FollowerResponse();
                        builder.offset((long) followerResponse.getFollowerOfUsers().size()).limit((long) NUM_OF_ITEMS);

                    }else {
                        builder.offset((long) followerResponse.getFollowerOfUsers().size()).limit((long) NUM_OF_ITEMS);
                    }

                    followerResponse.setUsers((ArrayList<FollowerInfo>) followersDao.query(builder.prepare()));

                    if ((followerResponse.getFollowerOfUsers().size() + NUM_OF_ITEMS) >= totalNumber)
                    {
                        followerResponse.setNext_cursor("0");
                    }

                    else
                    {
                        followerResponse.setNext_cursor("-1");
                    }

                    if (mAdapter == null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mFollowers = followerResponse.getFollowerOfUsers();

                                mAdapter = new RecyclerViewAdapter(FollowersActivity.this, followerResponse.getFollowerOfUsers());
                                recyclerView.setAdapter(mAdapter);
                                recyclerView.setHasFixedSize(true);
                                hideProgressbar();

                                recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                                    @Override
                                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                        super.onScrolled(recyclerView, dx, dy);
                                        mVisibleItemCount = recyclerView.getChildCount();
                                        mTotalItemCount = linearLayoutManager.getItemCount();
                                        mFirstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();

                                        if (isTheEnd) {
                                            return;
                                        }

                                        int lastInScreen = mFirstVisibleItem + mVisibleItemCount;

                                        if ((lastInScreen == mTotalItemCount) && !(isLoading) && (mTotalItemCount >= 30) && !isTheEnd) {
                                            cursor = followerResponse.getNext_cursor();
                                            isLoading = true;
                                            loadOffline();

                                        }

                                    }
                                });
                            }
                        });

                    } else {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mFollowers.addAll(followerResponse.getFollowerOfUsers());
                                mAdapter.add(followerResponse.getFollowerOfUsers());
                                hideProgressbar();
                                isLoading = false;
                            }
                        });

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(FollowersActivity.this, getString(R.string.error_msg), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        }).start();


    }

    @Override
    public void notifyFollowersData(FollowerResponse followerResponse) {

        if (followerResponse!=null)
        {
            this.followerResponse=followerResponse;
            showProgressbar();
        }
    }
}
