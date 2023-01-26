package com.iw.application.data.entity;

import org.hibernate.annotations.Type;
import org.iban4j.Iban;

import javax.persistence.*;
import java.util.Set;
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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "bankAccount")
    private Set<TransactionEntity> transactions;

    @OneToOne(
            mappedBy = "bankAccount",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private CreditCardEntity creditCard;

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

    public BankAccountEntity(UserEntity user, Iban iban, double balance, CreditCardEntity creditCard,
                             Set<TransactionEntity> transactions, UUID id, double creditLine) {
        this.user = user;
        this.iban = iban.toString();
        this.balance = balance;
        this.creditCard = creditCard;
        this.transactions = transactions;
        this.bankAccountId = id;
        this.creditLine = creditLine;
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


    public void editBalance(double amount) {
        balance += amount;
    }

    public UUID getUUID() {
        return this.bankAccountId;
    }

    public void addTransaction(TransactionEntity transactionEntity) {
        transactions.add(transactionEntity);
    }

    public Set<TransactionEntity> getTransactions() {
        return this.transactions;
    }

    public UUID getBankAccountId() {
        return bankAccountId;
    }
}
