package edu.palermo.transactionalapi.repositories;

import edu.palermo.transactionalapi.models.CreditCard;
import edu.palermo.transactionalapi.models.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CreditCardRepository extends CrudRepository<CreditCard, Long> {

    List<CreditCard> findAll();
    CreditCard findByNumberAndExpirationDate(String number, String expirationDate);
}
