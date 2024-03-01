package com.Subreddit.Model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.*;

@Data
@Document
public class Subreddit {

    @Id
    private UUID subId;
    @NotBlank
    private String subTitle;
    @NotBlank
    private String subDescription;
    private String subImage;
    private String subCoverImage;
    private LocalDate createdDate;
    private LocalDate updatedDate;
    private boolean isActive;

    private long postCount;
    private long userCount;
    private Set<UUID> postList; // posts on that sub
    private Set<UUID> userIds; // users on that sub
    private List<String> subRules;
    private HashMap<String, String> externalLinks;

    private Subreddit() {
        this.createdDate = LocalDate.now();
        this.subId = UUID.randomUUID();
        this.isActive = true;
        this.userIds = new HashSet<>();
        this.postList = new HashSet<>();
    }

}
