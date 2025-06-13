package com.ecommerce.backecommerce.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class OrdenCompraResponseDTO {
    private Long id;
    private LocalDate fecha;
    private UsuarioBasicoDTO usuario;
    private DireccionDTO direccionEntrega;
    private List<DetalleOrdenDTO> detalle;

    @Getter
    @Setter
    public static class UsuarioBasicoDTO {
        private Long id;
        private String nombre;
        private String email;
    }

    @Getter
    @Setter
    public static class DireccionDTO {
        private Long id;
        private String calle;
        private String localidad;
        private String cp;
    }

    @Getter
    @Setter
    public static class DetalleOrdenDTO {
        private Long id;
        private Integer cantidad;
        private ProductoDTO producto;
        private TalleDTO talle; // Nuevo campo: talle incluido en la respuesta
    }

    @Getter
    @Setter
    public static class ProductoDTO {
        private Long id;
        private String nombre;
        private String descripcion;
        private Double precio;
        private String marca;
        private String color;
    }

    @Getter
    @Setter
    public static class TalleDTO {
        private Long id;
        private String nombre; // Por ejemplo "40", "M", etc.
    }

}