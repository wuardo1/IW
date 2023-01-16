package com.iw.application.data.entity.account;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "credit_cards")
public class CreditCardEntity {
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    private int creditCardId;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "bank_account")
//    private BankAccount bankAccount;

    private int cardNumber;

    private int ccv;

    private LocalDate cardValidity;
}
