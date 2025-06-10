package com.ecommerce.backecommerce.service;

import com.ecommerce.backecommerce.entity.Categoria;
import com.ecommerce.backecommerce.repository.BaseRepository;
import com.ecommerce.backecommerce.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService extends BaseService<Categoria, Long> {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        super(categoriaRepository);
        this.categoriaRepository = categoriaRepository;
    }
/*
    // Buscar categorías por tipo
    @Transactional(readOnly = true)
    public List<Categoria> buscarPorTipo(Long idTipo) throws Exception {
        try {
            return categoriaRepository.buscarPorTipo(idTipo);
        } catch (Exception ex) {
            throw new Exception("Error al buscar categorías por tipo: " + ex.getMessage());
        }
    }

    // Buscar por nombre
    @Transactional(readOnly = true)
    public Optional<Categoria> buscarPorNombre(String nombre) throws Exception {
        try {
            return categoriaRepository.buscarPorNombre(nombre);
        } catch (Exception ex) {
            throw new Exception("Error al buscar categoría por nombre: " + ex.getMessage());
        }
    }

    // Override del método crear con validaciones
    @Override
    @Transactional
    public Categoria crear(Categoria categoria) throws Exception {
        try {
            validarCategoria(categoria, null);
            return super.crear(categoria);
        } catch (Exception ex) {
            throw new Exception("Error al crear categoría: " + ex.getMessage());
        }
    }

    // Override del método actualizar con validaciones
    @Override
    @Transactional
    public Categoria actualizar(Categoria categoria) throws Exception {
        try {
            validarCategoria(categoria, categoria.getId());
            return super.actualizar(categoria);
        } catch (Exception ex) {
            throw new Exception("Error al actualizar categoría: " + ex.getMessage());
        }
    }

    // Método privado para validaciones
    private void validarCategoria(Categoria categoria, Long idExcluir) throws Exception {
        if (categoria.getNombre() == null || categoria.getNombre().trim().isEmpty()) {
            throw new Exception("El nombre de la categoría es obligatorio");
        }
        if (categoria.getTipo() == null) {
            throw new Exception("El tipo es obligatorio");
        }

        // Verificar nombre único
        boolean existe;
        if (idExcluir == null) {
            existe = categoriaRepository.existePorNombre(categoria.getNombre());
        } else {
            existe = categoriaRepository.existePorNombreExcluyendoId(categoria.getNombre(), idExcluir);
        }

        if (existe) {
            throw new Exception("Ya existe una categoría con ese nombre");
        }
    }*/
}