package br.com.cursosEBS.payment.http;

import br.com.cursosEBS.payment.config.FeignClientConfig;
import br.com.cursosEBS.payment.entity.StatusPayment;
import br.com.cursosEBS.payment.http.dto.RegisterSubscriptionDTO;
import br.com.cursosEBS.payment.http.dto.SubscriptionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "enrollments-ms", configuration = FeignClientConfig.class, url = "http://localhost:8082/enrollments-ms")
public interface SubscriptionClient {

    @RequestMapping(method = RequestMethod.POST, value = "/subscription")
    SubscriptionDTO registerSubscription(RegisterSubscriptionDTO dto);

    @RequestMapping(method = RequestMethod.PATCH, value = "/status")
    ResponseEntity updateStatus(@RequestParam StatusPayment status, @RequestParam String id);
}
