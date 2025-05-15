package br.com.cursosEBS.payment.config;

import com.mercadopago.MercadoPagoConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class MercadoPaymentConfig {

    //Token do mercadopago
    @Value("${mercadopago.access.token}")
    private String accessToken;

    //Inicializar depois de injetar as dependencias
    @PostConstruct
    public void init() {
        MercadoPagoConfig.setAccessToken(accessToken);
    }
}
