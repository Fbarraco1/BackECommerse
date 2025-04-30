package com.ecommerce.backecommerce.controller;

import com.ecommerce.backecommerce.entity.OrdenDeCompra;
import com.ecommerce.backecommerce.service.OrdenDeCompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ordendecompra")
public class OrdenDeCompraController extends BaseController<OrdenDeCompra, Long> {
    @Autowired
    private OrdenDeCompraService ordenDeCompraService;

    public OrdenDeCompraController(OrdenDeCompraService ordenDeCompraService) {
        super(ordenDeCompraService);
    }
}
