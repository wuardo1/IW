package com.iw.application.data.entity.account;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Random;
import java.util.UUID;

@Entity(name = "bank_account")
public class BankAccount {
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    private UUID bankAccountId;

    @ManyToOne
    private User user;

//    @OneToOne(
//            mappedBy = "bank_account",
//            cascade = CascadeType.REMOVE,
//            orphanRemoval = true,
//            fetch = FetchType.EAGER
//    )
//    private CreditCard creditCard;

    private float balance;
    private float creditLine;

    @GeneratedValue
    private int accountNumber;

    public BankAccount() {

    }

    public BankAccount(User user) {
        this.user = user;
        balance = 0;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

//    public CreditCard getCreditCard() {
//        return creditCard;
//    }
//
//    public void setCreditCard(CreditCard creditCard) {
//        this.creditCard = creditCard;
//    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public float getCreditLine() {
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
}
