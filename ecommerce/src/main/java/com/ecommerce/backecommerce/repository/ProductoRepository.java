package com.ecommerce.backecommerce.repository;

import com.ecommerce.backecommerce.entity.Producto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends BaseRepository<Producto, Long> {

    // Buscar productos por tipo
    @Query("SELECT p FROM Producto p WHERE p.categoria.tipo.id = :idTipo AND p.activo = true")
    List<Producto> buscarPorTipo(@Param("idTipo") Long idTipo);

    // Buscar productos por categoría
    @Query("SELECT p FROM Producto p WHERE p.categoria.id = :idCategoria AND p.activo = true")
    List<Producto> buscarPorCategoria(@Param("idCategoria") Long idCategoria);

    // Buscar productos por nombre (búsqueda parcial)
    @Query("SELECT p FROM Producto p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')) AND p.activo = true")
    List<Producto> buscarPorNombre(@Param("nombre") String nombre);

    // Buscar productos por marca
    @Query("SELECT p FROM Producto p WHERE LOWER(p.marca) LIKE LOWER(CONCAT('%', :marca, '%')) AND p.activo = true")
    List<Producto> buscarPorMarca(@Param("marca") String marca);

    // Buscar productos por rango de precios
    @Query("SELECT p FROM Producto p WHERE p.precio BETWEEN :precioMin AND :precioMax AND p.activo = true")
    List<Producto> buscarPorRangoPrecio(@Param("precioMin") Double precioMin,
                                        @Param("precioMax") Double precioMax);

    // Buscar productos con stock disponible
    @Query("SELECT p FROM Producto p WHERE p.cantidad > 0 AND p.activo = true")
    List<Producto> buscarConStock();

    // Buscar productos por tipo y categoría
    @Query("SELECT p FROM Producto p WHERE p.categoria.tipo.id = :idTipo AND p.categoria.id = :idCategoria AND p.activo = true")
    List<Producto> buscarPorTipoYCategoria(@Param("idTipo") Long idTipo,
                                           @Param("idCategoria") Long idCategoria);

    // Buscar productos activos
    @Query("SELECT p FROM Producto p WHERE p.activo = true")
    List<Producto> buscarActivos();

    // Buscar productos por color
    @Query("SELECT p FROM Producto p WHERE LOWER(p.color) = LOWER(:color) AND p.activo = true")
    List<Producto> buscarPorColor(@Param("color") String color);
}
