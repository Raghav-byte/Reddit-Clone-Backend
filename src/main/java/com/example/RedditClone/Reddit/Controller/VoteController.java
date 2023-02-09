package com.example.RedditClone.Reddit.Controller;

import com.example.RedditClone.Reddit.Model.Vote;
import com.example.RedditClone.Reddit.Service.VoteService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/vote")
public class VoteController {

    @Autowired
    private VoteService voteService;

    @ApiOperation("Adding vote to a post")
    @PostMapping(path = "/")
    public Vote addVote(@RequestBody Vote vote) {
        return voteService.addVote(vote);
    }

    @ApiOperation("Find Vote by one User")
    @GetMapping(path = "/user")
    public List<Vote> voteByUser(@RequestParam UUID userId) {
        return voteService.voteByUser(userId);
    }

    @ApiOperation("Find Vote on one Post")
    @GetMapping(path = "/post")
    public List<Vote> voteByPost(@RequestParam UUID postId) {
        return voteService.voteByPost(postId);
    }
}
