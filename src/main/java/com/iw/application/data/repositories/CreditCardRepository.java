package com.iw.application.data.repositories;

import com.iw.application.data.entity.CreditCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CreditCardRepository extends JpaRepository<CreditCardEntity, UUID> {
}
