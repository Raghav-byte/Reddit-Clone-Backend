package com.Post.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Document
public class Post {

    @Id
    private UUID postId;
    private UUID userId;
    private UUID subId;

    private String userName;
    private String subName;
    private String postTitle;
    private String postDescription;
    private Constansts.postFlair flair;
    private String imgUrl;
    private boolean isActive;

    private List<Comment> comments;
    private long totalVote;
    private long totalComment;
    private long postVisitCount;

    private Date createdTimeStamp;
    private Date updatedTimeStamp;


//    List<Vote> votesInPost() {
//        Query query = new Query().addCriteria(Criteria.where("postId").is(postId));
//        return mongoOperations.find(query, Vote.class);
//    }

//    List<Comment> CommentsInPost() {
//        Query query = new Query().addCriteria(Criteria.where("postId").is(postId));
//        return mongoOperations.find(query, Comment.class);
//    }

    public Post() {
        this.postId = UUID.randomUUID();
        this.createdTimeStamp = new Date();
        this.isActive = true;
//        this.votes = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

}
