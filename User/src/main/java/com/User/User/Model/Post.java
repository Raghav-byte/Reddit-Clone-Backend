package com.User.User.Model;

import lombok.Data;

import java.util.UUID;

@Data
public class Post {

    private UUID postId;
    private UUID subId;
    private String subName;
    private String postTitle;
    private String postDescription;
    private String imgUrl;


    public Post(UUID postId, UUID subId, String subName, String postTitle, String postDescription, String imgUrl) {
        this.postId = postId;
        this.subId = subId;
        this.subName = subName;
        this.postTitle = postTitle;
        this.postDescription = postDescription;
        this.imgUrl = imgUrl;
    }
}
