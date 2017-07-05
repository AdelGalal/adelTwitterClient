package adel.twitterclient.businessModel.DTO;

/**
 * Created by adelhegazy on 7/4/17.
 */

public class UserInfo {
    private Long userId;
    private String UserName;
    private String UserAuthToken;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserAuthToken() {
        return UserAuthToken;
    }

    public void setUserAuthToken(String userAuthToken) {
        UserAuthToken = userAuthToken;
    }
}
