package com.iw.application.data.service;

import com.iw.application.data.entity.BankAccountEntity;
import com.iw.application.data.entity.UserEntity;
import com.iw.application.data.entity.UserGroupEntity;
import org.iban4j.Iban;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class ObjectMother {
    public static BankAccountEntity createTestBankAccount() {

        Collection<UserGroupEntity> groups = new ArrayList<>();
        groups.add(UserGroupEntity.ROLE_USER);
        UserEntity user = new UserEntity("mail@test.de", "Test1234", groups);
        UUID id = UUID.fromString("f6d5bc5f-43c6-46b6-8110-d2a6e58dc364");
        Iban iban = Iban.valueOf("ES5220809453885749891693");
        return new BankAccountEntity(user, iban, 50, null, null, id, 0);
    }
}
