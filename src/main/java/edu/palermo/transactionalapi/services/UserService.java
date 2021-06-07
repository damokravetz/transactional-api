package edu.palermo.transactionalapi.services;

import edu.palermo.transactionalapi.models.User;
import edu.palermo.transactionalapi.repositories.UserRepository;
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
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private static final String md5="MD5";

    public Optional<User> login(String mail, String pass){
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
        Optional<User>user=Optional.ofNullable(userRepository.findByMailAndPassword(mail, myHash));
        if(user.isPresent()){
            user.get().setToken(getJWTToken(mail));
        }
        return user;
    }

    public String getJWTToken(String username) {
        String secretKey = "ABC123DEF456GHI789JKL101112SUBUIWBUIS73979273HODHIODS";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("fuelPayJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256).compact();

        return "Bearer " + token;
    }
}
