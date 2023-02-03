package com.example.RedditClone.Reddit.Model;

import org.apache.logging.log4j.core.util.UuidUtil;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Document
public class Post {

    @Id
    private UUID postId;
    private String postTitle;
    private String postDescription;
    private Date createdTimeStamp;
    private Date updatedTimeStamp;
    private boolean isActive;
    private String imgUrl;
    private UUID userId;
    private UUID subId;
    private List<Vote> votes;
    private List<Comment> comments;
    private long totalVote;
    private long totalComment;

    public Post() {
        this.postId = UuidUtil.getTimeBasedUuid();
        this.createdTimeStamp = new Date();
        this.isActive = true;
        this.votes = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public long getTotalVote() {
        return totalVote;
    }

    public void setTotalVote(long totalVote) {
        this.totalVote = totalVote;
    }

    public long getTotalComment() {
        return totalComment;
    }

    public void setTotalComment(long totalComment) {
        this.totalComment = totalComment;
    }

    public UUID getSubId() {
        return subId;
    }

    public void setSubId(UUID subId) {
        this.subId = subId;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
