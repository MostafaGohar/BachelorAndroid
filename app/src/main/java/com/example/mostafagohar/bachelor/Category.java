package com.example.mostafagohar.bachelor;

import java.util.ArrayList;

/**
 * Created by Gohar on 12/6/2015.
 */
public class Category {
    int id;
    String name;
    ArrayList<Post> posts;
    ArrayList<Topic> topics;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public ArrayList<Topic> getTopics() {
        return topics;
    }

    public void setTopics(ArrayList<Topic> topics) {
        this.topics = topics;
    }
}
