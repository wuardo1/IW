package com.iw.application.data.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "payment_id", nullable = false)
    @Type(type = "uuid-char")
    private UUID paymentId;

    private int token;

    private UUID creditCardId;

    private double amount;

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }


}
