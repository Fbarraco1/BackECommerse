package com.ecommerce.backecommerce.service;

import com.ecommerce.backecommerce.dto.MercadoPagoPaymentRequestDTO;
import com.ecommerce.backecommerce.dto.MercadoPagoPaymentResponseDTO;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MercadoPagoService {

    @Value("${mercadopago.access.token}")
    private String mercadoPagoAccessToken;

    @Value("${app.frontend.url.success}")
    private String successUrl;

    @Value("${app.frontend.url.pending}")
    private String pendingUrl;

    @Value("${app.frontend.url.failure}")
    private String failureUrl;

    public MercadoPagoPaymentResponseDTO crearPreferencia(MercadoPagoPaymentRequestDTO requestDTO) throws Exception {
        try {
            MercadoPagoConfig.setAccessToken(mercadoPagoAccessToken);

            List<PreferenceItemRequest> items = requestDTO.getItems().stream()
                    .map(itemDTO -> PreferenceItemRequest.builder()
                            .id(itemDTO.getId().toString())
                            .title(itemDTO.getNombre())
                            .description(itemDTO.getDescripcion())
                            .quantity(itemDTO.getCantidad())
                            .currencyId("ARS")
                            // Aquí asegúrate que sea BigDecimal, no double:
                            .unitPrice(itemDTO.getPrecio())
                            .build())
                    .collect(Collectors.toList());

            System.out.println("Success URL: " + successUrl);
            System.out.println("Pending URL: " + pendingUrl);
            System.out.println("Failure URL: " + failureUrl);


            PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                    .success(successUrl)
                    .pending(pendingUrl)
                    .failure(failureUrl)
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

            return new MercadoPagoPaymentResponseDTO(preference.getId(), preference.getInitPoint());

        } catch (MPApiException apiEx) {
            System.out.println("❌ Error API MercadoPago:");
            System.out.println("Status code: " + apiEx.getStatusCode());
            System.out.println("Response body: " + apiEx.getApiResponse().getContent());
            throw new Exception("MercadoPago API error: " + apiEx.getApiResponse().getContent());
        } catch (MPException mpEx) {
            System.out.println("❌ Error SDK MercadoPago: " + mpEx.getMessage());
            throw new Exception("MercadoPago SDK error: " + mpEx.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Error general al crear preferencia: " + e.getMessage());
            throw new Exception("Error general al crear preferencia: " + e.getMessage());
        }
    }
}
