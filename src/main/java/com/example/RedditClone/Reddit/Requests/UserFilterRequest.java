package com.example.RedditClone.Reddit.Requests;

import com.example.RedditClone.Reddit.Model.User;

import java.util.Date;

public class UserFilterRequest {

    private Boolean isActive;
    private Date startDate;
    private Date endDate;
    private Date dateOfBirth;
    private String country;
    private String state;
    private String city;
    private User.GENDER gender;

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public User.GENDER getGender() {
        return gender;
    }

    public void setGender(User.GENDER gender) {
        this.gender = gender;
    }
}
