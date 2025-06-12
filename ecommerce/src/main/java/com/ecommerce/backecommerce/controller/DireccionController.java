package com.ecommerce.backecommerce.controller;

import com.ecommerce.backecommerce.entity.Direccion;
import com.ecommerce.backecommerce.service.DireccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/direccion")
public class DireccionController extends BaseController<Direccion, Long> {

    private final DireccionService direccionService;

    public DireccionController(DireccionService direccionService) {
        super(direccionService);
        this.direccionService = direccionService;
    }

    // Método específico para crear dirección con usuario ID en la URL
    @PostMapping("/usuario/{usuarioId}")
    public ResponseEntity<Direccion> crearDireccionParaUsuario(
            @PathVariable Long usuarioId,
            @RequestBody Direccion direccion) throws Exception {

        Direccion direccionCreada = direccionService.crearDireccionParaUsuario(usuarioId, direccion);
        return ResponseEntity.ok(direccionCreada);
    }

    // Sobrescribir el método crear para mejor manejo de errores
    @Override
    @PostMapping()
    public ResponseEntity<Direccion> crear(@RequestBody Direccion direccion) throws Exception {
        try {
            Direccion entidadCreada = direccionService.crear(direccion);
            return ResponseEntity.ok(entidadCreada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
