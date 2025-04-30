package com.ecommerce.backecommerce.controller;


import com.ecommerce.backecommerce.entity.Talle;
import com.ecommerce.backecommerce.service.TalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/talles")
public class TalleController extends BaseController<Talle, Long> {
    @Autowired
    private TalleService talleService;

    public TalleController(TalleService talleService) {
        super(talleService);
    }
}
