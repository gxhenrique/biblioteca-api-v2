package com.projeto.bibliotecaapi.controller;

import com.projeto.bibliotecaapi.dto.autorCrudDTO.CreateAutor;
import com.projeto.bibliotecaapi.dto.autorCrudDTO.ResponseAutorDTO;
import com.projeto.bibliotecaapi.service.AutorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/api/v2/autores")
public class AutorController {

    @Autowired
    private AutorService autorService;

    @GetMapping
    public ResponseEntity<List<ResponseAutorDTO>> findAll() {
        return ResponseEntity.ok(autorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseAutorDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(autorService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ResponseAutorDTO> create(
            @RequestBody @Valid CreateAutor dto) {

        return ResponseEntity.ok(autorService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseAutorDTO> update(
            @PathVariable Long id,
            @RequestBody @Valid CreateAutor dto) {

        return ResponseEntity.ok(autorService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        autorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
