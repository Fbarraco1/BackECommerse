package com.ecommerce.backecommerce.repository;


import com.ecommerce.backecommerce.entity.Producto;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends BaseRepository<Producto, Long> {
    @EntityGraph(attributePaths = {"categoria"})
    List<Producto> findAll();
}