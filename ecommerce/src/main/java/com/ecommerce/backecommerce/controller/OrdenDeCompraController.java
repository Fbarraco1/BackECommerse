package com.ecommerce.backecommerce.controller;

import com.ecommerce.backecommerce.dto.CrearOrdenDTO;
import com.ecommerce.backecommerce.dto.OrdenCompraResponseDTO;
import com.ecommerce.backecommerce.entity.OrdenDeCompra;
import com.ecommerce.backecommerce.service.OrdenDeCompraService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.ecommerce.backecommerce.entity.Usuario;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ordendecompra")
public class OrdenDeCompraController extends BaseController<OrdenDeCompra, Long> {

    private final OrdenDeCompraService ordenDeCompraService;

    public OrdenDeCompraController(OrdenDeCompraService ordenDeCompraService) {
        super(ordenDeCompraService);
        this.ordenDeCompraService = ordenDeCompraService;
    }

    @PostMapping("/crear")
    public OrdenCompraResponseDTO crearOrden(
            @RequestBody CrearOrdenDTO dto,
            @AuthenticationPrincipal Usuario usuario
    ) throws Exception {
        return ordenDeCompraService.crearOrden(dto, usuario);
    }
}