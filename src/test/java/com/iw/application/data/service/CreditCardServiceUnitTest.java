package com.iw.application.data.service;

import com.iw.application.data.entity.BankAccountEntity;
import com.iw.application.data.entity.CreditCardEntity;
import com.iw.application.data.repositories.CreditCardRepository;
import com.iw.application.service.CreditCardService;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.Random;

@SpringBootTest
class CreditCardServiceUnitTest {

    @Autowired
    private CreditCardService creditCardService;

    @MockBean
    private CreditCardRepository creditCardRepository;

    @BeforeEach
    void setUp() {
        creditCardService.setRandom(new Random(1));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createCreditCard() {
        BankAccountEntity bankAccount = ObjectMother.createTestBankAccount();
        CreditCardEntity creditCard = creditCardService.createCreditCard(bankAccount);
        Assertions.assertThat(creditCard.getCardNumber()).isEqualTo("5873444688937324");
        Assertions.assertThat(creditCard.getCcv()).isEqualTo("226");
        Date validity = creditCard.getValidityDate();
        Date issue = creditCard.getIssueDate();
        Assertions.assertThat(validity.getTime()-issue.getTime())
                .isCloseTo(157766400000L, Percentage.withPercentage(5));
    }
}