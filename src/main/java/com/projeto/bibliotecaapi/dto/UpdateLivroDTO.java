package com.projeto.bibliotecaapi.dto;

import java.math.BigDecimal;

public record UpdateLivroDTO(String titulo, String autor, String categoria, Integer paginas, Boolean disponivel,
                             BigDecimal preco) {
}
