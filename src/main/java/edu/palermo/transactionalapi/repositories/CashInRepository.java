package edu.palermo.transactionalapi.repositories;

import edu.palermo.transactionalapi.models.CashIn;
import edu.palermo.transactionalapi.models.Transfer;
import org.springframework.data.repository.CrudRepository;

public interface CashInRepository extends CrudRepository<CashIn, Long> {

}
