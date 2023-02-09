package com.example.RedditClone.Reddit.Model;

import com.example.RedditClone.Reddit.Common.Address;
import com.example.RedditClone.Reddit.Common.ContactInformation;
import org.apache.logging.log4j.core.util.UuidUtil;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Document
public class User {

    public enum GENDER {
        MALE, FEMALE, OTHER
    }

    @Id
    private UUID userId;
    private String name;
    private Date dateOfBirth;
    private String emailAddress;
    private GENDER gender;
    private Date createdTimeStamp;
    private Date updatedTimeStamp;
    private boolean isActive;
    private Address address;
    private ContactInformation contactInformation;

    public User() {
        this.userId = UuidUtil.getTimeBasedUuid();
        this.createdTimeStamp = new Date();
        this.isActive = true;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getCreatedTimeStamp() {
        return createdTimeStamp;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setCreatedTimeStamp(Date createdTimeStamp) {
        this.createdTimeStamp = createdTimeStamp;
    }

    public Date getUpdatedTimeStamp() {
        return updatedTimeStamp;
    }

    public void setUpdatedTimeStamp(Date updatedTimeStamp) {
        this.updatedTimeStamp = updatedTimeStamp;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ContactInformation getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(ContactInformation contactInformation) {
        this.contactInformation = contactInformation;
    }

    public GENDER getGender() {
        return gender;
    }

    public void setGender(GENDER gender) {
        this.gender = gender;
    }
}
