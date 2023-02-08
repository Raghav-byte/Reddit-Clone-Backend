package com.example.RedditClone.Reddit.Controller;

import com.example.RedditClone.Reddit.Service.MiscellaneousService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {

    @Autowired
    private MiscellaneousService miscellaneousService;

    @ApiOperation("Authenticate the username and password")
    @GetMapping("/credentials")
    public boolean checkUsernamePassword(@RequestParam UUID userId, @RequestParam String username , @RequestParam String password){
        return miscellaneousService.checkUsernamePassword(userId,username,password);
    }

    @ApiOperation("Reset password request")
    @GetMapping("/reset")
    public String resetPassword(@RequestParam(required = false) UUID userId, @RequestParam(required = false) String username){
        return miscellaneousService.resetPassword(userId,username);
    }



}
