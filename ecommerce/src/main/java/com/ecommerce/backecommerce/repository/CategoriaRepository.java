package com.ecommerce.backecommerce.repository;

import com.ecommerce.backecommerce.entity.Categoria;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends BaseRepository<Categoria, Long> {
}
