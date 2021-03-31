package edu.palermo.transactionalapi.controllers;

import edu.palermo.transactionalapi.models.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class TransactionController {
    private final AtomicLong counter = new AtomicLong();

    @PostMapping("/transaction")
    public Transaction greeting(@RequestHeader("user-agent") String device) {
        System.out.println(device);
        Transaction transaction=new Transaction();
        transaction.setTransactionId(counter.incrementAndGet());
        transaction.setTimestamp(Timestamp.from(Instant.now()));
        transaction.setStatus(true);
        transaction.setErrorCode(0);
        transaction.setMsg("-");
        return transaction;
    }
}
