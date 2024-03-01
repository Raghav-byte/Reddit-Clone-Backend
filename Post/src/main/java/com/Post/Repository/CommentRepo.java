package com.Post.Repository;

import com.Post.Model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CommentRepo extends MongoRepository<Comment, UUID> {

}
