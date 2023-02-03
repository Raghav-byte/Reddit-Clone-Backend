package com.example.RedditClone.Reddit.Repository;

import com.example.RedditClone.Reddit.Model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepo extends MongoRepository<Comment, UUID> {

    @Query("{ 'userId' :?0 }")
    List<Comment> commentByUser(UUID userId);

    @Query("{ 'postId' :?0 }")
    List<Comment> commentByPost(UUID userId);
}
