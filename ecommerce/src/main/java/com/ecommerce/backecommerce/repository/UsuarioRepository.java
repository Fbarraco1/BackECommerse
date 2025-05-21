package com.ecommerce.backecommerce.repository;
import com.ecommerce.backecommerce.entity.Usuario;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends BaseRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}
