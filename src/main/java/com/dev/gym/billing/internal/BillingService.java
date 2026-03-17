package com.dev.gym.billing.internal;

import com.dev.gym.billing.domain.Status;
import com.dev.gym.billing.InvoicePaidEvent;
import com.dev.gym.billing.dto.InvoiceResponse;
import com.dev.gym.memberships.Member;
import com.dev.gym.memberships.MemberRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BillingService {

    private final BillingRepository billingRepository;
    private final MemberRepository memberRepository;
    private final ApplicationEventPublisher eventPublisher;


    @Transactional(readOnly = true)
    public List<InvoiceResponse> getAllInvoices(){
        return billingRepository.findAll().stream().map(i -> new InvoiceResponse(
                i.getId(),
                i.getMemberId(),
                i.getAmount(),
                i.getStatus()
        )).toList();

    }

    @Transactional
    public void payInvoiceById(UUID id){
        Invoice invoice = billingRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Id de factura no encontrado!"));

        String memberEmail = memberRepository.findById(invoice.getMemberId())
                .map(Member::getEmail)
                .orElseThrow(() -> new IllegalArgumentException("No encontrado"));

        invoice.setStatus(Status.PAID);
        billingRepository.save(invoice);

        eventPublisher.publishEvent(new InvoicePaidEvent(id, memberEmail ));
        log.warn("Invoice con id: " + id);
    }

    @Transactional
    public void payFailedById(UUID id){
        Invoice invoice = billingRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Error de pago!"));
        invoice.setStatus(Status.FAILED);

        billingRepository.save(invoice);
        log.warn("ERROR DE PAGO");

    }
}
