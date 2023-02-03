package com.example.RedditClone.Reddit.Controller;

import com.example.RedditClone.Reddit.Model.Subreddit;
import com.example.RedditClone.Reddit.Requests.SubredditFilterRequest;
import com.example.RedditClone.Reddit.Response.CommentResponse;
import com.example.RedditClone.Reddit.Response.UserResponse;
import com.example.RedditClone.Reddit.Response.VoteResponse;
import com.example.RedditClone.Reddit.Service.SubredditService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/subreddit")
public class SubredditController {
    
    @Autowired
    private SubredditService subService;

    @ApiOperation("Creating Subreddit")
    @PostMapping(path = "/")
    public Subreddit createSubreddit(@RequestBody Subreddit sub){
        return subService.createSubreddit(sub);
    }

    @ApiOperation("Get all subs")
    @GetMapping(path = "/")
    public List<Subreddit> getAllSubreddit(){
        return subService.getAllSubreddit();
    }

    @ApiOperation("Get sub by id")
    @GetMapping(path = "/{subId}")
    public Optional<Subreddit> getSubredditById(@PathVariable UUID subId){
        return subService.getSubredditById(subId);
    }

    @ApiOperation("Get Active/In-active subs")
    @GetMapping(path = "/subs/status")
    public List<Subreddit> getSubredditByStatus(@RequestParam boolean status){
        return subService.getSubredditByStatus(status);
    }

    @ApiOperation("Update Subreddit Info")
    @PutMapping(path = "/")
    public Subreddit updateSubreddit(@RequestBody Subreddit sub){
        return subService.updateSubreddit(sub);
    }

    @ApiOperation("Delete Subreddit")
    @DeleteMapping(path = "/")
    public String deleteSubreddit(@RequestParam UUID subId){
        return subService.deleteSubreddit(subId);
    }

    @ApiOperation("Change status of sub")
    @PutMapping(path = "/change/status")
    public String changeStatus(@RequestParam boolean status, @RequestParam UUID subId){
        return subService.changeStatus(status,subId);

    }

    @ApiOperation("Search Subreddit")
    @PutMapping(path = "/search")
    public List<Subreddit> searchSubreddit(@RequestParam String searchRequest){
        return subService.searchSubreddit(searchRequest);
    }

    @ApiOperation("Filter Subreddit")
    @PutMapping(path = "/filter")
    public List<Subreddit> filterSubreddit(@RequestBody SubredditFilterRequest filterRequest){
        return subService.filterSubreddit(filterRequest);
    }

    @ApiOperation("Find Subs of one User")
    @GetMapping(path = "/users")
    public UserResponse usersOnSub(@RequestParam UUID subId){
        return subService.usersOnSub(subId);
    }

    @ApiOperation("Find Votes on Sub")
    @GetMapping(path = "/votes")
    public VoteResponse votesOnSub(@RequestParam UUID subId){
        return subService.votesOnSub(subId);
    }

    @ApiOperation("Find Comment on Sub")
    @GetMapping(path = "/comment")
    public CommentResponse commentsOnSub(@RequestParam UUID subId){
        return subService.commentsOnSub(subId);
    }

    @ApiOperation("Add User in Sub")
    @GetMapping(path = "/add/user")
    public String addUserInSub(@RequestParam UUID subId , @RequestParam UUID userId){
        return subService.addUserInSub(subId,userId);
    }
}
