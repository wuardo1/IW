package com.iw.application.security;

import com.iw.application.data.entity.account.User;
import com.iw.application.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityUserDetailsService implements UserDetailsService {
    private static final String ERROR_MSG = "User not found: ";

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> response = userRepository.findByMail(username);
        User user = response.orElseThrow(() -> new UsernameNotFoundException(ERROR_MSG + username));
        return new SecurityUserDetails(user);
    }

}
