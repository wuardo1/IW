package com.iw.application.data.entity;

import org.hibernate.annotations.Type;
import org.iban4j.Iban;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transactions")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "transaction_id", nullable = false)
    @Type(type = "uuid-char")
    private Long transactionId;

    private String sourceIban;

    private String destinationIban;

    private long dateExecuted;

    private double amount;

    public TransactionEntity() {
    }

    public TransactionEntity(Iban sourceIban, Iban destinationIban, Date dateExecuted, double amount) {
        this.sourceIban = sourceIban.toString();
        this.destinationIban = destinationIban.toString();
        this.amount = amount;
        this.dateExecuted = dateExecuted.getTime();
    }


    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
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
}
