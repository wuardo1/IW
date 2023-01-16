package com.iw.application.data.service.account;

import com.iw.application.data.entity.account.BankAccountEntity;
import com.iw.application.data.entity.account.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Random;

@Service
public class BankAccountService {
    private static final String BANK_ACCOUNT_NOT_FOUND_TEXT = "Bank Account with requested account number not found.";
    private static final String ILLEGAL_TRANSACTION_AMOUNT_TEXT = "The amount to be transferred is not allowed.";

    private static final int LOWER_BOUND_ACCOUNT_NUMBER = 1000;
    private static final int UPPER_BOUND_ACCOUNT_NUMBER = 9999;

    private Random random = new Random();

    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public void makeTransaction(int sourceAccountNumber, int destinationAccountNumber, double amount) throws Exception {
        BankAccountEntity sourceAccount = bankAccountRepository.findByAccountNumber(sourceAccountNumber)
                .orElseThrow(() -> new EntityNotFoundException(BANK_ACCOUNT_NOT_FOUND_TEXT));

        BankAccountEntity destinationAccount = bankAccountRepository.findByAccountNumber(destinationAccountNumber)
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

    public BankAccountEntity createBankAccount(UserEntity userEntity) {
        int accountNumber = accountNumberGenerator();
        bankAccountRepository.findByAccountNumber(accountNumber).ifPresent(e -> {throw new RuntimeException();}); // TODO
        BankAccountEntity bankAccountEntity = new BankAccountEntity(userEntity, accountNumber);
        bankAccountRepository.saveAndFlush(bankAccountEntity);
        return bankAccountEntity;
    }

    private int accountNumberGenerator() {
        // Generate number between 0 and upper bound - lower bound
        int number = random.nextInt(UPPER_BOUND_ACCOUNT_NUMBER - LOWER_BOUND_ACCOUNT_NUMBER + 1);

        // Shift range
        return number + LOWER_BOUND_ACCOUNT_NUMBER;
    }

    public void setSeed(int seed) {
        this.random = new Random(seed);
    }
}
