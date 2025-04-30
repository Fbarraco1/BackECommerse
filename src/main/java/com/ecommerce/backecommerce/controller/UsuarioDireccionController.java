package com.ecommerce.backecommerce.controller;

import com.ecommerce.backecommerce.entity.UsuarioDireccion;
import com.ecommerce.backecommerce.service.UsuarioDireccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuariodireccion")
public class UsuarioDireccionController extends BaseController<UsuarioDireccion, Long>{
    @Autowired
    private UsuarioDireccionService usuarioDireccionService;

    public UsuarioDireccionController(UsuarioDireccionService usuarioDireccionService) {
        super(usuarioDireccionService);
    }
}
