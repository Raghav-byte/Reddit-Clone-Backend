package com.User.User.Model;

import lombok.Data;

import java.util.Date;

@Data
public class UserFilterRequest {

    private Boolean isActive;
    private Date startDate;
    private Date endDate;
    private Date dateOfBirth;
    private String country;
    private String state;
    private String city;
    private User.GENDER gender;
}
