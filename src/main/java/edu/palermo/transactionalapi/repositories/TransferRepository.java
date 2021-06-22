package edu.palermo.transactionalapi.repositories;

import edu.palermo.transactionalapi.models.Transaction;
import edu.palermo.transactionalapi.models.Transfer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransferRepository extends CrudRepository<Transfer, Long> {

}
