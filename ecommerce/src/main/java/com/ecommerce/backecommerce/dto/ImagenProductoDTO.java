package com.ecommerce.backecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImagenProductoDTO {
    private String url;
    private String nombre;
    private Boolean esPrincipal;
    private Integer orden;
    private Long productoId;
}