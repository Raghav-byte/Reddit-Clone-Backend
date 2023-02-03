package com.example.RedditClone.Reddit.Repository;

import com.example.RedditClone.Reddit.Model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepo extends MongoRepository<Post, UUID> {

    @Query("{ 'isActive' : ?0}")
    List<Post> findByStatus(boolean status);

    @Query("{ 'postTitle' :?0}")
    List<Post> searchPost(String searchRequest);
}
