package com.ecommerce.backecommerce.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalleOrden extends Base{

    @ManyToOne
    private OrdenDeCompra orden;

    @ManyToOne
    private Producto producto;

    private Integer cantidad;
}

