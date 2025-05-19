package com.ecommerce.backecommerce.controller;

import com.ecommerce.backecommerce.entity.TalleProducto;
import com.ecommerce.backecommerce.service.TalleProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/talleproductos")
public class TalleProductoController extends BaseController<TalleProducto, Long>{
    @Autowired
    private TalleProductoService talleProductoService;

    public TalleProductoController(TalleProductoService talleProductoService) {
        super(talleProductoService);
    }
}
