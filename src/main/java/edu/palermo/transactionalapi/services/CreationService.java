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
    @Autowired
    private AccountService accountService;

    public User createUser(User user) throws IllegalArgumentException{
        return userRepository.save(user);
    }

    public Boolean userAlreadyExists(User user){
        Optional<User> myUser=Optional.ofNullable(userRepository.findByDni(user.getDni()));
        if(myUser.isPresent()){
            return true;
        }else{
            return false;
        }
    }

    public CreditCard createCreditCard(CreditCard creditCard)throws IllegalArgumentException{
        return creditCardRepository.save(creditCard);
    }

    public Boolean creditCardAlreadyExists(CreditCard creditCard){
        Optional<CreditCard> myCreditCard=Optional.ofNullable(creditCardRepository.findByNumber(creditCard.getNumber()));
        if(myCreditCard.isPresent()){
            return true;
        }else{
            return false;
        }
    }

    public Commerce createCommerce(Commerce commerce)throws IllegalArgumentException{
        return commerceRepository.save(commerce);
    }

    public Boolean commerceAlreadyExists(Commerce commerce){
        Optional<Commerce> myCommerce=Optional.ofNullable(commerceRepository.findByCuit(commerce.getCuit()));
        if(myCommerce.isPresent()){
            return true;
        }else{
            return false;
        }
    }

    public Account createAccount(Account account)throws IllegalArgumentException{
        String myHash=accountService.getHash(account.getPassword());
        account.setPassword(myHash);
        return accountRepository.save(account);
    }

    public Boolean accountAlreadyExists(Account account){
        Optional<Account> myAccount=Optional.ofNullable(accountRepository.findByUsername(account.getUsername()));
        if(myAccount.isPresent()){
            return true;
        }else{
            return false;
        }
    }
}
