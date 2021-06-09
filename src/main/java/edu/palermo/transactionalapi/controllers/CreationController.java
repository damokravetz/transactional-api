package edu.palermo.transactionalapi.controllers;

import edu.palermo.transactionalapi.models.Commerce;
import edu.palermo.transactionalapi.models.CreditCard;
import edu.palermo.transactionalapi.models.User;
import edu.palermo.transactionalapi.services.CreationService;
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

@RestController
public class CreationController {
    @Autowired
    private CreationService creationService;

    @PostMapping("/user/create")
    public ResponseEntity createUser(@RequestBody User user) {
        try{
            User myUser=creationService.createUser(user);
            return new ResponseEntity(myUser, HttpStatus.OK);

        }catch(IllegalArgumentException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/creditcard/create")
    public ResponseEntity createCreditCard(@RequestBody CreditCard creditCard) {
        try{
            CreditCard myCreditCard=creationService.createCreditCard(creditCard);
            return new ResponseEntity(myCreditCard, HttpStatus.OK);

        }catch(IllegalArgumentException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/commerce/create")
    public ResponseEntity createCommerce(@RequestBody Commerce commerce) {
        try{
            Commerce myCommerce=creationService.createCommerce(commerce);
            return new ResponseEntity(myCommerce, HttpStatus.OK);

        }catch(IllegalArgumentException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
