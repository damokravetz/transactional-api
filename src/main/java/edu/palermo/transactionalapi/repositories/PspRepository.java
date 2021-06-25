package edu.palermo.transactionalapi.repositories;

import edu.palermo.transactionalapi.models.Psp;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PspRepository extends CrudRepository<Psp, Long> {
    List<Psp> findAll();
    Psp findByUsernameAndPassword(String username, String pass);
    Psp findByUsername(String username);
    Psp findByAccountCbu(String cbu);
    Psp findByCuit(String cuit);
    Psp findTopByOrderByIdDesc();
    Psp findByPspCode(String pspCode);
}
