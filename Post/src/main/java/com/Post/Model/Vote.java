package com.Post.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Data
@Document
public class Vote {

    @Id
    private UUID voteId;
    private UUID postId;
    private UUID userId;
    private Date createdTimeStamp;
    private VOTE_TYPE voteType;

    public Vote() {
        this.voteId = UUID.randomUUID();
        this.createdTimeStamp = new Date();
    }

    public enum VOTE_TYPE {
        UP_VOTE(1), DOWN_VOTE(-1);

        VOTE_TYPE(int i) {
        }
    }
}
