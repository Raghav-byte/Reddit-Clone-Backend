package com.Post.Service;

import com.Post.FeignClient.CommentFeignRequest;
import com.Post.FeignClient.UserFeign;
import com.Post.Model.Comment;
import com.Post.Model.Post;
import com.Post.Repository.CommentRepo;
import com.Post.Repository.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CommentService {

    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private UserFeign userFeign;

    public String createComment(Comment comment) {
        if (comment.getPostId() != null) {
            // TO UPDATE COMMENT COUNT IN USER SERVICE
            CommentFeignRequest request = new CommentFeignRequest(comment.getCommentId(), comment.getUserId(), comment.getText(), true);
            userFeign.commentInUser(request);

            Optional<Post> post = postRepo.findById(comment.getPostId());
            if (post.isPresent()) {
                post.get().getComments().add(comment);
                post.get().setTotalComment(post.get().getTotalComment() + 1);
                postRepo.save(post.get());
            }

            commentRepo.save(comment);
            return "Comment Added Successfully";
        }
        return null;
    }


    public String removeComment(Comment comment) {
        Optional<Comment> existingComment = commentRepo.findById(comment.getCommentId());
        if (existingComment.isPresent()) {

            // TO UPDATE COMMENT COUNT IN USER SERVICE
            CommentFeignRequest request = new CommentFeignRequest(comment.getCommentId(), comment.getUserId(), comment.getText(), true);
            userFeign.commentInUser(request);

            Optional<Post> post = postRepo.findById(comment.getPostId());
            if (post.isPresent()) {
                post.get().getComments().remove(comment);
                post.get().setTotalComment(post.get().getTotalComment() - 1);
                postRepo.save(post.get());
            }

            commentRepo.deleteById(comment.getCommentId());
            return "Comment Deleted Successfully";
        }
        return null;
    }


    public String editComment(UUID commentId, String text) {
        Optional<Comment> existingComment = commentRepo.findById(commentId);
        if (existingComment.isPresent()) {
            existingComment.get().setText(text);
            commentRepo.save(existingComment.get());
            return "Comment Updated Successfully";
        }
        return null;
    }
}
