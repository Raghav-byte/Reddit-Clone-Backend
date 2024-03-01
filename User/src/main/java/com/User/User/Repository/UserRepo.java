package com.User.User.Repository;

import com.User.User.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepo extends MongoRepository<User, UUID> {

    @Query("{ 'isActive' : ?0 }")
    List<User> findByStatus(boolean status);

    @Query("{ $or :[ {'userName':{'$regex':'(?i)?0'}} , {'name':{'$regex':'(?i)?0'}} , {'contactInformation.mobileNumber':{'$regex':'(?i)?0'}} ] }")
    List<User> searchUser(String searchRequest);
}
