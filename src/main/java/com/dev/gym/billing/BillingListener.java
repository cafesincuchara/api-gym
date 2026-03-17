package com.dev.gym.billing;

import com.dev.gym.billing.domain.Status;
import com.dev.gym.billing.internal.BillingRepository;
import com.dev.gym.memberships.MemberRegisteredEvent;
import com.dev.gym.billing.internal.Invoice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class BillingListener {

    private final BillingRepository billingRepository;

    @ApplicationModuleListener
    public void onMemberRegistered(MemberRegisteredEvent event) {


       BigDecimal amount = switch (event.planName()){
           case "PREMIUM" -> new BigDecimal("7.900");
           case "VIP" -> new BigDecimal("15.000");
           case "BASIC" -> new BigDecimal("5.000");

           default -> new BigDecimal("2.000");
       };
       Invoice invoice = new Invoice(null, event.memberId(), amount, Status.PENDING);
        log.warn("Invoice creado");
       billingRepository.save(invoice);

    }
}