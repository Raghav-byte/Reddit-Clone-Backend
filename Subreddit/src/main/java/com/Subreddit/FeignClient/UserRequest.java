package com.Subreddit.FeignClient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRequest {

    private UUID subId;
    private UUID userId;
    private boolean isAdded;

}
