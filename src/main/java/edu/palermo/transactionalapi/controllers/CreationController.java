package edu.palermo.transactionalapi.controllers;

import edu.palermo.transactionalapi.models.*;
import edu.palermo.transactionalapi.services.AccountService;
import edu.palermo.transactionalapi.services.BusinessException;
import edu.palermo.transactionalapi.services.CreationService;
import edu.palermo.transactionalapi.services.JWTAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class CreationController {
    @Autowired
    private CreationService creationService;

    @PostMapping("/user/create")
    public ResponseEntity<Response> createUser(HttpServletRequest request, @RequestBody User user) {
        try{
            if(!creationService.userAlreadyExists(user)){
                User myUser=creationService.createUser(request, user);
                Response response=new Response();
                response.putItem("statusCode", 201);
                response.putItem("message", "User created succesfully");
                response.putItem("cvu", myUser.getCvu());
                return new ResponseEntity(response, HttpStatus.OK);
            }else{
                Response response=new Response();
                response.putItem("statusCode", 409);
                response.putItem("message", "User already exists");
                return new ResponseEntity(response, HttpStatus.CONFLICT);
            }
        }catch(IllegalArgumentException e){
            Response response=new Response();
            response.putItem("statusCode", 500);
            response.putItem("message", "Something went wrong");
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/creditcard/create")
    public ResponseEntity createCreditCard(@RequestBody CreditCard creditCard) {
        try{
            if(!creationService.creditCardAlreadyExists(creditCard)){
                CreditCard myCreditCard=creationService.createCreditCard(creditCard);
                Response response=new Response();
                response.putItem("statusCode", 201);
                response.putItem("message", "CreditCard created succesfully");
                return new ResponseEntity(response, HttpStatus.OK);
            }else{
                Response response=new Response();
                response.putItem("statusCode", 409);
                response.putItem("message", "CreditCard already exists");
                return new ResponseEntity(response, HttpStatus.CONFLICT);
            }
        }catch(IllegalArgumentException e){
            Response response=new Response();
            response.putItem("statusCode", 500);
            response.putItem("message", "Something went wrong");
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/commerce/create")
    public ResponseEntity createCommerce(@RequestBody Commerce commerce) {
        try{
            if(!creationService.commerceAlreadyExists(commerce)){
                Commerce myCommerce=creationService.createCommerce(commerce);
                Response response=new Response();
                response.putItem("statusCode", 201);
                response.putItem("message", "Commerce created succesfully");
                return new ResponseEntity(response, HttpStatus.OK);
            }else{
                Response response=new Response();
                response.putItem("statusCode", 409);
                response.putItem("message", "Commerce already exists");
                return new ResponseEntity(response, HttpStatus.CONFLICT);
            }
        }catch(IllegalArgumentException e){
            Response response=new Response();
            response.putItem("statusCode", 500);
            response.putItem("message", "Something went wrong");
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/account/create")
    public ResponseEntity createAccount(@RequestBody Account account) {
        try{
            if(!creationService.accountAlreadyExists(account)){
                Account myAccount=creationService.createAccount(account);
                Response response=new Response();
                response.putItem("statusCode", 201);
                response.putItem("message", "Account created succesfully");
                response.putItem("pspCode", myAccount.getPspCode());
                return new ResponseEntity(response, HttpStatus.OK);
            }else{
                Response response=new Response();
                response.putItem("statusCode", 409);
                response.putItem("message", "Account already exists");
                return new ResponseEntity(response, HttpStatus.CONFLICT);
            }
        }catch(IllegalArgumentException e){
            Response response=new Response();
            response.putItem("statusCode", 500);
            response.putItem("message", "Something went wrong");
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (BusinessException e){
            Response response=new Response();
            response.putItem("statusCode", 406);
            response.putItem("message", e.getMessage());
            return new ResponseEntity(response, HttpStatus.NOT_ACCEPTABLE);
        }
    }


}
