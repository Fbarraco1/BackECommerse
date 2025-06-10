package com.ecommerce.backecommerce.controller;

import com.ecommerce.backecommerce.entity.TalleProducto;
import com.ecommerce.backecommerce.repository.TalleProductoRepository;
import com.ecommerce.backecommerce.service.TalleProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


// 5. CONTROLLER extendiendo BaseController
@RestController
@RequestMapping("/talleproductos")
public class TalleProductoController extends BaseController<TalleProducto, Long> {

    @Autowired
    private TalleProductoService talleProductoService;

    public TalleProductoController(TalleProductoService talleProductoService) {
        super(talleProductoService);
        this.talleProductoService = talleProductoService;
    }

    // Los métodos CRUD básicos los hereda de BaseController
    // Solo agregamos los métodos específicos de validación

    // Endpoint para validar consistencia manual
    @PostMapping("/validar-consistencia/{productoId}")
    public ResponseEntity<?> validarConsistencia(@PathVariable Long productoId) {
        try {
            talleProductoService.validarConsistenciaInventario(productoId);
            return ResponseEntity.ok(Map.of("mensaje", "Inventario consistente"));
        } catch (TalleProductoRepository.InventarioInconsistenteException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error al validar: " + e.getMessage()));
        }
    }

    // Endpoint para sincronizar cantidad
    @PostMapping("/sincronizar/{productoId}")
    public ResponseEntity<?> sincronizarCantidad(@PathVariable Long productoId) {
        try {
            talleProductoService.sincronizarCantidadProducto(productoId);
            return ResponseEntity.ok(Map.of("mensaje", "Cantidad sincronizada exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error al sincronizar: " + e.getMessage()));
        }
    }

    // Endpoint para obtener talles por producto
    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<TalleProducto>> getTallesPorProducto(@PathVariable Long productoId) {
        try {
            List<TalleProducto> talles = talleProductoService.getTallesPorProducto(productoId);
            return ResponseEntity.ok(talles);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Endpoint para obtener cantidad total por producto
    @GetMapping("/cantidad/{productoId}")
    public ResponseEntity<?> getCantidadTotalPorProducto(@PathVariable Long productoId) {
        try {
            Integer cantidadTotal = talleProductoService.getCantidadTotalPorProducto(productoId);
            return ResponseEntity.ok(Map.of("productoId", productoId, "cantidadTotal", cantidadTotal));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error al obtener cantidad: " + e.getMessage()));
        }
    }

    // Manejo de excepciones específicas
    @ExceptionHandler(TalleProductoRepository.InventarioInconsistenteException.class)
    public ResponseEntity<?> handleInventarioInconsistente(TalleProductoRepository.InventarioInconsistenteException e) {
        return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }
}