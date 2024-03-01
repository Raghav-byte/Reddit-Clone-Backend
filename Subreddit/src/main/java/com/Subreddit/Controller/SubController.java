package com.Subreddit.Controller;

import com.Subreddit.FeignClient.PostRequest;
import com.Subreddit.FeignClient.UserRequest;
import com.Subreddit.Model.Subreddit;
import com.Subreddit.Requests.SubFilterRequest;
import com.Subreddit.Service.SubService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/subreddit")
public class SubController {

    @Autowired
    private SubService subService;

    @Operation(summary = ("Creating Subreddit"))
    @PostMapping(path = "/")
    public Subreddit createSubreddit(@RequestBody Subreddit sub) throws Exception {
        return subService.createSubreddit(sub);
    }

    @Operation(summary = ("Get all subs"))
    @GetMapping(path = "/")
    public List<Subreddit> getAllSubreddit() {
        return subService.getAllSubreddit();
    }

    @Operation(summary = ("Get sub by id"))
    @GetMapping(path = "/{subId}")
    public Optional<Subreddit> getSubredditById(@PathVariable UUID subId) {
        return subService.getSubredditById(subId);
    }

    @Operation(summary = ("Get Active/In-active subs"))
    @GetMapping(path = "/subs/status")
    public List<Subreddit> getSubredditByStatus(@RequestParam boolean status) {
        return subService.getSubredditByStatus(status);
    }

    @Operation(summary = ("Update Subreddit Info"))
    @PutMapping(path = "/")
    public Subreddit updateSubreddit(@RequestBody Subreddit sub) {
        return subService.updateSubreddit(sub);
    }

    @Operation(summary = ("Delete Subreddit"))
    @DeleteMapping(path = "/")
    public String deleteSubreddit(@RequestParam UUID subId) {
        return subService.deleteSubreddit(subId);
    }

    @Operation(summary = ("Change status of sub"))
    @PutMapping(path = "/change/status")
    public String changeStatus(@RequestParam boolean status, @RequestParam UUID subId) {
        return subService.changeStatus(status, subId);
    }

    @Operation(summary = ("Search Subreddit"))
    @PutMapping(path = "/search")
    public List<Subreddit> searchSubreddit(@RequestParam String searchRequest) {
        return subService.searchSubreddit(searchRequest);
    }

    @Operation(summary = ("Filter Subreddit"))
    @PutMapping(path = "/filter")
    public List<Subreddit> filterSubreddit(@RequestBody SubFilterRequest filterRequest) {
        return subService.filterSubreddit(filterRequest);
    }

    @Operation(summary = ("Add or Remove Post Count"))
    @PutMapping(path = "/post/count")
    public String updatePostCount(@RequestBody PostRequest request) {
        return subService.updatePostCount(request);
    }

    @Operation(summary = ("Add or Remove User Count"))
    @PutMapping(path = "/user/count")
    public String updateUserCount(@RequestBody UserRequest request) {
        return subService.updateUserCount(request);
    }


}
