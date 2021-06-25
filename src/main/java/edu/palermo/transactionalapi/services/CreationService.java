package edu.palermo.transactionalapi.services;

import edu.palermo.transactionalapi.models.*;
import edu.palermo.transactionalapi.repositories.PspRepository;
import edu.palermo.transactionalapi.repositories.CommerceRepository;
import edu.palermo.transactionalapi.repositories.CreditCardRepository;
import edu.palermo.transactionalapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CreationService {
    @Autowired
    private CreditCardRepository creditCardRepository;
    @Autowired
    private CommerceRepository commerceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PspRepository pspRepository;
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
    private static final String USER_ALIAS_LENGTH="^\\w{6,20}$";
    private static final String USER_ALIAS_CHARACTERS="([A-Za-z0-9\\-\\.]+)";


    public User createUser(HttpServletRequest request, User user) throws IllegalArgumentException{
        String myUser = jwtAuthorizationFilter.getUser(request);
        Psp psp = pspRepository.findByUsername(myUser);
        if(!userAlreadyExistsForPsp(user, psp)){
            String cvu= generateCVU(psp.getPspCode(), user.getUserPspId());
            user.setCvu(new Cvu(cvu,psp));
            return userRepository.save(user);
        }else {
            throw new BusinessException("Psp client already exists");
        }
    }

    /*public User editAlias(User user) {
        User myUser = userRepository.findByDni(user.getDni());
        Pattern lengthPattern = Pattern.compile(USER_ALIAS_LENGTH);
        Pattern charactersPattern = Pattern.compile(USER_ALIAS_CHARACTERS);
        Matcher lengthMatcher = lengthPattern.matcher(user.getCvu().getAlias());
        Matcher charactersMatcher = charactersPattern.matcher(user.getCvu().getAlias());
        if (lengthMatcher.matches()) {
            if(charactersMatcher.matches()){
                Optional<User>aliasExists= Optional.ofNullable(userRepository.findByAlias(user.getAlias()));
                if(aliasExists.isPresent() && !aliasExists.get().getDni().equals(user.getDni())){
                    throw new BusinessException("Alias is already in use");
                }else{
                    myUser.setAlias(user.getAlias());
                    myUser = userRepository.save(myUser);
                }
            }else{
                throw new BusinessException("Alias contains invalid characters");
            }
        } else {
            throw new BusinessException("Alias must be 6 to 20 characters long");
        }
        return myUser;
    }*/

    private String generateCVU(String pspCode, String userID){
        //cambiar a que tire numeros random para identificar
        String myUserId=String.format("%0"+CLIENT_CODE_LENGTH+"d", userID);
        return VIRTUAL_CODE+pspCode+FIRST_VERIFY_CODE+RESERVED_CODE+myUserId+SECOND_VERIFY_CODE;
    }

    public Boolean userAlreadyExistsForPsp(User user, Psp psp){
        Optional<User> myUser=Optional.ofNullable(userRepository.findByUserPspIdAndCvuPspId(user.getUserPspId(),psp.getId()));
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
    public Psp createPsp(Psp psp)throws IllegalArgumentException{
        pspAlreadyExists(psp);
        String myHash=accountService.getHash(psp.getPassword());
        psp.setPassword(myHash);
        generatePspCode(psp);
        psp.setTipoPSP(1);
        return pspRepository.save(psp);
    }

    private void generatePspCode(Psp psp) {
        Psp lastPsp = pspRepository.findTopByOrderByIdDesc();
        String newPsp;
        if(lastPsp ==null || lastPsp.getPspCode()==null){
            newPsp=INITIAL_PSP;
        }else{
            long code=Long.valueOf(lastPsp.getPspCode());
            code++;
            newPsp=String.format("%0"+PSP_CODE_LENGTH+"d", code);
        }
        psp.setPspCode(newPsp);
    }

    public Boolean pspAlreadyExists(Psp psp){
        Optional<Psp> myAccount=Optional.ofNullable(pspRepository.findByCuit(psp.getCuit()));
        if(myAccount.isPresent()){
            throw new BusinessException("There is already a registered PSP for the given CUIT");
        }
        myAccount=Optional.ofNullable(pspRepository.findByUsername(psp.getUsername()));
        if(myAccount.isPresent()){
            throw new BusinessException("There is already a registered PSP for the given Username");
        }
        myAccount=Optional.ofNullable(pspRepository.findByAccountCbu(psp.getCbu()));
        if(myAccount.isPresent()){
            throw new BusinessException("There is already a registered PSP for the given CBU");
        }
        return false;
    }
}
