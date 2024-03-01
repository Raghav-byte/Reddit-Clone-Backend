package com.User.User.FeignClient;

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

    public CommentFeignRequest(UUID commentId, UUID userId, String text) {
        this.commentId = commentId;
        this.userId = userId;
        this.text = text;
    }
}
