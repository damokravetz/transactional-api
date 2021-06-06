package edu.palermo.transactionalapi.controllers;

import edu.palermo.transactionalapi.models.Status;
import edu.palermo.transactionalapi.models.Transaction;
import edu.palermo.transactionalapi.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    private final AtomicLong counter = new AtomicLong();

    @PostMapping("/transaction")
    public Status excecuteTransaction(@RequestBody Transaction transaction) {
        return transactionService.makeTransaction(transaction);
    }

}
