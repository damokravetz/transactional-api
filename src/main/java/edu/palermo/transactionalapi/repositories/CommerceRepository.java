package edu.palermo.transactionalapi.repositories;

import edu.palermo.transactionalapi.models.Commerce;
import edu.palermo.transactionalapi.models.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommerceRepository extends CrudRepository<Commerce, Long> {

    List<Commerce> findAll();
    Commerce findByCuit(String cuit);
}
