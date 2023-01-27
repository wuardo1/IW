package com.iw.application.data.entity;

import org.hibernate.annotations.Type;
import org.iban4j.Iban;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "transaction_id", nullable = false)
    @Type(type = "uuid-char")
    private UUID transactionId;

    private String sourceIban;

    private String destinationIban;

    private long dateExecuted;

    private double amount;

    @ManyToOne()
    @JoinColumn(name = "bank_account_id")
    private BankAccountEntity bankAccount;

    public TransactionEntity() {
    }

    public TransactionEntity(BankAccountEntity bankAccount, Date date, double amount) {
        this.bankAccount = bankAccount;
        this.dateExecuted = date.getTime();
        this.amount = amount;
    }

    public TransactionEntity(BankAccountEntity bankAccount, Iban sourceIban, Iban destinationIban, Date dateExecuted,
                             double amount) {
        this.sourceIban = sourceIban.toString();
        this.destinationIban = destinationIban.toString();
        this.amount = amount;
        this.dateExecuted = dateExecuted.getTime();
        this.bankAccount = bankAccount;
    }


    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public String getSourceIban() {
        return sourceIban;
    }

    public void setSourceIban(String sourceIban) {
        this.sourceIban = sourceIban;
    }

    public String getDestinationIban() {
        return destinationIban;
    }

    public void setDestinationIban(String destinationIban) {
        this.destinationIban = destinationIban;
    }

    public Date getDateExecuted() {
        return new Date(dateExecuted);
    }

    public void setDateExecuted(Date dateExecuted) {
        this.dateExecuted = dateExecuted.getTime();
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public BankAccountEntity getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccountEntity bankAccount) {
        this.bankAccount = bankAccount;
    }
}
