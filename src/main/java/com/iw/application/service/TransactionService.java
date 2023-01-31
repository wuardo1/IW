package com.iw.application.service;

import com.iw.application.data.entity.BankAccountEntity;
import com.iw.application.data.entity.TransactionEntity;
import com.iw.application.data.repositories.TransactionRepository;
import org.iban4j.Iban;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TransactionService {

    TransactionRepository transactionRepository;

    public TransactionService(@Autowired TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public TransactionEntity addTransaction(BankAccountEntity bankAccount, Iban sourceIban, Iban destinationIban,
                                             double amount) {
        Date currentDate = new Date();
        TransactionEntity transaction = new TransactionEntity(bankAccount, sourceIban,
                destinationIban, currentDate, amount);
        return transactionRepository.save(transaction);
    }

    public TransactionEntity addPayment(BankAccountEntity bankAccount, double amount) {
        Date currentDate = new Date();
        TransactionEntity transaction = new TransactionEntity(bankAccount, currentDate, amount);
        return transactionRepository.save(transaction);
    }

    public TransactionEntity addWithdrawalOrDeposit(BankAccountEntity bankAccount, double amount) {
        Date currentDate = new Date();
        TransactionEntity transaction = new TransactionEntity(bankAccount, currentDate, amount);
        return transactionRepository.save(transaction);
    }

    public List<TransactionEntity> getTransactionsToBankAccount(BankAccountEntity bankAccount) {
        return transactionRepository.findAllByBankAccount(bankAccount);
    }
}
