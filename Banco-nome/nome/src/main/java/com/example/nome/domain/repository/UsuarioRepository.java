package com.example.nome.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.nome.domain.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> /*Tipo do banco sendo trabalhado, tipo do ID desse tipo*/{
    Optional<Usuario> findByEmail(String email);
}
