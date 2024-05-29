package com.example.nome.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.nome.domain.service.TituloService;
import com.example.nome.domain.titulo.TituloRequestDTO;
import com.example.nome.domain.titulo.TituloResponseDTO;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/titulos")
public class TituloController {
    @Autowired
    private TituloService tituloService;

    @GetMapping
    public ResponseEntity<List<TituloResponseDTO>> obterTodos(){
        return ResponseEntity.ok(tituloService.obterTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TituloResponseDTO> obterPorId(@PathVariable Long id){
        return ResponseEntity.ok(tituloService.obterPorId(id));
    }

    @PostMapping
    public ResponseEntity<TituloResponseDTO> cadastrar(@RequestBody TituloRequestDTO dto){
        TituloResponseDTO titulo = tituloService.cadastrar(dto);
        return new ResponseEntity<>(titulo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TituloResponseDTO> atualizar(@PathVariable Long id, @RequestBody TituloRequestDTO dto){
        TituloResponseDTO titulo = tituloService.atualizar(id, dto);
        return ResponseEntity.ok(titulo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id){
        tituloService.deletar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
