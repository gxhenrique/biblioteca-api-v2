package com.projeto.bibliotecaapi.dto;

import java.math.BigDecimal;

public record CreateLivroDTO(String titulo, String autor, String categoria, Integer paginas,
                             BigDecimal preco) {
}
