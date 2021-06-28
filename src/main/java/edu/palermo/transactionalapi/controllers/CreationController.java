package edu.palermo.transactionalapi.controllers;

import edu.palermo.transactionalapi.models.*;
import edu.palermo.transactionalapi.services.BusinessException;
import edu.palermo.transactionalapi.services.CreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController
public class CreationController {
    @Autowired
    private CreationService creationService;

    @PostMapping("/user/create")
    public ResponseEntity<Response> createUser(HttpServletRequest request, @RequestBody User user) {
        try{
            User myUser = creationService.createUser(request, user);
            Response response = new Response();
            response.putItem("statusCode", 201);
            response.putItem("message", "User created succesfully");
            response.putItem("cvu", myUser.getCvu().getCvu());
            return new ResponseEntity(response, HttpStatus.OK);
        }catch(IllegalArgumentException e){
            Response response=new Response();
            response.putItem("statusCode", 500);
            response.putItem("message", "Something went wrong");
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }catch(BusinessException e){
            Response response=new Response();
            response.putItem("statusCode", 409);
            response.putItem("message", e.getMessage());
            return new ResponseEntity(response, HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/user/alias")
    public ResponseEntity<Response> editAlias(HttpServletRequest request, @RequestBody User user) {
        try{
            User myUser = creationService.editAlias(request, user);
            Response response = new Response();
            response.putItem("statusCode", 201);
            response.putItem("message", "User alias modified succesfully");
            response.putItem("cvu", myUser.getCvu().getAlias());
            return new ResponseEntity(response, HttpStatus.OK);
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

    @PostMapping("/psp/create")
    public ResponseEntity createPsp(@RequestBody Psp psp) {
        try{
            Psp myPsp = creationService.createPsp(psp);
            Response response = new Response();
            response.putItem("statusCode", 201);
            response.putItem("message", "Psp created succesfully");
            response.putItem("pspCode", myPsp.getPspCode());
            response.putItem("cuit", myPsp.getCuit());
            response.putItem("razonSocial", myPsp.getRazonSocial());
            response.putItem("tipoPSP", myPsp.getTipoPSP());
            return new ResponseEntity(response, HttpStatus.OK);
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

    @PostMapping("/account/create")
    public ResponseEntity createAccount(@RequestBody Account account) {
        try{
            Account account1 = creationService.createAccount(account);
            Response response = new Response();
            response.putItem("statusCode", 201);
            response.putItem("message", "Account created succesfully");
            response.putItem("cbu", account1.getCbu());
            return new ResponseEntity(response, HttpStatus.OK);
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

    @GetMapping("/users")
    public ResponseEntity<UserDTO> getUsers(HttpServletRequest request) {
        try{
            List<UserDTO> users= creationService.getUsers(request);
            return new ResponseEntity(users, HttpStatus.OK);
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

    @GetMapping("/accountamount")
    public ResponseEntity getAccountAmount(HttpServletRequest request) {
        try{
            Account account= creationService.getAccount(request);
            Response response = new Response();
            response.putItem("statusCode", 200);
            response.putItem("message", "Account amount");
            response.putItem("amount", account.getAmount());
            return new ResponseEntity(response, HttpStatus.OK);
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
