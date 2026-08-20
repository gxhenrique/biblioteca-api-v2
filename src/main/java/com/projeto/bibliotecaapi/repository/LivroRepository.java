package com.projeto.bibliotecaapi.repository;

import com.projeto.bibliotecaapi.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {


}
