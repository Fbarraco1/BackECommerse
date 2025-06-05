package com.ecommerce.backecommerce.repository;

import com.ecommerce.backecommerce.entity.Tipo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TipoRepository extends BaseRepository<Tipo, Long> {

    // Buscar por nombre (case insensitive)
    @Query("SELECT t FROM Tipo t WHERE LOWER(t.nombre) = LOWER(:nombre) AND t.activo = true")
    Optional<Tipo> buscarPorNombre(@Param("nombre") String nombre);

    // Verificar si existe por nombre
    @Query("SELECT COUNT(t) > 0 FROM Tipo t WHERE LOWER(t.nombre) = LOWER(:nombre) AND t.activo = true")
    boolean existePorNombre(@Param("nombre") String nombre);

    // Buscar tipos activos
    @Query("SELECT t FROM Tipo t WHERE t.activo = true")
    List<Tipo> buscarActivos();

    // Verificar si existe por nombre excluyendo un ID específico
    @Query("SELECT COUNT(t) > 0 FROM Tipo t WHERE LOWER(t.nombre) = LOWER(:nombre) AND t.id != :id AND t.activo = true")
    boolean existePorNombreExcluyendoId(@Param("nombre") String nombre, @Param("id") Long id);
}
