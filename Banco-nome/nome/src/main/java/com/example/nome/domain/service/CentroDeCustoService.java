package com.example.nome.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.nome.domain.dto.centrodecusto.CentroDeCustoRequestDTO;
import com.example.nome.domain.dto.centrodecusto.CentroDeCustoResponseDTO;
import com.example.nome.domain.exception.ResourceNotFoundException;
import com.example.nome.domain.model.CentroDeCusto;
import com.example.nome.domain.model.Usuario;
import com.example.nome.domain.repository.CentroDeCustoRepository;

// @Service
// public class CentroDeCustoService implements ICRUDService<CentroDeCustoRequestDTO, CentroDeCustoResponseDTO>{
//     @Autowired
//     private CentroDeCustoRepository repository;
//     @Autowired
//     private ModelMapper mapper;

//     @Override
//     public List<CentroDeCustoResponseDTO> obterTodos() {
//         Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//         List<CentroDeCusto> lista = repository.findByUsuario(usuario);
//         return lista.stream().map(centroDeCusto -> mapper.map(centroDeCusto, CentroDeCustoResponseDTO.class)).collect(Collectors.toList());
//     }

//     @Override
//     public CentroDeCustoResponseDTO obterPorId(Long id) {
//         Optional<CentroDeCusto> optCentroDeCusto = repository.findById(id);
//         if(optCentroDeCusto.isEmpty()){
//             throw new ResourceNotFoundException("Não foi possível encontrar um centro de custo com este id: " + id);
//         }
//         Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//         if(usuario.getId() != optCentroDeCusto.get().getUsuario().getId()){
//             throw new ResourceNotFoundException("O centro de custo com o id " + id + " não pertence a este usuário");
//         }
//         return mapper.map(optCentroDeCusto.get(), CentroDeCustoResponseDTO.class);
//     }

//     @Override
//     public CentroDeCustoResponseDTO cadastrar(CentroDeCustoRequestDTO dto) {
//         CentroDeCusto centroDeCusto = mapper.map(dto, CentroDeCusto.class);
//         Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//         centroDeCusto.setUsuario(usuario);
//         centroDeCusto.setId(null);
//         centroDeCusto = repository.save(centroDeCusto);
//         return mapper.map(centroDeCusto, CentroDeCustoResponseDTO.class);
//     }

//     @Override
//     public CentroDeCustoResponseDTO atualizar(Long id, CentroDeCustoRequestDTO dto) {
//         obterPorId(id);
//         CentroDeCusto centroDeCusto = mapper.map(dto, CentroDeCusto.class);
//         Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//         centroDeCusto.setUsuario(usuario);
//         centroDeCusto.setId(id);
//         centroDeCusto = repository.save(centroDeCusto);
//         return mapper.map(centroDeCusto, CentroDeCustoResponseDTO.class);
//     }

//     @Override
//     public void deletar(Long id) {
//         obterPorId(id);
//         repository.deleteById(id);
//     }
    
// }


@Service
public class CentroDeCustoService implements ICRUDService
<CentroDeCustoRequestDTO, CentroDeCustoResponseDTO> {
@Autowired
private CentroDeCustoRepository centroDeCustoRepository;
@Autowired
private ModelMapper mapper;

    @Override
    public List<CentroDeCustoResponseDTO> obterTodos() {
       Usuario usuario = (Usuario) SecurityContextHolder
       .getContext().getAuthentication().getPrincipal();
       List<CentroDeCusto> lista = centroDeCustoRepository
       .findByUsuario(usuario);
       return lista.stream().map(centroDeCusto -> mapper
       .map(centroDeCusto, CentroDeCustoResponseDTO.class))
       .collect(Collectors.toList());
    }

    @Override
    public CentroDeCustoResponseDTO obterPorId(Long id) {
       Optional<CentroDeCusto> optCentroDeCusto = centroDeCustoRepository
       .findById(id);
       if(optCentroDeCusto.isEmpty()){
        throw new ResourceNotFoundException("Não foi possível encontrar o centro de custo com o id: " + id);
       }
       Usuario usuario = (Usuario) SecurityContextHolder.getContext()
       .getAuthentication().getPrincipal();
       if(optCentroDeCusto.get().getUsuario().getId() != usuario.getId()){
         throw new ResourceNotFoundException("O Centro de Custo de id: " +
         id + "não pertence a esse usuário");
       }
       return mapper.map(optCentroDeCusto.get(), 
       CentroDeCustoResponseDTO.class);
    }

    @Override
    public CentroDeCustoResponseDTO cadastrar(CentroDeCustoRequestDTO dto) {
       CentroDeCusto centroDeCusto = mapper.map(dto, 
       CentroDeCusto.class);
       Usuario usuario = (Usuario) SecurityContextHolder
       .getContext().getAuthentication().getPrincipal();
       centroDeCusto.setUsuario(usuario);
       centroDeCusto.setId(null);
       centroDeCusto = centroDeCustoRepository.save(centroDeCusto);
       return mapper.map(centroDeCusto, 
       CentroDeCustoResponseDTO.class);
    }

    @Override
    public CentroDeCustoResponseDTO atualizar(Long id, CentroDeCustoRequestDTO dto) {
        obterPorId(id);
        CentroDeCusto centroDeCusto = mapper.map(dto, 
        CentroDeCusto.class);
        Usuario usuario = (Usuario) SecurityContextHolder
        .getContext().getAuthentication().getPrincipal();
        centroDeCusto.setUsuario(usuario);
        centroDeCusto.setId(id);
        centroDeCusto = centroDeCustoRepository.save(centroDeCusto);
        return mapper.map(centroDeCusto, 
        CentroDeCustoResponseDTO.class);
    }

    @Override
    public void deletar(Long id) {
       obterPorId(id);
       centroDeCustoRepository.deleteById(id);
    }
    
}