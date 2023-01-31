package com.iw.application.data.repositories;

import com.iw.application.data.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentTokenRepository extends JpaRepository<PaymentEntity, UUID> {
    public PaymentEntity findByOperationId(String operationId);
}
