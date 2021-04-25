package edu.palermo.transactionalapi.services;

import edu.palermo.transactionalapi.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;


}
