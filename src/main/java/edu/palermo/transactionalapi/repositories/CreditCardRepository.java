package edu.palermo.transactionalapi.repositories;

import edu.palermo.transactionalapi.models.CreditCard;
import edu.palermo.transactionalapi.models.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CreditCardRepository extends CrudRepository<CreditCard, Long> {

    List<CreditCard> findAll();
    CreditCard findByNumberAndExpirationDateAndVerificationCode(String number, String expirationDate, String verificationCode);
    CreditCard findByNumber(String number);
}
