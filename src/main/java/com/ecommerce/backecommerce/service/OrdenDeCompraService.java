package com.ecommerce.backecommerce.service;

import com.ecommerce.backecommerce.entity.OrdenDeCompra;
import com.ecommerce.backecommerce.repository.OrdenDeCompraRepository;
import org.springframework.stereotype.Service;

@Service
public class OrdenDeCompraService extends BaseService<OrdenDeCompra, Long>{
    public OrdenDeCompraService(OrdenDeCompraRepository ordenDeCompraRepository) {
        super(ordenDeCompraRepository);
    }
}
