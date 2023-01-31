package com.iw.application.service;

import com.iw.application.data.entity.BankAccountEntity;
import com.iw.application.data.entity.UserEntity;
import com.iw.application.data.entity.UserGroupEntity;
import com.iw.application.data.repositories.UserRepository;
import com.iw.application.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserService {

    private final static String USER_NOT_FOUND_TEXT = "The requested user was not found.";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BankAccountService bankAccountService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       BankAccountService bankAccountService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.bankAccountService = bankAccountService;
    }

    public Collection<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public void addUser(String mail, String password) {

        Collection<UserGroupEntity> roles = new ArrayList<>();
        roles.add(UserGroupEntity.ROLE_USER);

        UserEntity user = new UserEntity(mail, passwordEncoder.encode(password), roles);
        userRepository.save(user);
    }

    public void addAdmin(String mail, String password) {
        Collection<UserGroupEntity> roles = new ArrayList<>();
        roles.add(UserGroupEntity.ROLE_ADMIN);

        UserEntity admin = new UserEntity(mail, passwordEncoder.encode(password), roles);
        userRepository.save(admin);
    }

    public void createBankAccount() {
        UserEntity userEntity = getCurrentUser();
        BankAccountEntity bankAccountEntity = bankAccountService.createBankAccount(userEntity);
        userEntity.addBankAccount(bankAccountEntity);
        userRepository.saveAndFlush(userEntity);
    }

    public UserEntity getCurrentUser() throws EntityNotFoundException {
        CustomUserDetails details =
                (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUserId(details.getUser().getUserId())
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_TEXT));
    }
}
