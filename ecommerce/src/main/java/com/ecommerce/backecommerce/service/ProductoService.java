package com.ecommerce.backecommerce.service;

import com.ecommerce.backecommerce.entity.Producto;
import com.ecommerce.backecommerce.repository.ProductoCriteriaRepository;
import com.ecommerce.backecommerce.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService extends BaseService<Producto, Long> {

    private final ProductoCriteriaRepository productoCriteriaRepository;

    @Autowired
    public ProductoService(ProductoRepository productoRepository, ProductoCriteriaRepository productoCriteriaRepository) {
        super(productoRepository); // Llama al constructor de BaseService con ProductoRepository
        this.productoCriteriaRepository = productoCriteriaRepository;
    }

    public List<Producto> filtrarProductos(Long tipoId, Long categoriaId, String color, String marca, Double precioMin, Double precioMax) {
        // Si todos los parámetros son null, devolver todos los productos
        if (tipoId == null && categoriaId == null && color == null && marca == null && precioMin == null && precioMax == null) {
            try {
                return listar(); // o el método que uses para obtener todos
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return productoCriteriaRepository.filtrarProductos(tipoId, categoriaId, color, marca, precioMin, precioMax);
    }
}
