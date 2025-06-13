package com.ecommerce.backecommerce.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CrearOrdenDTO {
    private Long idDireccion;
    private List<ProductoCantidadDTO> productos;

    @Getter
    @Setter
    public static class ProductoCantidadDTO {
        private Long idProducto;
        private Long idTalle;
        private Integer cantidad;
    }
}
