package com.iw.application.data.repositories;

import com.iw.application.data.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID> {
    //List<TransactionEntity> all(BankAccountEntity bankAccount);

}
