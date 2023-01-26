package com.iw.application.data.repositories;

import com.iw.application.data.entity.BankAccountEntity;
import com.iw.application.data.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID> {

    List<TransactionEntity> findAllByBankAccount(BankAccountEntity bankAccount);
}
