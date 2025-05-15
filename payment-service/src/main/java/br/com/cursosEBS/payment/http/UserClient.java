package br.com.cursosEBS.payment.http;

import br.com.cursosEBS.payment.config.FeignClientConfig;
import br.com.cursosEBS.payment.entity.StatusPayment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "users-ms", configuration = FeignClientConfig.class)
public interface UserClient {

    @RequestMapping(method = RequestMethod.PATCH, value = "/update-profile")
    ResponseEntity updateProfile(@RequestParam Long id, @RequestParam StatusPayment profile);
}

