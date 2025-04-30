package com.ecommerce.backecommerce.repository;

import com.ecommerce.backecommerce.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
