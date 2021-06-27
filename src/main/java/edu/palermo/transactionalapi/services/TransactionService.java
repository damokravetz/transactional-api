package edu.palermo.transactionalapi.services;

import edu.palermo.transactionalapi.models.*;
import edu.palermo.transactionalapi.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
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
    @Autowired
    private TransferRepository transferRepository;
    @Autowired
    private PspRepository pspRepository;
    @Autowired
    private CashInRepository cashInRepository;
    @Autowired
    private JWTAuthorizationFilter jwtAuthorizationFilter;

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
    public Transfer makeTransfer(HttpServletRequest request, String userPspIdOrigin, String cvuDestination, Double amount) {
        Transfer transfer;
        String myUser = jwtAuthorizationFilter.getUser(request);
        Psp psp = pspRepository.findByUsername(myUser);
        Optional<User> userOrigin = Optional.ofNullable(userRepository.findByUserPspIdAndCvuPspId(userPspIdOrigin,psp.getId()));
        Optional<User> userDestination = Optional.ofNullable(userRepository.findByCvuCvu(cvuDestination));
        if (userOrigin.isPresent() && userDestination.isPresent()) {
            String pspCodeOrigin=userOrigin.get().getCvu().getPsp().getPspCode();
            String pspCodeDestination=userDestination.get().getCvu().getPsp().getPspCode();
            //or get by alias
            Psp origin= pspRepository.findByPspCode(pspCodeOrigin);
            Psp destination= pspRepository.findByPspCode(pspCodeDestination);
            transfer= new Transfer(origin, destination, userOrigin.get(), userDestination.get(), amount);
            transfer=transferRepository.save(transfer);
            if(origin.getId()!=destination.getId()){
                //substraer y sumar a las cuentas
                Double originAmount=origin.getAccount().getAmount()-amount;
                Double destinationAmount=destination.getAccount().getAmount()+amount;
                origin.getAccount().setAmount(originAmount);
                destination.getAccount().setAmount(destinationAmount);
                pspRepository.save(origin);
                pspRepository.save(destination);
            }
        } else {
            throw new BusinessException("Invalid transfer");
        }
        return transfer;
    }

    @Transactional
    public CashIn cashIn(HttpServletRequest request, CashIn cashIn) {
        String currentUser = jwtAuthorizationFilter.getUser(request);
        Psp psp = pspRepository.findByUsername(currentUser);

        Optional<User> myUser = Optional.ofNullable(userRepository.findByUserPspIdAndCvuPspId(cashIn.getUser().getUserPspId(),psp.getId()));
        Optional<CreditCard> myCreditCard = Optional.ofNullable(creditCardRepository.findByNumberAndExpirationDateAndVerificationCode
                (cashIn.getCreditCard().getNumber(), cashIn.getCreditCard().getExpirationDate(), cashIn.getCreditCard().getVerificationCode()));

        if (myUser.isPresent() && myCreditCard.isPresent() && cashIn.getAmount()>0) {
            if (validateCreditCard(myCreditCard.get())) {
                cashIn= new CashIn(psp, myCreditCard.get(), myUser.get(), cashIn.getAmount(), cashIn.getIdTransaction(), cashIn.getDate());
                cashIn=cashInRepository.save(cashIn);
                psp.getAccount().setAmount(psp.getAccount().getAmount()+cashIn.getAmount());
                pspRepository.save(psp);
            } else {
                throw new BusinessException("Credit card expired");
            }
        } else {
            throw new BusinessException("Invalid transaction");
        }
        return cashIn;
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
