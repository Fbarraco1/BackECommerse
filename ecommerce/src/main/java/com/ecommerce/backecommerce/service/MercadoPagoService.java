package com.ecommerce.backecommerce.service;

import com.ecommerce.backecommerce.entity.Producto;
import com.ecommerce.backecommerce.entity.OrdenDeCompra;
import com.mercadopago.client.preference.*;
import com.mercadopago.resources.preference.Preference;
import com.mercadopago.MercadoPagoConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class MercadoPagoService {

    @Value("${mercadopago.access.token}")
    private String accessToken;

    public String crearPreferenciaPago(OrdenDeCompra orden) throws Exception {
        MercadoPagoConfig.setAccessToken(accessToken);

        List<PreferenceItemRequest> items = new ArrayList<>();

        for (Producto p : orden.getProductos()) {
            PreferenceItemRequest item = PreferenceItemRequest.builder()
                    .id(p.getId().toString())
                    .title(p.getNombre())
                    .description(p.getDescripcion())
                    .quantity(1)
                    .currencyId("ARS")
                    .unitPrice(BigDecimal.valueOf(p.getPrecio()))
                    .build();
            items.add(item);
        }

        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                .success("http://localhost:5173/paymentSuccess")
                .pending("http://localhost:5173/")
                .failure("http://localhost:5173/paymentFailure")
                .build();

        List<PreferencePaymentTypeRequest> excluded = List.of(
                PreferencePaymentTypeRequest.builder().id("ticket").build()
        );

        PreferencePaymentMethodsRequest paymentMethods = PreferencePaymentMethodsRequest.builder()
                .excludedPaymentTypes(excluded)
                .installments(1)
                .build();

        PreferenceRequest request = PreferenceRequest.builder()
                .items(items)
                .backUrls(backUrls)
                .paymentMethods(paymentMethods)
                .autoReturn("approved")
                .build();

        PreferenceClient client = new PreferenceClient();
        Preference preference = client.create(request);

        return preference.getId(); // o preference.getInitPoint() si querés la URL
    }
}
