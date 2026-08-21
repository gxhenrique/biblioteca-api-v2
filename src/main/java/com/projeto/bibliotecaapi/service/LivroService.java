package com.projeto.bibliotecaapi.service;

import com.projeto.bibliotecaapi.dto.livroCrudDTO.*;
import com.projeto.bibliotecaapi.entity.Autor;
import com.projeto.bibliotecaapi.entity.Categoria;
import com.projeto.bibliotecaapi.entity.Livro;
import com.projeto.bibliotecaapi.repository.AutorRepository;
import com.projeto.bibliotecaapi.repository.CategoriaRepository;
import com.projeto.bibliotecaapi.repository.LivroRepository;
import com.projeto.bibliotecaapi.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    // 1. CRUD de livros

    public List<ResponseLivroDTO> findAll(){
        return livroRepository.findAll().stream()
                .map(livro -> new ResponseLivroDTO(livro.getTitulo(),
                        livro.getAutor().getNome(), livro.getCategoria().getNome(),
                        livro.getPaginas(), livro.getDisponivel(),livro.getPreco())).toList();
    }



    public ResponseLivroIdDTO findById(Long id){
        return livroRepository.findById(id)
                .map(livro -> new ResponseLivroIdDTO(livro.getId(),livro.getTitulo(),
                        livro.getAutor().getNome(), livro.getCategoria().getNome(),
                        livro.getPaginas(), livro.getDisponivel(),livro.getPreco())).orElseThrow(() ->
                        new EntityNotFoundException("Entity not found " + "id: " + id));
    }

    public ResponseLivroIdDTO create(CreateLivroDTO dto){
         Livro livro =  livroCreated(dto);
         livroRepository.save(livro);
         return new ResponseLivroIdDTO(livro.getId(),livro.getTitulo(),livro.getAutor().getNome(),livro.getCategoria().getNome(),livro.getPaginas(),
         livro.getDisponivel(), livro.getPreco());
    }

    private  Livro livroCreated(CreateLivroDTO dto){
        Boolean disponivel = true;
        Livro livro = new Livro();


        Optional<Autor> autor = autorRepository.findByNome(dto.autor());
        Optional<Categoria> categoria = categoriaRepository.findByNome(dto.categoria());

        livro.setAutor(autor.get());
        livro.setCategoria(categoria.get());

        livro.setTitulo(dto.titulo());
        livro.setDisponivel(disponivel);
        livro.setPaginas(dto.paginas());
        livro.setPreco(dto.preco());

        return livro;
    }

    public ResponseLivroDTO update(Long id, UpdateLivroDTO dto){
        Livro entity = livroRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));
        updateLivro(entity, dto);
        livroRepository.save(entity);

        return new ResponseLivroDTO(entity.getTitulo(),entity.getAutor().getNome(),entity.getCategoria().getNome(),entity.getPaginas(),
                entity.getDisponivel(),entity.getPreco());
    }



    private void updateLivro(Livro entity, UpdateLivroDTO dto) {

        Optional<Autor> autor = autorRepository.findByNome(dto.autor());
        Optional<Categoria> categoria = categoriaRepository.findByNome(dto.categoria());

        entity.setTitulo(dto.titulo());
        entity.setPreco(dto.preco());
        entity.setPaginas(dto.paginas());
        entity.setAutor(autor.get());
        entity.setCategoria(categoria.get());
        entity.setDisponivel(dto.disponivel());
    }

    public DeleteLivroDTO delete(Long id){
        Livro livro = livroRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));
        livroRepository.delete(livro);

        return  new DeleteLivroDTO("Livro deletado com sucesso...");
    }

    //Consultas básicas

    public ResponseLivroIdDTO findByNameLivro(String nome){
        return livroRepository.findAll().stream()
                .filter(byName -> byName.getTitulo().equalsIgnoreCase(nome))
                .map(livro -> new ResponseLivroIdDTO(
                        livro.getId(),livro.getTitulo(),livro.getAutor().getNome(),livro.getCategoria().getNome(),livro.getPaginas(),
                        livro.getDisponivel(), livro.getPreco()))
                .findFirst().orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));
    }



    public List<ResponseLivroIdDTO> findByCategory(String categoria){
        return livroRepository.findAll().stream()
                .filter(byCategory -> byCategory.getCategoria().getNome().equalsIgnoreCase(categoria))
                .map(livro -> new ResponseLivroIdDTO(
                        livro.getId(),livro.getTitulo(),livro.getAutor().getNome(),livro.getCategoria().getNome(),livro.getPaginas(),
                        livro.getDisponivel(), livro.getPreco()))
                .toList();

    }

    public List<ResponseLivroIdDTO> findByAuthor(String autor){
        return livroRepository.findAll().stream()
                .filter(byAuthor -> byAuthor.getAutor().getNome().equalsIgnoreCase(autor))
                .map(livro -> new ResponseLivroIdDTO(
                        livro.getId(),livro.getTitulo(),livro.getAutor().getNome(),livro.getCategoria().getNome(),livro.getPaginas(),
                        livro.getDisponivel(), livro.getPreco())).toList();

    }

    public List<ResponseLivroIdDTO> findByAvailable(){
        return livroRepository.findAll().stream()
                .filter(Livro::getDisponivel)
                .map(livro -> new ResponseLivroIdDTO(
                        livro.getId(),livro.getTitulo(),livro.getAutor().getNome(),livro.getCategoria().getNome(),livro.getPaginas(),
                        livro.getDisponivel(), livro.getPreco())).toList();


    }
/*
    //Paginação e ordenação simples

    public PageResponseDTO<ResponseLivroIdDTO> pagination(Pageable pageable){
        Page<Livro> pages = livroRepository.findAll(pageable);

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
        List<Livro> livros = livroRepository.findAll();
        return livros.stream().map(livro -> new ResponseTituloDTO(livro.getTitulo())).toList();
    }

    public List<ResponseLivroIdDTO> listBooksMorePages(){
        return livroRepository.findAll().stream().filter(livro -> livro.getPaginas() > 500)
                .map(livro -> new ResponseLivroIdDTO(
                        livro.getId(),livro.getTitulo(),livro.getAutor(),livro.getCategoria(),livro.getPaginas(),
                        livro.getDisponivel(), livro.getPreco())).toList();
    }

    public List<ResponseLivroIdDTO> listBooksPrice(){

        return livroRepository.findAll().stream()
                .filter(livro -> livro.getPreco().compareTo(BigDecimal.valueOf(40)) > 0)
                .map(livro -> new ResponseLivroIdDTO(
                        livro.getId(),livro.getTitulo(),livro.getAutor(),livro.getCategoria(),livro.getPaginas(),
                        livro.getDisponivel(), livro.getPreco())).toList();

    }

    public List<ResponseLivroIdDTO> listBooksAvailableAndFantasy(){
        return livroRepository.findAll().stream()
                .filter(livro -> livro.getDisponivel().equals(true) && "Fantasia".equals(livro.getDisponivel()))
                .map(livro -> new ResponseLivroIdDTO(
                        livro.getId(),livro.getTitulo(),livro.getAutor(),livro.getCategoria(),livro.getPaginas(),
                        livro.getDisponivel(), livro.getPreco())).toList();
    }

    public ResponseLivroIdDTO bookMoreExpensive(){
        return livroRepository.findAll().stream()
                .max(Comparator.comparing(Livro::getPreco))
                .map(livro -> new ResponseLivroIdDTO(
                        livro.getId(),livro.getTitulo(),livro.getAutor(),livro.getCategoria(),livro.getPaginas(),
                        livro.getDisponivel(), livro.getPreco()))
                .orElseThrow(() -> new EntityNotFoundException("Livro mais caro não encontrado..."));
    }

    public ResponseLivroIdDTO bookCheaper(){
        return livroRepository.findAll().stream()
                .min(Comparator.comparing(Livro::getPreco))
                .map(livro -> new ResponseLivroIdDTO(
                        livro.getId(),livro.getTitulo(),livro.getAutor(),livro.getCategoria(),livro.getPaginas(),
                        livro.getDisponivel(), livro.getPreco()))
                .orElseThrow(() -> new EntityNotFoundException("Livro mais barato não encontrado..."));
    }

    public ResponseAveragePrice booksAveragePrice() {

       BigDecimal soma = BigDecimal.ZERO;
       List<Livro> list = livroRepository.findAll();

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
        Long total = livroRepository.findAll().stream().count();
        return new ResponseTotalQuantity(total);
    }


    // com Map

    public List<ResponseQuatidadeLivroAutor> quatidadeLivroAutor(){

        Map<String,Long> quatidade = livroRepository.findAll().stream()
                .collect(Collectors.groupingBy(Livro::getAutor, Collectors.counting()));

        return quatidade.entrySet().stream()
                .map(entry ->
                        new ResponseQuatidadeLivroAutor(entry.getKey(), entry.getValue()))
                .toList();

    }

    public List<ResponseQuatidadeLivrosCategory> quatidadeLivrosCategory(){
        Map<String,Long> quatidade = livroRepository.findAll().stream()
                .collect(Collectors.groupingBy(Livro::getCategoria, Collectors.counting()));

        return quatidade.entrySet().stream()
                .map(
                        entry -> new ResponseQuatidadeLivrosCategory(entry.getKey(), entry.getValue()))
                .toList();
    }

    public List<ResponseQuatidadeLivrosCategory> quatidadeDisponivelCategory(){
        Map<String, Long> quatidade = livroRepository.findAll().stream()
                .filter(livro -> livro.getDisponivel().equals(true))
                .collect(Collectors.groupingBy(Livro::getCategoria, Collectors.counting()));

        return quatidade.entrySet().stream()
                .map(
                        entry -> new ResponseQuatidadeLivrosCategory(entry.getKey(), entry.getValue()))
                .toList();
    }

    public List<ResponseQuatidadeLivroAutor> top3AutorLivros(){

        Map<String,Long> top3 = livroRepository.findAll().stream()
                .collect(Collectors.groupingBy(Livro::getAutor, Collectors.counting()));

       return top3.entrySet().stream()
               .sorted(Comparator.comparing((Map.Entry<String,Long> teste) -> teste.getValue())
                       .reversed()).limit(3)
               .map(entry -> new ResponseQuatidadeLivroAutor(entry.getKey(), entry.getValue())).toList();


    }

    //combinando operações

    public List<ResponseLivroIdDTO> precoOrder(){
       return livroRepository.findAll().stream()
                .filter(livro -> livro.getDisponivel().equals(true))
                .sorted(Comparator.comparing(Livro::getPreco).reversed())
                .map(livro -> new ResponseLivroIdDTO(
                    livro.getId(),livro.getTitulo(),livro.getAutor(),livro.getCategoria(),livro.getPaginas(),
                    livro.getDisponivel(), livro.getPreco())).toList();
    }






     */





}
