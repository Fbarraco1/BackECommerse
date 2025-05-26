package com.ecommerce.backecommerce.controller;

import com.ecommerce.backecommerce.entity.ImagenProducto;
import com.ecommerce.backecommerce.service.ImagenProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/imagenes")
public class ImagenProductoController {

    private final ImagenProductoService imagenProductoService;

    public ImagenProductoController(ImagenProductoService imagenProductoService) {
        this.imagenProductoService = imagenProductoService;
    }

    @PostMapping("/subir")
    public ResponseEntity<?> subirImagen(
            @RequestParam("file") MultipartFile file,
            @RequestParam("productoId") Long productoId,
            @RequestParam(value = "esPrincipal", defaultValue = "false") boolean esPrincipal,
            @RequestParam(value = "orden", defaultValue = "0") Integer orden
    ) {
        try {
            ImagenProducto imagen = imagenProductoService.subirImagen(file, productoId, esPrincipal, orden);
            return ResponseEntity.ok(imagen);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
