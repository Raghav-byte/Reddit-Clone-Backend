package com.example.RedditClone.Reddit.Model;

import org.apache.logging.log4j.core.util.UuidUtil;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Document
public class Subreddit {

    @Id
    private UUID subId;
    private String subTitle;
    private String subDescription;
    private Date createdTimeStamp;
    private Date updatedTimeStamp;
    private boolean isActive;
    private String profilePic;
    private List<Post> posts ;
    private List<User> users;
    private long totalUsers;
    private long totalPosts;

    public Subreddit(){
        this.subId = UuidUtil.getTimeBasedUuid();
        this.createdTimeStamp = new Date();
        this.isActive = true;
        this.posts = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public UUID getSubId() {
        return subId;
    }

    public void setSubId(UUID subId) {
        this.subId = subId;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getSubDescription() {
        return subDescription;
    }

    public void setSubDescription(String subDescription) {
        this.subDescription = subDescription;
    }

    public Date getCreatedTimeStamp() {
        return createdTimeStamp;
    }

    public void setCreatedTimeStamp(Date createdTimeStamp) {
        this.createdTimeStamp = createdTimeStamp;
    }

    public Date getUpdatedTimeStamp() {
        return updatedTimeStamp;
    }

    public void setUpdatedTimeStamp(Date updatedTimeStamp) {
        this.updatedTimeStamp = updatedTimeStamp;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<User> getUsers() {
        return users;
    }

    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public long getTotalPosts() {
        return totalPosts;
    }

    public void setTotalPosts(long totalPosts) {
        this.totalPosts = totalPosts;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
