package com.Subreddit.FeignClient;

import lombok.Data;

import java.util.UUID;

@Data
public class PostRequest {

    private UUID subId;
    private UUID postId;
    private boolean added;

}
