package com.ecommerce.backecommerce.service;

import com.ecommerce.backecommerce.entity.Tipo;
import com.ecommerce.backecommerce.repository.BaseRepository;
import com.ecommerce.backecommerce.repository.TipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TipoService extends BaseService<Tipo, Long> {

    @Autowired
    private TipoRepository tipoRepository;

    public TipoService(TipoRepository tipoRepository) {
        super(tipoRepository);
        this.tipoRepository = tipoRepository;
    }
/*
    // Buscar por nombre
    @Transactional(readOnly = true)
    public Optional<Tipo> buscarPorNombre(String nombre) throws Exception {
        try {
            return tipoRepository.buscarPorNombre(nombre);
        } catch (Exception ex) {
            throw new Exception("Error al buscar tipo por nombre: " + ex.getMessage());
        }
    }

    // Override del método crear con validaciones
    @Override
    @Transactional
    public Tipo crear(Tipo tipo) throws Exception {
        try {
            validarTipo(tipo, null);
            return super.crear(tipo);
        } catch (Exception ex) {
            throw new Exception("Error al crear tipo: " + ex.getMessage());
        }
    }

    // Override del método actualizar con validaciones
    @Override
    @Transactional
    public Tipo actualizar(Tipo tipo) throws Exception {
        try {
            validarTipo(tipo, tipo.getId());
            return super.actualizar(tipo);
        } catch (Exception ex) {
            throw new Exception("Error al actualizar tipo: " + ex.getMessage());
        }
    }

    // Override del método eliminar con validaciones
    @Override
    @Transactional
    public void eliminar(Long id) throws Exception {
        try {
            Optional<Tipo> optionalTipo = buscarPorId(id);
            if (optionalTipo.isEmpty()) {
                throw new Exception("Tipo no encontrado");
            }

            Tipo tipo = optionalTipo.get();
            // Verificar si tiene categorías asociadas
            if (tipo.getCategorias() != null && !tipo.getCategorias().isEmpty()) {
                throw new Exception("No se puede eliminar el tipo porque tiene categorías asociadas");
            }

            super.eliminar(id);
        } catch (Exception ex) {
            throw new Exception("Error al eliminar tipo: " + ex.getMessage());
        }
    }

    // Método privado para validaciones
    private void validarTipo(Tipo tipo, Long idExcluir) throws Exception {
        if (tipo.getNombre() == null || tipo.getNombre().trim().isEmpty()) {
            throw new Exception("El nombre del tipo es obligatorio");
        }

        // Verificar nombre único
        boolean existe;
        if (idExcluir == null) {
            existe = tipoRepository.existePorNombre(tipo.getNombre());
        } else {
            existe = tipoRepository.existePorNombreExcluyendoId(tipo.getNombre(), idExcluir);
        }

        if (existe) {
            throw new Exception("Ya existe un tipo con ese nombre");
        }
    }*/
}