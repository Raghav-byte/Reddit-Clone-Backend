package com.Subreddit.Requests;

import lombok.Data;

@Data
public class SubFilterRequest {

    private Boolean status;
    private long minPosts;
    private long maxPosts;
    private long minUsers;
    private long maxUsers;
}
