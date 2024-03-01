package com.User.User.Service;

import com.User.User.Model.User;
import com.User.User.Model.UserLoginDetails;
import com.User.User.Repository.UserLoginDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthenticationService {

    @Autowired
    UserLoginDetailsRepo userLoginDetailsRepo;


    public boolean checkUsernamePassword(UUID userId, String username, String password) {
        Optional<UserLoginDetails> loginDetails = userLoginDetailsRepo.findById(userId);
        if (loginDetails.isPresent()) {
            return loginDetails.get().getUserName().equals(username) && loginDetails.get().getPassword().equals(password);
        } else {
            throw new ResourceAccessException("Login Details not found");
        }

    }

    public String createCredentials(User user, String userName, String password) {
        // SEND THIS IN MAIL
        UserLoginDetails loginDetails = new UserLoginDetails();
        loginDetails.setUserId(user.getUserId());
        loginDetails.setName(user.getName());
        loginDetails.setEmailId(user.getEmailAddress());
        loginDetails.setUserName(userName);
        loginDetails.setPassword(password);
        userLoginDetailsRepo.save(loginDetails);
        return "Details Saved";
    }

    public String resetPassword(UUID userId, String password) {
        Optional<UserLoginDetails> loginDetails = userLoginDetailsRepo.findById(userId);
        if (loginDetails.isPresent()) {
            loginDetails.get().setPassword(password);
            userLoginDetailsRepo.save(loginDetails.get());
        } else {
            throw new ResourceAccessException("Login Details not found");
        }
        return "Password Changed";
    }
}
