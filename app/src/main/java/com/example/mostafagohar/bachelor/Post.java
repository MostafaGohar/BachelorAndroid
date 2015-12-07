package com.example.mostafagohar.bachelor;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Gohar on 12/6/2015.
 */
public class Post implements Serializable{
    int id;
    int desttype;
    int destid;
    String title;
    String content;
    String created_at;
    User user;
    ArrayList<Comment> comments;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDesttype() {
        return desttype;
    }

    public void setDesttype(int desttype) {
        this.desttype = desttype;
    }

    public int getDestid() {
        return destid;
    }

    public void setDestid(int destid) {
        this.destid = destid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
