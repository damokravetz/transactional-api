package edu.palermo.transactionalapi.services;

import edu.palermo.transactionalapi.models.*;
import edu.palermo.transactionalapi.repositories.CommerceRepository;
import edu.palermo.transactionalapi.repositories.CreditCardRepository;
import edu.palermo.transactionalapi.repositories.TransactionRepository;
import edu.palermo.transactionalapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;

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
        CreditCard myCreditCard=transaction.getCreditCard();
        Optional<User>user= Optional.ofNullable(userRepository.findByDni(transaction.getUser().getDni()));
        Optional<Commerce>commerce= Optional.ofNullable(commerceRepository.findByCuit(transaction.getCommerce().getCuit()));
        Optional<CreditCard>creditCard= Optional.ofNullable(creditCardRepository.findByNumberAndExpirationDateAndVerificationCode
                (myCreditCard.getNumber(), myCreditCard.getExpirationDate(), myCreditCard.getVerificationCode()));

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
        //verificar fecha expiracion
        Boolean res;
        Date date;
        try{
            SimpleDateFormat formatter = new SimpleDateFormat("MM/yy");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            date=formatter.parse(creditCard.getExpirationDate());
            if(date.after(new Date())){
                res=true;
            }else {
                res=false;
            }
        }catch(ParseException e){
            res= false;
        }
        return res;
    }


}
