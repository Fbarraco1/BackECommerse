package com.ecommerce.backecommerce.service;

import com.ecommerce.backecommerce.entity.Producto;
import com.ecommerce.backecommerce.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService extends BaseService<Producto, Long> {

    @Autowired
    private ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        super(productoRepository);
        this.productoRepository = productoRepository;
    }

    // Buscar productos por tipo
    @Transactional(readOnly = true)
    public List<Producto> buscarPorTipo(Long idTipo) throws Exception {
        try {
            return productoRepository.buscarPorTipo(idTipo);
        } catch (Exception ex) {
            throw new Exception("Error al buscar productos por tipo: " + ex.getMessage());
        }
    }

    // Buscar productos por categoría
    @Transactional(readOnly = true)
    public List<Producto> buscarPorCategoria(Long idCategoria) throws Exception {
        try {
            return productoRepository.buscarPorCategoria(idCategoria);
        } catch (Exception ex) {
            throw new Exception("Error al buscar productos por categoría: " + ex.getMessage());
        }
    }

    // Buscar productos por nombre
    @Transactional(readOnly = true)
    public List<Producto> buscarPorNombre(String nombre) throws Exception {
        try {
            return productoRepository.buscarPorNombre(nombre);
        } catch (Exception ex) {
            throw new Exception("Error al buscar productos por nombre: " + ex.getMessage());
        }
    }

    // Buscar productos por marca
    @Transactional(readOnly = true)
    public List<Producto> buscarPorMarca(String marca) throws Exception {
        try {
            return productoRepository.buscarPorMarca(marca);
        } catch (Exception ex) {
            throw new Exception("Error al buscar productos por marca: " + ex.getMessage());
        }
    }

    // Buscar productos por rango de precios
    @Transactional(readOnly = true)
    public List<Producto> buscarPorRangoPrecio(Double precioMin, Double precioMax) throws Exception {
        try {
            if (precioMin > precioMax) {
                throw new Exception("El precio mínimo no puede ser mayor al precio máximo");
            }
            return productoRepository.buscarPorRangoPrecio(precioMin, precioMax);
        } catch (Exception ex) {
            throw new Exception("Error al buscar productos por rango de precio: " + ex.getMessage());
        }
    }

    // Buscar productos con stock
    @Transactional(readOnly = true)
    public List<Producto> buscarConStock() throws Exception {
        try {
            return productoRepository.buscarConStock();
        } catch (Exception ex) {
            throw new Exception("Error al buscar productos con stock: " + ex.getMessage());
        }
    }

    // Buscar por tipo y categoría
    @Transactional(readOnly = true)
    public List<Producto> buscarPorTipoYCategoria(Long idTipo, Long idCategoria) throws Exception {
        try {
            return productoRepository.buscarPorTipoYCategoria(idTipo, idCategoria);
        } catch (Exception ex) {
            throw new Exception("Error al buscar productos por tipo y categoría: " + ex.getMessage());
        }
    }

    // Buscar productos por color
    @Transactional(readOnly = true)
    public List<Producto> buscarPorColor(String color) throws Exception {
        try {
            return productoRepository.buscarPorColor(color);
        } catch (Exception ex) {
            throw new Exception("Error al buscar productos por color: " + ex.getMessage());
        }
    }

    // Override del método crear con validaciones
    @Override
    @Transactional
    public Producto crear(Producto producto) throws Exception {
        try {
            validarProducto(producto);
            return super.crear(producto);
        } catch (Exception ex) {
            throw new Exception("Error al crear producto: " + ex.getMessage());
        }
    }

    // Override del método actualizar con validaciones
    @Override
    @Transactional
    public Producto actualizar(Producto producto) throws Exception {
        try {
            validarProducto(producto);
            return super.actualizar(producto);
        } catch (Exception ex) {
            throw new Exception("Error al actualizar producto: " + ex.getMessage());
        }
    }

    // Actualizar stock
    @Transactional
    public Producto actualizarStock(Long idProducto, Integer nuevaCantidad) throws Exception {
        try {
            Optional<Producto> optionalProducto = buscarPorId(idProducto);
            if (optionalProducto.isEmpty()) {
                throw new Exception("Producto no encontrado");
            }

            Producto producto = optionalProducto.get();
            if (nuevaCantidad < 0) {
                throw new Exception("La cantidad no puede ser negativa");
            }

            producto.setCantidad(nuevaCantidad);
            return actualizar(producto);
        } catch (Exception ex) {
            throw new Exception("Error al actualizar stock: " + ex.getMessage());
        }
    }

    // Método privado para validaciones
    private void validarProducto(Producto producto) throws Exception {
        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            throw new Exception("El nombre del producto es obligatorio");
        }
        if (producto.getPrecio() == null || producto.getPrecio() <= 0) {
            throw new Exception("El precio debe ser mayor a cero");
        }
        if (producto.getCantidad() == null || producto.getCantidad() < 0) {
            throw new Exception("La cantidad no puede ser negativa");
        }
        if (producto.getCategoria() == null) {
            throw new Exception("La categoría es obligatoria");
        }
    }
}

