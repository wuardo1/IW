package com.iw.application.data.service;

import com.iw.application.data.entity.BankAccountEntity;
import com.iw.application.data.entity.UserEntity;
import com.iw.application.data.repositories.BankAccountRepository;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class BankAccountService {
    private static final String BANK_ACCOUNT_NOT_FOUND_TEXT = "Bank Account with requested account number not found.";
    private static final String ILLEGAL_TRANSACTION_AMOUNT_TEXT = "The amount to be transferred is not allowed.";
    private static final String SAME_ACCOUNT_TRANSACTION_TEXT = "Source and destination accounts must be different.";
    private static final String ACCOUNT_HAS_TRANSACTIONS_TEXT = "Bank account has transactions and cannot be removed.";
    private static final String NULL_IBAN_TEXT = "IBAN cannot be null.";
    private static final String NULL_ACCOUNT_TEXT = "Bank account cannot be null.";

    private final BankAccountRepository bankAccountRepository;
    private final TransactionService transactionService;

    public BankAccountService(@Autowired BankAccountRepository bankAccountRepository,
                              @Autowired TransactionService transactionService) {
        this.bankAccountRepository = bankAccountRepository;
        this.transactionService = transactionService;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void makeTransaction(Iban sourceIban, Iban destinationIban, double amount) throws IllegalArgumentException {
        BankAccountEntity sourceAccount = getBankAccountByIban(sourceIban);

        BankAccountEntity destinationAccount = getBankAccountByIban(destinationIban);

        if (sourceAccount.getAmountDeductible() < amount || amount < 0) {
            throw new IllegalArgumentException(ILLEGAL_TRANSACTION_AMOUNT_TEXT);
        }

        sourceAccount.addTransaction(transactionService.addTransaction(
                sourceAccount, sourceAccount.getIban(), destinationIban, amount));

        destinationAccount.addTransaction(transactionService.addTransaction(
                destinationAccount, sourceIban, destinationAccount.getIban(), amount));

        sourceAccount.editBalance(-amount);
        bankAccountRepository.save(sourceAccount);

        destinationAccount.editBalance(amount);
        bankAccountRepository.save(destinationAccount);
    }

    public BankAccountEntity createBankAccount(UserEntity userEntity) {
        Iban iban = generateNewIban();

        BankAccountEntity bankAccountEntity = new BankAccountEntity(userEntity, iban);
        bankAccountRepository.save(bankAccountEntity);
        return bankAccountEntity;
    }

    public void removeBankAccount(BankAccountEntity bankAccount) {
        bankAccountRepository.delete(bankAccount);
        bankAccountRepository.flush();
    }

    private Iban generateNewIban() {
        Iban iban;
        do {
            iban = Iban.random(CountryCode.ES);
        } while (bankAccountRepository.findByIban(iban.toString()).isPresent());
        return iban;
    }

    public BankAccountEntity getBankAccountByIban(Iban iban) throws EntityNotFoundException {
        return bankAccountRepository.findByIban(iban.toString())
                .orElseThrow(() -> new EntityNotFoundException(BANK_ACCOUNT_NOT_FOUND_TEXT));
    }
}
