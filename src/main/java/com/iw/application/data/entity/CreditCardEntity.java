//package com.iw.application.data.entity;
//
//import org.hibernate.annotations.Type;
//
//import javax.persistence.*;
//import java.time.LocalDate;
//
//@Entity
//@Table(name = "credit_cards")
//public class CreditCardEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "credit_card_id", nullable = false)
//    @Type(type = "uuid-char")
//    private int creditCardId;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "bank_account")
//    private BankAccountEntity bankAccount;
//
//    private int cardNumber;
//
//    private int ccv;
//
//    private LocalDate cardValidity;
//
//    private double currentDebt;
//}
