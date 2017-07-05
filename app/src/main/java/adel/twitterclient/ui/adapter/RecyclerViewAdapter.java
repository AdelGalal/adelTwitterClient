package adel.twitterclient.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;

import adel.twitterclient.R;
import adel.twitterclient.businessModel.DTO.FollowerInfo;
import adel.twitterclient.ui.RecyclerViewClickListener;
import adel.twitterclient.ui.viewController.FollowerInformationActivity;
import adel.twitterclient.util.ActivitySwiping;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Authored by Ahmed Mabrook - ahmed.mabrook@chestnut.com
 * On Jan 2017 .
 * FollowersRecyclerViewAdapter: Describtion goes here.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<FollowerInfo> followersInfo;
    private LayoutInflater mInflater = null;
    ViewHolder viewHolder;
    Context mContext;
    private RecyclerViewClickListener onItemClickListener;

    public RecyclerViewAdapter(Context mContext, ArrayList<FollowerInfo> followersInfo) {

        this.followersInfo = followersInfo;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.follower_item, parent, false);
        viewHolder = new ViewHolder(view);

    return  viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FollowerInfo follower = followersInfo.get(position);
        if (follower.getProfileImageUrl() !=null){
            Glide.with(mContext)
                    .load(follower.getProfileImageUrl())
                    .asBitmap()
                    .centerCrop()
                    .placeholder(R.drawable.tw__composer_logo_blue)
                    .into( ((ViewHolder)holder).followerImageView);
        }

        if (follower.getName()!=null){
            ((ViewHolder)holder).followerNameTextView.setText(follower.getName());
        }

        if (follower.getScreenName()!=null){
            ((ViewHolder)holder).followerHandlerTextView.setText("@"+follower.getScreenName());
        }

        if (follower.getBio()!=null){
            ((ViewHolder)holder).followerBioTextView.setVisibility(View.VISIBLE);
            ((ViewHolder)holder).followerBioTextView.setText(follower.getBio());
        }

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick((FollowerInfo.NewsEntity)newsEntity);
            }
        };
    }

    @Override
    public int getItemCount() {
        return (null != followersInfo ? followersInfo.size() : 0);


    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.follower_ImageView)
        ImageView followerImageView;

        @BindView(R.id.followerNameTextView)
        TextView followerNameTextView;

        @BindView(R.id.followerHandlerTextView)
        TextView followerHandlerTextView;

        @BindView(R.id.followerBioTextView)
        TextView followerBioTextView;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(mContext, UserProfileActivity.class);
//                    intent.putExtra(FOLLOWER_EXTRA, mFollowers.get(getLayoutPosition()));
//                    mContext.startActivity(intent);

                    Bundle b=new Bundle();
                    b.putString("follower", followersInfo.get(getLayoutPosition()));
                    ActivitySwiping.goTOAnotherActivityWithBundle(mContext,FollowerInformationActivity.class,b);
                }
            });

        }

        private void emptyViewHolder() {

            followerNameTextView.setText("");
            followerHandlerTextView.setText("");
            followerBioTextView.setText("");
            followerImageView.setImageBitmap(null);
            followerBioTextView.setVisibility(View.GONE);

        }


    }


    public void add(ArrayList<? extends FollowerInfo> result) {
        for (int i = 0; i < result.size() - 1; i++) {
            followersInfo.add(((ArrayList<FollowerInfo>) result).get(i));
        }
        notifyDataSetChanged();
    }

}
