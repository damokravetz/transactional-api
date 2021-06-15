package edu.palermo.transactionalapi.controllers;

import edu.palermo.transactionalapi.models.Response;
import edu.palermo.transactionalapi.models.Transaction;
import edu.palermo.transactionalapi.services.BusinessException;
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
            Response response=new Response();
            response.putItem("statusCode", 200);
            response.putItem("message", "Succesful transcation");
            return new ResponseEntity(response, HttpStatus.OK);
        }catch (BusinessException e){
            Response response=new Response();
            response.putItem("statusCode", 409);
            response.putItem("message", e.getMessage());
            return new ResponseEntity(response, HttpStatus.CONFLICT);
        }catch(IllegalArgumentException e){
            Response response=new Response();
            response.putItem("statusCode", 500);
            response.putItem("message", "Something went wrong");
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
