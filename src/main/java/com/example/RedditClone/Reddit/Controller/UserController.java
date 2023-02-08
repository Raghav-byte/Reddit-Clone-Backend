package com.example.RedditClone.Reddit.Controller;

import com.example.RedditClone.Reddit.Model.User;
import com.example.RedditClone.Reddit.Requests.UserFilterRequest;
import com.example.RedditClone.Reddit.Service.UserService;
import io.swagger.annotations.ApiOperation;
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


    @ApiOperation("Creating User")
    @PostMapping(path = "/")
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @ApiOperation("Get all users")
    @GetMapping(path = "/")
    public List<User> getAllUser(){
        return userService.getAllUser();
    }

    @ApiOperation("Get user by id")
    @GetMapping(path = "/{userId}")
    public Optional<User> getUserById(@PathVariable UUID userId){
        return userService.getUserById(userId);
    }

    @ApiOperation("Get Active/In-active users")
    @GetMapping(path = "/users/status")
    public List<User> getUserByStatus(@RequestParam boolean status){
        return userService.getUserByStatus(status);
    }

    @ApiOperation("Update User Info")
    @PutMapping(path = "/")
    public User updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }

    @ApiOperation("Delete User")
    @DeleteMapping(path = "/")
    public String deleteUser(@RequestParam UUID userId){
        return userService.deleteUser(userId);
    }

    @ApiOperation("Change status of user")
    @PutMapping(path = "/change/status")
    public String changeStatus(@RequestParam boolean status, @RequestParam UUID userId){
        return userService.changeStatus(status,userId);
    }

    @ApiOperation("Search User")
    @PutMapping(path = "/search")
    public List<User> searchUser(@RequestParam String searchRequest){
        return userService.searchUser(searchRequest);
    }

    @ApiOperation("Filter User")
    @PutMapping(path = "/filter")
    public List<User> filterUser(@RequestBody UserFilterRequest filterRequest){
        return userService.filterUser(filterRequest);
    }


}
