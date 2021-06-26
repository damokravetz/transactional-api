package edu.palermo.transactionalapi.repositories;

import edu.palermo.transactionalapi.models.Account;
import edu.palermo.transactionalapi.models.Psp;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository extends CrudRepository<Account, Long> {
    List<Account> findAll();
    Account findByCbu(String cbu);
}
