package com.ecommerce.backecommerce.controller;

import com.ecommerce.backecommerce.dto.MercadoPagoPaymentRequestDTO;
import com.ecommerce.backecommerce.dto.MercadoPagoPreferenceResponseDTO;
import com.ecommerce.backecommerce.service.MercadoPagoService;
import com.mercadopago.exceptions.MPApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class MercadoPagoController {

    private final MercadoPagoService mercadoPagoService;

    @PostMapping("/crear-preferencia")
    @CrossOrigin("*")
    public ResponseEntity<?> crearPreferencia(@RequestBody MercadoPagoPaymentRequestDTO requestDTO) {
        try {
            // Llama al servicio para crear la preferencia de pago
            String initPoint = mercadoPagoService.crearPreferencia(requestDTO);

            // Devuelve la URL de pago (init_point)
            return ResponseEntity.ok(new MercadoPagoPreferenceResponseDTO(initPoint));
        } catch (MPApiException e) {
            // Loguea el detalle de la respuesta de Mercado Pago
            String apiError = e.getApiResponse() != null ? e.getApiResponse().getContent() : "Sin detalle";
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "error", "Error de Mercado Pago: " + e.getMessage(),
                            "detalle", apiError
                    ));
        } catch (Exception e) {
            e.printStackTrace(); // Log completo del error
            // Retorna error 500 con mensaje para frontend
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "No se pudo generar la preferencia: " + e.getMessage()));
        }
    }
}
