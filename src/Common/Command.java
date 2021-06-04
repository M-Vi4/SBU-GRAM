package Common;

import java.io.Serializable;

public enum Command implements Serializable {
    LOGIN , SIGNUP , LOGOUT , USERNAME_UNIQUE ,
    ADD_COMMENT , LIKE , DISLIKE , REPOST , UPDATE_PROFILE ,
    GET_POSTS , FOLLOW , UNFOLLOW , PUBLISH_POST ,
    GET_INFO , VIEW_COMMENTS , GET_PASSWORD
}
