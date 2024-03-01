package com.Post.Service;

//import com.Post.FeignClient.SubredditServiceInvoker;

import com.Post.FeignClient.PostFeignRequest;
import com.Post.FeignClient.SubredditFeign;
import com.Post.FeignClient.SubredditFeignRequest;
import com.Post.FeignClient.UserFeign;
import com.Post.Model.Post;
import com.Post.Repository.PostRepo;
import com.Post.Requests.PostFilterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private SubredditFeign subredditFeign;
    @Autowired
    private UserFeign userFeign;

    @Autowired
    private MongoTemplate mongoTemplate;

    //CREATE SUB
    public Post createPost(Post post) throws Exception {
        SubredditFeignRequest request = new SubredditFeignRequest(post.getSubId(), post.getPostId(), true);
        // TO UPDATE THE POST COUNT IN SUBREDDIT
        subredditFeign.updatePostCount(request);
        // TO UPDATE THE POST COUNT
        PostFeignRequest postFeignRequest = new PostFeignRequest(post.getUserId(), post.getPostId(), post.getSubId(), post.getSubName(), post.getPostTitle(), post.getPostDescription(), post.getImgUrl(), true);
        userFeign.postInUser(postFeignRequest);

        return postRepo.save(post);
    }

    //LIST OF SUB
    public List<Post> getAllPost() {
        return postRepo.findAll(Sort.by("createdTimeStamp").descending().and(Sort.by("isActive").descending()));
    }

    //GET SUB BY ID
    public Optional<Post> getPostById(UUID postId) {
        return postRepo.findById(postId);
    }

    //UPDATE SUB
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
        if (!post.getImgUrl().isEmpty()) {
            update.set("imgUrl", post.getImgUrl());
        }
        if (post.getFlair() != null) {
            update.set("flair", post.getFlair());
        }
        update.set("updatedTimeStamp", LocalDate.now());

        return mongoTemplate.findAndModify(query, update, Post.class);
    }

    //DELETE SUB
    public String deletePost(UUID postId) {
        Optional<Post> post = postRepo.findById(postId);
        // TO UPDATE THE POST COUNT IN SUBREDDIT
        SubredditFeignRequest request = new SubredditFeignRequest(post.get().getSubId(), postId, false);
        subredditFeign.updatePostCount(request);

        // TO UPDATE THE POST COUNT
        PostFeignRequest postFeignRequest = new PostFeignRequest(post.get().getUserId(), post.get().getPostId(), post.get().getSubId(), post.get().getSubName(), post.get().getPostTitle(), post.get().getPostDescription(), post.get().getImgUrl(), false);
        userFeign.postInUser(postFeignRequest);

        postRepo.deleteById(postId);
        return "Post Deleted Successfully";
    }

    //CHANGING STATUS OF SUB
    public String changeStatus(boolean status, UUID postId) {
        Optional<Post> post = postRepo.findById(postId);
        if (post.isPresent()) {
            post.get().setActive(status);
            postRepo.save(post.get());
        } else {
            throw new ResourceAccessException("Post Not Found");
        }
        return "Status changed Successfully";
    }

    //SEARCH SUB TITLE
    public List<Post> searchPost(String searchRequest) {
        return postRepo.searchPost(searchRequest);
    }

    //FILTER SUB
    public List<Post> filterPost(PostFilterRequest filterRequest) {
        Query query = new Query();
        List<Criteria> criteriaList = new ArrayList<>();

        if (filterRequest.getStatus() != null) {
            criteriaList.add(Criteria.where("isActive").is(filterRequest.getStatus()));
        }
        if (filterRequest.getMinVotes() > 0 && filterRequest.getMaxVotes() > 0) {
            criteriaList.add(Criteria.where("totalVote").gte(filterRequest.getMinVotes()).lte(filterRequest.getMaxVotes()));
        }
        if (filterRequest.getMinComments() > 0 && filterRequest.getMaxComments() > 0) {
            criteriaList.add(Criteria.where("totalComment").gte(filterRequest.getMinComments()).lt(filterRequest.getMaxComments()));
        }
        if (filterRequest.getPostVisitCount() > 0) {
            criteriaList.add(Criteria.where("postVisitCount").gte(filterRequest.getPostVisitCount()));
        }
        if (!CollectionUtils.isEmpty(criteriaList)) {
            query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[criteriaList.size()])));
        }
        return mongoTemplate.find(query, Post.class);
    }


    /**
     * @param subId
     * @return Posts In that Sub
     */
    public List<Post> getPostBySubId(UUID subId) {
        while (subId != null) {
            List<Post> postList = mongoTemplate.find(new Query().addCriteria(Criteria.where("subId").is(subId)), Post.class);
            return postList;
        }
        return null;
    }

    /**
     * @param userId
     * @return Posts by that User
     */
    public List<Post> getPostByUserId(UUID userId) {
        while (userId != null) {
            List<Post> postList = mongoTemplate.find(new Query().addCriteria(Criteria.where("userId").is(userId)), Post.class);
            return postList;
        }
        return null;
    }
}
