package edu.palermo.transactionalapi.repositories;

import edu.palermo.transactionalapi.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findAll();
    User findByUserPspIdAndCvuPspId(String userPspId, Long pspId);
    User findByCvuCvu(String cvu);
    User findByDni(String dni);
    User findByCvuCvuAndCvuPspId(String cvu, Long idPsp);
    User findByCvuAlias(String alias);
    User findTopByOrderByIdDesc();

}
