package com.ecommerce.backecommerce.repository;

import com.ecommerce.backecommerce.entity.Categoria;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends BaseRepository<Categoria, Long> {
    // Buscar categorías por tipo
    @Query("SELECT c FROM Categoria c WHERE c.tipo.id = :idTipo AND c.activo = true")
    List<Categoria> buscarPorTipo(@Param("idTipo") Long idTipo);

    // Buscar por nombre (case insensitive)
    @Query("SELECT c FROM Categoria c WHERE LOWER(c.nombre) = LOWER(:nombre) AND c.activo = true")
    Optional<Categoria> buscarPorNombre(@Param("nombre") String nombre);

    // Verificar si existe por nombre
    @Query("SELECT COUNT(c) > 0 FROM Categoria c WHERE LOWER(c.nombre) = LOWER(:nombre) AND c.activo = true")
    boolean existePorNombre(@Param("nombre") String nombre);

    // Buscar categorías activas
    @Query("SELECT c FROM Categoria c WHERE c.activo = true")
    List<Categoria> buscarActivas();

    // Verificar si existe por nombre excluyendo un ID específico
    @Query("SELECT COUNT(c) > 0 FROM Categoria c WHERE LOWER(c.nombre) = LOWER(:nombre) AND c.id != :id AND c.activo = true")
    boolean existePorNombreExcluyendoId(@Param("nombre") String nombre, @Param("id") Long id);
}
