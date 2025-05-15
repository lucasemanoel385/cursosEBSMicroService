package br.com.cursosEBS.payment.controller;

import br.com.cursosEBS.payment.dto.PreferenceDTO;
import br.com.cursosEBS.payment.service.PaymentService;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/create-preference")
    public ResponseEntity<PreferenceDTO> createPreference(@RequestParam String id) throws MPException, MPApiException {

        PreferenceDTO response = paymentService.processPayment(id);
        return ResponseEntity.ok(response);

    }
}
