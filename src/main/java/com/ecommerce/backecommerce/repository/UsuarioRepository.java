package com.ecommerce.backecommerce.repository;
import com.ecommerce.backecommerce.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
