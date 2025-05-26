package com.ecommerce.backecommerce.repository;

import com.ecommerce.backecommerce.entity.ImagenProducto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagenProductoRepository extends BaseRepository<ImagenProducto, Long> {
    List<ImagenProducto> findByProductoId(Long productoId);
}