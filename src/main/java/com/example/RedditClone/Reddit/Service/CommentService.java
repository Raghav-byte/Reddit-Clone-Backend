package com.example.RedditClone.Reddit.Service;

import com.example.RedditClone.Reddit.Model.Comment;
import com.example.RedditClone.Reddit.Model.Post;
import com.example.RedditClone.Reddit.Model.User;
import com.example.RedditClone.Reddit.Model.Comment;
import com.example.RedditClone.Reddit.Repository.PostRepo;
import com.example.RedditClone.Reddit.Repository.UserRepo;
import com.example.RedditClone.Reddit.Repository.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommentService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private CommentRepo commentRepo;

    //ADDING VOTE
    public Comment addComment(Comment comment) {
        Optional<User> user = userRepo.findById(comment.getUserId());
        Optional<Post> post = postRepo.findById(comment.getPostId());
        if (user.isPresent() && post.isPresent()){
            post.get().getComments().add(comment);
            post.get().setTotalComment(post.get().getTotalComment() + 1 );
            postRepo.save(post.get());
            commentRepo.save(comment);
        }else {
            throw new ResourceAccessException("User or Post Not Found");
        }
        return comment;
    }


    //LIST OF VOTES BY AN USER
    public List<Comment> commentByUser(UUID userId) {
        return commentRepo.commentByUser(userId);
    }

    //LIST OF VOTES ON A POST
    public List<Comment> commentByPost(UUID userId) {
        return commentRepo.commentByPost(userId);
    }
    
}
