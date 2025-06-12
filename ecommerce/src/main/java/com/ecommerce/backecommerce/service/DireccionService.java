package com.ecommerce.backecommerce.service;

import com.ecommerce.backecommerce.entity.Direccion;
import com.ecommerce.backecommerce.entity.Usuario;
import com.ecommerce.backecommerce.repository.BaseRepository;
import com.ecommerce.backecommerce.repository.DireccionRepository;
import com.ecommerce.backecommerce.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DireccionService extends BaseService<Direccion, Long>{

    @Autowired
    private UsuarioRepository usuarioRepository;

    public DireccionService(DireccionRepository direccionRepository) {
        super(direccionRepository);
    }

    @Override
    public Direccion crear(Direccion direccion) throws Exception {
        // Verificar que el usuario existe
        if (direccion.getUsuario() == null || direccion.getUsuario().getId() == null) {
            throw new Exception("El usuario es requerido para crear una dirección");
        }

        // Buscar el usuario completo desde la base de datos
        Usuario usuario = usuarioRepository.findById(direccion.getUsuario().getId())
                .orElseThrow(() -> new Exception("Usuario no encontrado con ID: " + direccion.getUsuario().getId()));

        // Asignar el usuario completo a la dirección
        direccion.setUsuario(usuario);

        // Crear la dirección
        Direccion direccionCreada = super.crear(direccion);

        // Opcional: Agregar la dirección a la lista del usuario
        usuario.getDirecciones().add(direccionCreada);
        usuarioRepository.save(usuario);

        return direccionCreada;
    }

    // Método adicional para crear dirección por ID de usuario
    public Direccion crearDireccionParaUsuario(Long usuarioId, Direccion direccion) throws Exception {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new Exception("Usuario no encontrado con ID: " + usuarioId));

        direccion.setUsuario(usuario);
        return super.crear(direccion);
    }
}
