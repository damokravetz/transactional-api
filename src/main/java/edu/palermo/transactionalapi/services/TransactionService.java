package edu.palermo.transactionalapi.services;

import edu.palermo.transactionalapi.models.*;
import edu.palermo.transactionalapi.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    @Autowired
    private TransferRepository transferRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CashInRepository cashInRepository;

    public Transaction makeTransaction(Transaction transaction) {

        Transaction transaction1;

        CreditCard myCreditCard = transaction.getCreditCard();
        Optional<User> user = Optional.ofNullable(userRepository.findByDni(transaction.getUser().getDni()));
        Optional<Commerce> commerce = Optional.ofNullable(commerceRepository.findByCuit(transaction.getCommerce().getCuit()));
        Optional<CreditCard> creditCard = Optional.ofNullable(creditCardRepository.findByNumberAndExpirationDateAndVerificationCode
                (myCreditCard.getNumber(), myCreditCard.getExpirationDate(), myCreditCard.getVerificationCode()));

        if (user.isPresent() && commerce.isPresent() && creditCard.isPresent()) {
            if (validateCreditCard(creditCard.get())) {
                transaction.setCreditCard(creditCard.get());
                transaction.setUser(user.get());
                transaction.setCommerce(commerce.get());
                transaction1 = transactionRepository.save(transaction);
            } else {
                throw new BusinessException("Credit card expired");
            }
        } else {
            throw new BusinessException("Invalid transaction");
        }
        return transaction1;
    }

    @Transactional
    public Transfer makeTransfer(String cvuOrigin, String cvuDestination, Double amount) {
        Transfer transfer;
        Optional<User> userOrigin = Optional.ofNullable(userRepository.findByCvu(cvuOrigin));
        Optional<User> userDestination = Optional.ofNullable(userRepository.findByCvu(cvuDestination));
        if (userOrigin.isPresent() && userDestination.isPresent()) {
            String pspCodeOrigin=getPspCodeFromCvu(userOrigin.get().getCvu());
            String pspCodeDestination=getPspCodeFromCvu(userDestination.get().getCvu());
            //or get by alias
            Account origin=accountRepository.findByPspCode(pspCodeOrigin);
            Account destination=accountRepository.findByPspCode(pspCodeDestination);
            transfer= new Transfer(origin, destination, userOrigin.get(), userDestination.get(), amount);
            transfer=transferRepository.save(transfer);
            if(origin.getId()!=destination.getId()){
                origin.setAmount(origin.getAmount()-amount);
                destination.setAmount(destination.getAmount()+amount);
                accountRepository.save(origin);
                accountRepository.save(destination);
            }
        } else {
            throw new BusinessException("Invalid transfer");
        }
        return transfer;
    }

    @Transactional
    public CashIn cashIn(CashIn cashIn) {
        CashIn myCashIn;
        Optional<User> myUser = Optional.ofNullable(userRepository.findByDni(cashIn.getUser().getDni()));
        Optional<CreditCard> myCreditCard = Optional.ofNullable(creditCardRepository.findByNumberAndExpirationDateAndVerificationCode
                (cashIn.getCreditCard().getNumber(), cashIn.getCreditCard().getExpirationDate(), cashIn.getCreditCard().getVerificationCode()));

        if (myUser.isPresent() && myCreditCard.isPresent() && cashIn.getAmount()>0) {
            if (validateCreditCard(myCreditCard.get())) {
                String pspCode=getPspCodeFromCvu(myUser.get().getCvu());
                Account account=accountRepository.findByPspCode(pspCode);
                cashIn= new CashIn(account, myCreditCard.get(), myUser.get(), cashIn.getAmount());
                cashIn=cashInRepository.save(cashIn);
                account.setAmount(account.getAmount()+cashIn.getAmount());
                accountRepository.save(account);
            } else {
                throw new BusinessException("Credit card expired");
            }
        } else {
            throw new BusinessException("Invalid transaction");
        }
        return cashIn;
    }

    private String getPspCodeFromCvu(String cvu){
        String pspCode= cvu.substring(3,7);
        return pspCode;
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
