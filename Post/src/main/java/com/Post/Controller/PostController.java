package com.Post.Controller;

import com.Post.Model.Post;
import com.Post.Requests.PostFilterRequest;
import com.Post.Service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @Operation(summary = "Creating Post")
    @PostMapping(path = "/")
    public Post createPost(@RequestBody Post post) throws Exception {
        return postService.createPost(post);
    }

    @Operation(summary = ("Get all posts"))
    @GetMapping(path = "/")
    public List<Post> getAllPost() {
        return postService.getAllPost();
    }

    @Operation(summary = ("Get post by id"))
    @GetMapping(path = "/{postId}")
    public Optional<Post> getPostById(@PathVariable UUID postId) {
        return postService.getPostById(postId);
    }

    @Operation(summary = ("Update Post Info"))
    @PutMapping(path = "/")
    public Post updatePost(@RequestBody Post post) {
        return postService.updatePost(post);
    }

    @Operation(summary = ("Delete Post"))
    @DeleteMapping(path = "/")
    public String deletePost(@RequestParam UUID postId) {
        return postService.deletePost(postId);
    }

    @Operation(summary = ("Change status of post"))
    @PutMapping(path = "/change/status")
    public String changeStatus(@RequestParam boolean status, @RequestParam UUID postId) {
        return postService.changeStatus(status, postId);
    }

    @Operation(summary = ("Search Post"))
    @PutMapping(path = "/search")
    public List<Post> searchPost(@RequestParam String searchRequest) {
        return postService.searchPost(searchRequest);
    }

    @Operation(summary = ("Filter Post"))
    @PutMapping(path = "/filter")
    public List<Post> filterPost(@RequestBody PostFilterRequest filterRequest) {
        return postService.filterPost(filterRequest);
    }

    @Operation(summary = ("Get post by Sub Id"))
    @GetMapping(path = "/sub")
    public List<Post> getPostBySubId(@RequestParam UUID subId) {
        return postService.getPostBySubId(subId);
    }

    @Operation(summary = ("Get post by User Id"))
    @GetMapping(path = "/user")
    public List<Post> getPostByUserId(@RequestParam UUID userId) {
        return postService.getPostByUserId(userId);
    }

}
