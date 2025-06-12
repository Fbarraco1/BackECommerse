package com.ecommerce.backecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Direccion extends Base {

    private String calle;
    private String localidad;
    private String cp;

    @ManyToOne
    private Usuario usuario;
}
