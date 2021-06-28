package edu.palermo.transactionalapi.controllers;

import edu.palermo.transactionalapi.models.*;
import edu.palermo.transactionalapi.services.BusinessException;
import edu.palermo.transactionalapi.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.atomic.AtomicLong;

@CrossOrigin
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

    @PostMapping("/transfer")
    public ResponseEntity excecuteTransfer(HttpServletRequest request, @RequestBody TransferRequest transferRequest) {
        try{
            Transfer transfer=transactionService.makeTransfer(request,transferRequest.getUserPspId(), transferRequest.getCvuAliasDestination(), transferRequest.getAmount());
            Response response=new Response();
            response.putItem("statusCode", 200);
            response.putItem("message", "Succesful transfer");
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

    @PostMapping("/cashin")
    public ResponseEntity cashIn(HttpServletRequest request, @RequestBody CashIn cashIn) {
        try{
            CashIn myCashIn=transactionService.cashIn(request,cashIn);
            Response response=new Response();
            response.putItem("statusCode", 200);
            response.putItem("message", "Succesful transfer");
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
