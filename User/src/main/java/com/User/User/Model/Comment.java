package com.User.User.Model;

import lombok.Data;

import java.util.UUID;

@Data
public class Comment {

    private UUID commentId;
    private String text;

    public Comment(UUID commentId, String text) {
        this.commentId = commentId;
        this.text = text;
    }
}
