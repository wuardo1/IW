package com.iw.application.data.entity;

import org.hibernate.annotations.Type;
import org.iban4j.Iban;

import javax.persistence.*;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "bank_accounts")
public class BankAccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bank_account_id", nullable = false)
    @Type(type = "uuid-char")
    private UUID bankAccountId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(fetch = FetchType.LAZY)
    private Collection<TransactionEntity> transactions;

//    @OneToOne(
//            mappedBy = "bankAccount",
//            cascade = CascadeType.REMOVE,
//            orphanRemoval = true,
//            fetch = FetchType.EAGER
//    )
//    private CreditCardEntity creditCard;

    private double balance;
    private double creditLine;

    private String iban;

    public BankAccountEntity(UserEntity userEntity) {
        this.user = userEntity;
    }

    public BankAccountEntity(UserEntity userEntity, Iban iban) {
        this.user = userEntity;
        this.balance = 0;
        this.iban = iban.toString();
    }

    public BankAccountEntity() {

    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity userEntity) {
        this.user = userEntity;
    }

//    public CreditCard getCreditCard() {
//        return creditCard;
//    }
//
//    public void setCreditCard(CreditCard creditCard) {
//        this.creditCard = creditCard;
//    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public Iban getIban() {
        return Iban.valueOf(iban);
    }

    public void setIban(Iban iban) {
        this.iban = iban.toString();
    }

    public double getCreditLine() {
        return creditLine;
    }

    public void setCreditLine(float creditLine) {
        this.creditLine = creditLine;
    }

    public double getAmountDeductible() {
        return balance + creditLine; // TODO debt ??
    }

    public void reduceBalance(double amount) {
        balance -= amount;
    }

    public void increaseBalance(double amount) {
        balance += amount;
    }

    public UUID getUUID() {
        return this.bankAccountId;
    }

    public void addTransaction(TransactionEntity transactionEntity) {
        transactions.add(transactionEntity);
    }

    public Collection<TransactionEntity> getTransactions() {
        return this.transactions;
    }

    public UUID getBankAccountId() {
        return bankAccountId;
    }
}
