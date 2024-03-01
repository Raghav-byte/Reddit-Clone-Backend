package com.Post.Repository;

import com.Post.Model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepo extends MongoRepository<Post, UUID> {

    @Query(value = "{'isActive': ?0}")
    List<Post> findByStatus(boolean status);

    @Query("{ 'subTitle' : {'$regex':'(?i)?0 '} }")
    List<Post> searchSubreddit(String searchRequest);

    @Query(value = "{'postTitle': {$regex : ?0, $options: 'i'}}")
    List<Post> searchPost(String searchRequest);
}
