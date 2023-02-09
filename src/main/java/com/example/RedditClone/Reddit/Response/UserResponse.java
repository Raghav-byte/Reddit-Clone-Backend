package com.example.RedditClone.Reddit.Response;

import com.example.RedditClone.Reddit.Model.User;

import java.util.ArrayList;
import java.util.List;

public class UserResponse {

    private long totalUsers;
    private long activeUser;
    private long inActiveUsers;
    private List<User> userList;

    public UserResponse() {
        this.userList = new ArrayList<>();
    }

    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public long getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(long activeUser) {
        this.activeUser = activeUser;
    }

    public long getInActiveUsers() {
        return inActiveUsers;
    }

    public void setInActiveUsers(long inActiveUsers) {
        this.inActiveUsers = inActiveUsers;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
