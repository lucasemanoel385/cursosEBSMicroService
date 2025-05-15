package br.com.cursosEBS.enrollments.amqp;

import br.com.cursosEBS.enrollments.dto.RegisterSubscriptionDTO;
import br.com.cursosEBS.enrollments.dto.UpdateStatusSubscriptionDTO;
import br.com.cursosEBS.enrollments.service.SubscriptionService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionListener {

    @Autowired
    private SubscriptionService service;

    @RabbitListener(queues = "payments.create")
    public void registerSubscription(RegisterSubscriptionDTO dto) throws MessagingException {

        service.registerSubscription(dto);
    }

    @RabbitListener(queues = "payments.status")
    public void updateStatusSubscription(UpdateStatusSubscriptionDTO dto) throws MessagingException {

        service.updateStatus(dto);
    }
}
