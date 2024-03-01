package com.User.User.Controller;

import com.User.User.Model.User;
import com.User.User.Service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Operation(summary = "Create username and password")
    @PostMapping("/")
    public String createCredentials(@RequestBody User user, @RequestParam String userName, @RequestParam String password) {
        return authenticationService.createCredentials(user, userName, password);
    }

    @Operation(summary = "Authenticate the username and password")
    @GetMapping("/credentials")
    public boolean checkUsernamePassword(@RequestParam UUID userId, @RequestParam String username, @RequestParam String password) {
        return authenticationService.checkUsernamePassword(userId, username, password);
    }

    @Operation(summary = "Reset password request")
    @GetMapping("/reset")
    public String resetPassword(@RequestParam UUID userId, @RequestParam String password) {
        return authenticationService.resetPassword(userId, password);
    }


}
