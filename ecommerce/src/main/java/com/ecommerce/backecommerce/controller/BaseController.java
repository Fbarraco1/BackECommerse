package com.ecommerce.backecommerce.controller;


import com.ecommerce.backecommerce.entity.Base;
import com.ecommerce.backecommerce.service.BaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class BaseController<E extends Base, ID extends Serializable> {

    protected BaseService<E, ID> service;

    public BaseController(BaseService<E, ID> service){
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<List<E>> listar() throws Exception {
        List<E> entities = service.listar();
        return ResponseEntity.ok(entities);
    }

    @GetMapping("/{id}")
    public Optional<E> buscarPorId(@PathVariable ID id) throws Exception {
        return service.buscarPorId(id);
    }

    @PostMapping()
    public ResponseEntity<E> crear(@RequestBody E entity) throws Exception {
        E entidadCreada = service.crear(entity);
        return ResponseEntity.ok(entidadCreada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<E> actualizar(@PathVariable ID id, @RequestBody E entity) throws Exception {
        E entidadActualizada = service.actualizar(entity, id);
        return ResponseEntity.ok(entidadActualizada);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable ID id) throws Exception {
        service.eliminar(id);
    }

    @PatchMapping("/desactivar/{id}")
    public ResponseEntity<Void> eliminadoLogico(@PathVariable ID id) throws Exception {
        service.eliminadoLogico(id);
        return ResponseEntity.noContent().build();
    }


    @PatchMapping("/activar/{id}")
    public ResponseEntity<Void> activarEntidad(@PathVariable ID id) throws Exception {
        service.activarEntidad(id);
        return ResponseEntity.noContent().build();
    }

}

