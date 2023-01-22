package com.iw.application.data.service;

import com.iw.application.data.entity.BankAccountEntity;
import com.iw.application.data.entity.CreditCardEntity;
import com.iw.application.data.repositories.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditCardService {

    CreditCardRepository creditCardRepository;

    public CreditCardService (@Autowired CreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }

    public void createCreditCard(BankAccountEntity bankAccount) {
        CreditCardEntity creditCard = new CreditCardEntity(bankAccount);
    }
    
//    public makePayment(CIban destinationIban) {
//
//    }

//    private int generateCCV() {
//        Random random = new Random();
//        return random.nextInt(100,1000);
//    }
//
//    private long generateCardNumber() {
//        Random random = new Random();
//        return random.nextLong(1000000000000000L,10000000000000000L);
//    }
    
}
