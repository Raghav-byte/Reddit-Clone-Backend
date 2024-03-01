package com.Post.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "SUBREDDIT-SERVICE")
public interface SubredditFeign {

    @RequestMapping(method = RequestMethod.PUT, value = "/subreddit/post/count")
    String updatePostCount(@RequestBody SubredditFeignRequest request);

}