package com.ecommerce.backecommerce.controller;

import com.ecommerce.backecommerce.dto.MercadoPagoPaymentRequestDTO;
import com.ecommerce.backecommerce.dto.MercadoPagoPaymentResponseDTO;
import com.ecommerce.backecommerce.service.MercadoPagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")
public class MercadoPagoController {

    private final MercadoPagoService mercadoPagoService;

    @PostMapping("/crear-preferencia")
    @CrossOrigin("*")
    public ResponseEntity<?> crearPreferencia(@RequestBody MercadoPagoPaymentRequestDTO requestDTO) {
        try {
            // Obtenés el DTO con los datos de respuesta
            MercadoPagoPaymentResponseDTO respuesta = mercadoPagoService.crearPreferencia(requestDTO);

            // Retornás el objeto completo como JSON
            return ResponseEntity.ok().body(respuesta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}
