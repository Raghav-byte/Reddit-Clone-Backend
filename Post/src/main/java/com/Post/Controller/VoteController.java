package com.Post.Controller;

import com.Post.Model.Vote;
import com.Post.Service.VoteService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vote")
public class VoteController {

    @Autowired
    private VoteService voteService;

    @Operation(summary = ("Up-Vote/Down-Vote any Post"))
    @PostMapping(path = "/")
    public String createVote(@RequestBody Vote vote) {
        return voteService.createVote(vote);
    }

}
