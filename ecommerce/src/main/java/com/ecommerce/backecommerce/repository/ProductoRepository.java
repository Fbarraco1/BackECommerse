package com.ecommerce.backecommerce.repository;

import com.ecommerce.backecommerce.entity.Producto;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends BaseRepository<Producto, Long> {
}
