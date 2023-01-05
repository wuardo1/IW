package com.iw.application.data.service.account;

import com.iw.application.data.entity.account.BankAccount;
import com.iw.application.data.entity.account.User;
import com.iw.application.security.CustomUserDetails;
import org.atmosphere.interceptor.AtmosphereResourceStateRecovery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private BankAccountRepository bankAccountRepository;

    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void createBankAccount() {
        CustomUserDetails details =
                (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUserId(details.getUser().getUserId()).get();
        BankAccount bankAccount = new BankAccount(user);
        bankAccountRepository.save(bankAccount);
        userRepository.save(user);
    }
}
