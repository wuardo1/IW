package com.iw.application.data.service.account;

import com.iw.application.data.entity.account.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByMail(String mail);

    Optional<User> findByUserId(UUID id);
}
