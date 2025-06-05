package com.ecommerce.backecommerce.entity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tipo extends Base {

    private String nombre;

    // Relación con categorías
    @OneToMany(mappedBy = "tipo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("tipo-categorias")
    private List<Categoria> categorias = new ArrayList<>();

}

