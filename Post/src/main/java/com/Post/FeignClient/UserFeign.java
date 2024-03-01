package com.Post.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "USER-SERVICE")
public interface UserFeign {

    @RequestMapping(method = RequestMethod.PUT, value = "/user/post/in/user")
    String postInUser(@RequestBody PostFeignRequest request);

    @RequestMapping(method = RequestMethod.PUT, value = "/user/post/in/user")
    String commentInUser(@RequestBody CommentFeignRequest request);

}
