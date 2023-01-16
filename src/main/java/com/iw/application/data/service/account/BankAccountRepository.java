package com.iw.application.data.service.account;

import com.iw.application.data.entity.account.BankAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BankAccountRepository extends JpaRepository<BankAccountEntity, UUID> {
    Optional<BankAccountEntity> findByAccountNumber(int accountNumber);

}
