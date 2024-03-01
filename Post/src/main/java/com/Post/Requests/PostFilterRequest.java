package com.Post.Requests;

import lombok.Data;

@Data
public class PostFilterRequest {

    private Boolean status;
    private long minComments;
    private long maxComments;
    private long minVotes;
    private long maxVotes;
    private long postVisitCount;
}
