package com.dev.gym.billing.web;

import com.dev.gym.billing.dto.InvoiceResponse;
import com.dev.gym.billing.internal.BillingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final BillingService billingService;

    @GetMapping
    public ResponseEntity<List<InvoiceResponse>> getAllInvoices(){
        List<InvoiceResponse> responses = billingService.getAllInvoices();
        return ResponseEntity.ok(responses);
    }


    @PatchMapping("/{id}/pay")
    public ResponseEntity<Void> payInvoiceById(@PathVariable UUID id){
        billingService.payInvoiceById(id);
        return ResponseEntity.noContent().build();
    }



}
