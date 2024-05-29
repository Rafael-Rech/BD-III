package com.example.nome.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nome.domain.model.Titulo;
import com.example.nome.domain.model.Usuario;

import java.util.List;


public interface TituloRepository extends JpaRepository<Titulo, Long>{
    List<Titulo> findByUsuario(Usuario usuario);
}
