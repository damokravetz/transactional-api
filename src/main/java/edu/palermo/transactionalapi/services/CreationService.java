package edu.palermo.transactionalapi.services;

import edu.palermo.transactionalapi.models.*;
import edu.palermo.transactionalapi.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
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
    @Autowired
    private AccountRepository accountRepository;

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
        if(!user.isUserDniValid()){
            throw new BusinessException("Dni is not valid");
        }
        if(!user.isUserPspIdValid()){
            throw new BusinessException("Client PspId not valid");
        }
        if(!userAlreadyExistsForPsp(user, psp)){
            String cvu= generateCVU(psp.getPspCode(), user.getUserPspId());
            user.setCvu(new Cvu(cvu,psp));
            return userRepository.save(user);
        }else {
            throw new BusinessException("Psp client already exists");
        }
    }

    public User editAlias(HttpServletRequest request, User user) {
        String currentUser = jwtAuthorizationFilter.getUser(request);
        Psp psp = pspRepository.findByUsername(currentUser);
        Optional<User> myUser = Optional.ofNullable(userRepository.findByUserPspIdAndCvuPspId(user.getUserPspId(), psp.getId()));
        if(myUser.isPresent()){
            Pattern lengthPattern = Pattern.compile(USER_ALIAS_LENGTH);
            Pattern charactersPattern = Pattern.compile(USER_ALIAS_CHARACTERS);
            Matcher lengthMatcher = lengthPattern.matcher(user.getAlias());
            Matcher charactersMatcher = charactersPattern.matcher(user.getAlias());
            if (lengthMatcher.matches()) {
                if(charactersMatcher.matches()){
                    Optional<User>aliasExists= Optional.ofNullable(userRepository.findByCvuAlias(user.getAlias()));
                    if(aliasExists.isPresent() && !aliasExists.get().getDni().equals(user.getDni())){
                        throw new BusinessException("Alias is already in use");
                    }else{
                        myUser.get().getCvu().setAlias(user.getAlias());
                        user = userRepository.save(myUser.get());
                    }
                }else{
                    throw new BusinessException("Alias contains invalid characters");
                }
            } else {
                throw new BusinessException("Alias must be 6 to 20 characters long");
            }
        }else {
            throw new BusinessException("User not valid for Psp");
        }
        return user;
    }

    private String generateCVU(String pspCode, String userID){
        //cambiar a que tire numeros random para identificar
        String myUserId;
        if(userID.length()<CLIENT_CODE_LENGTH){
            myUserId=String.format("%0"+CLIENT_CODE_LENGTH+"d", userID);
        }else{
            myUserId=userID;
        }
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
        if(!psp.isCbuValid()){
            throw new BusinessException("Cvu is not valid");
        }
        if(!psp.isCuitValid()){
            throw new BusinessException("Cuit is not valid");
        }
        Optional<Account> account= Optional.ofNullable(accountRepository.findByCbu(psp.getCbu()));
        if(!account.isPresent()){
            throw new BusinessException("Cbu does not exist");
        }
        pspAlreadyExists(psp);
        String myHash=accountService.getHash(psp.getPassword());
        psp.setPassword(myHash);
        generatePspCode(psp);
        psp.setTipoPSP(1);
        psp.setAccount(account.get());
        return pspRepository.save(psp);
    }

    public Account createAccount(Account account)throws IllegalArgumentException{
        if(!account.isCbuValid()){
            throw new BusinessException("Cbu not valid");
        }
        if(!account.isAmountValid()){
            throw new BusinessException("Amount must be greater or equal than cero");
        }
        Optional<Account> accountExists=Optional.ofNullable(accountRepository.findByCbu(account.getCbu()));
        if(accountExists.isPresent()){
            throw new BusinessException("Account already exists");
        }
        return accountRepository.save(account);
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

    public List<UserDTO> getUsers(HttpServletRequest request)throws IllegalArgumentException{
        String currentUser = jwtAuthorizationFilter.getUser(request);
        Psp psp = pspRepository.findByUsername(currentUser);
        List<User>users=userRepository.findByCvuPspId(psp.getId());
        List<UserDTO>usersResponse=new ArrayList<>();
        for (User u:users) {
            UserDTO userDTO=new UserDTO();
            userDTO.setId(u.getId());
            userDTO.setName(u.getName());
            userDTO.setDni(u.getDni());
            userDTO.setCvu(u.getCvu().getCvu());
            usersResponse.add(userDTO);
        }
        return usersResponse;
    }
}
