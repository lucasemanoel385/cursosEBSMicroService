package br.com.cursosEBS.users.amqp;

import br.com.cursosEBS.users.dto.UpdateProfileDTO;
import br.com.cursosEBS.users.service.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsersListener {

    @Autowired
    private UserService service;

    @RabbitListener(queues = "users.profile")
    public void updateStatusSubscription(UpdateProfileDTO dto) {

        service.updateProfile(dto);
    }
}
