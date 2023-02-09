package com.example.RedditClone.Reddit.Service;

import com.example.RedditClone.Reddit.Model.Post;
import com.example.RedditClone.Reddit.Model.Post;
import com.example.RedditClone.Reddit.Model.Subreddit;
import com.example.RedditClone.Reddit.Model.User;
import com.example.RedditClone.Reddit.Repository.PostRepo;
import com.example.RedditClone.Reddit.Repository.SubredditRepo;
import com.example.RedditClone.Reddit.Repository.UserRepo;
import com.example.RedditClone.Reddit.Repository.VoteRepo;
import com.example.RedditClone.Reddit.Requests.PostFilterRequest;
import com.example.RedditClone.Reddit.Response.CommentResponse;
import com.example.RedditClone.Reddit.Response.UserResponse;
import com.example.RedditClone.Reddit.Response.VoteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.ResourceAccessException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private VoteRepo voteRepo;
    @Autowired
    private SubredditRepo subrepo;
    @Autowired
    private MongoTemplate mongoTemplate;

    //CREATE POST
    public Post createPost(Post post) {
        Optional<Subreddit> subreddit = subrepo.findById(post.getSubId());
        if (subreddit.isPresent()) {
            subreddit.get().getPosts().add(post);
            subreddit.get().setTotalPosts(subreddit.get().getTotalPosts() + 1);
            subrepo.save(subreddit.get());
        }
        return postRepo.save(post);
    }

    //LIST OF POST
    public List<Post> getAllPost() {
        return postRepo.findAll(Sort.by("name").ascending());
    }

    //GET POST BY ID
    public Optional<Post> getPostById(UUID postId) {
        return postRepo.findById(postId);
    }

    //GET POST BY STATUS (ACTIVE/INACTIVE)
    public List<Post> getPostByStatus(boolean status) {
        return postRepo.findByStatus(status);
    }

    //UPDATE POST
    public Post updatePost(Post post) {
        Query query = new Query();
        query.addCriteria(Criteria.where("postId").is(post.getPostId()));

        Update update = new Update();
        if (!post.getPostTitle().isEmpty()) {
            update.set("postTitle", post.getPostTitle());
        }
        if (!post.getPostDescription().isEmpty()) {
            update.set("postDescription", post.getPostDescription());
        }
        update.set("updatedTimeStamp", new Date());

        return mongoTemplate.findAndModify(query, update, Post.class);
    }

    //DELETE POST
    public String deletePost(UUID postId) {
        postRepo.deleteById(postId);
        return "Post Deleted Successfully";
    }

    //CHANGING STATUS OF POST
    public String changeStatus(boolean status, UUID postId) {
        Optional<Post> post = postRepo.findById(postId);
        if (post.isPresent()) {
            post.get().setActive(status);
        } else {
            throw new ResourceAccessException("Post Not Found");
        }
        return "Status changed Successfully";
    }

    //SEARCH POST TITLE
    public List<Post> searchPost(String searchRequest) {
        return postRepo.searchPost(searchRequest);
    }

    //FILTER POST
    public List<Post> filterPost(PostFilterRequest filterRequest) {
        Query query = new Query();
        List<Criteria> criteriaList = new ArrayList<>();

        if (filterRequest.getActive() != null) {
            criteriaList.add(Criteria.where("isActive").is(filterRequest.getActive()));
        }
        if (filterRequest.getComments() > 0) {
            criteriaList.add(Criteria.where("totalComment").gte(filterRequest.getComments()));
        }
        if (filterRequest.getVotes() > 0) {
            criteriaList.add(Criteria.where("totalVote").gte(filterRequest.getVotes()));
        }

        if (!CollectionUtils.isEmpty(criteriaList)) {
            query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[criteriaList.size()])));
        }

        return mongoTemplate.find(query, Post.class);
    }

    public VoteResponse votesOnSub(UUID postId) {
        Optional<Post> post = postRepo.findById(postId);
        if (post.isPresent()) {

            VoteResponse voteResponse = new VoteResponse();
            voteResponse.setTotalVotes(post.get().getVotes().size());
            voteResponse.setVoteList(post.get().getVotes());
            return voteResponse;
        } else {
            throw new ResourceAccessException("Post not found");
        }
    }

    public CommentResponse commentsOnSub(UUID postId) {
        Optional<Post> post = postRepo.findById(postId);
        if (post.isPresent()) {

            CommentResponse commentResponse = new CommentResponse();
            commentResponse.setTotalComments(post.get().getComments().size());
            commentResponse.setCommentList(post.get().getComments());
            return commentResponse;
        } else {
            throw new ResourceAccessException("Post not found");
        }
    }

}
