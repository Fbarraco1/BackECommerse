package com.ecommerce.backecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalleOrden extends Base {

    @ManyToOne
    private OrdenDeCompra orden;

    @ManyToOne
    private Producto producto;

    private Integer cantidad;
}
