package com.dev.gym.billing;

import com.dev.gym.billing.domain.Status;
import com.dev.gym.billing.internal.BillingRepository;
import com.dev.gym.memberships.MemberRegisteredEvent;
import com.dev.gym.billing.internal.Invoice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class BillingListener {

    private final BillingRepository billingRepository;

    @ApplicationModuleListener
    public void onMemberRegistered(MemberRegisteredEvent event) {

        Invoice invoice = new Invoice(null, event.memberId(), event.price(), Status.PENDING, LocalDateTime.now(), null);
        billingRepository.save(invoice);
        log.warn("Invoice creado y guardado");

    }
}