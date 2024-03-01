package com.Subreddit.Repository;

import com.Subreddit.Model.Subreddit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SubRepo extends MongoRepository<Subreddit, UUID> {

    @Query(value = "{'subTitle': {$regex : ?0, $options: 'i'}}")
    List<Subreddit> findByName(String subTitle);

    @Query(value = "{'isActive': ?0}")
    List<Subreddit> findByStatus(boolean status);

    @Query("{'subTitle' : {'$regex':'(?i)?0'} }")
    List<Subreddit> searchSubreddit(String searchRequest);

}
