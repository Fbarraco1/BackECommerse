package com.ecommerce.backecommerce.controller;


import com.ecommerce.backecommerce.entity.DetalleOrden;
import com.ecommerce.backecommerce.service.DetalleOrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/detalleorden")
public class DetalleOrdenController extends BaseController<DetalleOrden, Long> {
    @Autowired
    private DetalleOrdenService detalleOrdenService;

    public DetalleOrdenController(DetalleOrdenService detalleOrdenService) {
        super(detalleOrdenService);
    }
}
