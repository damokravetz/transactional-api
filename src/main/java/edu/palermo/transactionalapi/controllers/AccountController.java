package edu.palermo.transactionalapi.controllers;

import edu.palermo.transactionalapi.models.Psp;
import edu.palermo.transactionalapi.models.Response;
import edu.palermo.transactionalapi.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@CrossOrigin
@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/auth")
    public ResponseEntity<Response> authorize(@RequestBody Psp psp) {
        try{
            Optional<Psp> account1= accountService.authorize(psp.getUsername(), psp.getPassword());
            if(account1.isPresent()){
                HttpHeaders headers = new HttpHeaders();
                headers.add("Authorization", account1.get().getToken());
                Response response=new Response();
                response.putItem("statusCode", 200);
                response.putItem("message", "Authorized");
                response.putItem("token",account1.get().getToken());
                return new ResponseEntity(response, HttpStatus.OK);
            }else{
                Response response=new Response();
                response.putItem("statusCode", 401);
                response.putItem("message", "Unauthorized");
                return new ResponseEntity(response, HttpStatus.UNAUTHORIZED);
            }
        }catch(IllegalArgumentException e) {
            Response response=new Response();
            response.putItem("statusCode", 500);
            response.putItem("message", "Something went wrong");
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
