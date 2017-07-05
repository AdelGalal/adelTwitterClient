package adel.twitterclient.businessModel.DTO;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by adelhegazy on 7/4/17.
 */

public class FollowerResponse implements Parcelable {

    @SerializedName("users")
    private ArrayList<FollowerInfo> users;
    private String next_cursor;
    private String previous_cursor_str;
    private String previous_cursor;
    private String next_cursor_str;

    protected FollowerResponse(Parcel in) {
        users = in.createTypedArrayList(FollowerInfo.CREATOR);
        next_cursor = in.readString();
        previous_cursor_str = in.readString();
        previous_cursor = in.readString();
        next_cursor_str = in.readString();
    }

    public static final Creator<FollowerResponse> CREATOR = new Creator<FollowerResponse>() {
        @Override
        public FollowerResponse createFromParcel(Parcel in) {
            return new FollowerResponse(in);
        }

        @Override
        public FollowerResponse[] newArray(int size) {
            return new FollowerResponse[size];
        }
    };

    public ArrayList<FollowerInfo> getFollowerOfUsers() {
        return users;
    }

    public void setUsers(ArrayList<FollowerInfo> users) {
        this.users = users;
    }

    public String getNext_cursor() {
        return next_cursor;
    }

    public void setNext_cursor(String next_cursor) {
        this.next_cursor = next_cursor;
    }

    public String getPrevious_cursor_str() {
        return previous_cursor_str;
    }

    public void setPrevious_cursor_str(String previous_cursor_str) {
        this.previous_cursor_str = previous_cursor_str;
    }

    public String getPrevious_cursor() {
        return previous_cursor;
    }

    public void setPrevious_cursor(String previous_cursor) {
        this.previous_cursor = previous_cursor;
    }

    public String getNext_cursor_str() {
        return next_cursor_str;
    }

    public void setNext_cursor_str(String next_cursor_str) {
        this.next_cursor_str = next_cursor_str;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(users);
        parcel.writeString(next_cursor);
        parcel.writeString(previous_cursor_str);
        parcel.writeString(previous_cursor);
        parcel.writeString(next_cursor_str);
    }
}

