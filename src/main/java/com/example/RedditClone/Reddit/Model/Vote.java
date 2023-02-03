package com.example.RedditClone.Reddit.Model;

import org.apache.logging.log4j.core.util.UuidUtil;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Document
public class Vote {

    public enum VOTE_TYPE {
        UP_VOTE(1), DOWN_VOTE(-1);

        VOTE_TYPE(int i) {
        }
    }

    @Id
    private UUID voteId;
    private UUID postId;
    private Date createdTimeStamp;
    private VOTE_TYPE voteType;
    private UUID userId;

    public Vote(){
        this.voteId = UuidUtil.getTimeBasedUuid();
        this.createdTimeStamp = new Date();
    }

    public UUID getVoteId() {
        return voteId;
    }

    public void setVoteId(UUID voteId) {
        this.voteId = voteId;
    }

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    public Date getCreatedTimeStamp() {
        return createdTimeStamp;
    }

    public void setCreatedTimeStamp(Date createdTimeStamp) {
        this.createdTimeStamp = createdTimeStamp;
    }

    public VOTE_TYPE getVoteType() {
        return voteType;
    }

    public void setVoteType(VOTE_TYPE voteType) {
        this.voteType = voteType;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
