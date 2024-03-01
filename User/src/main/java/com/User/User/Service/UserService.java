package com.User.User.Service;

import com.User.User.FeignClient.CommentFeignRequest;
import com.User.User.FeignClient.PostFeignRequest;
import com.User.User.FeignClient.SubredditClient;
import com.User.User.FeignClient.SubredditRequest;
import com.User.User.Model.*;
import com.User.User.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.ResourceAccessException;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private MongoOperations mongoOperations;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private SubredditClient subredditClient;

    //CREATE USER
    public User createUser(User user) {
        return userRepo.save(user);
    }

    //LIST OF USER
    public List<User> getAllUser() {
        return userRepo.findAll(Sort.by("name").ascending());
    }

    //GET USER BY ID
    public Optional<User> getUserById(UUID userId) {
        return userRepo.findById(userId);
    }

    //GET USER BY STATUS (ACTIVE/INACTIVE)
    public List<User> getUserByStatus(boolean status) {
        return userRepo.findByStatus(status);
    }

    //UPDATE USER
    public User updateUser(User user) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(user.getUserId()));

        Update update = new Update();
        if (!user.getName().isEmpty()) {
            update.set("name", user.getName());
        }
        if (user.getDateOfBirth() != null) {
            update.set("dateOfBirth", user.getDateOfBirth());
        }
        if (user.getAddress() != null) {
            update.set("address", user.getAddress());
        }
        if (user.getContactInformation() != null) {
            update.set("contactInformation", user.getContactInformation());
        }
        update.set("updatedTimeStamp", new Date());

        return mongoTemplate.findAndModify(query, update, User.class);
    }

    //DELETE USER
    public String deleteUser(UUID userId) {
        // REMOVE IN SUB
        userRepo.deleteById(userId);
        return "User Deleted Successfully";
    }

    //CHANGING STATUS OF USER
    public String changeStatus(boolean status, UUID userId) {
        Optional<User> user = userRepo.findById(userId);
        // REMOVE IN SUBREDDIT
        if (user.isPresent()) {
            user.get().setActive(status);
            user.get().setUpdatedTimeStamp(new Date());
            userRepo.save(user.get());
        } else {
            throw new ResourceAccessException("User Not Found");
        }
        return "Status changed Successfully";
    }

    //SEARCH USER BY NAME AND USERNAME
    public List<User> searchUser(String searchRequest) {
        return userRepo.searchUser(searchRequest);
    }

    //FILTER USER
    public List<User> filterUser(UserFilterRequest filterRequest) {
        Query query = new Query();
        List<Criteria> criteriaList = new ArrayList<>();

        if (filterRequest.getIsActive() != null) {
            criteriaList.add(Criteria.where("isActive").is(filterRequest.getIsActive()));
        }
        if (filterRequest.getStartDate() != null && filterRequest.getEndDate() != null) {
            criteriaList.add(Criteria.where("createdTimeStamp").gte(filterRequest.getStartDate()));
            criteriaList.add(Criteria.where("createdTimeStamp").lt(filterRequest.getEndDate()));
        }
        if (filterRequest.getDateOfBirth() != null) {
            criteriaList.add(Criteria.where("dateOfBirth").gte(filterRequest.getDateOfBirth()));
        }
        if (filterRequest.getCountry() != null && !filterRequest.getCountry().isEmpty()) {
            criteriaList.add(Criteria.where("country").is(filterRequest.getCountry()));
        }
        if (filterRequest.getCountry() != null && !filterRequest.getState().isEmpty()) {
            criteriaList.add(Criteria.where("state").is(filterRequest.getState()));
        }
        if (filterRequest.getCountry() != null && !filterRequest.getCity().isEmpty()) {
            criteriaList.add(Criteria.where("city").is(filterRequest.getCountry()));
        }
        if (filterRequest.getGender() != null) {
            criteriaList.add(Criteria.where("gender").is(filterRequest.getGender()));
        }

        if (!CollectionUtils.isEmpty(criteriaList)) {
            query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[criteriaList.size()])));
        }

        return mongoTemplate.find(query, User.class);
    }

    public String addUserInSub(SubredditRequest request) {
        Optional<User> user = userRepo.findById(request.getUserId());
        if (user.isPresent()) {
            Subreddit subreddit = new Subreddit(request.getSubId(), request.getSubTitle(), request.getSubDescription(), request.getSubImage(), request.getSubCoverImage(), request.isActive());
            if (request.isAdded()) {
                user.get().getSubList().add(subreddit);
                user.get().setSubCount(user.get().getSubCount() + 1);
            } else {
                user.get().getSubList().remove(subreddit);
                user.get().setSubCount(user.get().getSubCount() - 1);
            }
            userRepo.save(user.get());
        }
        // UPDATE USER COUNT IN SUBREDDIT SERVICE
        subredditClient.updateUserCount(request);
        return "User Added/Removed in Sub";
    }

    public String postInUser(PostFeignRequest request) {
        Optional<User> user = userRepo.findById(request.getUserId());
        if (user.isPresent()) {
            Post post = new Post(request.getPostId(), request.getSubId(), request.getSubName(), request.getPostTitle(), request.getPostDescription(), request.getImgUrl());
            if (request.isAdded()) {
                user.get().getPosts().add(post);
                user.get().setPostCount(user.get().getPostCount() + 1);
            } else {
                user.get().getPosts().remove(post);
                user.get().setPostCount(user.get().getPostCount() - 1);
            }
            userRepo.save(user.get());
        }
        return "User Added/Removed in Sub";
    }

    public String commentInUser(CommentFeignRequest request) {
        Optional<User> user = userRepo.findById(request.getUserId());
        if (user.isPresent()) {
            Comment comment = new Comment(request.getCommentId(), request.getText());
            if (request.isAdded()) {
                user.get().getComments().add(comment);
                user.get().setCommentCount(user.get().getCommentCount() + 1);
            } else {
                user.get().getComments().remove(comment);
                user.get().setCommentCount(user.get().getCommentCount() - 1);
            }
            userRepo.save(user.get());
        }
        return "User Added/Removed in Sub";
    }
}
