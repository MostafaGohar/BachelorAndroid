package com.example.mostafagohar.bachelor;

/**
 * Created by Gohar on 12/6/2015.
 */
public class Follow {
    int id;
    User follower;
    User followee;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }

    public User getFollowee() {
        return followee;
    }

    public void setFollowee(User followee) {
        this.followee = followee;
    }
}
