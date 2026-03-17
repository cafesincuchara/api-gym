package com.dev.gym.notifications.internal;


import com.dev.gym.billing.InvoicePaidEvent;
import com.dev.gym.memberships.MemberRegisteredEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificationConsumer {

    @RabbitListener(queues = "member.registration.queue")
    public void consume(MemberRegisteredEvent event) {
        log.info("#### MIEMBRO EN COLA PARA PAGO ####");
        log.info("Correo del miembro: {} | Plan: {}", event.email(), event.planName());
        log.info("#########################################");
    }

    @RabbitListener(queues = "invoice.paid.queue")
    public void consumePayment(InvoicePaidEvent event) {
        log.info("$$$$ PAGO CONFIRMADO $$$$");
        log.info("Factura ID: {} enviada por {}", event.invoiceId(), event.memberEmail());
        log.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
    }
}
