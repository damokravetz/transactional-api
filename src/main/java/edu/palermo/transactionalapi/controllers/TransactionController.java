package edu.palermo.transactionalapi.controllers;

import edu.palermo.transactionalapi.models.Transaction;
import edu.palermo.transactionalapi.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    private final AtomicLong counter = new AtomicLong();

    @PostMapping("/transaction")
    public ResponseEntity excecuteTransaction(@RequestBody Transaction transaction) {
        try{
            Transaction transaction1=transactionService.makeTransaction(transaction);
            return new ResponseEntity(transaction1, HttpStatus.OK);

        }catch(IllegalArgumentException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }

    }

}
