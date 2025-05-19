package com.ecommerce.backecommerce.service;

import com.ecommerce.backecommerce.entity.UsuarioDireccion;
import com.ecommerce.backecommerce.repository.UsuarioDireccionRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDireccionService extends BaseService<UsuarioDireccion, Long>{
    public UsuarioDireccionService(UsuarioDireccionRepository usuarioDireccionRepository) {
        super(usuarioDireccionRepository);
    }
}
