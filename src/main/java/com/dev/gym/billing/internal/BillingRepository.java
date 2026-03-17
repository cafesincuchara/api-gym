package com.dev.gym.billing.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BillingRepository extends JpaRepository<Invoice, UUID> {
}
