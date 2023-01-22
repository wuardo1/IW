package com.iw.application.data.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "credit_cards")
public class CreditCardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "credit_card_id", nullable = false)
    @Type(type = "uuid-char")
    private int creditCardId;

    @OneToOne()
    @JoinColumn(name = "bank_account_id")
    private BankAccountEntity bankAccount;



    private long cardNumber;

    private int ccv;

    private LocalDate cardValidity;

    private double currentDebt;

    public CreditCardEntity() {

    }

    public CreditCardEntity(BankAccountEntity bankAccount) {
        this.bankAccount = bankAccount;

    }

    public int getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(int creditCardId) {
        this.creditCardId = creditCardId;
    }

    public BankAccountEntity getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccountEntity bankAccount) {
        this.bankAccount = bankAccount;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getCcv() {
        return ccv;
    }

    public void setCcv(int ccv) {
        this.ccv = ccv;
    }

    public LocalDate getCardValidity() {
        return cardValidity;
    }

    public void setCardValidity(LocalDate cardValidity) {
        this.cardValidity = cardValidity;
    }

    public double getCurrentDebt() {
        return currentDebt;
    }

    public void setCurrentDebt(double currentDebt) {
        this.currentDebt = currentDebt;
    }
}
