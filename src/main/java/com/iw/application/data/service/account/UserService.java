package com.iw.application.data.service.account;

import com.iw.application.data.entity.account.BankAccount;
import com.iw.application.data.entity.account.User;
import com.iw.application.security.CustomUserDetails;
import org.atmosphere.interceptor.AtmosphereResourceStateRecovery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class UserService {

    private final static String USER_NOT_FOUND_TEXT = "The requested user was not found.";
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private BankAccountRepository bankAccountRepository;

    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void createBankAccount() {
        User user = getCurrentUser();
        BankAccount bankAccount = new BankAccount(user);
        bankAccountRepository.save(bankAccount);
        userRepository.save(user);
    }



    public User getCurrentUser() throws EntityNotFoundException {
        CustomUserDetails details =
                (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUserId(details.getUser().getUserId())
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_TEXT));
    }
}
