package com.projeto.bibliotecaapi.repository;

import com.projeto.bibliotecaapi.entity.Livro;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {

   //List<Livro> findByDisponivelTrue();
   List<Livro> findByPaginasGreaterThan(Integer paginas);
   Optional<Livro> findByTituloIgnoreCase(String titulo);
   List<Livro> findByAutorNomeIgnoreCase(String autor);
   List<Livro> findByCategoriaNomeIgnoreCase(String categoria);
   List<Livro> findByPrecoGreaterThan(BigDecimal preco);

   @Query("Select l from Livro l where l.disponivel = true")
   List<Livro> buscarPorDispovivel();

   @Query("SELECT l.categoria.nome, COUNT(1) FROM Livro l WHERE l.disponivel = true GROUP BY l.categoria.nome")
   List<Object[]> quantidadeLivrosDisponiveisPorCategoria();

   @Query("SELECT l FROM Livro l where l.disponivel = true and l.categoria.nome = 'Fantasia' ")
   List<Livro> listaLivroDisponiveisFantasia();

   @Query("SELECT l.autor.nome, COUNT(1) FROM Livro l GROUP BY l.autor.nome ORDER BY COUNT(1) DESC")
   List<Object[]> top3AutoresComMaisLivros(Pageable pageable);

}
