package com.ecommerce.backecommerce.controller;

import com.ecommerce.backecommerce.entity.Direccion;
import com.ecommerce.backecommerce.service.DireccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/direccion")
public class DireccionController extends BaseController<Direccion, Long> {
    @Autowired
    private DireccionService direccionService;

    public DireccionController(DireccionService direccionService) {
        super(direccionService);
    }
}
