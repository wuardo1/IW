package com.iw.application.data.service.account;

import com.iw.application.data.entity.account.BankAccountEntity;
import com.iw.application.data.entity.account.UserEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

// https://www.javaguides.net/2022/03/spring-boot-unit-testing-service-layer.html
class BankAccountServiceUnitTest {

    @Mock
    BankAccountRepository bankAccountRepository;

    @InjectMocks
    BankAccountService bankAccountService;

    @BeforeEach
    void setUp() {
        bankAccountRepository = Mockito.mock(BankAccountRepository.class);
        bankAccountService = new BankAccountService(bankAccountRepository);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createBankAccount() {
        UserEntity userEntity = new UserEntity();

        bankAccountService.setSeed(10);

        BankAccountEntity bankAccountEntity = bankAccountService.createBankAccount(userEntity);

        Assertions.assertThat(bankAccountEntity.getAccountNumber()).isEqualTo(5113);
        Assertions.assertThat(bankAccountEntity.getBalance()).isEqualTo(0);
        //Assertions.assertThat(bankAccountEntity.getUUID()).

    }
}