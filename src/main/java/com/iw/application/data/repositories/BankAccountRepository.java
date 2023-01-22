package com.iw.application.data.repositories;

import com.iw.application.data.entity.BankAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BankAccountRepository extends JpaRepository<BankAccountEntity, UUID> {
    Optional<BankAccountEntity> findByIban(String iban);

}
