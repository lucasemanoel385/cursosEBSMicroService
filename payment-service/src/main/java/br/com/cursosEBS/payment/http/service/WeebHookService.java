package br.com.cursosEBS.payment.http.service;

import br.com.cursosEBS.payment.dto.WebHookDTO;
import br.com.cursosEBS.payment.entity.StatusPayment;
import br.com.cursosEBS.payment.http.SubscriptionClient;
import br.com.cursosEBS.payment.http.UserClient;
import br.com.cursosEBS.payment.http.dto.RegisterSubscriptionDTO;
import br.com.cursosEBS.payment.http.dto.UpdateStatusSubscriptionDTO;
import br.com.cursosEBS.payment.repository.PaymentRepository;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class WeebHookService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void registerPayment(WebHookDTO dto) throws MPException, MPApiException {

        PaymentClient paymentClient = new PaymentClient();

        // Buscar o pagamento pelo ID
        Payment payment;

        try{
            payment = paymentClient.get(Long.valueOf(dto.data().id()));
        } catch (MPException e) {
            throw new MPException("Pagamento inexistente!!!");
        }

        switchStatusOrCreateOrderPayment(payment, payment.getStatus());
    }

    public void switchStatusOrCreateOrderPayment(Payment payment, String status) {
        var paymentRe = paymentRepository.findByPaidMarketId(payment.getId().toString());

        if (paymentRe.isPresent()) {

            if (Objects.equals(StatusPayment.APPROVED.name().toUpperCase(), status.toUpperCase())) {
                paymentRe.get().setStatus(StatusPayment.APPROVED);

            } else if (Objects.equals(StatusPayment.REFUNDED.name().toUpperCase(), status.toUpperCase())) {
                paymentRe.get().setStatus(StatusPayment.REFUNDED);

            } else {
                paymentRe.get().setStatus(StatusPayment.valueOf(status.toUpperCase()));
            }

            rabbitTemplate.convertAndSend("payments.sb", "payments.status",
                    new UpdateStatusSubscriptionDTO(paymentRe.get().getStatus(), paymentRe.get().getSubscriptionId()));

            paymentRepository.save(paymentRe.get());

        } else {
            var createPayment = new br.com.cursosEBS.payment.entity.Payment(Long.valueOf(
                    payment.getExternalReference()),
                    payment.getTransactionAmount().doubleValue(),
                    payment.getPaymentMethodId(),
                    StatusPayment.valueOf(status.toUpperCase()),
                    payment.getId().toString());

            rabbitTemplate.convertAndSend("payments.sb", "payments.create", new RegisterSubscriptionDTO(
                    Long.valueOf(payment.getExternalReference()), StatusPayment.valueOf(status.toUpperCase()), payment.getId().toString()));

            paymentRepository.save(createPayment);
        }





    }
}
