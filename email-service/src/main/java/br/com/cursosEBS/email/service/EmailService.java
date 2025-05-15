package br.com.cursosEBS.email.service;

import br.com.cursosEBS.email.dto.EmailDTO;
import br.com.cursosEBS.email.infra.messageConfig.MessageSourceConfig;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value(value = "${spring.mail.username}")
    private String emailFrom;

    @Autowired
    private MessageSourceConfig messages;

    @Value("${spring.url}")
    private String contextPath;

    public void sendEmail(EmailDTO dto) throws MessagingException {

        String url = "<p><a href='http://" + contextPath + "?token=" + dto.token() + "' style='font-weight: bold; text-decoration: underline; color: blue;'>"
                + contextPath + "?token=" + dto.token() + "</a></p>";

        String messageReceive = messages.messageSource().getMessage("message.resetPassword", null, null);

        String text = messageReceive + " \r\n" + url;

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(dto.user().email());
        helper.setSubject(dto.subject());
        helper.setText(text, true);
        mailSender.send(message);


    }
}
