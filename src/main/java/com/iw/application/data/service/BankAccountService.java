package com.iw.application.data.service;

import com.iw.application.data.entity.BankAccountEntity;
import com.iw.application.data.entity.TransactionEntity;
import com.iw.application.data.entity.UserEntity;
import com.iw.application.data.repositories.BankAccountRepository;
import com.iw.application.data.repositories.TransactionRepository;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;

@Service
public class BankAccountService {
    private static final String BANK_ACCOUNT_NOT_FOUND_TEXT = "Bank Account with requested account number not found.";
    private static final String ILLEGAL_TRANSACTION_AMOUNT_TEXT = "The amount to be transferred is not allowed.";

    private final BankAccountRepository bankAccountRepository;
    private final TransactionRepository transactionRepository;

    public BankAccountService(@Autowired BankAccountRepository bankAccountRepository,
                              @Autowired TransactionRepository transactionRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.transactionRepository = transactionRepository;
    }


    public void makeTransaction(Iban sourceIban, Iban destinationIban, double amount) throws Exception {
        BankAccountEntity sourceAccount = getBankAccountByIban(sourceIban);

        BankAccountEntity destinationAccount = getBankAccountByIban(destinationIban);

        Date currentDate = new Date();
        TransactionEntity sourceTransaction = new TransactionEntity(sourceIban, destinationIban, currentDate, amount);
        TransactionEntity destinationTransaction = new TransactionEntity(sourceIban, destinationIban, currentDate, amount);

        if (sourceAccount.getAmountDeductible() < amount || amount < 0) { // TODO amount checker
            throw new Exception(ILLEGAL_TRANSACTION_AMOUNT_TEXT); // TODO change exception type
        }

        sourceAccount.reduceBalance(amount);
        sourceAccount.addTransaction(sourceTransaction);
        destinationAccount.increaseBalance(amount);
        destinationAccount.addTransaction(destinationTransaction);
        bankAccountRepository.save(sourceAccount);
        bankAccountRepository.save(destinationAccount);
        transactionRepository.save(sourceTransaction);
        transactionRepository.saveAndFlush(destinationTransaction);
    }

    public BankAccountEntity createBankAccount(UserEntity userEntity) {
        Iban iban = ibanGenerator();
        bankAccountRepository.findByIban(iban.toString()).ifPresent(e -> {throw new RuntimeException();}); // TODO
        BankAccountEntity bankAccountEntity = new BankAccountEntity(userEntity, iban);
        bankAccountRepository.saveAndFlush(bankAccountEntity);
        return bankAccountEntity;
    }

    public void removeBankAccount(BankAccountEntity bankAccount) {
        bankAccountRepository.delete(bankAccount);
        bankAccountRepository.flush();
    }

    private Iban ibanGenerator() {
        // Generate number between 0 and upper bound - lower bound
        //int number = random.nextInt(UPPER_BOUND_ACCOUNT_NUMBER - LOWER_BOUND_ACCOUNT_NUMBER + 1);

        // Shift range
        //return number + LOWER_BOUND_ACCOUNT_NUMBER;

        return Iban.random(CountryCode.ES);
    }

    public BankAccountEntity getBankAccountByIban(Iban iban) throws EntityNotFoundException {
        return bankAccountRepository.findByIban(iban.toString())
                .orElseThrow(() -> new EntityNotFoundException(BANK_ACCOUNT_NOT_FOUND_TEXT));
    }


    // tried to fix jpa session closing...
    public List<TransactionEntity> getTransactionsToBankAccount(BankAccountEntity bankAccount) {
        return null;
//        List<TransactionEntity> tr = transactionRepository.findAll();
//        return tr.stream().filter(transactionEntity ->
//                transactionEntity.getBankAccount().getIban() == bankAccount.getIban()).collect(Collectors.toList());
    }
}