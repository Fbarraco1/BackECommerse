package com.ecommerce.backecommerce.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Direccion extends Base {

    @Column(nullable = false)
    private String calle;

    @Column(nullable = false)
    private String localidad;

    @Column(nullable = false)
    private String cp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonBackReference
    private Usuario usuario;

    // Constructor personalizado para facilitar la creación
    public Direccion(String calle, String localidad, String cp, Usuario usuario) {
        this.calle = calle;
        this.localidad = localidad;
        this.cp = cp;
        this.usuario = usuario;
    }
}