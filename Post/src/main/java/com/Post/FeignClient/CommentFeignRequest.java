package com.Post.FeignClient;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class CommentFeignRequest {

    private UUID commentId;
    private UUID userId;
    @NotBlank
    private String text;

    private boolean added;

    public CommentFeignRequest(UUID commentId, UUID userId, String text, boolean added) {
        this.commentId = commentId;
        this.userId = userId;
        this.text = text;
        this.added = added;
    }
}
