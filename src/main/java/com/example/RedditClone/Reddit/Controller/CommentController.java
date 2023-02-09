package com.example.RedditClone.Reddit.Controller;

import com.example.RedditClone.Reddit.Model.Comment;
import com.example.RedditClone.Reddit.Service.CommentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ApiOperation("Adding comment to a post")
    @PostMapping(path = "/")
    public Comment addComment(@RequestBody Comment comment) {
        return commentService.addComment(comment);
    }

    @ApiOperation("Find Comment by one User")
    @GetMapping(path = "/user")
    public List<Comment> commentByUser(@RequestParam UUID userId) {
        return commentService.commentByUser(userId);
    }

    @ApiOperation("Find Comment on one Post")
    @GetMapping(path = "/post")
    public List<Comment> commentByPost(@RequestParam UUID postId) {
        return commentService.commentByPost(postId);
    }

    @ApiOperation("Update Comment Info")
    @PutMapping(path = "/")
    public Comment updateComment(@RequestBody Comment comment) {
        return commentService.updateComment(comment);
    }

    @ApiOperation("Delete Comment")
    @DeleteMapping(path = "/")
    public String deleteComment(@RequestParam UUID commentId) {
        return commentService.deleteComment(commentId);
    }

    @ApiOperation("Get comment by id")
    @GetMapping(path = "/{commentId}")
    public Optional<Comment> getCommentById(@PathVariable UUID commentId) {
        return commentService.getCommentById(commentId);
    }
}
