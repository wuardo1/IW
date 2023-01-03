package com.iw.application.data.entity.account;

import com.iw.application.data.entity.AbstractEntity;

import javax.persistence.Entity;

@Entity
public class BankAccount extends AbstractEntity {

// TODO
//    @ManyToOne
    private User user;

    private double balance;
}
