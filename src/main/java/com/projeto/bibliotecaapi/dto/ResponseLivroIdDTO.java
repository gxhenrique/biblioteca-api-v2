package com.projeto.bibliotecaapi.dto;

import java.math.BigDecimal;

public record ResponseLivroIdDTO(Long id,String titulo, String autor, String categoria, Integer paginas, Boolean disponivel,
                                 BigDecimal preco) {
}
