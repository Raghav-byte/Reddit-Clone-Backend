package com.User.User.Model;

import lombok.Data;

import java.util.UUID;

@Data
public class Subreddit {

    private UUID subId;
    private String subTitle;
    private String subDescription;
    private String subImage;
    private String subCoverImage;
    private boolean isActive;


    public Subreddit(UUID subId, String subTitle, String subDescription, String subImage, String subCoverImage, boolean isActive) {
        this.subId = subId;
        this.subTitle = subTitle;
        this.subDescription = subDescription;
        this.subImage = subImage;
        this.subCoverImage = subCoverImage;
        this.isActive = isActive;
    }
}
