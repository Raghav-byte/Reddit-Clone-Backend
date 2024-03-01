package com.Post.FeignClient;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Data
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

    private Long postCount;
    private Long userCount;
    private List<UUID> postList; // posts on that sub
    private List<UUID> userIds; // users on that sub
    private List<String> subRules;
    private HashMap<String, String> externalLinks;


}
