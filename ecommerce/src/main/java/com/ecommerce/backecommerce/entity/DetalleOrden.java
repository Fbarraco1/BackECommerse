// DetalleOrden.java
package com.ecommerce.backecommerce.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalleOrden extends Base {

    @ManyToOne
    @JsonBackReference // Esta anotación evita la serialización de vuelta
    private OrdenDeCompra orden;

    @ManyToOne
    private Producto producto;

    @ManyToOne
    private Talle talle;
    private Integer cantidad;
}