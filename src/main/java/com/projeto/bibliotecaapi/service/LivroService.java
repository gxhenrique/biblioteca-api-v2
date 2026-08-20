package com.projeto.bibliotecaapi.service;

import com.projeto.bibliotecaapi.dto.*;
import com.projeto.bibliotecaapi.dto.PageResponseDTO;
import com.projeto.bibliotecaapi.dto.ResponseMap.ResponseQuatidadeLivroAutor;
import com.projeto.bibliotecaapi.dto.ResponseMap.ResponseQuatidadeLivrosCategory;
import com.projeto.bibliotecaapi.entity.Livro;
import com.projeto.bibliotecaapi.repository.LivroRepository;
import com.projeto.bibliotecaapi.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LivroService {

    @Autowired
    private LivroRepository repository;

    // 1. CRUD de livros
    /*
    public List<ResponseLivroDTO> findAll(){
        return repository.findAll().stream()
                .map(livro -> new ResponseLivroDTO(livro.getTitulo(),
                        livro.getAutor(), livro.getCategoria(),
                        livro.getPaginas(), livro.getDisponivel(),livro.getPreco())).toList();
    }

     */

    public ResponseLivroIdDTO findById(Long id){
        return repository.findById(id)
                .map(livro -> new ResponseLivroIdDTO(livro.getId(),livro.getTitulo(),
                        livro.getAutor(), livro.getCategoria(),
                        livro.getPaginas(), livro.getDisponivel(),livro.getPreco())).orElseThrow(() ->
                        new EntityNotFoundException("Entity not found " + "id: " + id));
    }

    public ResponseLivroIdDTO create(CreateLivroDTO dto){
         Livro livro =  livroCreated(dto);
         repository.save(livro);
         return new ResponseLivroIdDTO(livro.getId(),livro.getTitulo(),livro.getAutor(),livro.getCategoria(),livro.getPaginas(),
         livro.getDisponivel(), livro.getPreco());
    }

    private  Livro livroCreated(CreateLivroDTO dto){
        Boolean disponivel = true;
        Livro livro = new Livro();
        livro.setTitulo(dto.titulo());
        livro.setAutor(dto.autor());
        livro.setCategoria(dto.categoria());
        livro.setDisponivel(disponivel);
        livro.setPaginas(dto.paginas());
        livro.setPreco(dto.preco());

        return livro;
    }

    public ResponseLivroDTO update(Long id, UpdateLivroDTO dto){
        Livro entity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));
        updateLivro(entity, dto);
        repository.save(entity);

        return new ResponseLivroDTO(entity.getTitulo(),entity.getAutor(),entity.getCategoria(),entity.getPaginas(),
                entity.getDisponivel(),entity.getPreco());
    }

    private void updateLivro(Livro entity, UpdateLivroDTO dto) {
        entity.setTitulo(dto.titulo());
        entity.setPreco(dto.preco());
        entity.setPaginas(dto.paginas());
        entity.setAutor(dto.autor());
        entity.setCategoria(dto.categoria());
        entity.setDisponivel(dto.disponivel());
    }

    public DeleteLivroDTO delete(Long id){
        Livro livro = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));
        repository.delete(livro);

        return  new DeleteLivroDTO("Livro deletado com sucesso...");
    }

    //Consultas básicas

    public ResponseLivroIdDTO findByNameLivro(String nome){
        return repository.findAll().stream()
                .filter(byName -> byName.getTitulo().equalsIgnoreCase(nome))
                .map(livro -> new ResponseLivroIdDTO(
                        livro.getId(),livro.getTitulo(),livro.getAutor(),livro.getCategoria(),livro.getPaginas(),
                        livro.getDisponivel(), livro.getPreco()))
                .findFirst().orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));
    }

    public List<ResponseLivroIdDTO> findByCategory(String categoria){
        return repository.findAll().stream()
                .filter(byCategory -> byCategory.getCategoria().equalsIgnoreCase(categoria))
                .map(livro -> new ResponseLivroIdDTO(
                        livro.getId(),livro.getTitulo(),livro.getAutor(),livro.getCategoria(),livro.getPaginas(),
                        livro.getDisponivel(), livro.getPreco()))
                .toList();

    }

    public List<ResponseLivroIdDTO> findByAuthor(String autor){
        return repository.findAll().stream()
                .filter(byAuthor -> byAuthor.getAutor().equalsIgnoreCase(autor))
                .map(livro -> new ResponseLivroIdDTO(
                        livro.getId(),livro.getTitulo(),livro.getAutor(),livro.getCategoria(),livro.getPaginas(),
                        livro.getDisponivel(), livro.getPreco())).toList();

    }

    public List<ResponseLivroIdDTO> findByAvailable(){
        return repository.findAll().stream()
                .filter(Livro::getDisponivel)
                .map(livro -> new ResponseLivroIdDTO(
                        livro.getId(),livro.getTitulo(),livro.getAutor(),livro.getCategoria(),livro.getPaginas(),
                        livro.getDisponivel(), livro.getPreco())).toList();


    }

    //Paginação e ordenação simples

    public PageResponseDTO<ResponseLivroIdDTO> pagination(Pageable pageable){
        Page<Livro> pages = repository.findAll(pageable);

        List<ResponseLivroIdDTO> content = pages.getContent().stream()
                .map(livro -> new ResponseLivroIdDTO(
                        livro.getId(),livro.getTitulo(),livro.getAutor(),livro.getCategoria(),livro.getPaginas(),
                        livro.getDisponivel(), livro.getPreco())).toList();

        return new PageResponseDTO<>(
                content,
                pages.getNumber(),
                pages.getSize(),
                pages.getTotalElements(),
                pages.getTotalPages()
        );

        
    }

    //com Streams


    public List<ResponseTituloDTO> listTitle(){
        List<Livro> livros = repository.findAll();
        return livros.stream().map(livro -> new ResponseTituloDTO(livro.getTitulo())).toList();
    }

    public List<ResponseLivroIdDTO> listBooksMorePages(){
        return repository.findAll().stream().filter(livro -> livro.getPaginas() > 500)
                .map(livro -> new ResponseLivroIdDTO(
                        livro.getId(),livro.getTitulo(),livro.getAutor(),livro.getCategoria(),livro.getPaginas(),
                        livro.getDisponivel(), livro.getPreco())).toList();
    }

    public List<ResponseLivroIdDTO> listBooksPrice(){

        return repository.findAll().stream()
                .filter(livro -> livro.getPreco().compareTo(BigDecimal.valueOf(40)) > 0)
                .map(livro -> new ResponseLivroIdDTO(
                        livro.getId(),livro.getTitulo(),livro.getAutor(),livro.getCategoria(),livro.getPaginas(),
                        livro.getDisponivel(), livro.getPreco())).toList();

    }

    public List<ResponseLivroIdDTO> listBooksAvailableAndFantasy(){
        return repository.findAll().stream()
                .filter(livro -> livro.getDisponivel().equals(true) && "Fantasia".equals(livro.getDisponivel()))
                .map(livro -> new ResponseLivroIdDTO(
                        livro.getId(),livro.getTitulo(),livro.getAutor(),livro.getCategoria(),livro.getPaginas(),
                        livro.getDisponivel(), livro.getPreco())).toList();
    }

    public ResponseLivroIdDTO bookMoreExpensive(){
        return repository.findAll().stream()
                .max(Comparator.comparing(Livro::getPreco))
                .map(livro -> new ResponseLivroIdDTO(
                        livro.getId(),livro.getTitulo(),livro.getAutor(),livro.getCategoria(),livro.getPaginas(),
                        livro.getDisponivel(), livro.getPreco()))
                .orElseThrow(() -> new EntityNotFoundException("Livro mais caro não encontrado..."));
    }

    public ResponseLivroIdDTO bookCheaper(){
        return repository.findAll().stream()
                .min(Comparator.comparing(Livro::getPreco))
                .map(livro -> new ResponseLivroIdDTO(
                        livro.getId(),livro.getTitulo(),livro.getAutor(),livro.getCategoria(),livro.getPaginas(),
                        livro.getDisponivel(), livro.getPreco()))
                .orElseThrow(() -> new EntityNotFoundException("Livro mais barato não encontrado..."));
    }

    public ResponseAveragePrice booksAveragePrice() {

       BigDecimal soma = BigDecimal.ZERO;
       List<Livro> list = repository.findAll();

       for(Livro livro : list){
           soma = soma.add(livro.getPreco());
       }

       BigDecimal media = soma.divide(
               BigDecimal.valueOf(list.size()),
               2,
               RoundingMode.HALF_UP
       );

        return new ResponseAveragePrice(media);
    }

    public ResponseTotalQuantity booksTotal(){
        Long total = repository.findAll().stream().count();
        return new ResponseTotalQuantity(total);
    }


    // com Map

    public List<ResponseQuatidadeLivroAutor> quatidadeLivroAutor(){

        Map<String,Long> quatidade = repository.findAll().stream()
                .collect(Collectors.groupingBy(Livro::getAutor, Collectors.counting()));

        return quatidade.entrySet().stream()
                .map(entry ->
                        new ResponseQuatidadeLivroAutor(entry.getKey(), entry.getValue()))
                .toList();

    }

    public List<ResponseQuatidadeLivrosCategory> quatidadeLivrosCategory(){
        Map<String,Long> quatidade = repository.findAll().stream()
                .collect(Collectors.groupingBy(Livro::getCategoria, Collectors.counting()));

        return quatidade.entrySet().stream()
                .map(
                        entry -> new ResponseQuatidadeLivrosCategory(entry.getKey(), entry.getValue()))
                .toList();
    }

    public List<ResponseQuatidadeLivrosCategory> quatidadeDisponivelCategory(){
        Map<String, Long> quatidade = repository.findAll().stream()
                .filter(livro -> livro.getDisponivel().equals(true))
                .collect(Collectors.groupingBy(Livro::getCategoria, Collectors.counting()));

        return quatidade.entrySet().stream()
                .map(
                        entry -> new ResponseQuatidadeLivrosCategory(entry.getKey(), entry.getValue()))
                .toList();
    }

    public List<ResponseQuatidadeLivroAutor> top3AutorLivros(){

        Map<String,Long> top3 = repository.findAll().stream()
                .collect(Collectors.groupingBy(Livro::getAutor, Collectors.counting()));

       return top3.entrySet().stream()
               .sorted(Comparator.comparing((Map.Entry<String,Long> teste) -> teste.getValue())
                       .reversed()).limit(3)
               .map(entry -> new ResponseQuatidadeLivroAutor(entry.getKey(), entry.getValue())).toList();


    }

    //combinando operações

    public List<ResponseLivroIdDTO> precoOrder(){
       return repository.findAll().stream()
                .filter(livro -> livro.getDisponivel().equals(true))
                .sorted(Comparator.comparing(Livro::getPreco).reversed())
                .map(livro -> new ResponseLivroIdDTO(
                    livro.getId(),livro.getTitulo(),livro.getAutor(),livro.getCategoria(),livro.getPaginas(),
                    livro.getDisponivel(), livro.getPreco())).toList();
    }










}
