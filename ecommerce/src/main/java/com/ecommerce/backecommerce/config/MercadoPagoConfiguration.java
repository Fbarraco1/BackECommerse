package com.ecommerce.backecommerce.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import com.mercadopago.MercadoPagoConfig;

@Configuration
public class MercadoPagoConfiguration {

    @Value("${mercadopago.access.token}")
    private String accessToken;
    @PostConstruct
    public void init() {
        System.out.println(">>> MP TOKEN: " + accessToken);
        MercadoPagoConfig.setAccessToken(accessToken);
    }

}
