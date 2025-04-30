package com.ecommerce.backecommerce.controller;

import com.ecommerce.backecommerce.entity.Tipo;
import com.ecommerce.backecommerce.service.TipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tipos")
public class TipoController extends BaseController<Tipo, Long>{
    @Autowired
    private TipoService tipoService;

    public TipoController(TipoService tipoService) {
        super(tipoService);
    }
}
