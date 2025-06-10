package com.ecommerce.backecommerce.repository;

import com.ecommerce.backecommerce.entity.TalleProducto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TalleProductoRepository extends BaseRepository<TalleProducto, Long> {

    @Query("SELECT COALESCE(SUM(tp.cantidad), 0) FROM TalleProducto tp WHERE tp.producto.id = :productoId AND tp.activo = true")
    Integer sumCantidadByProductoId(@Param("productoId") Long productoId);

    @Query("SELECT tp FROM TalleProducto tp WHERE tp.producto.id = :productoId AND tp.activo = true")
    List<TalleProducto> findByProductoIdAndActivoTrue(@Param("productoId") Long productoId);

    // 3. EXCEPCIÓN personalizada
    public class InventarioInconsistenteException extends RuntimeException {
        public InventarioInconsistenteException(String message) {
            super(message);
        }
    }
}