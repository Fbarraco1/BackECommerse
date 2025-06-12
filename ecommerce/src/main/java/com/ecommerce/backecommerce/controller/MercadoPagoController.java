package com.ecommerce.backecommerce.controller;

import com.ecommerce.backecommerce.dto.MercadoPagoPaymentRequestDTO;
import com.ecommerce.backecommerce.service.MercadoPagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class MercadoPagoController {

    private final MercadoPagoService mercadoPagoService;

    @PostMapping("/crear-preferencia")
    @CrossOrigin("*")
    public ResponseEntity<?> crearPreferencia(@RequestBody MercadoPagoPaymentRequestDTO requestDTO) {
        try {
            String initPoint = mercadoPagoService.crearPreferencia(requestDTO);
            return ResponseEntity.ok().body("{\"initPoint\": \"" + initPoint + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"No se pudo generar la preferencia: " + e.getMessage() + "\"}");
        }
    }
}
