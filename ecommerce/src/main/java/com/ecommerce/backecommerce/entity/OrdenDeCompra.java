package com.ecommerce.backecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrdenDeCompra extends Base {

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Direccion direccionEntrega;

    private LocalDate fecha;

    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleOrden> detalle;
}
