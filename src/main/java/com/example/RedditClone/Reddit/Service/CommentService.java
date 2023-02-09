package com.example.RedditClone.Reddit.Service;

import com.example.RedditClone.Reddit.Model.Comment;
import com.example.RedditClone.Reddit.Model.Post;
import com.example.RedditClone.Reddit.Model.User;
import com.example.RedditClone.Reddit.Repository.PostRepo;
import com.example.RedditClone.Reddit.Repository.UserRepo;
import com.example.RedditClone.Reddit.Repository.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.Date;
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
    @Autowired
    private MongoTemplate mongoTemplate;

    //ADDING VOTE
    public Comment addComment(Comment comment) {
        Optional<User> user = userRepo.findById(comment.getUserId());
        Optional<Post> post = postRepo.findById(comment.getPostId());
        if (user.isPresent() && post.isPresent()) {
            post.get().getComments().add(comment);
            post.get().setTotalComment(post.get().getTotalComment() + 1);
            postRepo.save(post.get());
            commentRepo.save(comment);
        } else {
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

    //DELETE COMMENT
    public String deleteComment(UUID commentId) {
        commentRepo.deleteById(commentId);
        return "Comment Deleted";
    }

    //VIEW COMMENT BY ID
    public Optional<Comment> getCommentById(UUID commentId) {
        return commentRepo.findById(commentId);
    }

    //UPDATE COMMENT
    public Comment updateComment(Comment comment) {
        Query query = new Query();
        query.addCriteria(Criteria.where("commentId").is(comment.getCommentId()));

        Update update = new Update();
        if (!comment.getText().isEmpty()) {
            update.set("text", comment.getText());
        }
        update.set("updatedTimeStamp", new Date());

        return mongoTemplate.findAndModify(query, update, Comment.class);
    }
}
