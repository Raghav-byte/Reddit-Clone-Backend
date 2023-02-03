package com.example.RedditClone.Reddit.Repository;

import com.example.RedditClone.Reddit.Model.Vote;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;;

import java.util.List;
import java.util.UUID;

@Repository
public interface VoteRepo extends MongoRepository<Vote, UUID> {


    @Query("{ 'userId' :?0 }")
    List<Vote> voteByUser(UUID userId);

    @Query("{ 'postId' :?0 }")
    List<Vote> voteByPost(UUID userId);
}
