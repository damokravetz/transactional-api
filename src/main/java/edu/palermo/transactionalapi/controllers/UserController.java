package edu.palermo.transactionalapi.controllers;

import edu.palermo.transactionalapi.models.User;
import edu.palermo.transactionalapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User user) {
        Optional<User> user1=userService.login(user.getMail(), user.getPassword());
        if(user1.isPresent()){
            User myUser=user1.get();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", myUser.getToken());
            return new ResponseEntity(headers, HttpStatus.OK);
        }else{
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }


}
