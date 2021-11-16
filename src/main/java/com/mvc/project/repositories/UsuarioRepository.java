package com.mvc.project.repositories;


import com.mvc.project.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByUsername(String email);

    Optional<Usuario> getOptionalUsuarioByUsername(String email);
}
