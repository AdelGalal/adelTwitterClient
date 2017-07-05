package adel.twitterclient.businessModel.DTO;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by adelhegazy on 7/4/17.
 */

public class FollowerInfo implements Parcelable {
    @SerializedName("id_str")
    private String id;

    @SerializedName("description")
    private String bio;
    @SerializedName("name")
    private String name;


    @SerializedName("screen_name")
    private String screenName;

    @SerializedName("profile_image_url")
    private String profileImageUrl;

    @SerializedName("profile_banner_url")
    private String bannerBackgroundUrl;

    protected FollowerInfo(Parcel in) {
        id = in.readString();
        bio = in.readString();
        name = in.readString();
        screenName = in.readString();
        profileImageUrl = in.readString();
        bannerBackgroundUrl = in.readString();
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getBannerBackgroundUrl() {
        return bannerBackgroundUrl;
    }

    public void setBannerBackgroundUrl(String bannerBackgroundUrl) {
        this.bannerBackgroundUrl = bannerBackgroundUrl;
    }
    public static final Creator<FollowerInfo> CREATOR = new Creator<FollowerInfo>() {
        @Override
        public FollowerInfo createFromParcel(Parcel in) {
            return new FollowerInfo(in);
        }

        @Override
        public FollowerInfo[] newArray(int size) {
            return new FollowerInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(bio);
        parcel.writeString(name);
        parcel.writeString(screenName);
        parcel.writeString(profileImageUrl);
        parcel.writeString(bannerBackgroundUrl);
    }
}
