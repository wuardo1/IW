package com.iw.application.data.service.account;

import com.iw.application.data.entity.account.BankAccountEntity;
import com.iw.application.data.entity.account.UserEntity;
import com.iw.application.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

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

    public void addUser(UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userRepository.save(userEntity);
    }

    public void createBankAccount() {
        UserEntity userEntity = getCurrentUser();
        BankAccountEntity bankAccountEntity = bankAccountService.createBankAccount(userEntity);
        userEntity.addBankAccount(bankAccountEntity);
        userRepository.flush();
    }



    public UserEntity getCurrentUser() throws EntityNotFoundException {
        CustomUserDetails details =
                (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUserId(details.getUser().getUserId())
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_TEXT));
    }
}
