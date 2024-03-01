package com.Post.Controller;

import com.Post.Model.Comment;
import com.Post.Service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Operation(summary = ("Adding Comment to Post"))
    @PostMapping(path = "/add")
    public String createComment(@RequestBody Comment comment) {
        return commentService.createComment(comment);
    }

    @Operation(summary = ("Removing to Post"))
    @DeleteMapping(path = "/remove")
    public String removeComment(@RequestBody Comment comment) {
        return commentService.removeComment(comment);
    }

    @Operation(summary = ("Editing Comment in Post"))
    @PatchMapping(path = "/edit")
    public String editComment(@RequestParam UUID commentId, @RequestParam String text) {
        return commentService.editComment(commentId, text);
    }

}
