package com.Post.FeignClient;

import lombok.Data;

import java.util.UUID;

@Data
public class SubredditFeignRequest {

    private UUID subId;
    private UUID postId;
    private boolean added;

    public SubredditFeignRequest(UUID subId, UUID postId, boolean added) {
        this.subId = subId;
        this.postId = postId;
        this.added = added;
    }
}
