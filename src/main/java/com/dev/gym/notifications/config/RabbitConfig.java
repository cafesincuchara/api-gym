package com.dev.gym.notifications.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String PAID_INVOICE_QUEUE = "invoice.paid.queue";

    @Bean
    public Queue registrationQueue() {
        return new Queue("member.registration.queue", true);
    }
    @Bean
    public Queue paidInvoiceQueue() {
        return new Queue(PAID_INVOICE_QUEUE, true);
    }
}