package com.ecommerce.backecommerce.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 1. ENTIDAD TalleProducto modificada
@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class TalleProducto extends Base{
    @ManyToOne
    private Producto producto;

    @ManyToOne
    private Talle talle;

    private Integer cantidad; // NUEVO CAMPO
}