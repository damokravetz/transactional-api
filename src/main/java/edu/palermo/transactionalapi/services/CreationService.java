package edu.palermo.transactionalapi.services;

import edu.palermo.transactionalapi.models.*;
import edu.palermo.transactionalapi.repositories.AccountRepository;
import edu.palermo.transactionalapi.repositories.CommerceRepository;
import edu.palermo.transactionalapi.repositories.CreditCardRepository;
import edu.palermo.transactionalapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
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
    @Autowired
    private JWTAuthorizationFilter jwtAuthorizationFilter;

    private static final String INITIAL_PSP="0001";
    private static final int PSP_CODE_LENGTH=4;
    private static final int CLIENT_CODE_LENGTH=12;
    private static final String  VIRTUAL_CODE="000";
    private static final String  FIRST_VERIFY_CODE="8";
    private static final String  RESERVED_CODE="0";
    private static final String  SECOND_VERIFY_CODE="8";


    public User createUser(HttpServletRequest request, User user) throws IllegalArgumentException{
        String myUser = jwtAuthorizationFilter.getUser(request);
        Account account=accountRepository.findByUsername(myUser);
        User savedUser=userRepository.save(user);
        String cvu= generateCVU(account.getPspCode(), savedUser.getId());
        savedUser.setCvu(cvu);
        return userRepository.save(user);
    }

    private String generateCVU(String pspCode, Long userID){
        //cambiar a que tire numeros random para identificar
        String myUserId=String.format("%0"+CLIENT_CODE_LENGTH+"d", userID);
        return VIRTUAL_CODE+pspCode+FIRST_VERIFY_CODE+RESERVED_CODE+myUserId+SECOND_VERIFY_CODE;
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

    @Transactional
    public Account createAccount(Account account)throws IllegalArgumentException{
        String myHash=accountService.getHash(account.getPassword());
        account.setPassword(myHash);
        generatePspCode(account);
        if(account.getAmount()==null){
            account.setAmount(0.0);
        }else{
            if(account.getAmount()<0){
                throw new BusinessException("Business amount must be equal or greater than cero");
            }
        }
        return accountRepository.save(account);
    }

    private void generatePspCode(Account account) {
        Account lastAccount=accountRepository.findTopByOrderByIdDesc();
        String newPsp;
        if(lastAccount==null || lastAccount.getPspCode()==null){
            newPsp=INITIAL_PSP;
        }else{
            long code=Long.valueOf(lastAccount.getPspCode());
            code++;
            newPsp=String.format("%0"+PSP_CODE_LENGTH+"d", code);
        }
        account.setPspCode(newPsp);
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
