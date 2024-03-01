package com.Post.Repository;

import com.Post.Model.Vote;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VoteRepo extends MongoRepository<Vote, UUID> {
}
