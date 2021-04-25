package edu.palermo.transactionalapi.repositories;

import edu.palermo.transactionalapi.models.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    List<Transaction> findAll();
    Transaction findById(long id);
}
