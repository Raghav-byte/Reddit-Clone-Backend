package com.example.RedditClone.Reddit.Service;

import com.example.RedditClone.Reddit.Model.Post;
import com.example.RedditClone.Reddit.Model.Subreddit;
import com.example.RedditClone.Reddit.Model.User;
import com.example.RedditClone.Reddit.Repository.SubredditRepo;
import com.example.RedditClone.Reddit.Repository.UserRepo;
import com.example.RedditClone.Reddit.Requests.SubredditFilterRequest;
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
public class SubredditService {

    @Autowired
    private SubredditRepo subRepo;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private MiscellaneousService miscellaneousService;

    //CREATE SUB
    public Subreddit createSubreddit(Subreddit sub) {
        return subRepo.save(sub);
    }

    //LIST OF SUB
    public List<Subreddit> getAllSubreddit() {
        return subRepo.findAll(Sort.by("name").ascending());
    }

    //GET SUB BY ID
    public Optional<Subreddit> getSubredditById(UUID subId) {
        return subRepo.findById(subId);
    }

    //GET SUB BY STATUS (ACTIVE/INACTIVE)
    public List<Subreddit> getSubredditByStatus(boolean status) {
        return subRepo.findByStatus(status);
    }

    //UPDATE SUB
    public Subreddit updateSubreddit(Subreddit sub) {
        Query query = new Query();
        query.addCriteria(Criteria.where("subId").is(sub.getSubId()));

        Update update = new Update();
        if (!sub.getSubTitle().isEmpty()) {
            update.set("subTitle", sub.getSubTitle());
        }
        if (!sub.getSubDescription().isEmpty()) {
            update.set("subDescription", sub.getSubDescription());
        }
        if (!sub.getProfilePic().isEmpty()) {
            update.set("profilePic", sub.getProfilePic());
        }
        update.set("updatedTimeStamp", new Date());

        return mongoTemplate.findAndModify(query, update, Subreddit.class);
    }

    //DELETE SUB
    public String deleteSubreddit(UUID subId) {
        subRepo.deleteById(subId);
        return "Subreddit Deleted Successfully";
    }

    //CHANGING STATUS OF SUB
    public String changeStatus(boolean status, UUID subId) {
        Optional<Subreddit> sub = subRepo.findById(subId);
        if (sub.isPresent()) {
            sub.get().setActive(status);
        } else {
            throw new ResourceAccessException("Subreddit Not Found");
        }
        return "Status changed Successfully";
    }

    //SEARCH SUB TITLE
    public List<Subreddit> searchSubreddit(String searchRequest) {
        return subRepo.searchSubreddit(searchRequest);
    }

    //FILTER SUB
    public List<Subreddit> filterSubreddit(SubredditFilterRequest filterRequest) {
        Query query = new Query();
        List<Criteria> criteriaList = new ArrayList<>();

        if (filterRequest.getActive() != null) {
            criteriaList.add(Criteria.where("isActive").is(filterRequest.getActive()));
        }
        //DO FOR SIZE OF USERS AND POSTS
        if (filterRequest.getPost() > 0) {
            criteriaList.add(Criteria.where("posts").gte(filterRequest.getPost()));
        }
        if (filterRequest.getUsers() > 0) {
            criteriaList.add(Criteria.where("users").gte(filterRequest.getUsers()));
        }

        if (!CollectionUtils.isEmpty(criteriaList)) {
            query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[criteriaList.size()])));
        }

        return mongoTemplate.find(query, Subreddit.class);
    }

    public UserResponse usersOnSub(UUID subId) {
        Optional<Subreddit> subreddit = subRepo.findById(subId);
        if (subreddit.isPresent()) {
            UserResponse userResponse = new UserResponse();
            userResponse.setTotalUsers(subreddit.get().getUsers().size());
            userResponse.setActiveUser(subreddit.get().getUsers().stream().filter(f -> f.isActive()).collect(Collectors.toList()).size());
            userResponse.setInActiveUsers(subreddit.get().getUsers().stream().filter(f -> !f.isActive()).collect(Collectors.toList()).size());
            userResponse.setUserList(subreddit.get().getUsers());
            return userResponse;
        } else {
            throw new ResourceAccessException("Subreddit not found");
        }
    }

    public VoteResponse votesOnSub(UUID subId) {
        Optional<Subreddit> subreddit = subRepo.findById(subId);
        if (subreddit.isPresent()) {

            VoteResponse voteResponse = new VoteResponse();
            for (Post post : subreddit.get().getPosts()) {
                voteResponse.setTotalVotes(voteResponse.getTotalVotes() + post.getVotes().size());
                voteResponse.getVoteList().addAll(post.getVotes());
                return voteResponse;
            }
        } else {
            throw new ResourceAccessException("Subreddit not found");
        }
        return null;
    }

    public CommentResponse commentsOnSub(UUID subId) {
        Optional<Subreddit> subreddit = subRepo.findById(subId);
        if (subreddit.isPresent()) {

            CommentResponse commentResponse = new CommentResponse();
            for (Post post : subreddit.get().getPosts()) {
                commentResponse.getCommentList().addAll(post.getComments());
                commentResponse.setTotalComments(commentResponse.getTotalComments() + post.getComments().size());
                return commentResponse;
            }
        } else {
            throw new ResourceAccessException("Subreddit not found");
        }
        return null;
    }

    //ADD USER IN SUB
    public String addUserInSub(UUID subId, UUID userId) {
        Optional<Subreddit> subreddit = subRepo.findById(subId);
        Optional<User> user = userRepo.findById(userId);

        if (subreddit.isPresent() && user.isPresent()) {
            if (subreddit.get().getUsers() == null) {
                subreddit.get().setUsers(new ArrayList<>());
            }
            subreddit.get().getUsers().add(user.get());
            subreddit.get().setTotalUsers(subreddit.get().getTotalUsers() + 1);
            subRepo.save(subreddit.get());
        } else {
            throw new ResourceAccessException("User or Post not found");
        }
        return miscellaneousService.findUserName(userId) + "Added in the subreddit";
    }
}
