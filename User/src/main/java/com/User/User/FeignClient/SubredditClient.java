package com.User.User.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "SUBREDDIT-SERVICE")
public interface SubredditClient {

    @RequestMapping(method = RequestMethod.PUT, value = "/subreddit/user/count")
    String updateUserCount(@RequestBody SubredditRequest request);

}
