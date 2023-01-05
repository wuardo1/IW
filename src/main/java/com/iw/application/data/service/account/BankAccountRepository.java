package com.iw.application.data.service.account;

import com.iw.application.data.entity.account.BankAccount;
import com.iw.application.data.entity.account.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {
    Optional<BankAccount> findByAccountNumber(int accountNumber);

}
