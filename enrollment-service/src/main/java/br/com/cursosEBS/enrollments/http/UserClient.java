package br.com.cursosEBS.enrollments.http;

import br.com.cursosEBS.enrollments.config.FeignClientConfig;

import br.com.cursosEBS.enrollments.http.dto.UsersDTO;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.*;

@FeignClient(value = "users-ms", configuration = FeignClientConfig.class)
public interface UserClient {

    @RequestMapping(method = RequestMethod.GET, value = "/user/{id}")
    UsersDTO getUserById(@PathVariable @NotNull Long id);
}

