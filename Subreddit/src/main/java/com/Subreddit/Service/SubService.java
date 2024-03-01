package com.Subreddit.Service;

import com.Subreddit.FeignClient.PostRequest;
import com.Subreddit.FeignClient.UserRequest;
import com.Subreddit.Model.Subreddit;
import com.Subreddit.Repository.SubRepo;
import com.Subreddit.Requests.SubFilterRequest;
import org.apache.http.util.TextUtils;
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
public class SubService {

    @Autowired
    private SubRepo subRepo;
    @Autowired
    private MongoTemplate mongoTemplate;

    //CREATE SUB
    public Subreddit createSubreddit(Subreddit sub) throws Exception {
        List<Subreddit> alreadyCreatedSubs = subRepo.findByName(sub.getSubTitle());
        if (!CollectionUtils.isEmpty(alreadyCreatedSubs)) {
            throw new Exception("Subreddit already created by that name");
        }
        return subRepo.save(sub);
    }

    //LIST OF SUB
    public List<Subreddit> getAllSubreddit() {
        return subRepo.findAll(Sort.by("name").and(Sort.by("isActive").descending()));
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
        if (!TextUtils.isBlank(sub.getSubTitle())) {
            update.set("subTitle", sub.getSubTitle());
        }
        if (!TextUtils.isBlank(sub.getSubDescription())) {
            update.set("subDescription", sub.getSubDescription());
        }
        if (!TextUtils.isBlank(sub.getSubImage())) {
            update.set("subImage", sub.getSubImage());
        }
        if (!TextUtils.isBlank(sub.getSubCoverImage())) {
            update.set("subCoverImage", sub.getSubCoverImage());
        }
        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(sub.getSubRules())) {
            update.set("subRules", sub.getSubRules());
        }
        if (sub.getExternalLinks() != null) {
            update.set("externalLinks", sub.getExternalLinks());
        }
        update.set("updatedDate", LocalDate.now());

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
            sub.get().setUpdatedDate(LocalDate.now());
            subRepo.save(sub.get());
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
    public List<Subreddit> filterSubreddit(SubFilterRequest filterRequest) {
        Query query = new Query();
        List<Criteria> criteriaList = new ArrayList<>();

        if (filterRequest.getStatus() != null) {
            criteriaList.add(Criteria.where("isActive").is(filterRequest.getStatus()));
        }
        if (filterRequest.getMinPosts() > 0 && filterRequest.getMaxPosts() > 0) {
            criteriaList.add(Criteria.where("postCount").gte(filterRequest.getMinPosts()).lt(filterRequest.getMaxPosts()));
        }
        if (filterRequest.getMinUsers() > 0 && filterRequest.getMaxUsers() > 0) {
            criteriaList.add(Criteria.where("userCount").gte(filterRequest.getMinUsers()).lt(filterRequest.getMaxUsers()));
        }

        if (!CollectionUtils.isEmpty(criteriaList)) {
            query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[criteriaList.size()])));
        }
        return mongoTemplate.find(query, Subreddit.class);
    }

    public String updatePostCount(PostRequest request) {
        Optional<Subreddit> subreddit = subRepo.findById(request.getSubId());
        subreddit.ifPresentOrElse(sub -> {
            if (request.isAdded()) {
                sub.setPostCount(sub.getPostCount() + 1);
                sub.getPostList().add(request.getPostId());
            } else {
                sub.setPostCount(sub.getPostCount() - 1);
                sub.getPostList().remove(request.getPostId());
            }
            subRepo.save(sub);
        }, () -> {
            throw new ResourceAccessException("No Subreddit Found");
        });
        return "Post Count Updated";
    }

    public String updateUserCount(UserRequest request) {
        Optional<Subreddit> subreddit = subRepo.findById(request.getSubId());
        subreddit.ifPresentOrElse(sub -> {
            if (request.isAdded()) {
                sub.setUserCount(sub.getUserCount() + 1);
                sub.getUserIds().add(request.getUserId());
            } else {
                sub.setUserCount(sub.getUserCount() - 1);
                sub.getUserIds().remove(request.getUserId());
            }
            subRepo.save(sub);
        }, () -> {
            throw new ResourceAccessException("No Subreddit Found");
        });
        return "User Count Updated";
    }
}
