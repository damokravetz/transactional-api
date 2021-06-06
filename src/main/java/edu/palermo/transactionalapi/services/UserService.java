package edu.palermo.transactionalapi.services;

import edu.palermo.transactionalapi.models.User;
import edu.palermo.transactionalapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

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
        return Optional.ofNullable(userRepository.findByMailAndPassword(mail, myHash));
    }
}
