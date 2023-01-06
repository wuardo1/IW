package com.iw.application.data.service.account;

import com.iw.application.data.entity.account.BankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class BankAccountService {
    private static final String BANK_ACCOUNT_NOT_FOUND_TEXT = "Bank Account with requested account number not found.";
    private static final String ILLEGAL_TRANSACTION_AMOUNT_TEXT = "The amount to be transferred is not allowed.";
    @Autowired
    private BankAccountRepository bankAccountRepository;

    public void makeTransaction(int sourceAccountNumber, int destinationAccountNumber, double amount) throws Exception {
        BankAccount sourceAccount = bankAccountRepository.findByAccountNumber(sourceAccountNumber)
                .orElseThrow(() -> new EntityNotFoundException(BANK_ACCOUNT_NOT_FOUND_TEXT));

        BankAccount destinationAccount = bankAccountRepository.findByAccountNumber(destinationAccountNumber)
                .orElseThrow(() -> new EntityNotFoundException(BANK_ACCOUNT_NOT_FOUND_TEXT));

        if (sourceAccount.getAmountDeductible() < amount) {
            throw new Exception(ILLEGAL_TRANSACTION_AMOUNT_TEXT); // TODO change exception type
        } else {
            sourceAccount.reduceBalance(amount);
            destinationAccount.increaseBalance(amount);
            bankAccountRepository.save(sourceAccount);
            bankAccountRepository.save(destinationAccount);
        }
    }
}
