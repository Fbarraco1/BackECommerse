package com.ecommerce.backecommerce.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class TalleProducto extends Base{
    @ManyToOne
    private Producto producto;

    @ManyToOne
    private Talle talle;
}