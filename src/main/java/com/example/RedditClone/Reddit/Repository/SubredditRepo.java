package com.example.RedditClone.Reddit.Repository;

import com.example.RedditClone.Reddit.Model.Subreddit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SubredditRepo extends MongoRepository<Subreddit, UUID> {

    @Query("{ 'isActive' : ?0 }")
    List<Subreddit> findByStatus(boolean status);

    @Query("{ 'subTitle' : {'$regex':'(?i)?0 '} }")
    List<Subreddit> searchSubreddit(String searchRequest);
}
