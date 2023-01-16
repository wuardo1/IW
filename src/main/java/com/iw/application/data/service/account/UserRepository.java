package com.iw.application.data.service.account;

import com.iw.application.data.entity.account.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByMail(String mail);

    Optional<UserEntity> findByUserId(UUID id);
}
