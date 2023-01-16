package com.iw.application.data.entity.account;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "bank_accounts")
public class BankAccountEntity {
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    private UUID bankAccountId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

//    @OneToOne(
//            mappedBy = "bank_account",
//            cascade = CascadeType.REMOVE,
//            orphanRemoval = true,
//            fetch = FetchType.EAGER
//    )
//    private CreditCard creditCard;

    private float balance;
    private float creditLine;

    private int accountNumber;

    public BankAccountEntity(UserEntity userEntity) {
        this.user = userEntity;
    }

    public BankAccountEntity(UserEntity userEntity, int accountNumber) {
        this.user = userEntity;
        this.balance = 0;
        this.accountNumber = accountNumber;
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

    public UUID getUUID() {
        return this.bankAccountId;
    }
}
