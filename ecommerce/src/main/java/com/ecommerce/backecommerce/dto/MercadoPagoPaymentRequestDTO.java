package com.ecommerce.backecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MercadoPagoPaymentRequestDTO {
    private List<ItemDTO> items;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemDTO {
        private Long id;
        private String nombre;
        private String descripcion;
        private Integer cantidad;
        private BigDecimal precio;
    }
}
