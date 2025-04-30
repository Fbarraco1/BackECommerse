package com.ecommerce.backecommerce.service;

import com.ecommerce.backecommerce.entity.Producto;
import com.ecommerce.backecommerce.repository.ProductoRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductoService extends BaseService<Producto, Long>{
    public ProductoService(ProductoRepository productoRepository) {
        super(productoRepository);
    }
}
