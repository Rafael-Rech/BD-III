package com.example.nome.domain.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.nome.domain.dto.usuario.UsuarioRequestDTO;
import com.example.nome.domain.dto.usuario.UsuarioResponseDTO;
import com.example.nome.domain.exception.BadRequestException;
import com.example.nome.domain.exception.ResourceNotFoundException;
import com.example.nome.domain.model.Usuario;
import com.example.nome.domain.repository.UsuarioRepository;

@Service
public class UsuarioService implements ICRUDService<UsuarioRequestDTO, UsuarioResponseDTO>{
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public List<UsuarioResponseDTO> obterTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream().map(usuario -> mapper.map(usuario, UsuarioResponseDTO.class)).collect(Collectors.toList());
    }

    @Override
    public UsuarioResponseDTO obterPorId(Long id){
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if(usuario.isEmpty()){
            throw new ResourceNotFoundException("Não foi possível obter o usuário com o id " + id);
        }
        return mapper.map(usuario.get(), UsuarioResponseDTO.class);
    }

    @Override
    public UsuarioResponseDTO cadastrar(UsuarioRequestDTO dto) {
        if(dto.getEmail() == null || dto.getSenha() == null){
            throw new BadRequestException("Email e Senha são obrigatórios");
        }
        Optional<Usuario> optUs = usuarioRepository.findByEmail(dto.getEmail());
        if(optUs.isPresent()){
            throw new BadRequestException("Já existe um usuário existente com esse email.");
        }
        Usuario usuario = mapper.map(dto, Usuario.class);
        usuario.setDataCadastro(new Date());
        //Criptografar senha
        usuario = usuarioRepository.save(usuario);
        return mapper.map(usuario, UsuarioResponseDTO.class);
    }

    @Override
    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO dto) {
        UsuarioResponseDTO usuarioBanco = obterPorId(id);
        if(dto.getEmail() == null || dto.getSenha() == null){
            throw new BadRequestException("Email e Senha são obrigatórios");
        }
        Usuario usuario = mapper.map(dto, Usuario.class);
        usuario.setId(id);
        usuario.setDataCadastro(usuarioBanco.getDataCadastro());
        usuario.setDataInativacao(usuarioBanco.getDataInativacao());
        usuario = usuarioRepository.save(usuario);
        return mapper.map(usuario, UsuarioResponseDTO.class);
    }

    @Override
    public void deletar(Long id) {
        /*
         * Caso não fosse utilizada a data de inativação
         * e fosse desejado apagar o usuário do banco 
         * 
         * obterPorId(id);
         * usuarioRepository.deleteById(id);
         */

        Optional<Usuario> optUsuario = usuarioRepository.findById((id));
        if(optUsuario.isEmpty()){
            throw new ResourceNotFoundException("Não foi possível obter o usuário com o id " + id);
        }
        Usuario usuario = optUsuario.get();
        usuario.setDataInativacao(new Date());
        usuarioRepository.save(usuario);
    }  
}
