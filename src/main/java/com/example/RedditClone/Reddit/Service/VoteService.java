package com.example.RedditClone.Reddit.Service;

import com.example.RedditClone.Reddit.Model.Post;
import com.example.RedditClone.Reddit.Model.User;
import com.example.RedditClone.Reddit.Model.Vote;
import com.example.RedditClone.Reddit.Repository.PostRepo;
import com.example.RedditClone.Reddit.Repository.UserRepo;
import com.example.RedditClone.Reddit.Repository.VoteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoteService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private VoteRepo voteRepo;

    //ADDING VOTE
    public Vote addVote(Vote vote) {
        Optional<User> user = userRepo.findById(vote.getUserId());
        Optional<Post> post = postRepo.findById(vote.getPostId());
        if (user.isPresent() && post.isPresent()) {
            post.get().getVotes().add(vote);
            post.get().setTotalVote(post.get().getTotalVote() + 1);
            postRepo.save(post.get());
            voteRepo.save(vote);
        } else {
            throw new ResourceAccessException("User or Post Not Found");
        }
        return vote;
    }


    //LIST OF VOTES BY AN USER
    public List<Vote> voteByUser(UUID userId) {
        return voteRepo.voteByUser(userId);
    }

    //LIST OF VOTES ON A POST
    public List<Vote> voteByPost(UUID userId) {
        return voteRepo.voteByPost(userId);
    }
}
