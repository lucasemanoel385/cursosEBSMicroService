package br.com.cursosEBS.email.amqp;

import br.com.cursosEBS.email.dto.EmailDTO;
import br.com.cursosEBS.email.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailListener {

    @Autowired
    private EmailService service;

    @RabbitListener(queues = "email.send-token")
    public void receiveEmailMessageResetPassword(EmailDTO email) throws MessagingException {

        service.sendEmail(email);
    }
}
