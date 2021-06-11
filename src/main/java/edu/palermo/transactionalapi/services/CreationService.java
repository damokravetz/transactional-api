package edu.palermo.transactionalapi.services;

import edu.palermo.transactionalapi.models.*;
import edu.palermo.transactionalapi.repositories.AccountRepository;
import edu.palermo.transactionalapi.repositories.CommerceRepository;
import edu.palermo.transactionalapi.repositories.CreditCardRepository;
import edu.palermo.transactionalapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreationService {
    @Autowired
    private CreditCardRepository creditCardRepository;
    @Autowired
    private CommerceRepository commerceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;

    public User createUser(User user) throws IllegalArgumentException{
        return userRepository.save(user);
    }

    public CreditCard createCreditCard(CreditCard creditCard)throws IllegalArgumentException{
        return creditCardRepository.save(creditCard);
    }

    public Commerce createCommerce(Commerce commerce)throws IllegalArgumentException{
        return commerceRepository.save(commerce);
    }

    public Account createAccount(Account account)throws IllegalArgumentException{
        return accountRepository.save(account);
    }
}
