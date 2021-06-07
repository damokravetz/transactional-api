package edu.palermo.transactionalapi.services;

import edu.palermo.transactionalapi.models.*;
import edu.palermo.transactionalapi.repositories.CommerceRepository;
import edu.palermo.transactionalapi.repositories.CreditCardRepository;
import edu.palermo.transactionalapi.repositories.TransactionRepository;
import edu.palermo.transactionalapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CreditCardRepository creditCardRepository;
    @Autowired
    private CommerceRepository commerceRepository;
    @Autowired
    private UserRepository userRepository;

    public Status makeTransaction(Transaction transaction){
        Status status=Status.FAILURE;
        Optional<User>user= Optional.ofNullable(userRepository.findByDni(transaction.getUser().getDni()));
        Optional<Commerce>commerce= Optional.ofNullable(commerceRepository.findByCuit(transaction.getCommerce().getCuit()));
        Optional<CreditCard>creditCard= Optional.ofNullable(creditCardRepository.findByNumberAndExpirationDate(transaction.getCreditCard().getNumber(), transaction.getCreditCard().getExpirationDate()));
        if(user.isPresent()&&commerce.isPresent()&&creditCard.isPresent()){
            if(validateCreditCard(creditCard.get())){
                transaction.setCreditCard(creditCard.get());
                transaction.setUser(user.get());
                transaction.setCommerce(commerce.get());
                transactionRepository.save(transaction);
                status=Status.SUCCESS;
            }
        }
        return status;
    }

    private Boolean validateCreditCard(CreditCard creditCard){
        //creditCard.getExpirationDate();
        return true;
    }


}
