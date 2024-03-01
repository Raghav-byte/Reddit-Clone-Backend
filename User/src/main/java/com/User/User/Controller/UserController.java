package com.User.User.Controller;

import com.User.User.FeignClient.CommentFeignRequest;
import com.User.User.FeignClient.PostFeignRequest;
import com.User.User.FeignClient.SubredditRequest;
import com.User.User.Model.User;
import com.User.User.Model.UserFilterRequest;
import com.User.User.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @Operation(summary = "Creating User")
    @PostMapping(path = "/")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @Operation(summary = "Get all users")
    @GetMapping(path = "/")
    public List<User> getAllUser() {
        return userService.getAllUser();
    }

    @Operation(summary = "Get user by id")
    @GetMapping(path = "/{userId}")
    public Optional<User> getUserById(@PathVariable UUID userId) {
        return userService.getUserById(userId);
    }

    @Operation(summary = "Get Active/In-active users")
    @GetMapping(path = "/users/status")
    public List<User> getUserByStatus(@RequestParam boolean status) {
        return userService.getUserByStatus(status);
    }

    @Operation(summary = "Update User Info")
    @PutMapping(path = "/")
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @Operation(summary = "Delete User")
    @DeleteMapping(path = "/")
    public String deleteUser(@RequestParam UUID userId) {
        return userService.deleteUser(userId);
    }

    @Operation(summary = "Change status of user")
    @PutMapping(path = "/change/status")
    public String changeStatus(@RequestParam boolean status, @RequestParam UUID userId) {
        return userService.changeStatus(status, userId);
    }

    @Operation(summary = "Search User")
    @PutMapping(path = "/search")
    public List<User> searchUser(@RequestParam String searchRequest) {
        return userService.searchUser(searchRequest);
    }

    @Operation(summary = "Filter User")
    @PutMapping(path = "/filter")
    public List<User> filterUser(@RequestBody UserFilterRequest filterRequest) {
        return userService.filterUser(filterRequest);
    }

    @Operation(summary = "Add/remove User in Sub ")
    @PutMapping(path = "/user/in/sub")
    public String userInSub(@RequestBody SubredditRequest request) {
        return userService.addUserInSub(request);
    }

    @Operation(summary = "Add/remove post in user")
    @PutMapping(path = "/post/in/user")
    public String postInSub(@RequestBody PostFeignRequest request) {
        return userService.postInUser(request);
    }

    @Operation(summary = "Add/remove comment in user")
    @PutMapping(path = "/comment/in/user")
    public String commentInUser(@RequestBody CommentFeignRequest request) {
        return userService.commentInUser(request);
    }

}
