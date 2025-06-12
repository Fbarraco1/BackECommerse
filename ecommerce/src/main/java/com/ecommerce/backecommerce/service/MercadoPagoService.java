package com.ecommerce.backecommerce.service;

import com.ecommerce.backecommerce.dto.MercadoPagoPaymentRequestDTO;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.*;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MercadoPagoService {

    @Value("${mercadopago.access-token}")
    private String mercadoPagoAccessToken;

    public String crearPreferencia(MercadoPagoPaymentRequestDTO requestDTO) throws Exception {
        try {
            MercadoPagoConfig.setAccessToken(mercadoPagoAccessToken);

            List<PreferenceItemRequest> items = requestDTO.getItems().stream()
                    .map(itemDTO -> PreferenceItemRequest.builder()
                            .id(itemDTO.getId().toString())
                            .title(itemDTO.getNombre())
                            .description(itemDTO.getDescripcion())
                            .quantity(itemDTO.getCantidad())
                            .currencyId("ARS")
                            .unitPrice(itemDTO.getPrecio())
                            .build())
                    .collect(Collectors.toList());

            PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                    .success("http://localhost:5173/paymentSuccess")
                    .pending("http://localhost:5173/paymentPending")
                    .failure("http://localhost:5173/paymentFailure")
                    .build();

            List<PreferencePaymentTypeRequest> excludedPaymentTypes = List.of(
                    PreferencePaymentTypeRequest.builder().id("ticket").build()
            );

            PreferencePaymentMethodsRequest paymentMethods = PreferencePaymentMethodsRequest.builder()
                    .excludedPaymentTypes(excludedPaymentTypes)
                    .installments(1)
                    .build();

            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(items)
                    .backUrls(backUrls)
                    .paymentMethods(paymentMethods)
                    .autoReturn("approved")
                    .build();

            PreferenceClient client = new PreferenceClient();
            Preference preference = client.create(preferenceRequest);

            return preference.getInitPoint();
        } catch (Exception e) {
            throw new Exception("Error al crear la preferencia de pago: " + e.getMessage(), e);
        }
    }
}
