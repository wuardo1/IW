package com.iw.application.data.service.account;

import com.iw.application.data.entity.BankAccountEntity;
import com.iw.application.data.entity.UserEntity;
import com.iw.application.data.repositories.BankAccountRepository;
import com.iw.application.data.repositories.TransactionRepository;
import com.iw.application.data.service.BankAccountService;
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

    @Mock
    TransactionRepository transactionRepository;

    @InjectMocks
    BankAccountService bankAccountService;

    @BeforeEach
    void setUp() {
        bankAccountRepository = Mockito.mock(BankAccountRepository.class);
        transactionRepository = Mockito.mock(TransactionRepository.class);
        bankAccountService = new BankAccountService(bankAccountRepository, transactionRepository);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createBankAccount() {
        UserEntity userEntity = new UserEntity();

        BankAccountEntity bankAccountEntity = bankAccountService.createBankAccount(userEntity);

        Assertions.assertThat(bankAccountEntity.getBalance()).isEqualTo(0);
        // TODO further tests
    }

    @Test
    void makeTransaction() throws Exception {
        /*
        Iban a = Iban.valueOf("ES1220389717824292519288");
        Iban b = Iban.valueOf("ES7131904395848711939975");
        bankAccountService.makeTransaction(Iban.valueOf("ES1220389717824292519288"),
                Iban.valueOf("ES7131904395848711939975"), 5);
        Assertions.assertThat(bankAccountService.getBankAccountByIban(a).getBalance()).isEqualTo(45);
        Assertions.assertThat(bankAccountService.getBankAccountByIban(b).getBalance()).isEqualTo(15);
         */
    }
}