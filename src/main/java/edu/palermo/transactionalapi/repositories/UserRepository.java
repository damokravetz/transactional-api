package edu.palermo.transactionalapi.repositories;

import edu.palermo.transactionalapi.models.Account;
import edu.palermo.transactionalapi.models.Transaction;
import edu.palermo.transactionalapi.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findAll();
    User findByDni(String dni);
    User findByCvu(String cvu);
    User findTopByOrderByIdDesc();

}
