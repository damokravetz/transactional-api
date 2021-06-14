package edu.palermo.transactionalapi.services;

import edu.palermo.transactionalapi.models.Account;
import edu.palermo.transactionalapi.repositories.AccountRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    private static final String md5="MD5";

    public Optional<Account> authorize(String username, String pass){
        String myHash = getHash(pass);
        Optional<Account>account=Optional.ofNullable(accountRepository.findByUsernameAndPassword(username, myHash));
        if(account.isPresent()){
            account.get().setToken(getJWTToken(username));
        }
        return account;
    }

    public String getHash(String pass) {
        String myHash="";
        System.out.println(pass);
        try{
            MessageDigest md = MessageDigest.getInstance(md5);
            md.update(pass.getBytes());
            byte[] digest = md.digest();
            myHash = DatatypeConverter
                    .printHexBinary(digest);
            System.out.println(myHash);

        }catch(NoSuchAlgorithmException exception){
            System.out.println(exception.getMessage());
        }
        return myHash;
    }

    public String getJWTToken(String username) {
        String secretKey = "ABC123DEF456GHI789JKL101112SUBUIWBUIS73979273HODHIODS";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256).compact();

        return "Bearer " + token;
    }
}
