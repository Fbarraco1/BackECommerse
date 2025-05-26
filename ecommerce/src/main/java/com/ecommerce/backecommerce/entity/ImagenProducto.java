package com.ecommerce.backecommerce.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ImagenProducto extends Base {
    private String url;
    private String nombre;
    private Boolean esPrincipal = false; // Para marcar la imagen principal
    private Integer orden = 0; // Para el orden de visualización

    @ManyToOne
    @JoinColumn(name = "producto_id")
    @JsonBackReference
    private Producto producto;
}
