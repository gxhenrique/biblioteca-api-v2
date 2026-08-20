package com.projeto.bibliotecaapi.config;


import com.projeto.bibliotecaapi.entity.Livro;
import com.projeto.bibliotecaapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private LivroRepository repository;

    @Override
    public void run(String... args) throws Exception {
        if(repository.count() > 0){
            return;
        }

        BufferedReader br = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream("/livros.csv")));

        String linha;

        br.readLine();

        while ((linha = br.readLine()) != null){

            String[] dados = linha.split(";");

            Livro livro = new Livro();

            livro.setTitulo(dados[0]);
            livro.setAutor(dados[1]);
            livro.setCategoria(dados[2]);
            livro.setPaginas(Integer.parseInt(dados[3]));
            livro.setDisponivel(Boolean.parseBoolean(dados[4]));
            livro.setPreco(new BigDecimal(dados[5]));

            repository.save(livro);

        }

        br.close();
    }
}
