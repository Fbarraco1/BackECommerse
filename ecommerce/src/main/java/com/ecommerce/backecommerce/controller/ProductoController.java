package com.ecommerce.backecommerce.controller;

import com.ecommerce.backecommerce.entity.Producto;
import com.ecommerce.backecommerce.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController extends BaseController<Producto, Long> {

    @Autowired
    private ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        super(productoService);
        this.productoService = productoService;
    }

    // Obtener productos por tipo
    @GetMapping("/tipos/{idTipo}")
    public ResponseEntity<?> obtenerPorTipo(@PathVariable Long idTipo) {
        try {
            List<Producto> productos = productoService.buscarPorTipo(idTipo);
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener productos por tipo: " + e.getMessage());
        }
    }

    // Obtener productos por categoría
    @GetMapping("/categorias/{idCategoria}")
    public ResponseEntity<?> obtenerPorCategoria(@PathVariable Long idCategoria) {
        try {
            List<Producto> productos = productoService.buscarPorCategoria(idCategoria);
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener productos por categoría: " + e.getMessage());
        }
    }

    // Buscar productos por nombre
    @GetMapping("/buscar")
    public ResponseEntity<?> buscarPorNombre(@RequestParam String nombre) {
        try {
            List<Producto> productos = productoService.buscarPorNombre(nombre);
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al buscar productos por nombre: " + e.getMessage());
        }
    }

    // Buscar productos por marca
    @GetMapping("/marca/{marca}")
    public ResponseEntity<?> buscarPorMarca(@PathVariable String marca) {
        try {
            List<Producto> productos = productoService.buscarPorMarca(marca);
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al buscar productos por marca: " + e.getMessage());
        }
    }

    // Buscar productos por rango de precio
    @GetMapping("/precio")
    public ResponseEntity<?> buscarPorRangoPrecio(@RequestParam Double min, @RequestParam Double max) {
        try {
            List<Producto> productos = productoService.buscarPorRangoPrecio(min, max);
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al buscar productos por rango de precio: " + e.getMessage());
        }
    }

    // Buscar productos con stock disponible
    @GetMapping("/stock")
    public ResponseEntity<?> buscarConStock() {
        try {
            List<Producto> productos = productoService.buscarConStock();
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al buscar productos con stock: " + e.getMessage());
        }
    }

    // Buscar productos por tipo y categoría
    @GetMapping("/filtrar")
    public ResponseEntity<?> buscarPorTipoYCategoria(
            @RequestParam Long idTipo,
            @RequestParam Long idCategoria) {
        try {
            List<Producto> productos = productoService.buscarPorTipoYCategoria(idTipo, idCategoria);
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al buscar productos por tipo y categoría: " + e.getMessage());
        }
    }

    // Buscar productos por color
    @GetMapping("/color/{color}")
    public ResponseEntity<?> buscarPorColor(@PathVariable String color) {
        try {
            List<Producto> productos = productoService.buscarPorColor(color);
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al buscar productos por color: " + e.getMessage());
        }
    }

    // Actualizar stock
    @PutMapping("/{id}/stock")
    public ResponseEntity<?> actualizarStock(@PathVariable Long id, @RequestParam Integer cantidad) {
        try {
            Producto actualizado = productoService.actualizarStock(id, cantidad);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al actualizar stock: " + e.getMessage());
        }
    }
}