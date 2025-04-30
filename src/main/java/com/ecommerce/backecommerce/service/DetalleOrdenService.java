package com.ecommerce.backecommerce.service;

import com.ecommerce.backecommerce.entity.DetalleOrden;
import com.ecommerce.backecommerce.repository.DetalleOrdenRepository;
import org.springframework.stereotype.Service;

@Service
public class DetalleOrdenService extends BaseService<DetalleOrden, Long>{

    public DetalleOrdenService(DetalleOrdenRepository detalleOrdenRepository) {
        super(detalleOrdenRepository);
    }
}
