package com.ecommerce.backecommerce.dto;

import com.ecommerce.backecommerce.entity.TalleProducto;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Data
public class TalleProductoDTO {
    private Long id;
    private String talleNombre;
    private String tipoNombre;
    private Integer cantidad;

    public TalleProductoDTO(com.ecommerce.backecommerce.entity.TalleProducto entity) {
        this.id = entity.getId();
        this.cantidad = entity.getCantidad();

        if (entity.getTalle() != null) {
            this.talleNombre = entity.getTalle().getTipoTalle();


            if (entity.getTalle().getTipo() != null) {
                this.tipoNombre = entity.getTalle().getTipo().getNombre();
            }
        }
    }
}
