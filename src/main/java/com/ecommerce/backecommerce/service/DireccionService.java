package com.ecommerce.backecommerce.service;

import com.ecommerce.backecommerce.entity.Direccion;
import com.ecommerce.backecommerce.repository.BaseRepository;
import com.ecommerce.backecommerce.repository.DireccionRepository;
import org.springframework.stereotype.Service;

@Service
public class DireccionService extends BaseService<Direccion, Long>{

    public DireccionService(DireccionRepository direccionRepository) {
        super(direccionRepository);
    }
}
