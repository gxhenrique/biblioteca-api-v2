package com.projeto.bibliotecaapi.controller;

import com.projeto.bibliotecaapi.dto.*;
import com.projeto.bibliotecaapi.dto.ResponseMap.ResponseQuatidadeLivroAutor;
import com.projeto.bibliotecaapi.dto.ResponseMap.ResponseQuatidadeLivrosCategory;
import com.projeto.bibliotecaapi.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/api/v1/livros")
public class LivroController {

    @Autowired
    private  LivroService service;

    /*
    @GetMapping
    public ResponseEntity<List<ResponseLivroDTO>> findAll(){
        return ResponseEntity.ok().body(service.findAll());
    }

     */

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseLivroIdDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ResponseLivroIdDTO> create(@Valid @RequestBody CreateLivroDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ResponseLivroDTO> update(@PathVariable Long id, @RequestBody UpdateLivroDTO dto){
        return ResponseEntity.ok().body(service.update(id,dto));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<DeleteLivroDTO> delete(@PathVariable  Long id){
        return ResponseEntity.ok().body(service.delete(id));
    }

    // Consultas básicas

    @GetMapping(value = "/name/{nome}")
    public ResponseEntity<ResponseLivroIdDTO> findByNameLivro(@PathVariable String nome){
        return ResponseEntity.ok().body(service.findByNameLivro(nome));
    }

    @GetMapping(value = "/category/{category}")
    public ResponseEntity<List<ResponseLivroIdDTO>> findByCategory(@PathVariable String category){
        return ResponseEntity.ok().body(service.findByCategory(category));
    }
    @GetMapping(value = "/author/{author}")
    public ResponseEntity<List<ResponseLivroIdDTO>> findByAuthor(@PathVariable String author){
        return  ResponseEntity.ok().body(service.findByAuthor(author));
    }
    @GetMapping(value = "/available")
    public ResponseEntity<List<ResponseLivroIdDTO>> findByAvailable(){
        return  ResponseEntity.ok().body(service.findByAvailable());
    }

    //Paginação e ordenação simples

    @GetMapping
    public ResponseEntity<PageResponseDTO<ResponseLivroIdDTO>> pagination(Pageable pageable) {
        return ResponseEntity.ok(service.pagination(pageable));
    }

  // com Streams

    @GetMapping(value = "/title")
    public ResponseEntity<List<ResponseTituloDTO>> listTitle(){
        return ResponseEntity.ok(service.listTitle());
    }

    @GetMapping(value = "/listBooksMorePages")
    public ResponseEntity<List<ResponseLivroIdDTO>> listBooksMorePages(){
        return ResponseEntity.ok(service.listBooksMorePages());
    }

    @GetMapping(value = "/listBooksPrice")
    public ResponseEntity<List<ResponseLivroIdDTO>> listBooksPrice(){
        return ResponseEntity.ok(service.listBooksPrice());
    }

    @GetMapping(value = "/listBooksAvailableAndFantasy")
    public ResponseEntity<List<ResponseLivroIdDTO>> listBooksAvailableAndFantasy(){
        return ResponseEntity.ok(service.listBooksAvailableAndFantasy());
    }

    @GetMapping(value = "/averageBooksPrice")
    public ResponseEntity<ResponseAveragePrice> averageBooksPrice(){
        return ResponseEntity.ok(service.booksAveragePrice());
    }
    @GetMapping(value = "/booksTotal")
    public ResponseEntity<ResponseTotalQuantity>  booksTotal(){
        return ResponseEntity.ok(service.booksTotal());
    }

    // com Map

    @GetMapping(value = "/quantidadeLivrosAutor")
    public ResponseEntity<List<ResponseQuatidadeLivroAutor>> quatidadeLivroAutor(){
        return ResponseEntity.ok(service.quatidadeLivroAutor());
    }

    @GetMapping(value = "/quatidadeCategory")
    public ResponseEntity<List<ResponseQuatidadeLivrosCategory>> quatidadeCategory(){
        return ResponseEntity.ok(service.quatidadeLivrosCategory());
    }

    @GetMapping(value = "/quatidadeDisponivelCategory")
    public ResponseEntity<List<ResponseQuatidadeLivrosCategory>> quatidadeDisponivelCategory(){
        return  ResponseEntity.ok(service.quatidadeDisponivelCategory());
    }

    @GetMapping(value = "/top3AutorLivros")
    public ResponseEntity<List<ResponseQuatidadeLivroAutor>>  top3AutorLivros(){
        return ResponseEntity.ok(service.top3AutorLivros());
    }

    //combinando operações

    @GetMapping(value = "/precoOrder")
    public ResponseEntity<List<ResponseLivroIdDTO>> precoOrder(){
        return ResponseEntity.ok(service.precoOrder());
    }
}
