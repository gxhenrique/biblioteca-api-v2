# 📚 Biblioteca API v2

API REST desenvolvida com Java e Spring Boot para gerenciamento de livros, autores e categorias.

Esta é a **versão 2** do projeto Biblioteca API, criada como evolução da primeira versão. O objetivo desta etapa é aprofundar os conhecimentos em Spring Boot, Spring Data JPA, Hibernate e desenvolvimento de APIs REST.

---

## 🚀 Tecnologias utilizadas

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- Bean Validation
- PostgreSQL
- H2
- Maven
- Git e GitHub
- Postman

---

## 🎯 Objetivos da versão 2

Nesta versão, o projeto será evoluído para trabalhar com uma estrutura de dados mais próxima de uma aplicação real.

Principais objetivos:

- Criar entidades relacionadas
- Trabalhar com relacionamentos JPA
- Utilizar `@ManyToOne` e `@OneToMany`
- Criar CRUD de autores e categorias
- Utilizar Query Methods do Spring Data
- Criar consultas com JPQL e `@Query`
- Trabalhar com filtros
- Combinar filtros, paginação e ordenação
- Utilizar DTOs para entidades relacionadas
- Melhorar validações
- Criar testes automatizados

---

## 🏗️ Estrutura do projeto

A aplicação segue uma arquitetura baseada em camadas:

```text
src
└── main
    └── java
        └── com.projeto.bibliotecaapi
            ├── controller
            ├── service
            ├── repository
            ├── entity
            ├── dto
            └── exception
