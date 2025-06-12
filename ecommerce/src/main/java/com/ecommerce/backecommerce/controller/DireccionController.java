package com.ecommerce.backecommerce.controller;

import com.ecommerce.backecommerce.entity.Direccion;
import com.ecommerce.backecommerce.service.DireccionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/direccion") // Tu mapping personalizado
public class DireccionController extends BaseController<Direccion, Long> {

    private final DireccionService direccionService;

    public DireccionController(DireccionService direccionService) {
        super(direccionService);
        this.direccionService = direccionService;
    }

    // Tu endpoint personalizado
    @PostMapping("/usuario/{usuarioId}")
    public ResponseEntity<Direccion> crearDireccionParaUsuario(
            @PathVariable Long usuarioId,
            @RequestBody Direccion direccion) throws Exception {

        // AQUÍ está el fix: llamar al método correcto
        Direccion direccionCreada = direccionService.crearDireccionParaUsuario(usuarioId, direccion);
        return ResponseEntity.ok(direccionCreada);
    }

    // Opcional: Sobrescribir el método crear original para mejor manejo de errores
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