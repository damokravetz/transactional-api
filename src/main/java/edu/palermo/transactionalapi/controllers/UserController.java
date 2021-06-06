package edu.palermo.transactionalapi.controllers;

import edu.palermo.transactionalapi.models.User;
import edu.palermo.transactionalapi.models.Status;
import edu.palermo.transactionalapi.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public User login(@RequestBody User user) {
        Optional<User> user1=userService.login(user.getMail(), user.getPassword());
        if(user1.isPresent()){
            String token=getJWTToken(user.getDni());
            user.setToken(token);
        }else{
        }
        return user;
    }

    private String getJWTToken(String username) {
        String secretKey = "ABC123DEF456GHI789JKL101112SUBUIWBUIS73979273HODHIODS";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()),SignatureAlgorithm.HS256).compact();

        return "Bearer " + token;
    }
}
