package com.projeto.bibliotecaapi.service;


import com.projeto.bibliotecaapi.dto.autorCrudDTO.CreateAutor;
import com.projeto.bibliotecaapi.dto.autorCrudDTO.ResponseAutorDTO;
import com.projeto.bibliotecaapi.entity.Autor;
import com.projeto.bibliotecaapi.exception.EntityNotFoundException;
import com.projeto.bibliotecaapi.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    public List<ResponseAutorDTO> findAll(){
        return autorRepository.findAll().stream().map(
                autor -> new ResponseAutorDTO(autor.getId(), autor.getNome())
        ).toList();

    }

    public ResponseAutorDTO findById(Long id){
        return autorRepository.findById(id).map(
                autor -> new ResponseAutorDTO(autor.getId(), autor.getNome()))
                .orElseThrow(() -> new EntityNotFoundException("Autor não encontrado"));
    }

    public ResponseAutorDTO create(CreateAutor dto){
        Autor autor = autorCreate(dto);
        autorRepository.save(autor);

        return new ResponseAutorDTO(autor.getId(),autor.getNome());
    }

    private Autor autorCreate(CreateAutor dto) {
        Autor autor = new Autor();
        autor.setNome(dto.nome());
        return autor;
    }

    public void delete(Long id){
        Autor autor = autorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Autor não encontrado"));
        autorRepository.delete(autor);
    }

    public ResponseAutorDTO update(Long id, CreateAutor dto){
        Autor entity = autorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Autor não encontrado"));
        updateNovoAutor(entity,dto);
        autorRepository.save(entity);

        return new ResponseAutorDTO(entity.getId(), entity.getNome());
    }

    private void updateNovoAutor(Autor entity, CreateAutor dto) {
        entity.setNome(dto.nome());
    }
}
