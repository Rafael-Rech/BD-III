package com.example.nome.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nome.domain.model.CentroDeCusto;
import com.example.nome.domain.model.Usuario;

public interface CentroDeCustoRepository extends JpaRepository<CentroDeCusto, Long>{
    List<CentroDeCusto> findByUsuario(Usuario usuario);
}