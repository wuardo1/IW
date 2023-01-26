package com.iw.application.data.service;

import com.iw.application.data.entity.BankAccountEntity;
import com.iw.application.data.entity.CreditCardEntity;
import com.iw.application.data.repositories.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@Service
public class CreditCardService {

    private Random random = new Random();

    private final CreditCardRepository creditCardRepository;

    private final UserService userService;

    public CreditCardService (@Autowired CreditCardRepository creditCardRepository,
                              @Autowired UserService userService) {
        this.creditCardRepository = creditCardRepository;
        this.userService = userService;
    }

    public CreditCardEntity createCreditCard(BankAccountEntity bankAccount) {
        Date issueDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(issueDate);
        calendar.add(Calendar.YEAR, 5);
        Date validityDate = calendar.getTime();
        CreditCardEntity creditCard = new CreditCardEntity(bankAccount, generateCardNumber(), generateCCV(),
                issueDate, validityDate);
        creditCardRepository.save(creditCard);
        return creditCard;
    }

    private String generateCCV() {
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            int number = random.nextInt(10);
            cardNumber.append(number);
        }
        return cardNumber.toString();
    }

    private String generateCardNumber() {
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            int number = random.nextInt(10);
            cardNumber.append(number);
        }
        return cardNumber.toString();
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public CreditCardEntity getCreditCardToBankAccount(BankAccountEntity bankAccount) {
        return creditCardRepository.findByBankAccount(bankAccount);
    }
}
