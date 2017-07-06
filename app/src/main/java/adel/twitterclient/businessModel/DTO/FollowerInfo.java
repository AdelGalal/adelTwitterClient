package adel.twitterclient.businessModel.DTO;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;


import java.util.ArrayList;

/**
 * Created by adelhegazy on 7/4/17.
 */

public class FollowerInfo implements Parcelable {
    @SerializedName("id_str")
    @DatabaseField(id = true)
    private String id;

    @SerializedName("name")
    @DatabaseField(canBeNull = false)
    private String name;

    @SerializedName("description")
    @DatabaseField
    private String bio;


    @SerializedName("screen_name")
    @DatabaseField
    private String screenName;

    @SerializedName("profile_image_url_https")
    @DatabaseField
    private String profileImageUrlHttps;

    @SerializedName("profile_background_color")
    @DatabaseField
    private String profileBackgroundColor;

    @SerializedName("profile_image_url")
    @DatabaseField
    private String profileImageUrl;

    @SerializedName("profile_banner_url")
    @DatabaseField
    private String bannerBackgroundUrl;



    protected FollowerInfo(Parcel in) {
        id = in.readString();
        bio = in.readString();
        name = in.readString();
        screenName = in.readString();
        profileImageUrlHttps=in.readString();
        profileBackgroundColor=in.readString();
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
        parcel.writeString(profileImageUrlHttps);
        parcel.writeString(profileBackgroundColor);
        parcel.writeString(profileImageUrl);
        parcel.writeString(bannerBackgroundUrl);
    }

    public String getProfileBackgroundColor() {
        return profileBackgroundColor;
    }

    public void setProfileBackgroundColor(String profileBackgroundColor) {
        this.profileBackgroundColor = profileBackgroundColor;
    }

    public String getProfileImageUrlHttps() {
        return profileImageUrlHttps;
    }

    public void setProfileImageUrlHttps(String profileImageUrlHttps) {
        this.profileImageUrlHttps = profileImageUrlHttps;
    }
}
