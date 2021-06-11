package edu.palermo.transactionalapi.controllers;

import edu.palermo.transactionalapi.models.Account;
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

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/auth")
    public ResponseEntity login(@RequestBody Account account) {
        Optional<Account> account1= accountService.authorize(account.getUsername(), account.getPassword());
        if(account1.isPresent()){
            Account myAccount=account1.get();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", myAccount.getToken());
            return new ResponseEntity(headers, HttpStatus.OK);
        }else{
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }


}
