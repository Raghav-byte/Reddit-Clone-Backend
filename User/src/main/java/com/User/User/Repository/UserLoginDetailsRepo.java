package com.User.User.Repository;

import com.User.User.Model.UserLoginDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserLoginDetailsRepo extends MongoRepository<UserLoginDetails, UUID> {

}
