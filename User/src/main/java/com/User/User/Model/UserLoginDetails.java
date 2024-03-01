package com.User.User.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.logging.log4j.core.util.UuidUtil;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@Document
public class UserLoginDetails {

    @Id
    private UUID userId;
    private String name;

    private String userName;
    private String password;
    private String emailId;
    private Date createdTimeStamp;

    public UserLoginDetails() {
        this.userId = UuidUtil.getTimeBasedUuid();
        this.createdTimeStamp = new Date();
    }
}
