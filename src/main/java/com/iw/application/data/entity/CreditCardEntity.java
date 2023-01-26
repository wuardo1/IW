package com.iw.application.data.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "credit_cards")
public class CreditCardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "credit_card_id", nullable = false)
    @Type(type = "uuid-char")
    private UUID creditCardId;

    @OneToOne()
    @JoinColumn(name = "bank_account_id")
    private BankAccountEntity bankAccount;

    private String cardNumber;

    private String ccv;

    private long validityDate;

    private double currentDebt;

    private double limit;

    private long issueDate;

    public CreditCardEntity() {

    }

    public CreditCardEntity(BankAccountEntity bankAccount, String cardNumber, String ccv, Date issueDate,
                            Date validityDate, double limit, double currentDebt) {
        this.bankAccount = bankAccount;
        this.cardNumber = cardNumber;
        this.ccv = ccv;
        this.issueDate = issueDate.getTime();
        this.validityDate = validityDate.getTime();
        this.limit = limit;
        this.currentDebt = currentDebt;
    }


    public UUID getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(UUID creditCardId) {
        this.creditCardId = creditCardId;
    }

    public BankAccountEntity getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccountEntity bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCcv() {
        return ccv;
    }

    public void setCcv(String ccv) {
        this.ccv = ccv;
    }

    public Date getValidityDate() {
        return new Date(validityDate);
    }

    public void setValidityDate(Date validityDate) {
        this.validityDate = validityDate.getTime();
    }

    public double getCurrentDebt() {
        return currentDebt;
    }

    public void setCurrentDebt(double currentDebt) {
        this.currentDebt = currentDebt;
    }

    public Date getIssueDate() {
        return new Date(issueDate);
    }
}
