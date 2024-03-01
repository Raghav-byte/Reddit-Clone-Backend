package com.User.User.FeignClient;

import lombok.Data;

import java.util.UUID;

@Data
public class SubredditRequest {

    private UUID subId;
    private UUID userId;
    private boolean isAdded;

    private String subTitle;
    private String subDescription;
    private String subImage;
    private String subCoverImage;
    private boolean isActive;

    public SubredditRequest(UUID subId, UUID userId, boolean isAdded) {
        this.subId = subId;
        this.userId = userId;
        this.isAdded = isAdded;
    }
}
