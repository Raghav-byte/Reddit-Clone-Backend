package com.User.User.FeignClient;

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

}
