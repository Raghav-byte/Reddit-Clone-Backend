package com.example.RedditClone.Reddit.Controller;

import com.example.RedditClone.Reddit.Model.Post;
import com.example.RedditClone.Reddit.Requests.PostFilterRequest;
import com.example.RedditClone.Reddit.Response.CommentResponse;
import com.example.RedditClone.Reddit.Response.VoteResponse;
import com.example.RedditClone.Reddit.Service.PostService;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation("Creating Post")
    @PostMapping(path = "/")
    public Post createPost(@RequestBody Post post){
        return postService.createPost(post);
    }

    @ApiOperation("Get all posts")
    @GetMapping(path = "/")
    public List<Post> getAllPost(){
        return postService.getAllPost();
    }

    @ApiOperation("Get post by id")
    @GetMapping(path = "/{postId}")
    public Optional<Post> getPostById(@PathVariable UUID postId){
        return postService.getPostById(postId);
    }

    @ApiOperation("Get Active/In-active posts")
    @GetMapping(path = "/posts/status")
    public List<Post> getPostByStatus(@RequestParam boolean status){
        return postService.getPostByStatus(status);
    }

    @ApiOperation("Update Post Info")
    @PutMapping(path = "/")
    public Post updatePost(@RequestBody Post post){
        return postService.updatePost(post);
    }

    @ApiOperation("Delete Post")
    @DeleteMapping(path = "/")
    public String deletePost(@RequestParam UUID postId){
        return postService.deletePost(postId);
    }

    @ApiOperation("Change status of post")
    @PutMapping(path = "/change/status")
    public String changeStatus(@RequestParam boolean status, @RequestParam UUID postId){
        return postService.changeStatus(status,postId);
    }

    @ApiOperation("Search Post")
    @PutMapping(path = "/search")
    public List<Post> searchPost(@RequestParam String searchRequest){
        return postService.searchPost(searchRequest);
    }

    @ApiOperation("Filter Post")
    @PutMapping(path = "/filter")
    public List<Post> filterPost(@RequestBody PostFilterRequest filterRequest){
        return postService.filterPost(filterRequest);
    }

    @ApiOperation("Find Votes on Sub")
    @GetMapping(path = "/votes")
    public VoteResponse votesOnPost(@RequestParam UUID postId){
        return postService.votesOnSub(postId);
    }

    @ApiOperation("Find Comment on Sub")
    @GetMapping(path = "/comment")
    public CommentResponse commentsOnPost(@RequestParam UUID postId){
        return postService.commentsOnSub(postId);
    }
    
}
