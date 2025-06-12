package com.ecommerce.backecommerce.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference // Esta anotación controla la serialización
    private List<DetalleOrden> detalle;
}