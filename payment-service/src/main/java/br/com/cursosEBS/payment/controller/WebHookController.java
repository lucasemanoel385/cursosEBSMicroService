package br.com.cursosEBS.payment.controller;

import br.com.cursosEBS.payment.dto.WebHookDTO;

import br.com.cursosEBS.payment.entity.StatusPayment;
import br.com.cursosEBS.payment.http.SubscriptionClient;
import br.com.cursosEBS.payment.http.UserClient;

import br.com.cursosEBS.payment.http.dto.RegisterSubscriptionDTO;
import br.com.cursosEBS.payment.http.service.WeebHookService;
import br.com.cursosEBS.payment.repository.PaymentRepository;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/webhook")
public class WebHookController {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private WeebHookService weebHookService;

    @Autowired
    private SubscriptionClient subscriptionClient;

    @Autowired
    private UserClient userClient;

    @PostMapping
    public String handleWebhook(@RequestBody WebHookDTO dto) throws MPException, MPApiException {

        weebHookService.registerPayment(dto);

        return "OK";
    }

}
