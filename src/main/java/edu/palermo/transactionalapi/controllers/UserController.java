package edu.palermo.transactionalapi.controllers;

import edu.palermo.transactionalapi.models.User;
import edu.palermo.transactionalapi.models.UserStatus;
import edu.palermo.transactionalapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public UserStatus login(@RequestBody User user) {
        UserStatus userStatus;
        Optional<User> user1=userService.login(user.getMail(), user.getPassword());
        if(user1.isPresent()){
            userStatus=UserStatus.SUCCESS;
        }else{
            userStatus=UserStatus.FAILURE;
        }
        return userStatus;
    }
}
