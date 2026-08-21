package com.projeto.bibliotecaapi.dto.livroCrudDTO;

import java.math.BigDecimal;

public record ResponseLivroDTO(String titulo, String autor, String categoria, Integer paginas, Boolean disponivel,
                               BigDecimal preco) {
}
