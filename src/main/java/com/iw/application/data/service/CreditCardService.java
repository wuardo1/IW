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
    public static final String CREDIT_CARD_DETAILS_WRONG_TEXT = "The credit card details are invalid.";
    public static final String ILLEGAL_AMOUNT_TEXT = "The entered amount is not possible.";

    private Random random = new Random();

    private final CreditCardRepository creditCardRepository;

    private final UserService userService;

    private static final int DEFAULT_VALIDITY_IN_YEARS = 5;
    private static final int DEFAULT_LIMIT = 500;
    private static final int DEFAULT_DEBT = 0;
    private static final boolean DEFAULT_ACTIVE = true;
    private final BankAccountService bankAccountService;

    public CreditCardService (@Autowired CreditCardRepository creditCardRepository,
                              @Autowired UserService userService,
                              BankAccountService bankAccountService) {
        this.creditCardRepository = creditCardRepository;
        this.userService = userService;
        this.bankAccountService = bankAccountService;
    }

    public CreditCardEntity createCreditCard(BankAccountEntity bankAccount) {
        Date issueDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(issueDate);
        calendar.add(Calendar.YEAR, DEFAULT_VALIDITY_IN_YEARS);
        Date validityDate = calendar.getTime();
        CreditCardEntity creditCard = new CreditCardEntity(bankAccount, generateCardNumber(), generateCCV(),
                issueDate, validityDate, DEFAULT_LIMIT, DEFAULT_DEBT, DEFAULT_ACTIVE);
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

    public void setActive(CreditCardEntity creditCard, boolean active) {
        creditCard.setActive(active);
        creditCardRepository.save(creditCard);
    }

    public void setLimit(CreditCardEntity creditCard, double limit) {
        creditCard.setCardLimit(limit);
        creditCardRepository.save(creditCard);
    }

    public void makePayment(String cardNumber, int month, int year, String ccv, double amount, String type,
                            int token) {
        CreditCardEntity creditCard = creditCardRepository.findByCardNumber(cardNumber);
        Calendar validity = Calendar.getInstance();
        validity.setTime(creditCard.getValidityDate());

        if (validity.get(Calendar.YEAR) != year
        || validity.get(Calendar.MONTH) != month
        || !creditCard.getCcv().equals(ccv)) {
            throw new IllegalAccessError(CREDIT_CARD_DETAILS_WRONG_TEXT);
        }

        if (amount <= 0 || 50 < amount)
            throw new IllegalArgumentException(ILLEGAL_AMOUNT_TEXT);

        if (amount <= 10) {
            bankAccountService.makeCreditCardPayment(creditCard, amount);
        }
    }
}
