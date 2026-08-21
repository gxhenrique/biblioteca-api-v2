package com.projeto.bibliotecaapi.service;


import com.projeto.bibliotecaapi.dto.categoriaDTO.CreateCategoria;
import com.projeto.bibliotecaapi.dto.categoriaDTO.ResposnseCategoriaDTO;
import com.projeto.bibliotecaapi.entity.Categoria;
import com.projeto.bibliotecaapi.exception.EntityNotFoundException;
import com.projeto.bibliotecaapi.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;


    public List<ResposnseCategoriaDTO> findAll(){
        return categoriaRepository.findAll().stream().map(
                categoria -> new ResposnseCategoriaDTO(categoria.getId(), categoria.getNome())
        ).toList();

    }

    public ResposnseCategoriaDTO findById(Long id){
        return categoriaRepository.findById(id).map(
                        categoria -> new ResposnseCategoriaDTO(categoria.getId(), categoria.getNome()))
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrado"));
    }

    public ResposnseCategoriaDTO create(CreateCategoria dto){
        Categoria categoria = categoriaCreate(dto);
        categoriaRepository.save(categoria);

        return new ResposnseCategoriaDTO(categoria.getId(),categoria.getNome());
    }

    private Categoria categoriaCreate(CreateCategoria dto) {
        Categoria categoria = new Categoria();
        categoria.setNome(dto.nome());
        return categoria;
    }

    public void delete(Long id){
        Categoria categoria = categoriaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Categoria não encontrado"));
        categoriaRepository.delete(categoria);
    }

    public ResposnseCategoriaDTO update(Long id, CreateCategoria dto){
        Categoria entity = categoriaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Categoria não encontrado"));
        updateNovaCategoria(entity,dto);
        categoriaRepository.save(entity);

        return new ResposnseCategoriaDTO(entity.getId(), entity.getNome());
    }

    private void updateNovaCategoria(Categoria entity, CreateCategoria dto) {
        entity.setNome(dto.nome());
    }



}
