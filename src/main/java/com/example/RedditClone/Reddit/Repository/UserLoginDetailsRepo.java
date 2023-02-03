package com.example.RedditClone.Reddit.Repository;

import com.example.RedditClone.Reddit.Model.UserLoginDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserLoginDetailsRepo extends MongoRepository<UserLoginDetails, UUID> {
}
