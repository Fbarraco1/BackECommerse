package com.ecommerce.backecommerce.controller;

import com.ecommerce.backecommerce.entity.Producto;
import com.ecommerce.backecommerce.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productos")
public class ProductoController extends BaseController<Producto, Long> {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        super(productoService);
        this.productoService = productoService;
    }

    @GetMapping("/filtrar")
    public ResponseEntity<List<Producto>> filtrarProductos(
            @RequestParam(required = false) Long tipoId,
            @RequestParam(required = false) Long categoriaId,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) Double precioMin,
            @RequestParam(required = false) Double precioMax
    ) {
        List<Producto> productos = productoService.filtrarProductos(tipoId, categoriaId, color, marca, precioMin, precioMax);
        return ResponseEntity.ok(productos);
    }
}