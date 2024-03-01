package com.User.User.Model;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.logging.log4j.core.util.UuidUtil;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Document
public class User {

    @Id
    private UUID userId;
    private String name;
    private Date dateOfBirth;
    @Email
    private String emailAddress;
    private GENDER gender;
    private boolean isActive;
    private Address address;
    private ContactInformation contactInformation;
    private Date createdTimeStamp;
    private Date updatedTimeStamp;

    private List<Subreddit> subList;
    private int subCount;
    private List<Post> posts;
    private int postCount;
    private List<Comment> comments;
    private int commentCount;

    public User() {
        this.userId = UuidUtil.getTimeBasedUuid();
        this.createdTimeStamp = new Date();
        this.isActive = true;
        this.subList = new ArrayList<>();
        this.posts = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public enum GENDER {
        MALE, FEMALE, OTHER
    }


}
