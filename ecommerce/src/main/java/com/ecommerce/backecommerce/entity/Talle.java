package com.ecommerce.backecommerce.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Talle extends Base{
    private String nombre;
    private Integer stock;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "producto_id")
    private Producto producto;

}

