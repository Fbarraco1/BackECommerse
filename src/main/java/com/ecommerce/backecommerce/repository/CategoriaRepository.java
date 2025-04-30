package com.ecommerce.backecommerce.repository;

import com.ecommerce.backecommerce.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
