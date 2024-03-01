package com.Post.Model;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Data
@Document
public class Comment {

    @Id
    private UUID commentId;
    private UUID userId;
    private UUID postId;

    private String userName;
    @NotBlank
    private String text;
    private Date createdTimeStamp;

    public Comment() {
        this.commentId = UUID.randomUUID();
        this.createdTimeStamp = new Date();
    }

}
