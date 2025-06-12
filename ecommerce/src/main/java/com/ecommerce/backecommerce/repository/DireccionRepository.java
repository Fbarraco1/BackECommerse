package com.ecommerce.backecommerce.repository;

import com.ecommerce.backecommerce.entity.Direccion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DireccionRepository extends BaseRepository<Direccion, Long> {

    // Método para buscar dirección con usuario cargado
    @Query("SELECT d FROM Direccion d JOIN FETCH d.usuario WHERE d.id = :id")
    Optional<Direccion> findByIdWithUsuario(@Param("id") Long id);

    // Método para buscar direcciones por usuario
    @Query("SELECT d FROM Direccion d WHERE d.usuario.id = :usuarioId AND d.activo = true")
    java.util.List<Direccion> findByUsuarioIdAndActivoTrue(@Param("usuarioId") Long usuarioId);
}