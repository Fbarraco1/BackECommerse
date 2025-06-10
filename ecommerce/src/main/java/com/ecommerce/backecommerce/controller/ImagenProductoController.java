package com.ecommerce.backecommerce.controller;

import com.ecommerce.backecommerce.dto.ImagenProductoDTO;
import com.ecommerce.backecommerce.entity.ImagenProducto;
import com.ecommerce.backecommerce.entity.Producto;
import com.ecommerce.backecommerce.service.ImagenProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

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

    // ✅ NUEVO MÉTODO: subir múltiples imágenes con URLs (desde Postman u otro frontend)
    @PostMapping("/cargar-multiples")
    public ResponseEntity<?> cargarMultiplesImagenesJson(
            @RequestBody List<ImagenProductoDTO> imagenesDTO
    ) {
        List<ImagenProducto> imagenesCreadas = new ArrayList<>();
        try {
            for (ImagenProductoDTO dto : imagenesDTO) {
                ImagenProducto imagen = new ImagenProducto();
                imagen.setUrl(dto.getUrl());
                imagen.setNombre(dto.getNombre());
                imagen.setEsPrincipal(dto.getEsPrincipal());
                imagen.setOrden(dto.getOrden());

                Producto producto = new Producto();
                producto.setId(dto.getProductoId());
                imagen.setProducto(producto);

                imagenesCreadas.add(imagenProductoService.guardar(imagen));
            }

            return ResponseEntity.ok(imagenesCreadas);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al guardar las imágenes: " + e.getMessage());
        }
    }


    @DeleteMapping("/eliminar/{productoId}/{imagenId}")
    public ResponseEntity<?> eliminarImagen(
            @PathVariable Long productoId,
            @PathVariable Long imagenId
    ) {
        try {
            imagenProductoService.eliminarImagen(imagenId, productoId);
            return ResponseEntity.ok("Imagen eliminada correctamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
