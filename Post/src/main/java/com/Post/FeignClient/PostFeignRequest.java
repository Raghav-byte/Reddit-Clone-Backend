package com.Post.FeignClient;

import lombok.Data;

import java.util.UUID;

@Data
public class PostFeignRequest {

    private UUID userId;
    private UUID postId;
    private UUID subId;
    private String subName;
    private String postTitle;
    private String postDescription;
    private String imgUrl;

    private boolean added;

    public PostFeignRequest(UUID userId, UUID postId, UUID subId, String subName, String postTitle, String postDescription, String imgUrl, boolean added) {
        this.userId = userId;
        this.postId = postId;
        this.subId = subId;
        this.subName = subName;
        this.postTitle = postTitle;
        this.postDescription = postDescription;
        this.imgUrl = imgUrl;
        this.added = added;
    }
}
