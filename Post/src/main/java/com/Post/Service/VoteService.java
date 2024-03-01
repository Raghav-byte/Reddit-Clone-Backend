package com.Post.Service;

import com.Post.Model.Post;
import com.Post.Model.Vote;
import com.Post.Repository.PostRepo;
import com.Post.Repository.VoteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VoteService {

    @Autowired
    private VoteRepo voteRepo;
    @Autowired
    private PostRepo postRepo;

    public String createVote(Vote vote) {
        Optional<Post> post = postRepo.findById(vote.getPostId());
        if (post.isPresent()) {
            if (vote.getVoteType().equals(Vote.VOTE_TYPE.UP_VOTE)) {
                post.get().setTotalVote(post.get().getTotalVote() + 1);
            } else {
                post.get().setTotalVote(post.get().getTotalVote() - 1);
            }
            postRepo.save(post.get());
        }
        voteRepo.save(vote);
        return "Vote Added Successfully";
    }


}
