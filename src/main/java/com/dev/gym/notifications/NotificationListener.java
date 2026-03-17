package com.dev.gym.notifications;


import com.dev.gym.billing.InvoicePaidEvent;
import com.dev.gym.memberships.MemberRegisteredEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationListener {

    private final RabbitTemplate template;
    private static final String QUEUE_NAME = "member.registration.queue";

    @ApplicationModuleListener
    public void onMemberRegistered(MemberRegisteredEvent event){
        log.warn("usuario: " + event.email() + " en cola");
        template.convertAndSend(QUEUE_NAME, event);
    }

    @ApplicationModuleListener
    public void onInvoicePaid(InvoicePaidEvent event) {
        log.info("Enviando comprobante de pago a RabbitMQ para: {}", event.memberEmail());
        template.convertAndSend("invoice.paid.queue", event);
    }
}
