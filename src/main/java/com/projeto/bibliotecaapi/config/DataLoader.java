package com.projeto.bibliotecaapi.config;


import com.projeto.bibliotecaapi.entity.Autor;
import com.projeto.bibliotecaapi.entity.Categoria;
import com.projeto.bibliotecaapi.entity.Livro;
import com.projeto.bibliotecaapi.repository.AutorRepository;
import com.projeto.bibliotecaapi.repository.CategoriaRepository;
import com.projeto.bibliotecaapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private LivroRepository livroRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private AutorRepository autorRepository;

    @Override
    public void run(String... args) throws Exception {
        if(livroRepository.count() > 0){
            return;
        }

        BufferedReader br = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream("/livros.csv")));

        String linha;

        br.readLine();

        while ((linha = br.readLine()) != null){

            String[] dados = linha.split(";");

            Autor autor = buscarOuCriarAutor(dados[1]);

            Categoria categoria = buscarOuCriarCategoria(dados[2]);

            Livro livro = new Livro();

            livro.setTitulo(dados[0]);
            livro.setAutor(autor);
            livro.setCategoria(categoria);
            livro.setPaginas(Integer.parseInt(dados[3]));
            livro.setDisponivel(Boolean.parseBoolean(dados[4]));
            livro.setPreco(new BigDecimal(dados[5]));

            livroRepository.save(livro);

        }

        br.close();
    }

    private Categoria buscarOuCriarCategoria(String dado) {

        Optional<Categoria> categoriaAtual = categoriaRepository.findByNome(dado);

        if(categoriaAtual.isPresent()){
            return categoriaAtual.get();
        }

        Categoria novaCategoria  = new Categoria();
        novaCategoria.setNome(dado);
        return categoriaRepository.save(novaCategoria);
    }

    private Autor buscarOuCriarAutor(String dado) {

        Optional<Autor> autorAtual = autorRepository.findByNome(dado);

        if(autorAtual.isPresent()){
            return autorAtual.get();
        }

        Autor novoAutor = new Autor();
        novoAutor.setNome(dado);

        return autorRepository.save(novoAutor);
    }
}
