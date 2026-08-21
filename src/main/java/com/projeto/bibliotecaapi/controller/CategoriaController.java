package com.projeto.bibliotecaapi.controller;

import com.projeto.bibliotecaapi.dto.categoriaDTO.CreateCategoria;
import com.projeto.bibliotecaapi.dto.categoriaDTO.ResposnseCategoriaDTO;
import com.projeto.bibliotecaapi.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/api/v2/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<ResposnseCategoriaDTO>> findAll() {
        return ResponseEntity.ok(categoriaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResposnseCategoriaDTO> findById(
            @PathVariable Long id) {

        return ResponseEntity.ok(categoriaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ResposnseCategoriaDTO> create(
            @RequestBody @Valid CreateCategoria dto) {

        return ResponseEntity.ok(categoriaService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResposnseCategoriaDTO> update(
            @PathVariable Long id,
            @RequestBody @Valid CreateCategoria dto) {

        return ResponseEntity.ok(categoriaService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
