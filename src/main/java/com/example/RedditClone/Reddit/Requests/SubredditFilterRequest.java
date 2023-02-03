package com.example.RedditClone.Reddit.Requests;

public class SubredditFilterRequest {

    private Boolean isActive;
    private long post;
    private long users;

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public long getPost() {
        return post;
    }

    public void setPost(long post) {
        this.post = post;
    }

    public long getUsers() {
        return users;
    }

    public void setUsers(long users) {
        this.users = users;
    }
}
